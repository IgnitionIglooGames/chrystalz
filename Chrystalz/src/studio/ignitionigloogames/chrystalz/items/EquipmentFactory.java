/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import studio.ignitionigloogames.chrystalz.shops.Shop;

public class EquipmentFactory {
    // Private constructor
    private EquipmentFactory() {
        // Do nothing
    }

    // Methods
    public static Equipment createWeapon(final int material,
            final int weaponType) {
        final Equipment e = new Equipment(
                WeaponMaterialConstants.getWeaponMaterial(material) + " "
                        + WeaponConstants.getWeapon(weaponType),
                0, 0,
                EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ONE_HANDED_WEAPON,
                material);
        e.setSlotUsed(EquipmentSlotConstants.SLOT_MAINHAND);
        e.setPotency(material);
        e.setBuyPrice(Shop.getEquipmentCost(material));
        return e;
    }

    public static Equipment createArmor(final int material,
            final int armorType) {
        final Equipment e = new Equipment(
                ArmorMaterialConstants.getArmorMaterial(material) + " "
                        + ArmorConstants.getArmor(armorType),
                0, 0, EquipmentCategoryConstants.EQUIPMENT_CATEGORY_ARMOR,
                material);
        e.setSlotUsed(armorType);
        e.setPotency(material);
        e.setBuyPrice(Shop.getEquipmentCost(material));
        return e;
    }

    public static String[] createWeaponNames(final int weaponType) {
        final String[] res = new String[12];
        for (int x = 0; x < res.length; x++) {
            res[x] = WeaponMaterialConstants.getWeaponMaterial(x) + " "
                    + WeaponConstants.getWeapon(weaponType);
        }
        return res;
    }

    public static String[] createArmorNames(final int armorType) {
        final String[] res = new String[12];
        for (int x = 0; x < res.length; x++) {
            res[x] = ArmorMaterialConstants.getArmorMaterial(x) + " "
                    + ArmorConstants.getArmor(armorType);
        }
        return res;
    }
}
