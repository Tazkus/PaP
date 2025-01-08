package mod.tropidragon.packapunch.inventory;

import com.tacz.guns.api.TimelessAPI;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;

public class ArsenalMachineMenu extends AbstractContainerMenu {
    public static final MenuType<ArsenalMachineMenu> TYPE = IForgeMenuType
            .create((windowId, inv, data) -> new ArsenalMachineMenu(windowId, inv));

    public ArsenalMachineMenu(int id, Inventory inventory) {
        super(TYPE, id);
    }

    @Override
    public boolean stillValid(Player player) {
        return player.isAlive();
    }

    //
    public void doCraft(ResourceLocation recipeId, Player player) {

    }
}
