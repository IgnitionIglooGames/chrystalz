/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.assetmanagers;

import java.net.URL;

import studio.ignitionigloogames.chrystalz.dungeon.Extension;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.wavplayer.WAVFactory;

public class SoundManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/sounds/";
    private static String LOAD_PATH = SoundManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = SoundManager.class;

    private static WAVFactory getSound(final String filename) {
        try {
            final URL url = SoundManager.LOAD_CLASS
                    .getResource(SoundManager.LOAD_PATH + filename.toLowerCase()
                            + Extension.getSoundExtensionWithPeriod());
            return WAVFactory.getNonLoopingResource(url);
        } catch (final NullPointerException np) {
            return null;
        }
    }

    public static void playSound(final int soundID) {
        try {
            if (PreferencesManager.getSoundsEnabled()) {
                final String soundName = SoundConstants.getSoundName(soundID);
                final WAVFactory snd = SoundManager.getSound(soundName);
                snd.start();
            }
        } catch (final ArrayIndexOutOfBoundsException aioob) {
            // Do nothing
        }
    }
}