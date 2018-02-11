/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.assetmanagers;

import studio.ignitionigloogames.chrystalz.datamanagers.MonsterDataManager;

public class MonsterNames {
    // Package-Protected Constants
    private static final String[] MONSTER_NAMES = MonsterDataManager
            .getMonsterData();

    public static final String[] getAllNames() {
        return MonsterNames.MONSTER_NAMES;
    }

    // Private constructor
    private MonsterNames() {
        // Do nothing
    }
}