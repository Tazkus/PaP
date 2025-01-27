package mod.tropidragon.packapunch.inventory;

import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.api.item.IGun;
import com.tacz.guns.item.ModernKineticGunItem;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import mod.tropidragon.packapunch.block.entity.ArsenalMachineBlockEntity;
import mod.tropidragon.packapunch.common.Pap;
import mod.tropidragon.packapunch.network.NetworkHandler;
import mod.tropidragon.packapunch.network.message.ServerMessageUpgrade;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.extensions.IForgeMenuType;
// import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ArsenalMachineMenu extends AbstractContainerMenu {

    public static final MenuType<ArsenalMachineMenu> TYPE = IForgeMenuType
            .create((windowId, inv, data) -> new ArsenalMachineMenu(windowId, inv, inv.player, data.readBlockPos()));

    private Player playerEntity;
    private IItemHandler playerInventory;
    private final Level level;
    private final BlockEntity blockEntity;

    public ArsenalMachineMenu(int id, Inventory inventory, Player player, BlockPos pos) {
        super(TYPE, id);
        checkContainerSize(inventory, 1);
        BlockEntity be = player.getCommandSenderWorld().getBlockEntity(pos);
        blockEntity = be;
        this.level = inventory.player.level();
        this.playerEntity = player;

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 80, 38));
        });

        addPlayerInventory(inventory);
        addPlayerHotbar(inventory);
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemStack = stack.copy();

            if (index == 0) {
                if (!this.moveItemStackTo(stack, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(stack, itemStack);
            } else {
                IGun iGun = IGun.getIGunOrNull(stack);
                if (iGun instanceof ModernKineticGunItem) {
                    if (!this.moveItemStackTo(stack, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 28) {
                    if (!this.moveItemStackTo(stack, 28, 37, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (stack.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, stack);
        }

        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    // 武器升级
    public int getWeaponRarityLevel() {
        ArsenalMachineBlockEntity be = ((ArsenalMachineBlockEntity) blockEntity);
        if (ArsenalMachineBlockEntity.hasGunInWeaponSlot(be)) {
            int rarityLvl = ArsenalMachineBlockEntity.getWeaponRarityLevel(be);
            return rarityLvl;
        }
        return -1;
    }

    public void doUpgrade(Player player) {
        ArsenalMachineBlockEntity be = ((ArsenalMachineBlockEntity) blockEntity);
        if (player == null)
            return;

        if (be != null && ArsenalMachineBlockEntity.hasGunInWeaponSlot(be)) {
            // 升级条件
            int lvl = this.getWeaponRarityLevel();
            if (!Pap.upgradable(0, lvl))
                return;
            Item item = Pap.getRarityUpgradeItem(lvl).getItem();
            int needCount = Pap.getRarityUpgradeCost(lvl);

            // 扣除材料
            player.getCapability(ForgeCapabilities.ITEM_HANDLER, null).ifPresent(handler -> {
                if (PlayerInventoryUtil.consumePlayerItem(handler, item, needCount)) {
                    ArsenalMachineBlockEntity.upgradeWeapon(be);
                    player.inventoryMenu.broadcastFullState();
                    NetworkHandler.sendToClientPlayer(new ServerMessageUpgrade(this.containerId, lvl + 1), player);
                }
            });
        }
    }
}
