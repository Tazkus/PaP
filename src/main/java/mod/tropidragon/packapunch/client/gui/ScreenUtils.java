package mod.tropidragon.packapunch.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;

import mod.tropidragon.packapunch.common.Pap;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;

public class ScreenUtils {

    public static void drawModCenteredString(PoseStack poseStack, Font font, Component component, int pX, int pY,
            int color) {
        FormattedCharSequence text = component.getVisualOrderText();
        font.draw(poseStack, text, (float) (pX - font.width(text) / 2), (float) pY, color);
    }
}
