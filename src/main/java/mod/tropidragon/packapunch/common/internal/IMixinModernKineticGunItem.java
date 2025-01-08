package mod.tropidragon.packapunch.common.internal;

import net.minecraft.world.item.ItemStack;

public interface IMixinModernKineticGunItem {

    int getPaPLevel(ItemStack gunItem);

    int getRarityLevel(ItemStack gunItem);

    void setPaPLevel(ItemStack gunItem, int level);
    
    void setRarityLevel(ItemStack gunItem, int level);
}
