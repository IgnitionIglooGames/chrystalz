/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.items;

import studio.ignitionigloogames.chrystalz.manager.name.NamesConstants;
import studio.ignitionigloogames.chrystalz.manager.name.NamesManager;

public class ArmorConstants {
    private static String[] ARMOR = null;
    private static String[] ARMOR_CHOICES = null;

    public static synchronized String[] getArmorChoices() {
        if (ArmorConstants.ARMOR_CHOICES == null) {
            final String[] temp1 = EquipmentSlotConstants.getArmorSlotNames();
            final String[] temp2 = new String[temp1.length];
            System.arraycopy(temp1, 0, temp2, 0, temp1.length);
            temp2[EquipmentSlotConstants.SLOT_OFFHAND - 1] = NamesManager
                    .getName(NamesConstants.SECTION_EQUIP_ARMOR,
                            NamesConstants.ARMOR_SHIELD);
            ArmorConstants.ARMOR_CHOICES = temp2;
        }
        return ArmorConstants.ARMOR_CHOICES;
    }

    public static synchronized String[] getArmor() {
        if (ArmorConstants.ARMOR == null) {
            final String[] temp1 = ArmorConstants.getArmorChoices();
            final String[] temp2 = new String[temp1.length + 1];
            temp2[EquipmentSlotConstants.SLOT_MAINHAND] = "";
            temp2[EquipmentSlotConstants.SLOT_OFFHAND] = temp1[0];
            temp2[EquipmentSlotConstants.SLOT_BODY] = temp1[1];
            ArmorConstants.ARMOR = temp2;
        }
        return ArmorConstants.ARMOR;
    }
}
