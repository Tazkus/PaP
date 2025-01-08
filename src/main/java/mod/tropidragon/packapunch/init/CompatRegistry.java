package mod.tropidragon.packapunch.init;

import mod.tropidragon.packapunch.config.cloth.ClothConfigScreen;
import mod.tropidragon.packapunch.config.cloth.MenuIntegration;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.loading.FMLEnvironment;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class CompatRegistry {
    public static final String CLOTH_CONFIG = "cloth_config";

    @SubscribeEvent
    public static void onEnqueue(final InterModEnqueueEvent event) {
        // event.enqueueWork(() -> checkModLoad(CLOTH_CONFIG, () -> {
        // if (FMLEnvironment.dist == Dist.CLIENT) {
        // MenuIntegration.registerModsPage();
        // }
        // }));
        event.enqueueWork(() -> checkModLoad(CLOTH_CONFIG,
                () -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> MenuIntegration::registerModsPage)));
        // event.enqueueWork(() -> {
        // if (FMLEnvironment.dist == Dist.CLIENT) {
        // ClothConfigScreen.registerNoClothConfigPage();
        // }
        // });
        event.enqueueWork(
                () -> DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ClothConfigScreen::registerNoClothConfigPage));
    }

    private static void checkModLoad(String modId, Runnable runnable) {
        if (ModList.get().isLoaded(modId)) {
            runnable.run();
        }
    }
}
