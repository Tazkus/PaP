package mod.tropidragon.packapunch.mixin.tacz;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.tacz.guns.entity.EntityKineticBullet;
import com.tacz.guns.resource.pojo.data.gun.BulletData;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import mod.tropidragon.packapunch.common.Pap;
import mod.tropidragon.packapunch.common.internal.IMixinEntityKineticBullet;

import net.minecraft.world.level.Level;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

// 修改实例子弹的伤害
@Mixin(EntityKineticBullet.class)
public class MixinEntityKineticBullet implements IMixinEntityKineticBullet {

    private float leveledDamageModifier;

    // 创建子弹时，记录武器的伤害倍率
    @Inject(method = "<init>(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;ZLcom/tacz/guns/resource/pojo/data/gun/GunData;Lcom/tacz/guns/resource/pojo/data/gun/BulletData;)V", at = @At("TAIL"), remap = false)
    public void setLeveledDamageModifier(Level worldIn, LivingEntity throwerIn, ItemStack gunItem,
            ResourceLocation ammoId, ResourceLocation gunId, boolean isTracerAmmo, GunData gunData,
            BulletData bulletData, CallbackInfo ci) {

        this.leveledDamageModifier = Pap.getDamageModifier(gunItem);
    }

    // 结算伤害倍率
    @ModifyReturnValue(method = "getDamage", at = @At("RETURN"), remap = false)
    public float applyLeveledDamageModifier(float original) {
        return original * leveledDamageModifier;
    }

}
