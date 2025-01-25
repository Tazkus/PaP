package mod.tropidragon.packapunch.compat.vault;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.IItemHandler;
import iskallia.vault.init.ModItems;
import iskallia.vault.item.ItemShardPouch;
import mod.tropidragon.packapunch.config.subconfig.PapConfig;

import static iskallia.vault.item.ItemShardPouch.getContainedStack;
import static iskallia.vault.item.ItemShardPouch.setContainedStack;

public class VaultCompat {
    private static final String VAULT_ID = "the_vault";
    public static boolean INSTALLED = false;

    public static void init() {
        if (ModList.get().isLoaded(VAULT_ID)) {
            INSTALLED = true;
        }
    }

    public static ItemStack getPapUpgradeItem(int papLevel) {
        switch (papLevel) {
            default:
                Ingredient ingredient = Ingredient.of(ModItems.SOUL_SHARD);
                ItemStack[] items = ingredient.getItems();
                return items[0];
        }
    }

    public static ItemStack getRarityUpgradeItem(int rarityLevel) {
        switch (rarityLevel) {
            default:
                Ingredient ingredient = Ingredient.of(ModItems.VAULT_SCRAP);
                ItemStack[] items = ingredient.getItems();
                return items[0];
        }
    }

    private static final int SOUL_SHARD_INFLATE = 100;

    public static boolean consumePlayerSoulShard(IItemHandler handler, int needCount) {
        int count = 0;
        needCount *= SOUL_SHARD_INFLATE;
        // Curio

        // Player Inventory
        for (int slotIndex = 0; slotIndex < handler.getSlots(); slotIndex++) {
            ItemStack stack = handler.getStackInSlot(slotIndex);

            if (stack.getItem() instanceof ItemShardPouch) {
                count += getContainedStack(stack).getCount();
            } else if (stack.getItem() == ModItems.SOUL_SHARD) {
                count += stack.getCount();
            }
        }

        if (count >= needCount) {
            for (int slotIndex = 0; slotIndex < handler.getSlots(); slotIndex++) {
                if (needCount <= 0) {
                    break;
                }
                ItemStack stack = handler.getStackInSlot(slotIndex);
                if (stack.getItem() instanceof ItemShardPouch) {
                    ItemStack shardStack = getContainedStack(stack);
                    int toReduce = Math.min(needCount, shardStack.getCount());
                    shardStack.setCount(shardStack.getCount() - toReduce);
                    setContainedStack(stack, shardStack);
                    needCount -= toReduce;
                } else if (stack.getItem() == ModItems.SOUL_SHARD) {
                    int toReduce = Math.min(needCount, stack.getCount());
                    // stack.shrink(toReduce);
                    // playerInventory.setItem(slot, stack);
                    handler.extractItem(slotIndex, toReduce, false);
                    needCount -= toReduce;
                }
            }
            return true;
        }
        return false;
    }
}
