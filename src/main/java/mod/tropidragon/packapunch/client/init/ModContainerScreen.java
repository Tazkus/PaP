package mod.tropidragon.packapunch.client.init;

import mod.tropidragon.packapunch.client.gui.ArsenalMachineScreen;
import mod.tropidragon.packapunch.client.gui.RetrofitMachineScreen;
import mod.tropidragon.packapunch.init.ModBlocks;
import mod.tropidragon.packapunch.init.ModContainer;
import mod.tropidragon.packapunch.inventory.ArsenalMachineMenu;
import mod.tropidragon.packapunch.inventory.RetrofitMachineMenu;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModContainerScreen {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
                // use event.enqueueWork to ensure thread-safety (run in main thread)
                event.enqueueWork(
                                () -> MenuScreens.register(ModContainer.RETROFIT_MACHINE_MENU.get(),
                                                RetrofitMachineScreen::new));
                event.enqueueWork(
                                () -> MenuScreens.register(ModContainer.ARSENAL_MACHINE_MENU.get(),
                                                ArsenalMachineScreen::new));
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.ARSENAL_MACHINE.get(),
                                RenderType.translucent());
                ItemBlockRenderTypes.setRenderLayer(ModBlocks.RETROFIT_MACHINE.get(),
                                RenderType.translucent());
        }

}
