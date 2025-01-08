package mod.tropidragon.packapunch.config.cloth;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import net.minecraft.client.Minecraft;

public class ClothConfigCompat {
    public static void openConfigScreen() {
        ConfigBuilder configBuilder = MenuIntegration.getConfigBuilder();
        configBuilder.setGlobalizedExpanded(true);
        Minecraft.getInstance().setScreen(configBuilder.build());
    }
}
