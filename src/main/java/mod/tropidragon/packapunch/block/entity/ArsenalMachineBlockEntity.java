package mod.tropidragon.packapunch.block.entity;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import com.tacz.guns.api.item.IGun;
import com.tacz.guns.item.ModernKineticGunItem;

import mod.tropidragon.packapunch.common.internal.IMixinModernKineticGunItem;
import mod.tropidragon.packapunch.init.ModBlocks;
import mod.tropidragon.packapunch.inventory.ArsenalMachineMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;

import static mod.tropidragon.packapunch.block.ArsenalMachineBlock.FACING;

public class ArsenalMachineBlockEntity extends BlockEntity implements MenuProvider {
    // public class ArsenalMachineBlockEntity extends BlockEntity {
    public static final BlockEntityType<ArsenalMachineBlockEntity> TYPE = BlockEntityType.Builder
            .of(ArsenalMachineBlockEntity::new, ModBlocks.ARSENAL_MACHINE.get()).build(null);

    public ArsenalMachineBlockEntity(BlockPos pos, BlockState blockstate) {
        // super(TYPE, pos, blockstate);
        super(ModBlocks.ARSENAL_MACHINE_BE.get(), pos, blockstate);
    }

    // MenuProvider
    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Arsenal Machine");
        // return Component.translatable("");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        BlockPos pos = this.getBlockPos();
        return new ArsenalMachineMenu(id, inventory, player, pos);
    }

    @Override
    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.offset(-2, 0, -2), worldPosition.offset(2, 2, 2));
    }

    // 供电
    public static final int POWER_CAPACITY = 2000;
    public static final int POWER_THRESHOLD = 0;
    private int power;

    // 容器
    // private ItemStack gunItem = ItemStack.EMPTY;
    private final ItemStackHandler itemHandler = createHandler();
    private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);

    @Override
    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("inventory", itemHandler.serializeNBT());
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = super.getUpdateTag();
        tag.put("inventory", itemHandler.serializeNBT());
        return tag;
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("inventory")) {
            itemHandler.deserializeNBT(tag.getCompound("inventory"));
        }
        // if (tag.contains("energy")) {
        // itemHandler.deserializeNBT(tag.getCompound("energy"));
        // }
        // if (tag.contains("info")) {
        // tag.getCompound("info").getInt("Counter");
        // }

        super.load(tag);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        handler.invalidate();
    }

    public void dropItem() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());

        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(level, worldPosition, inventory);

        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
        }
        this.setChanged();
    }

    private ItemStackHandler createHandler() {
        return new ItemStackHandler(1) {
            @Override
            protected void onContentsChanged(int slot) {
                setChanged();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                IGun iGun = IGun.getIGunOrNull(stack);
                if (iGun != null) {
                    return true;
                }
                return false;
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                IGun iGun = IGun.getIGunOrNull(stack);
                if (iGun != null) {
                    return super.insertItem(slot, stack, simulate);
                }
                return stack;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {

        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        return super.getCapability(cap, side);
    }

    public void tickServer() {
        // BlockState blockState = level.getBlockState(worldPosition);
        // if (blockState.getValue(BlockStateProperties.POWERED) != (power >=
        // POWER_THRESHOLD)) {
        // level.setBlock(worldPosition,
        // blockState.setValue(BlockStateProperties.POWERED, power >= POWER_THRESHOLD),
        // Block.UPDATE_ALL);
        // }
    }

    public static boolean hasGunInWeaponSlot(ArsenalMachineBlockEntity entity) {
        return entity.itemHandler.getStackInSlot(0).getItem() instanceof ModernKineticGunItem;
    }

    public ItemStack getGunItem() {
        // return this.gunItem;
        return this.itemHandler.extractItem(0, 1, true);
    }

    public static int getWeaponRarityLevel(ArsenalMachineBlockEntity entity) {
        ItemStack gun = entity.itemHandler.getStackInSlot(0);
        IGun iGun = IGun.getIGunOrNull(gun);
        return ((IMixinModernKineticGunItem) (Object) iGun).getRarityLevel(gun);
    }

    public static void upgradeWeapon(ArsenalMachineBlockEntity entity) {
        ItemStack gun = entity.itemHandler.extractItem(0, 1, false);
        IGun iGun = IGun.getIGunOrNull(gun);

        int curLvl = ((IMixinModernKineticGunItem) (Object) iGun).getRarityLevel(gun);
        ((IMixinModernKineticGunItem) (Object) iGun).setRarityLevel(gun, curLvl + 1);

        // 重新放入升级后的枪械
        entity.itemHandler.insertItem(0, gun, false);
    }

}
