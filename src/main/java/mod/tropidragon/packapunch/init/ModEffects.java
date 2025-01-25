package mod.tropidragon.packapunch.init;

import java.awt.Color;
import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.effect.DeadshotEffect;
import mod.tropidragon.packapunch.effect.PhdEffect;
import mod.tropidragon.packapunch.effect.SpeedEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
        public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(
                        ForgeRegistries.MOB_EFFECTS,
                        Divinium.MODID);

        public static final RegistryObject<MobEffect> DEADSHOT = MOB_EFFECTS.register("deadshot",
                        () -> new DeadshotEffect(MobEffectCategory.BENEFICIAL, Color.ORANGE.getRGB()));
        public static final RegistryObject<MobEffect> PHD = MOB_EFFECTS.register("phd",
                        () -> new PhdEffect(MobEffectCategory.BENEFICIAL, new Color(255, 0, 255).getRGB()));
        public static final RegistryObject<MobEffect> SPEED = MOB_EFFECTS.register("speed",
                        () -> new SpeedEffect(MobEffectCategory.BENEFICIAL, new Color(25, 255, 25).getRGB()));

        public static void register(IEventBus eventBus) {
                MOB_EFFECTS.register(eventBus);
        }
}
