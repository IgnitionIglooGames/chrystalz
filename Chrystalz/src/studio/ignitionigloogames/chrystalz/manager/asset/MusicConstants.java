/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import studio.ignitionigloogames.chrystalz.manager.data.MusicDataManager;

public class MusicConstants {
    // Public Music Constants
    public static final int MUSIC_BATTLE = 0;
    public static final int MUSIC_CREATE = 1;
    public static final int MUSIC_DUNGEON = 2;
    public static final int MUSIC_SHOP = 3;
    private static final String[] MUSIC_NAMES = MusicDataManager.getMusicData();

    // Private constructor
    private MusicConstants() {
        // Do nothing
    }

    static String getMusicName(final int ID) {
        return MusicConstants.MUSIC_NAMES[ID];
    }
}