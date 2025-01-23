package mod.tropidragon.packapunch.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

import mod.tropidragon.packapunch.init.ModBlocks;
import mod.tropidragon.packapunch.init.ModItems;

public class ModRecipes extends RecipeProvider {
    public ModRecipes(DataGenerator dataGenerator) {
        super(dataGenerator);

    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder
                .shaped(ModItems.ARTIFICIAL_115.get())
                .pattern("###")
                .pattern("#X#")
                .pattern("###")
                .define('X', Items.DIAMOND_BLOCK)
                .define('#', Items.GHAST_TEAR) // ghast tear
                .group("packapunch")
                .unlockedBy("has_ore", has(Tags.Items.ORES_DIAMOND))
                .save(consumer);

        ShapedRecipeBuilder
                .shaped(ModItems.ELEMENT_115.get())
                .pattern("###")
                .pattern("###")
                .pattern("###")
                .define('#', ModItems.ARTIFICIAL_115.get())
                .group("packapunch")
                .unlockedBy("has_ore", has(Tags.Items.ORES_DIAMOND))
                .save(consumer);

        ShapedRecipeBuilder
                .shaped(ModBlocks.RETROFIT_MACHINE.get())
                .pattern("III")
                .pattern("IXI")
                .pattern("NNN")
                .define('X', ModItems.ELEMENT_115.get())
                .define('I', Items.IRON_INGOT)
                .define('N', Items.NETHERITE_INGOT)
                .group("packapunch")
                .unlockedBy("has_ore", has(Tags.Items.ORES_DIAMOND))
                .save(consumer);

        ShapedRecipeBuilder
                .shaped(ModBlocks.ARSENAL_MACHINE.get())
                .pattern("III")
                .pattern("IAI")
                .pattern("IRI")
                // .define('X', ModItems.ELEMENT_115.get())
                .define('I', Items.IRON_INGOT)
                .define('R', Items.REDSTONE_BLOCK)
                .define('A', com.tacz.guns.init.ModItems.AMMO_BOX.get())
                .group("packapunch")
                .unlockedBy("has_ore", has(Tags.Items.ORES_DIAMOND))
                .save(consumer);

    }
}
