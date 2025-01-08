package mod.tropidragon.packapunch.config;

import mod.tropidragon.packapunch.config.subconfig.PapConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public final class CommonConfig {
    public static ForgeConfigSpec init() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        PapConfig.init(builder);
        return builder.build();
    }
}
