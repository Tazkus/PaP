package mod.tropidragon.packapunch.init;

import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.inventory.ArsenalMachineMenu;
import mod.tropidragon.packapunch.inventory.RetrofitMachineMenu;

import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModContainer {
        public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister
                        .create(ForgeRegistries.CONTAINERS, Divinium.MODID);

        public static final RegistryObject<MenuType<ArsenalMachineMenu>> ARSENAL_MACHINE_MENU = CONTAINERS
                        .register("arsenal_machine_menu", () -> ArsenalMachineMenu.TYPE);

        public static final RegistryObject<MenuType<RetrofitMachineMenu>> RETROFIT_MACHINE_MENU = CONTAINERS
                        .register("retrofit_machine_menu", () -> RetrofitMachineMenu.TYPE);
}
