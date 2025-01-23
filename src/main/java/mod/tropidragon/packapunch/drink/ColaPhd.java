package mod.tropidragon.packapunch.drink;

import mod.tropidragon.packapunch.init.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ColaPhd extends PerkCola {
    public ColaPhd() {
        super((new FoodProperties.Builder())
                .nutrition(2)
                .saturationMod(0.1F)
                .effect(() -> new MobEffectInstance(ModEffects.PHD.get(), EFFECT_DURATION, 0), 1.0F)
                .build(),
                "cola_phd");
    }

    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pEntityLiving) {
        super.finishUsingItem(pStack, pLevel, pEntityLiving);

        if (!pLevel.isClientSide) {
        }

        return pStack;
    }
}
