package mod.tropidragon.packapunch.mixin.tacz;

import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import com.tacz.guns.resource.pojo.data.gun.GunData;
import com.tacz.guns.util.AttachmentDataUtils;
import mod.tropidragon.packapunch.common.Pap;
import net.minecraft.world.item.ItemStack;
import com.tacz.guns.client.tooltip.ClientGunTooltip;

@Mixin(AttachmentDataUtils.class)
public class MixinAttachmentDataUtils {

    // 无效，因为Capture的相当于一个参数，无法修改到方法中的变量
    // @Inject(method = "getDamageWithAttachment", at = @At("RETURN"),
    // locals = LocalCapture.CAPTURE_FAILHARD, remap = false)
    // private static void applyLeveledDamageModifier(ItemStack gunItem, GunData
    // gunData,
    // CallbackInfoReturnable<Double> cir, @Local(ordinal = 1) float finalBase) {
    // finalBase = finalBase * Pap.getDamageModifier(gunItem);
    // }
}
