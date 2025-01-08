package mod.tropidragon.packapunch.common;

import mod.tropidragon.packapunch.common.internal.IMixinModernKineticGunItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

import com.tacz.guns.api.item.IGun;

public class Pap {
    // C, UC, R, E, L
    static private float[] rarityModifiers = { 1.0f, 1.5f, 2.0f, 3.0f, 4.0f };
    // 无改造, 超改I, 超改II, 超改III
    static private float[] papModifiers = { 1.0f, 2.0f, 4.0f, 8.0f };

    public static boolean upgradable(int papLvl, int rarityLvl) {
        return papLvl >= 0 && papLvl < 3 && rarityLvl >= 0 && rarityLvl < 4;
    }

    public static String getPaPTierSymbol(int level) {
        switch (level) {
            case 0:
                return "";
            case 1:
                return "[I]";
            // return "[Ⅰ]";
            case 2:
                return "[II]";
            // return "[Ⅱ]";
            case 3:
                return "[III]";
            // return "[Ⅲ]";

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

    public static float getDamageModifier(ItemStack gun, IGun iGun) {

        float gunRarityModifier = 1.0f;
        float gunPaPModifier = 1.0f;

        int gunRarityTier = ((IMixinModernKineticGunItem) (Object) iGun).getRarityLevel(gun);
        int gunPaPTier = ((IMixinModernKineticGunItem) (Object) iGun).getPaPLevel(gun);
        gunRarityModifier = rarityModifiers[gunRarityTier];
        gunPaPModifier = papModifiers[gunPaPTier];

        return gunRarityModifier * gunPaPModifier;
    }

    public static float getDamageModifier(ItemStack gun) {

        float gunRarityModifier = 1.0f;
        float gunPaPModifier = 1.0f;

        IGun iGun = IGun.getIGunOrNull(gun);

        if (iGun instanceof IMixinModernKineticGunItem) {
            int gunRarityTier = ((IMixinModernKineticGunItem) (Object) iGun).getRarityLevel(gun);
            int gunPaPTier = ((IMixinModernKineticGunItem) (Object) iGun).getPaPLevel(gun);
            gunRarityModifier = rarityModifiers[gunRarityTier];
            gunPaPModifier = papModifiers[gunPaPTier];
        }

        return gunRarityModifier * gunPaPModifier;
    }

    public static ItemStack getPapUpgradeMaterial(int papLevel) {
        Ingredient ingredient = Ingredient.of(Items.DIAMOND, Items.GOLDEN_APPLE);
        ItemStack[] items = ingredient.getItems();
        return items[0];
    }
    // public static List<ItemStack> getPapUpgradeMaterial(int papLevel) {
    // List<ItemStack> list = new ArrayList<>();
    // Ingredient ingredient = Ingredient.of(Items.DIAMOND, Items.GOLDEN_APPLE);
    // ItemStack[] items = ingredient.getItems();
    // for(ItemStack item : items){
    // list.add(item)
    // }
    // return list;
    // }

    public static int getPapUpgradeMaterialCount(int papLevel) {
        switch (papLevel) {
            case 0:
                return 50;
            case 1:
                return 150;
            case 2:
                return 300;
            default:
                return 0;
        }
    }
}
