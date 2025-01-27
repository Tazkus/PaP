package mod.tropidragon.packapunch.client.gui;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.GunMod;
import com.tacz.guns.client.gui.components.smith.TypeButton;
import com.tacz.guns.init.ModCreativeTabs;

import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.common.Pap;
import mod.tropidragon.packapunch.init.ModItems;
import mod.tropidragon.packapunch.inventory.ArsenalMachineMenu;
import mod.tropidragon.packapunch.network.NetworkHandler;
import mod.tropidragon.packapunch.network.message.ClientMessageUpgrade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

public class ArsenalMachineScreen extends AbstractContainerScreen<ArsenalMachineMenu> {
    private static final ResourceLocation GUI = new ResourceLocation(Divinium.MODID,
            "textures/gui/arsenal_machine_gui.png");
    private static final ResourceLocation TEXTURE = new ResourceLocation(GunMod.MOD_ID,
            "textures/gui/gun_smith_table.png");

    public ArsenalMachineScreen(ArsenalMachineMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 168;

        typePage = 0;
        this.recipeKeys.add("rifle");
        this.recipeKeys.add("ammo");
        this.recipeKeys.add("extended_mag");
        this.recipeKeys.add("extended_mag");
    }

    // 多页菜单
    private static int PAGE_COUNT = 4;
    private int typePage;
    private String selectedType;
    private final List<String> recipeKeys = Lists.newArrayList();

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();
        this.addCraftButton();
        this.addPageButtons();
        // this.addTypePageButtons();
        // this.addTypeButtons();
        // this.addIndexPageButtons();
        // this.addIndexButtons();
        // this.addScaleButtons();
        // this.addCraftButton();
        // this.addUrlButton();
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTicks);
        this.renderTooltip(gui, mouseX, mouseY);

        this.renderPageIcon(gui);

        int level = this.menu.getWeaponRarityLevel();
        if (Pap.upgradable(0, level)) {
            this.renderIngredient(gui, level);
        } else {
            gui.drawCenteredString(font,
                    Component.translatable("gui.papckapunch.arsenal_machine.upgrade"),
                    leftPos + 88, topPos + 60 + 5, 0xFFFFFF);
        }
    }

    @Override
    protected void renderLabels(GuiGraphics poseStack, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        // RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI);
        RenderSystem.enableDepthTest();

        // 左上角
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        gui.blit(GUI, x, y, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    //
    private void addCraftButton() {
        this.addRenderableWidget(
                new ImageButton(leftPos + 88 - 24, topPos + 60, 48, 18, 138, 164, 18, TEXTURE,
                        // Component.literal("Upgrade"),
                        b -> {
                            NetworkHandler.CHANNEL.sendToServer(new ClientMessageUpgrade(this.menu.containerId));
                        }));
    }

    private void addPageButtons() {
        for (int i = 0; i < PAGE_COUNT; i++) {
            int typeIndex = i;

            int pXTexStart = i == typePage ? 0 : 26;
            this.addRenderableWidget(
                    new ImageButton(leftPos + i * 26, topPos - 25, 24, 25, pXTexStart, 204, 25, TEXTURE,
                            b -> {
                                this.typePage = typeIndex;
                                this.init();
                            }));
        }
    }

    private void renderPageIcon(GuiGraphics gui) {
        // poseStack.pushPose();
        // poseStack.translate(0, 0, 200);

        for (int i = 0; i < PAGE_COUNT; i++) {
            int offsetX = leftPos + i * 26 + 4;
            int offsetY = topPos - 26 + 5;

            String type = recipeKeys.get(i);
            // ResourceLocation tabId = new ResourceLocation(GunMod.MOD_ID, type);
            CreativeModeTab modTab = getGunModTab(i);
            ItemStack icon = ItemStack.EMPTY;
            if (modTab != null) {
                icon = modTab.getIconItem();
            }

            // Ingredient ingredient = Ingredient.of(Items.ACACIA_BOAT);
            // ItemStack[] items = ingredient.getItems();
            // icon = items[0];

            // Minecraft mc = Minecraft.getInstance();
            // mc.getItemRenderer().renderGuiItem(icon, offsetX, offsetY);
            gui.renderFakeItem(icon, offsetX, offsetY);
        }
    }

    private static CreativeModeTab getGunModTab(int tabId) {
        switch (tabId) {
            case 0:
                return ModCreativeTabs.GUN_RIFLE_TAB.get();
            case 1:
                return ModCreativeTabs.ATTACHMENT_EXTENDED_MAG_TAB.get();
            default:
                return ModCreativeTabs.ATTACHMENT_EXTENDED_MAG_TAB.get();
        }
    }

    private void renderIngredient(GuiGraphics gui, int weaponLevel) {
        int offsetX = leftPos + 80;
        int offsetY = topPos + 60 + 1;

        ItemStack item = Pap.getRarityUpgradeItem(weaponLevel);
        int count = Pap.getRarityUpgradeCost(weaponLevel);

        gui.renderFakeItem(item, offsetX, offsetY);

        PoseStack poseStack = gui.pose();
        poseStack.pushPose();
        poseStack.translate(0, 0, 200);
        poseStack.scale(0.5f, 0.5f, 1);

        int color = 0xFFFFFF;
        gui.drawCenteredString(font, String.format("%d", count), (offsetX + 4) * 2, (offsetY + 10) * 2, color);
        poseStack.popPose();
    }
}
