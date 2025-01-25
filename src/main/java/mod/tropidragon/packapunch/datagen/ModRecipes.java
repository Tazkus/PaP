package mod.tropidragon.packapunch.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;

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
                                .pattern(" # ")
                                .pattern("#X#")
                                .pattern(" # ")
                                .define('X', Items.DIAMOND)
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
                                .pattern("IGI")
                                .pattern("IGI")
                                .pattern("IAI")
                                // .define('X', ModItems.ELEMENT_115.get())
                                // .define('R', Items.COMPARATOR)
                                .define('I', Items.IRON_INGOT)
                                .define('G', Items.GLASS)
                                .define('A', Items.ANVIL)
                                // .define('A', com.tacz.guns.init.ModItems.AMMO_BOX.get())
                                .group("packapunch")
                                .unlockedBy("has_ore", has(Tags.Items.ORES_DIAMOND))
                                .save(consumer);

                ShapelessRecipeBuilder
                                .shapeless(ModItems.LIQUID_115.get(), 9)
                                .requires(ModItems.ARTIFICIAL_115.get())
                                .unlockedBy("has_ore", has(Tags.Items.ORES_DIAMOND))
                                .save(consumer);

                ShapelessRecipeBuilder
                                .shapeless(ModItems.COLA_DEADSHOT.get(), 3)
                                .requires(ModItems.LIQUID_115.get(), 1)
                                .requires(Items.GOLDEN_CARROT, 3)
                                .unlockedBy("has_ore", has(Tags.Items.ORES_DIAMOND))
                                .save(consumer);

                ShapelessRecipeBuilder
                                .shapeless(ModItems.COLA_PHD.get(), 3)
                                .requires(ModItems.LIQUID_115.get(), 1)
                                .requires(Items.CHORUS_FRUIT, 3)
                                .unlockedBy("has_ore", has(Tags.Items.ORES_DIAMOND))
                                .save(consumer);

        }
}
