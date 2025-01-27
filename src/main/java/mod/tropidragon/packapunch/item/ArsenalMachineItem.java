package mod.tropidragon.packapunch.item;

import java.util.function.Consumer;

import com.tacz.guns.GunMod;
import com.tacz.guns.client.renderer.item.GunSmithTableItemRenderer;
import com.tacz.guns.init.ModCreativeTabs;

import mod.tropidragon.packapunch.init.ModBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

public class ArsenalMachineItem extends BlockItem {
    public ArsenalMachineItem() {
        super(ModBlocks.ARSENAL_MACHINE.get(),
                (new Item.Properties()).stacksTo(64)
                        .tab(ModCreativeTabs.getModTabs(new ResourceLocation(GunMod.MOD_ID, "other"))));

    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                Minecraft minecraft = Minecraft.getInstance();
                return new GunSmithTableItemRenderer(minecraft.getBlockEntityRenderDispatcher(),
                        minecraft.getEntityModels());
            }
        });
    }
}
