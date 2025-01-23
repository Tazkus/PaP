package mod.tropidragon.packapunch.effect;

import com.tacz.guns.api.entity.KnockBackModifier;

import mod.tropidragon.packapunch.init.ModEffects;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class PhdEffect extends MobEffect {
    public PhdEffect(MobEffectCategory mobEffectCategory, int color) {
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

    private static final float FALL_THRESHOLD = 3.5F;

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerFall(LivingFallEvent event) {
        Entity entity = event.getEntity();
        if (!(event.getEntity().level.isClientSide()) && event.getEntityLiving() instanceof Player player) {
            if (event.getDistance() < FALL_THRESHOLD || !player.hasEffect(ModEffects.PHD.get())) {
                return;
            }

            float distance = event.getDistance();
            // event.setDamageMultiplier(0);
            event.setCanceled(true);

            // sound
            float sfxPitch = player.getRandom().nextFloat(0.8F, 1.25F);
            player.level.playSound(null, player, SoundEvents.GENERIC_EXPLODE,
                    player.getSoundSource(), 1, sfxPitch);

            // damage
            if (player.getLevel() instanceof ServerLevel level) {

                float range = getExplosionRange(distance);
                float damage = getExlosionDamage(distance);
                var targetCondition = TargetingConditions.forCombat().range(range);
                var roughBB = player.getBoundingBox().inflate(range);
                // explosion?
                var source = DamageSource.playerAttack(player);

                for (LivingEntity nearbyVictim : level.getNearbyEntities(
                        LivingEntity.class, targetCondition, player, roughBB)) {
                    //
                    if (nearbyVictim == player) {
                        continue;
                    }

                    if (player.canAttack(nearbyVictim)) {
                        KnockBackModifier.fromLivingEntity(nearbyVictim).setKnockBackStrength(1.5);
                        nearbyVictim.hurt(source, damage);
                    }
                }

                // particle
                float particleSpeed = 1;
                Vec3 parStart = player.position().add(0, player.getBbHeight(), 0);
                // Vec3 parEnd = parStart.add(0, nearbyVictim.getBbHeight() / 2, 0);
                // Vec3 dir = parEnd.subtract(parStart).normalize().yRot(distance);
                int amount = getExplosionParticleAmount(distance);
                for (int i = 0; i < amount; i++) {

                    Vec3 dir = new Vec3(2, 0, 0).yRot(i / amount * 360);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH,
                            parStart.x, parStart.y, parStart.z,
                            2,
                            dir.x, dir.y, dir.z,
                            particleSpeed);
                }
            }
        }
    }

    private static float getExplosionRange(float distance) {
        return (distance - FALL_THRESHOLD) * 0.5f;
    }

    private static float getExlosionDamage(float distance) {
        return distance * 1.0f;
    }

    private static int getExplosionParticleAmount(float distance) {
        if (distance < 10) {
            return 80;
        } else if (distance < 15) {
            return 120;
        } else if (distance < 25) {
            return 160;
        } else if (distance < 50) {
            return 240;
        }
        return 320;
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {

        if (!(event.getEntity().level.isClientSide()) && event.getEntityLiving() instanceof Player player) {
            if (!player.hasEffect(ModEffects.PHD.get())) {
                return;
            }
            DamageSource source = event.getSource();
            Entity entity = source.getEntity();
            if (entity == player) {
                event.setCanceled(true);
            }
        }
    }
}
