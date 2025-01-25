package mod.tropidragon.packapunch.config.cloth;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.DropdownMenuBuilder;
import mod.tropidragon.packapunch.config.subconfig.PapConfig;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

public class MenuIntegration {
        public static ConfigBuilder getConfigBuilder() {
                ConfigBuilder root = ConfigBuilder.create().setTitle(new TextComponent("Pack-a-Punched"));
                root.setGlobalized(true);
                root.setGlobalizedExpanded(false);
                ConfigEntryBuilder entryBuilder = root.entryBuilder();
                papConfig(root, entryBuilder);

                MinecraftForge.EVENT_BUS.post(new AddClothConfigEvent(root, entryBuilder));
                return root;
        }

        public static void registerModsPage() {
                ModLoadingContext.get().registerExtensionPoint(ConfigGuiHandler.ConfigGuiFactory.class,
                                () -> new ConfigGuiHandler.ConfigGuiFactory(
                                                (client, parent) -> getConfigBuilder().setParentScreen(parent)
                                                                .build()));
        }

        private static String getItemId(Item item) {
                ResourceLocation key = ForgeRegistries.ITEMS.getKey(item);
                Preconditions.checkNotNull(key);
                return key.toString();
        }

        @SuppressWarnings("all")
        private static void papConfig(ConfigBuilder root, ConfigEntryBuilder entryBuilder) {
                ConfigCategory pap = root.getOrCreateCategory(new TranslatableComponent("config.packapunch.pap"));

                pap.addEntry(entryBuilder
                                .startDropdownMenu(new TranslatableComponent("config.packapunch.pap.pap_item_1"),
                                                DropdownMenuBuilder.TopCellElementBuilder
                                                                .ofItemObject(ForgeRegistries.ITEMS
                                                                                .getValue(new ResourceLocation(
                                                                                                PapConfig.PAP_ITEM_1
                                                                                                                .get()))),
                                                DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString))
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                                .setDefaultValue(Items.DIAMOND)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(s -> PapConfig.PAP_ITEM_1
                                                .set(ForgeRegistries.ITEMS.getKey(s).toString()))
                                .build());
                pap.addEntry(entryBuilder
                                .startDropdownMenu(new TranslatableComponent("config.packapunch.pap.pap_item_2"),
                                                DropdownMenuBuilder.TopCellElementBuilder
                                                                .ofItemObject(ForgeRegistries.ITEMS
                                                                                .getValue(new ResourceLocation(
                                                                                                PapConfig.PAP_ITEM_2
                                                                                                                .get()))),
                                                DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString))
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                                .setDefaultValue(Items.DIAMOND)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(s -> PapConfig.PAP_ITEM_2
                                                .set(ForgeRegistries.ITEMS.getKey(s).toString()))
                                .build());
                pap.addEntry(entryBuilder
                                .startDropdownMenu(new TranslatableComponent("config.packapunch.pap.pap_item_3"),
                                                DropdownMenuBuilder.TopCellElementBuilder
                                                                .ofItemObject(ForgeRegistries.ITEMS
                                                                                .getValue(new ResourceLocation(
                                                                                                PapConfig.PAP_ITEM_3
                                                                                                                .get()))),
                                                DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString))
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                                .setDefaultValue(Items.DIAMOND)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(s -> PapConfig.PAP_ITEM_3
                                                .set(ForgeRegistries.ITEMS.getKey(s).toString()))
                                .build());

                pap.addEntry(entryBuilder
                                .startDropdownMenu(new TranslatableComponent("config.packapunch.pap.rarity_item_c"),
                                                DropdownMenuBuilder.TopCellElementBuilder
                                                                .ofItemObject(ForgeRegistries.ITEMS
                                                                                .getValue(new ResourceLocation(
                                                                                                PapConfig.RARITY_ITEM_C
                                                                                                                .get()))),
                                                DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString))
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                                .setDefaultValue(Items.NETHERITE_SCRAP)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(s -> PapConfig.RARITY_ITEM_C
                                                .set(ForgeRegistries.ITEMS.getKey(s).toString()))
                                .build());

                pap.addEntry(entryBuilder
                                .startDropdownMenu(new TranslatableComponent("config.packapunch.pap.rarity_item_b"),
                                                DropdownMenuBuilder.TopCellElementBuilder
                                                                .ofItemObject(ForgeRegistries.ITEMS
                                                                                .getValue(new ResourceLocation(
                                                                                                PapConfig.RARITY_ITEM_B
                                                                                                                .get()))),
                                                DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString))
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                                .setDefaultValue(Items.NETHERITE_SCRAP)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(s -> PapConfig.RARITY_ITEM_B
                                                .set(ForgeRegistries.ITEMS.getKey(s).toString()))
                                .build());

                pap.addEntry(entryBuilder
                                .startDropdownMenu(new TranslatableComponent("config.packapunch.pap.rarity_item_a"),
                                                DropdownMenuBuilder.TopCellElementBuilder
                                                                .ofItemObject(ForgeRegistries.ITEMS
                                                                                .getValue(new ResourceLocation(
                                                                                                PapConfig.RARITY_ITEM_A
                                                                                                                .get()))),
                                                DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString))
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                                .setDefaultValue(Items.NETHERITE_SCRAP)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(s -> PapConfig.RARITY_ITEM_A
                                                .set(ForgeRegistries.ITEMS.getKey(s).toString()))
                                .build());
                pap.addEntry(entryBuilder
                                .startDropdownMenu(new TranslatableComponent("config.packapunch.pap.rarity_item_s"),
                                                DropdownMenuBuilder.TopCellElementBuilder
                                                                .ofItemObject(ForgeRegistries.ITEMS
                                                                                .getValue(new ResourceLocation(
                                                                                                PapConfig.RARITY_ITEM_S
                                                                                                                .get()))),
                                                DropdownMenuBuilder.CellCreatorBuilder.ofItemObject())
                                .setSelections(Registry.ITEM.stream().sorted(Comparator.comparing(Item::toString))
                                                .collect(Collectors.toCollection(LinkedHashSet::new)))
                                .setDefaultValue(Items.NETHERITE_SCRAP)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(s -> PapConfig.RARITY_ITEM_S
                                                .set(ForgeRegistries.ITEMS.getKey(s).toString()))
                                .build());

                // COUNT
                pap.addEntry(entryBuilder
                                .startIntField(new TranslatableComponent("config.packapunch.pap.pap_cost_1"),
                                                PapConfig.PAP_COST_1.get())
                                .setMin(1).setMax(65536).setDefaultValue(50)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(i -> PapConfig.PAP_COST_1.set(i)).build());
                pap.addEntry(entryBuilder
                                .startIntField(new TranslatableComponent("config.packapunch.pap.pap_cost_2"),
                                                PapConfig.PAP_COST_2.get())
                                .setMin(1).setMax(65536).setDefaultValue(150)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(i -> PapConfig.PAP_COST_2.set(i)).build());
                pap.addEntry(entryBuilder
                                .startIntField(new TranslatableComponent("config.packapunch.pap.pap_cost_3"),
                                                PapConfig.PAP_COST_3.get())
                                .setMin(1).setMax(65536).setDefaultValue(300)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(i -> PapConfig.PAP_COST_3.set(i)).build());

                pap.addEntry(entryBuilder
                                .startIntField(new TranslatableComponent("config.packapunch.pap.rarity_cost_c"),
                                                PapConfig.RARITY_COST_C.get())
                                .setMin(1).setMax(65536).setDefaultValue(50)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(i -> PapConfig.RARITY_COST_C.set(i)).build());
                pap.addEntry(entryBuilder
                                .startIntField(new TranslatableComponent("config.packapunch.pap.rarity_cost_b"),
                                                PapConfig.RARITY_COST_B.get())
                                .setMin(1).setMax(65536).setDefaultValue(100)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(i -> PapConfig.RARITY_COST_B.set(i)).build());
                pap.addEntry(entryBuilder
                                .startIntField(new TranslatableComponent("config.packapunch.pap.rarity_cost_a"),
                                                PapConfig.RARITY_COST_A.get())
                                .setMin(1).setMax(65536).setDefaultValue(250)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(i -> PapConfig.RARITY_COST_A.set(i)).build());
                pap.addEntry(entryBuilder
                                .startIntField(new TranslatableComponent("config.packapunch.pap.rarity_cost_s"),
                                                PapConfig.RARITY_COST_S.get())
                                .setMin(1).setMax(65536).setDefaultValue(500)
                                .setTooltip(new TextComponent(""))
                                .setSaveConsumer(i -> PapConfig.RARITY_COST_S.set(i)).build());

                // USE_ADDITIVE
                pap.addEntry(entryBuilder
                                .startBooleanToggle(new TranslatableComponent("config.packapunch.pap.use_additive"),
                                                PapConfig.USE_ADDITIVE_DAMAGE_MULTIPLIER.get())
                                .setDefaultValue(false)
                                .setSaveConsumer(i -> PapConfig.USE_ADDITIVE_DAMAGE_MULTIPLIER.set(i)).build());

                pap.addEntry(entryBuilder
                                .startDoubleField(new TranslatableComponent("config.packapunch.pap.pap_1_rate"),
                                                PapConfig.PAP_1_RATE.get())
                                .setMin(0.0).setMax(10.0).setDefaultValue(2.0)
                                .setSaveConsumer(i -> PapConfig.PAP_1_RATE.set(i)).build());
                pap.addEntry(entryBuilder
                                .startDoubleField(new TranslatableComponent("config.packapunch.pap.pap_2_rate"),
                                                PapConfig.PAP_2_RATE.get())
                                .setMin(0.0).setMax(10.0).setDefaultValue(4.0)
                                .setSaveConsumer(i -> PapConfig.PAP_2_RATE.set(i)).build());
                pap.addEntry(entryBuilder
                                .startDoubleField(new TranslatableComponent("config.packapunch.pap.pap_3_rate"),
                                                PapConfig.PAP_3_RATE.get())
                                .setMin(0.0).setMax(10.0).setDefaultValue(8.0)
                                .setSaveConsumer(i -> PapConfig.PAP_3_RATE.set(i)).build());

                pap.addEntry(entryBuilder
                                .startDoubleField(new TranslatableComponent("config.packapunch.pap.rarity_c_rate"),
                                                PapConfig.RARITY_C_RATE.get())
                                .setMin(0.0).setMax(10.0).setDefaultValue(1.5)
                                .setSaveConsumer(i -> PapConfig.RARITY_C_RATE.set(i)).build());
                pap.addEntry(entryBuilder
                                .startDoubleField(new TranslatableComponent("config.packapunch.pap.rarity_b_rate"),
                                                PapConfig.RARITY_B_RATE.get())
                                .setMin(0.0).setMax(10.0).setDefaultValue(2.0)
                                .setSaveConsumer(i -> PapConfig.RARITY_B_RATE.set(i)).build());
                pap.addEntry(entryBuilder
                                .startDoubleField(new TranslatableComponent("config.packapunch.pap.rarity_a_rate"),
                                                PapConfig.RARITY_A_RATE.get())
                                .setMin(0.0).setMax(10.0).setDefaultValue(3.0)
                                .setSaveConsumer(i -> PapConfig.RARITY_A_RATE.set(i)).build());
                pap.addEntry(entryBuilder
                                .startDoubleField(new TranslatableComponent("config.packapunch.pap.rarity_s_rate"),
                                                PapConfig.RARITY_S_RATE.get())
                                .setMin(0.0).setMax(10.0).setDefaultValue(4.0)
                                .setSaveConsumer(i -> PapConfig.RARITY_S_RATE.set(i)).build());

        }
}
