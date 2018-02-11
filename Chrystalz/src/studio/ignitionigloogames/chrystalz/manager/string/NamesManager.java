/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.string;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.manager.data.NamesDataManager;

public class NamesManager {
    private static boolean CACHE_CREATED = false;
    private static String[] RAW_CACHE;
    private static String[][] CACHE;

    public static String getName(final String section, final String type) {
        try {
            NamesManager.createCache();
        } catch (final Exception e) {
            Chrystalz.getErrorLogger().logError(e);
        }
        final String key = section + ":" + type;
        if (!NamesManager.containsKey(key)) {
            Chrystalz.getErrorLogger().logError(
                    new IllegalArgumentException("No such key " + key));
        }
        return NamesManager.getValue(key);
    }

    private static boolean containsKey(final String key) {
        for (final String[] element : NamesManager.CACHE) {
            if (element[0].equals(key)) {
                return true;
            }
        }
        return false;
    }

    private static String getValue(final String key) {
        for (final String[] element : NamesManager.CACHE) {
            if (element[0].equals(key)) {
                return element[1];
            }
        }
        return null;
    }

    private static void createCache() {
        if (!NamesManager.CACHE_CREATED) {
            // Create raw cache
            NamesManager.RAW_CACHE = NamesDataManager.getNamesData();
            if (NamesManager.RAW_CACHE != null) {
                NamesManager.CACHE = new String[NamesManager.RAW_CACHE.length][2];
                for (int x = 0; x < NamesManager.RAW_CACHE.length; x++) {
                    if (NamesManager.RAW_CACHE[x] != null
                            && !NamesManager.RAW_CACHE[x].isEmpty()) {
                        // Entry
                        final String[] splitEntry = NamesManager.RAW_CACHE[x]
                                .split("=");
                        // Sanity check
                        if (splitEntry.length < 2) {
                            throw new IllegalArgumentException(
                                    "Invalid names file format: Entry format invalid!");
                        }
                        final String key = splitEntry[0];
                        final String value = splitEntry[1];
                        NamesManager.CACHE[x][0] = key;
                        NamesManager.CACHE[x][1] = value;
                    }
                }
                NamesManager.CACHE_CREATED = true;
            } else {
                throw new IllegalArgumentException("Names file not found!");
            }
        }
    }
}