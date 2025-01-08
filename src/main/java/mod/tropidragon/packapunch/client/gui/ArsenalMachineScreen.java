package mod.tropidragon.packapunch.client.gui;

import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.tacz.guns.api.TimelessAPI;
import com.tacz.guns.block.GunSmithTableBlock;
import com.tacz.guns.crafting.GunSmithTableIngredient;
import com.tacz.guns.crafting.GunSmithTableRecipe;
import com.tacz.guns.network.message.ClientMessageCraft;
import it.unimi.dsi.fastutil.ints.Int2IntArrayMap;
import org.apache.commons.compress.utils.Lists;

import mod.tropidragon.packapunch.Divinium;
import mod.tropidragon.packapunch.inventory.ArsenalMachineMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class ArsenalMachineScreen extends AbstractContainerScreen<ArsenalMachineMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Divinium.MODID,
            "textures/gui/arsenal_machine.png");
    private static final ResourceLocation SIDE = new ResourceLocation(Divinium.MODID,
            "textures/gui/arsenal_machine_side.png");

    private final List<String> recipeKeys = Lists.newArrayList();
    private final Map<String, List<ResourceLocation>> recipes = Maps.newHashMap();

    private int typePage;
    private String selectedType;
    private List<ResourceLocation> selectedRecipeList;

    private int indexPage;
    private @Nullable GunSmithTableRecipe selectedRecipe;
    private @Nullable Int2IntArrayMap playerIngredientCount;

    private int scale = 70;

    public ArsenalMachineScreen(ArsenalMachineMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 344;
        this.imageHeight = 186;

        this.typePage = 0;
        this.selectedType = ArsenalMachineResult.GUN;
        this.selectedRecipeList = recipes.get(selectedType);

        this.indexPage = 0;
        // this.selectedRecipe = this.getSelectedRecipe(this.selectedRecipeList.get(0));

        this.getPlayerIngredientCount(this.selectedRecipe);
    }

    public static void drawModCenteredString(PoseStack poseStack, Font font, Component component,
            int pX, int pY, int color) {
        FormattedCharSequence text = component.getVisualOrderText();

        font.draw(poseStack, text, (float) (pX - font.width(text) / 2), (float) pY, color);
    }

    private void classifyRecipes() {
        // putRecipeType
        this.recipeKeys.add("upgrade");
        this.recipeKeys.add("ammo_mod");
    }

    private void putRecipeType(String tabName) {
        this.recipeKeys.add(tabName);
    }

    // public GunSmithTableRecipe getSelectedRecipe(ResourceLocation recipeId) {
    // return TimelessAPI.getAllRecipes().get(recipeId);
    // }

    private void getPlayerIngredientCount(GunSmithTableRecipe recipe) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player == null) {
            return;
        }
        List<GunSmithTableIngredient> ingredients = recipe.getInputs();
        int size = ingredients.size();
        this.playerIngredientCount = new Int2IntArrayMap(size);
        for (int i = 0; i < size; i++) {
            GunSmithTableIngredient ingredient = ingredients.get(i);
            Inventory inventory = player.getInventory();
            int count = 0;
            for (ItemStack stack : inventory.items) {
                if (!stack.isEmpty() && ingredient.getIngredient().test(stack)) {
                    count = count + stack.getCount();
                }
            }
            playerIngredientCount.put(i, count);
        }
    }

    public void updateIngredientCount() {
        if (this.selectedRecipe != null) {
            this.getPlayerIngredientCount(selectedRecipe);
        }
        this.init();
    }

    @Override
    protected void init() {
        super.init();
        this.clearWidgets();
        // this.addTypePageButtons();
        // this.addTypeButtons();
        // this.addIndexPageButtons();
        // this.addIndexButtons();
        // this.addScaleButtons();
        // this.addCraftButton();
        // this.addUrlButton();
    }

    private void addCraftButton() {
        this.addRenderableWidget(
                // new ImageButton(inventoryLabelX, SLOT_ITEM_BLIT_OFFSET, imageWidth,
                // imageHeight,
                // width, height, BACKGROUND_LOCATION, null)
                new ImageButton(leftPos + 289, topPos + 162, 48, 18, 138, 164, 18, TEXTURE, b -> {
                    if (this.selectedRecipe != null && playerIngredientCount != null) {
                        // 检查是否能合成，不能就不发包
                        List<GunSmithTableIngredient> inputs = selectedRecipe.getInputs();
                        int size = inputs.size();
                        for (int i = 0; i < size; i++) {
                            if (i >= playerIngredientCount.size()) {
                                return;
                            }
                            int hasCount = playerIngredientCount.get(i);
                            int needCount = inputs.get(i).getCount();
                            // 拥有数量小于需求数量，不发包
                            if (hasCount < needCount) {
                                return;
                            }
                        }
                        // NetworkHandler.CHANNEL.sendToServer(new ClientMessageCraft(
                        // this.selectedRecipe.getId(), this.menu.containerId));
                    }
                }));
    }

    @Override
    protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
    }

    @Override
    protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
        this.renderBackground(poseStack);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SIDE);
        blit(poseStack, leftPos, topPos, 0, 0, 134, 187);
        RenderSystem.setShaderTexture(0, TEXTURE);
        blit(poseStack, leftPos + 136, topPos + 27, 0, 0, 208, 160);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
