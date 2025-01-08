package mod.tropidragon.packapunch.init;

import java.rmi.registry.Registry;

import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.effect.DeathShooter;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,
            Divinium.MODID);

    public static final RegistryObject<MobEffect> DEATH_SHOOTER = MOB_EFFECTS.register("death_shooter",
            () -> new DeathShooter(MobEffectCategory.BENEFICIAL, 0));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
