package mod.tropidragon.packapunch.effect;

import com.tacz.guns.api.event.common.EntityHurtByGunEvent;

import mod.tropidragon.packapunch.init.ModEffects;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class DeadshotEffect extends MobEffect {

    public static final float HEADSHOT_MULTIPLIER = 1.25F;

    public DeadshotEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onShot(EntityHurtByGunEvent.Pre event) {
        // DMG_CAPTURE_CACHE.remove(event.getHurtEntity());
        if (event.getLogicalSide().isClient()) {
            return;
        }

        if (event.getAttacker().hasEffect(ModEffects.DEADSHOT.get())) {

            event.setHeadshotMultiplier(event.getHeadshotMultiplier() *
                    HEADSHOT_MULTIPLIER);
            // if (event.isHeadShot()) {
            // event.setBaseAmount(HEADSHOT_MULTIPLIER * event.getBaseAmount());
            // }
        }
    }
}
