/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

public class ObjectImageConstants {
    public static final int OBJECT_IMAGE_NONE = -1;
    public static final int OBJECT_IMAGE_ARMOR_SHOP = 0;
    public static final int OBJECT_IMAGE_CLOSED_DOOR = 1;
    public static final int OBJECT_IMAGE_DARKNESS = 2;
    public static final int OBJECT_IMAGE_EMPTY = 4;
    public static final int OBJECT_IMAGE_HEAL_SHOP = 5;
    public static final int OBJECT_IMAGE_ICE = 6;
    public static final int OBJECT_IMAGE_OPEN_DOOR = 7;
    public static final int OBJECT_IMAGE_REGENERATOR = 8;
    public static final int OBJECT_IMAGE_SPELL_SHOP = 9;
    public static final int OBJECT_IMAGE_STAIRS_DOWN = 3;
    public static final int OBJECT_IMAGE_STAIRS_UP = 11;
    public static final int OBJECT_IMAGE_TILE = 10;
    public static final int OBJECT_IMAGE_WALL = 12;
    public static final int OBJECT_IMAGE_WEAPONS_SHOP = 13;
    private static final String[] OBJECT_IMAGE_NAMES = ImageDataManager
            .getObjectGraphicsData();

    static String getObjectImageName(final int ID) {
        if (ID == ObjectImageConstants.OBJECT_IMAGE_NONE) {
            return "";
        } else {
            return ObjectImageConstants.OBJECT_IMAGE_NAMES[ID];
        }
    }
}
