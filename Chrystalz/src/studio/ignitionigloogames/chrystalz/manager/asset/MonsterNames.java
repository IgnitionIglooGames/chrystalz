/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import studio.ignitionigloogames.chrystalz.manager.string.LocalizedFile;
import studio.ignitionigloogames.chrystalz.manager.string.StringManager;

public class MonsterNames {
    // Private constructor
    private MonsterNames() {
        // Do nothing
    }

    public static final String getName(final int ID) {
        String tempMonID = Integer.toString(ID);
        String monID;
        if (tempMonID.length() == 1) {
            monID = "0" + tempMonID;
        } else {
            monID = tempMonID;
        }
        return StringManager.getLocalizedString(LocalizedFile.MONSTERS, monID);
    }
}