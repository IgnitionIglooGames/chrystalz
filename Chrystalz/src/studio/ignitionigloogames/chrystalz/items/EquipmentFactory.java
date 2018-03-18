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
                material, WeaponConstants.getWeaponTypeHitSound(weaponType));
        e.setSlotUsed(EquipmentSlotConstants.SLOT_WEAPON);
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
        e.setSlotUsed(EquipmentSlotConstants.getArmorSlotForType(armorType));
        e.setPotency(material);
        e.setBuyPrice(Shop.getEquipmentCost(material));
        return e;
    }

    public static String getWeaponName(final int zoneID, final int weaponType) {
        return WeaponMaterialConstants.getWeaponMaterial(zoneID) + " "
                + WeaponConstants.getWeapon(weaponType);
    }

    public static String getArmorName(final int zoneID, final int armorType) {
        return ArmorMaterialConstants.getArmorMaterial(zoneID) + " "
                + ArmorConstants.getArmor(armorType);
    }
}
