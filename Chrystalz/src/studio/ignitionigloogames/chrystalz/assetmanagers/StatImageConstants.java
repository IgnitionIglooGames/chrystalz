package studio.ignitionigloogames.chrystalz.assetmanagers;

import studio.ignitionigloogames.chrystalz.datamanagers.ImageDataManager;

public class StatImageConstants {
    public static final int STAT_IMAGE_ATTACK = 0;
    public static final int STAT_IMAGE_DEFENSE = 1;
    public static final int STAT_IMAGE_GOLD = 6;
    public static final int STAT_IMAGE_HEALTH = 3;
    public static final int STAT_IMAGE_MAGIC = 5;
    public static final int STAT_IMAGE_NAME = 7;
    public static final int STAT_IMAGE_XP = 2;
    public static final int STAT_IMAGE_LEVEL = 4;
    private static final String[] STAT_IMAGE_NAMES = ImageDataManager
            .getStatGraphicsData();

    static String getStatImageName(final int ID) {
        return StatImageConstants.STAT_IMAGE_NAMES[ID];
    }
}
