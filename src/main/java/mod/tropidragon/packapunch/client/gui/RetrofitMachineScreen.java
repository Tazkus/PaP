package mod.tropidragon.packapunch.client.gui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.GunMod;

import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.common.Pap;
import mod.tropidragon.packapunch.inventory.RetrofitMachineMenu;
import mod.tropidragon.packapunch.network.NetworkHandler;
import mod.tropidragon.packapunch.network.message.ClientMessageUpgrade;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import net.minecraft.world.item.ItemStack;

public class RetrofitMachineScreen extends AbstractContainerScreen<RetrofitMachineMenu> {

    private final ResourceLocation GUI = new ResourceLocation(Divinium.MODID,
            "textures/gui/retrofit_machine_gui.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation(GunMod.MOD_ID,
            "textures/gui/gun_smith_table.png");

    private final List<String> recipeKeys = Lists.newArrayList();
    private @Nullable Int2IntArrayMap playerIngredientCount;

    public RetrofitMachineScreen(RetrofitMachineMenu menu, Inventory inv, Component name) {
        super(menu, inv, name);
        this.imageWidth = 176;
        this.imageHeight = 168;
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();
        this.addCraftButton();
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);

        // 绘制强化方案
        int level = this.menu.getWeaponPapLevel();
        if (Pap.upgradable(level, 0)) {
            // blit(pPoseStack, x + 102, y + 41, 176, 0, 8, menu.getScaledProgress());
            this.renderIngredient(matrixStack, level);
        } else {
            // 按钮文本
            drawCenteredString(matrixStack, font,
                    Component.translatable("gui.papckapunch.retrofit_machine.upgrade"),
                    leftPos + 88, topPos + 60 + 5, 0xFFFFFF);
        }
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        // drawString(matrixStack, Minecraft.getInstance().font, "Energy: " +
        // menu.getEnergy(), 10, 10, 0xffffff);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI);

        // 左上角
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        this.blit(matrixStack, x, y, 0, 0, this.imageWidth, this.imageHeight);
        // PAP招牌
        this.blit(matrixStack, x, y - 33 - 2, 0, 169, this.imageWidth, 33);
        // 绘制进度条
        // this.blit(matrixStack, x + 102, y + 41, 176, 0, 8, menu.getScaledProgress());
    }

    private void addCraftButton() {
        this.addRenderableWidget(
                new ImageButton(leftPos + 88 - 24, topPos + 60, 48, 18, 138, 164, 18, TEXTURE,
                        // Component.literal("Upgrade"),
                        b -> {
                            NetworkHandler.CHANNEL.sendToServer(new ClientMessageUpgrade(this.menu.containerId));
                        }));
    }

    private void renderIngredient(PoseStack poseStack, int weaponLevel) {
        int offsetX = leftPos + 80;
        int offsetY = topPos + 60 + 1;

        ItemStack item = Pap.getPapUpgradeItem(weaponLevel);
        int count = Pap.getPapUpgradeCost(weaponLevel);

        this.itemRenderer.renderAndDecorateFakeItem(item, offsetX, offsetY);

        poseStack.pushPose();
        poseStack.translate(0, 0, 200);
        poseStack.scale(0.5f, 0.5f, 1);

        int color = 0xFFFFFF;
        font.draw(poseStack, String.format("%d", count), (offsetX + 4) * 2, (offsetY + 10) * 2, color);
    }
}
