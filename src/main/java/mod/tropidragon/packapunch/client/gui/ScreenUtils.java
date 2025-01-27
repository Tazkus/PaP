package mod.tropidragon.packapunch.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class ScreenUtils {

    public static void drawModCenteredString(GuiGraphics gui, Font font, Component component, int pX, int pY,
            int color) {
        FormattedCharSequence text = component.getVisualOrderText();
        gui.drawString(font, text, (pX - font.width(text) / 2), pY, color);
    }
}
