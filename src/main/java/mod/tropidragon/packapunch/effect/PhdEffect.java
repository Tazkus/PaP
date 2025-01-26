package mod.tropidragon.packapunch.effect;

import com.tacz.guns.api.entity.KnockBackModifier;

import mod.tropidragon.packapunch.init.ModEffects;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
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

    private static final float FALL_THRESHOLD = 3F;

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
                float particleSpeed = 0.6f;
                Vec3 parStart = player.position();
                // .add(0, -player.getBbHeight() / 2, 0);
                // Vec3 parEnd = parStart.add(0, nearbyVictim.getBbHeight() / 2, 0);
                // Vec3 dir = parEnd.subtract(parStart).normalize().yRot(distance);
                int amount = getExplosionParticleAmount(distance);
                for (int i = 0; i < amount; i++) {

                    Vec3 dir = new Vec3(0.5, 0, 0).yRot(i / amount * 360);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH,
                            parStart.x, parStart.y, parStart.z,
                            3,
                            dir.x, dir.y, dir.z,
                            0.25f);
                    level.sendParticles(ParticleTypes.DRAGON_BREATH,
                            parStart.x, parStart.y, parStart.z,
                            5,
                            dir.x, dir.y, dir.z,
                            1f);
                }
            }
        }
    }

    private static float RANGE_CAP = 12;

    private static float getExplosionRange(float distance) {
        return Math.min((distance - FALL_THRESHOLD) * 0.5f, RANGE_CAP);
    }

    private static double DAMAGE_CAP = 999;

    private static float getExlosionDamage(float distance) {
        return (float) Math.min(Math.pow((double) distance, 1.5), DAMAGE_CAP);
    }

    private static int getExplosionParticleAmount(float distance) {
        return Math.min(4 * Mth.ceil(distance), 64);
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
