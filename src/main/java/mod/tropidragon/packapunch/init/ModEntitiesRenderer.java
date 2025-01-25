package mod.tropidragon.packapunch.init;

import mod.tropidragon.packapunch.block.entity.ArsenalMachineBlockEntity;
import mod.tropidragon.packapunch.renderer.ArsenalMachineRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntitiesRenderer {
    @SubscribeEvent
    public static void onEntityRenderers(EntityRenderersEvent.RegisterRenderers evt) {
        BlockEntityRenderers.register(ArsenalMachineBlockEntity.TYPE, ArsenalMachineRenderer::new);
    }
}
