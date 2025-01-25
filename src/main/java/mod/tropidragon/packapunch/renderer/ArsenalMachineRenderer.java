package mod.tropidragon.packapunch.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.tacz.guns.block.entity.StatueBlockEntity;
import com.tacz.guns.config.client.RenderConfig;

import mod.tropidragon.packapunch.block.ArsenalMachineBlock;
import mod.tropidragon.packapunch.block.entity.ArsenalMachineBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class ArsenalMachineRenderer implements BlockEntityRenderer<ArsenalMachineBlockEntity> {
    public ArsenalMachineRenderer(BlockEntityRendererProvider.Context context) {

    }

    @Override
    public void render(ArsenalMachineBlockEntity blockEntity, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn) {

        Level level = blockEntity.getLevel();
        if (level == null) {
            return;
        }

        poseStack.pushPose();
        {
            BlockState blockState = blockEntity.getBlockState();
            Direction facing = blockState.getValue(ArsenalMachineBlock.FACING);

            poseStack.translate(0.5, 1, 0.5); // center
            poseStack.scale(0.25f, 0.25f, 0.25f);

            poseStack.mulPose(Vector3f.YN.rotationDegrees((facing.get2DDataValue() + 2) % 4 * 90));
            poseStack.mulPose(Vector3f.ZN.rotationDegrees(90));

            float offset = (float) (Math.sin(Util.getMillis() / 500.0));
            poseStack.translate(0, 0.4, 1.4); // left & back

            // render stored gun
            ItemStack stack = blockEntity.getGunItem();
            Minecraft.getInstance().getItemRenderer().renderStatic(stack, ItemTransforms.TransformType.FIXED,
                    LightTexture.pack(15, 15), OverlayTexture.NO_OVERLAY, poseStack, bufferIn, 0);
        }
        poseStack.popPose();
    }

    @Override
    public int getViewDistance() {
        return RenderConfig.TARGET_RENDER_DISTANCE.get();
    }

    @Override
    public boolean shouldRenderOffScreen(ArsenalMachineBlockEntity blockEntity) {
        return true;
    }

    @Override
    public boolean shouldRender(ArsenalMachineBlockEntity pBlockEntity, Vec3 pCameraPos) {
        return Vec3.atCenterOf(pBlockEntity.getBlockPos().above()).closerThan(pCameraPos, this.getViewDistance());
    }
}
