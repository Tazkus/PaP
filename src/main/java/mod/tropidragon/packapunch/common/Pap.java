package mod.tropidragon.packapunch.common;

import mod.tropidragon.packapunch.common.internal.IMixinModernKineticGunItem;
import mod.tropidragon.packapunch.compat.vault.VaultCompat;
import mod.tropidragon.packapunch.config.subconfig.PapConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

import com.tacz.guns.api.item.IGun;

public class Pap {
    // C, UC, R, E, L
    // static private float[] rarityModifiers = { 1.00f, 1.25f, 1.50f, 1.75f, 2.00f
    // };
    // static private float[] rarityModifiers = { 1.0f, 1.5f, 2.0f, 3.0f, 4.0f };
    // 无改造, 超改I, 超改II, 超改III
    // static private float[] papModifiers = { 1.0f, 2.0f, 3.0f, 4.0f };
    // static private float[] papModifiers = { 1.0f, 2.0f, 4.0f, 8.0f };

    public static Item DEFAULT_PAP_UPGRADE_ITEM = Items.DIAMOND;
    public static Item DEFAULT_RARITY_UPGRADE_ITEM = Items.NETHERITE_SCRAP;

    public static boolean upgradable(int papLvl, int rarityLvl) {
        return papLvl >= 0 && papLvl < 3 && rarityLvl >= 0 && rarityLvl < 4;
    }

    public static float getRarityDamageRate(int level) {
        switch (level) {
            case 0:
                return 1.0f;
            case 1:
                return PapConfig.RARITY_C_RATE.get().floatValue();
            case 2:
                return PapConfig.RARITY_B_RATE.get().floatValue();
            case 3:
                return PapConfig.RARITY_A_RATE.get().floatValue();
            case 4:
                return PapConfig.RARITY_S_RATE.get().floatValue();
            default:
                return 1.0f;
        }
    }

    public static float getPapDamageRate(int level) {
        switch (level) {
            case 0:
                return 1.0f;
            case 1:
                return PapConfig.PAP_1_RATE.get().floatValue();
            case 2:
                return PapConfig.PAP_2_RATE.get().floatValue();
            case 3:
                return PapConfig.PAP_3_RATE.get().floatValue();

            default:
                return 1.0f;
        }
    }

    public static String getPaPTierSymbol(int level) {
        switch (level) {
            case 0:
                return "";
            case 1:
                return "I";
            // return "Ⅰ";
            case 2:
                return "II";
            // return "Ⅱ";
            case 3:
                return "III";
            // return "Ⅲ";

            default:
                return "⑨";
        }
    }

    public static String getRarityTierSymbol(int level) {
        switch (level) {
            case 0:
                return "D";
            case 1:
                return "C";
            case 2:
                return "B";
            case 3:
                return "A";
            case 4:
                return "S";

            default:
                return "Z";
        }
    }

    public static float getDamageModifier(ItemStack gun) {

        float gunRarityModifier = 1.0f;
        float gunPaPModifier = 1.0f;

        IGun iGun = IGun.getIGunOrNull(gun);

        if (iGun instanceof IMixinModernKineticGunItem) {
            int gunRarityLevel = ((IMixinModernKineticGunItem) (Object) iGun).getRarityLevel(gun);
            int gunPaPLevel = ((IMixinModernKineticGunItem) (Object) iGun).getPaPLevel(gun);
            // gunRarityModifier = rarityModifiers[gunRarityLevel];
            // gunPaPModifier = papModifiers[gunPaPLevel];
            gunRarityModifier = getRarityDamageRate(gunRarityLevel);
            gunPaPModifier = PapConfig.USE_EXTENDED_LEVEL.get()
                    ? getExtendedPapDamageRate(gunPaPLevel)
                    : getPapDamageRate(gunPaPLevel);
        }
        // nerf it to additive 😂
        return PapConfig.USE_ADDITIVE_DAMAGE_MULTIPLIER.get() ? gunRarityModifier + gunPaPModifier - 1f
                : gunRarityModifier * gunPaPModifier;
    }

    public static int getPapUpgradeCost(int papLevel) {
        if (PapConfig.USE_EXTENDED_LEVEL.get()) {
            return getExtendedPapUpgradeCost(papLevel);
        }
        switch (papLevel) {
            case 0:
                return PapConfig.PAP_COST_1.get();
            case 1:
                return PapConfig.PAP_COST_2.get();
            case 2:
                return PapConfig.PAP_COST_3.get();
            default:
                return 0;
        }
    }

    public static int getRarityUpgradeCost(int rarityLevel) {
        switch (rarityLevel) {
            case 0:
                return PapConfig.RARITY_COST_C.get();
            case 1:
                return PapConfig.RARITY_COST_B.get();
            case 2:
                return PapConfig.RARITY_COST_A.get();
            case 3:
                return PapConfig.RARITY_COST_S.get();
            default:
                return 0;
        }
    }

    public static ItemStack getPapUpgradeItem(int papLevel) {
        if (PapConfig.USE_EXTENDED_LEVEL.get()) {
            return getExtendedPapUpgradeItem(papLevel);
        }
        if (VaultCompat.INSTALLED) {
            return VaultCompat.getPapUpgradeItem(papLevel);
        }
        switch (papLevel) {
            case 0:
                return getConfigItem(PapConfig.PAP_ITEM_1.get(), DEFAULT_PAP_UPGRADE_ITEM)[0];
            case 1:
                return getConfigItem(PapConfig.PAP_ITEM_2.get(), DEFAULT_PAP_UPGRADE_ITEM)[0];
            case 2:
                return getConfigItem(PapConfig.PAP_ITEM_3.get(), DEFAULT_PAP_UPGRADE_ITEM)[0];
            default:
                return getItemStack(DEFAULT_PAP_UPGRADE_ITEM);
        }
    }

    public static ItemStack getRarityUpgradeItem(int rarityLvl) {
        if (VaultCompat.INSTALLED) {
            return VaultCompat.getRarityUpgradeItem(rarityLvl);
        }
        switch (rarityLvl) {
            case 0:
                return getConfigItem(PapConfig.RARITY_ITEM_C.get(), DEFAULT_RARITY_UPGRADE_ITEM)[0];
            case 1:
                return getConfigItem(PapConfig.RARITY_ITEM_B.get(), DEFAULT_RARITY_UPGRADE_ITEM)[0];
            case 2:
                return getConfigItem(PapConfig.RARITY_ITEM_A.get(), DEFAULT_RARITY_UPGRADE_ITEM)[0];
            case 3:
                return getConfigItem(PapConfig.RARITY_ITEM_S.get(), DEFAULT_RARITY_UPGRADE_ITEM)[0];
            default:
                return getItemStack(DEFAULT_RARITY_UPGRADE_ITEM);
        }
    }

    public static float getExtendedPapDamageRate(int level) {
        try {
            return (PapConfig.EXTENDED_LEVEL_RATE.get()).get(level - 1).floatValue();
        } catch (IndexOutOfBoundsException e) {
            return 1.0F;
        }
    }

    public static ItemStack getExtendedPapUpgradeItem(int level) {
        try {
            String itemName = (PapConfig.EXTENDED_LEVEL_ITEM.get()).get(level - 1);
            return getConfigItem(itemName, DEFAULT_PAP_UPGRADE_ITEM)[0];
        } catch (IndexOutOfBoundsException e) {
            return getItemStack(DEFAULT_PAP_UPGRADE_ITEM);
        }
    }

    public static int getExtendedPapUpgradeCost(int level) {
        try {
            return (PapConfig.EXTENDED_LEVEL_COST.get()).get(level - 1);
        } catch (IndexOutOfBoundsException e) {
            return 114514;
        }
    }

    private static ItemStack[] getConfigItem(String config, Item defaultItem) {
        ResourceLocation key = new ResourceLocation(config);
        if (ForgeRegistries.ITEMS.containsKey(key)) {
            return Ingredient.of(ForgeRegistries.ITEMS.getValue(key)).getItems();
        }
        return Ingredient.of(defaultItem).getItems();
    }

    private static ItemStack getItemStack(Item item) {
        Ingredient ingredient = Ingredient.of(item);
        ItemStack[] items = ingredient.getItems();
        return items[0];
    }

}
