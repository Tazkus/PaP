package mod.tropidragon.packapunch.config.subconfig;

import net.minecraftforge.common.ForgeConfigSpec;

public final class PapConfig {
    public static ForgeConfigSpec.ConfigValue<String> PAP_ITEM_1;
    public static ForgeConfigSpec.ConfigValue<String> PAP_ITEM_2;
    public static ForgeConfigSpec.ConfigValue<String> PAP_ITEM_3;
    public static ForgeConfigSpec.IntValue PAP_COST_1;
    public static ForgeConfigSpec.IntValue PAP_COST_2;
    public static ForgeConfigSpec.IntValue PAP_COST_3;

    public static ForgeConfigSpec.ConfigValue<String> RARITY_ITEM_C;
    public static ForgeConfigSpec.ConfigValue<String> RARITY_ITEM_B;
    public static ForgeConfigSpec.ConfigValue<String> RARITY_ITEM_A;
    public static ForgeConfigSpec.ConfigValue<String> RARITY_ITEM_S;
    public static ForgeConfigSpec.IntValue RARITY_COST_C;
    public static ForgeConfigSpec.IntValue RARITY_COST_B;
    public static ForgeConfigSpec.IntValue RARITY_COST_A;
    public static ForgeConfigSpec.IntValue RARITY_COST_S;

    public static ForgeConfigSpec.BooleanValue USE_ADDITIVE_DAMAGE_MULTIPLIER;

    public static ForgeConfigSpec.DoubleValue PAP_1_RATE;
    public static ForgeConfigSpec.DoubleValue PAP_2_RATE;
    public static ForgeConfigSpec.DoubleValue PAP_3_RATE;

    public static ForgeConfigSpec.DoubleValue RARITY_C_RATE;
    public static ForgeConfigSpec.DoubleValue RARITY_B_RATE;
    public static ForgeConfigSpec.DoubleValue RARITY_A_RATE;
    public static ForgeConfigSpec.DoubleValue RARITY_S_RATE;

    public static void init(ForgeConfigSpec.Builder builder) {
        builder.push("pap");

        builder.comment("The item used in PaP upgrade level I");
        PAP_ITEM_1 = builder.define("PapItem1", "minecraft:diamond");
        builder.comment("The item used in PaP upgrade level II");
        PAP_ITEM_2 = builder.define("PapItem2", "minecraft:diamond");
        builder.comment("The item used in PaP upgrade level III");
        PAP_ITEM_3 = builder.define("PapItem3", "minecraft:diamond");

        builder.comment("The number of items required in PaP upgrade level I");
        PAP_COST_1 = builder.defineInRange("PapCost1", 50, 1, 65536);
        builder.comment("The number of items required in PaP upgrade level II");
        PAP_COST_2 = builder.defineInRange("PapCost2", 150, 1, 65536);
        builder.comment("The number of items required in PaP upgrade level III");
        PAP_COST_3 = builder.defineInRange("PapCost3", 300, 1, 65536);

        builder.comment("The item used in Rarity upgrade tier C");
        RARITY_ITEM_C = builder.define("RarityItemC", "minecraft:netherite_scrap");
        builder.comment("The item used in Rarity upgrade tier B");
        RARITY_ITEM_B = builder.define("RarityItemB", "minecraft:netherite_scrap");
        builder.comment("The item used in Rarity upgrade tier A");
        RARITY_ITEM_A = builder.define("RarityItemA", "minecraft:netherite_scrap");
        builder.comment("The item used in Rarity upgrade tier S");
        RARITY_ITEM_S = builder.define("RarityItemS", "minecraft:netherite_scrap");

        builder.comment("The number of items required in Rarity upgrade tier C");
        RARITY_COST_C = builder.defineInRange("RarityCostC", 50, 1, 65536);
        builder.comment("The number of items required in Rarity upgrade tier B");
        RARITY_COST_B = builder.defineInRange("RarityCostB", 100, 1, 65536);
        builder.comment("The number of items required in Rarity upgrade tier A");
        RARITY_COST_A = builder.defineInRange("RarityCostA", 250, 1, 65536);
        builder.comment("The number of items required in Rarity upgrade tier S");
        RARITY_COST_S = builder.defineInRange("RarityCostS", 500, 1, 65536);

        builder.comment(
                "Whether the damage multiplier from PaP and Rarity should be combined ADDITIVELY instead of Multiplicatively");
        USE_ADDITIVE_DAMAGE_MULTIPLIER = builder.define("UseAdditive", false);

        builder.comment("Damage multiplier for weapon of PaP Level 1");
        PAP_1_RATE = builder.defineInRange("PapRate1", 2.0, 0.0, 10.0);
        builder.comment("Damage multiplier for weapon of PaP Level 2");
        PAP_2_RATE = builder.defineInRange("PapRate2", 4.0, 0.0, 10.0);
        builder.comment("Damage multiplier for weapon of PaP Level 3");
        PAP_3_RATE = builder.defineInRange("PapRate3", 8.0, 0.0, 10.0);

        builder.comment("Damage multiplier for weapon of Rarity C");
        RARITY_C_RATE = builder.defineInRange("RarityRateC", 1.5, 0.0, 10.0);
        builder.comment("Damage multiplier for weapon of Rarity B");
        RARITY_B_RATE = builder.defineInRange("RarityRateB", 2.0, 0.0, 10.0);
        builder.comment("Damage multiplier for weapon of Rarity A");
        RARITY_A_RATE = builder.defineInRange("RarityRateA", 3.0, 0.0, 10.0);
        builder.comment("Damage multiplier for weapon of Rarity S");
        RARITY_S_RATE = builder.defineInRange("RarityRateS", 4.0, 0.0, 10.0);

        builder.pop();
    }
}
