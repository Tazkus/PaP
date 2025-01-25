package mod.tropidragon.packapunch.inventory;

import iskallia.vault.init.ModItems;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import mod.tropidragon.packapunch.block.entity.RetrofitMachineBlockEntity;
import mod.tropidragon.packapunch.common.Pap;
import mod.tropidragon.packapunch.compat.vault.VaultCompat;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class PlayerInventoryUtil {

    public static int getPlayerIngredientCount(Player playerEntity, Item item) {
        if (playerEntity == null) {
            return -1;
        }
        int count = 0;

        Inventory inventory = playerEntity.getInventory();
        for (ItemStack stack : inventory.items) {
            if (!stack.isEmpty() && stack.getItem() == item) {
                count = count + stack.getCount();
            }
        }

        return count;
    }

    public static boolean consumePlayerItem(IItemHandler handler, Item item, int needCount) {
        if (VaultCompat.INSTALLED && item == ModItems.SOUL_SHARD) {
            return VaultCompat.consumePlayerSoulShard(handler, needCount);
        }

        int hasCount = 0;
        Int2IntArrayMap recordCount = new Int2IntArrayMap();
        for (int slotIndex = 0; slotIndex < handler.getSlots(); slotIndex++) {
            ItemStack stack = handler.getStackInSlot(slotIndex);
            int stackCount = stack.getCount();
            if (!stack.isEmpty() && stack.getItem() == item) {
                hasCount = hasCount + stackCount;
                // 记录该 slot 的材料数量
                if (hasCount <= needCount) {
                    // 如果数量不足，全扣
                    recordCount.put(slotIndex, stackCount);
                } else {
                    // 数量够了，只扣需要的数量
                    int remaining = hasCount - needCount;
                    recordCount.put(slotIndex, stackCount - remaining);
                    break;
                }
            }
        }
        if (hasCount >= needCount) {
            // 扣除材料
            for (int slotIndex : recordCount.keySet()) {
                handler.extractItem(slotIndex, recordCount.get(slotIndex), false);
            }
            return true;
        }
        return false;
    }

}
