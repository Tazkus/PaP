package mod.tropidragon.packapunch.client.gui;

import net.minecraft.world.item.ItemStack;

public class ArsenalMachineResult {
    public static final String GUN = "gun";
    public static final String AMMO_MOD = "ammo";
    // public static final String ATTACHMENT = "attachment";

    private final ItemStack result;
    private final String group;

    public ArsenalMachineResult(ItemStack result, String group) {
        this.result = result;
        this.group = group;
    }

    public ItemStack getResult() {
        return result;
    }

    public String getGroup() {
        return group;
    }
}
