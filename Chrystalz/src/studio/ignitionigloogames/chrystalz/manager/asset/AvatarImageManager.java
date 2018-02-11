/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class AvatarImageManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/images/avatars/";
    private static String LOAD_PATH = AvatarImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = AvatarImageManager.class;
    static int MONSTER_IMAGE_SIZE = 64;
    private static final String[] INTERNAL_GENDER_NAMES = { "male/",
            "female/" };

    public static BufferedImageIcon getImage(final int genderID,
            final int hairID, final int skinID) {
        // Get it from the cache
        final String name = AvatarImageManager.INTERNAL_GENDER_NAMES[genderID]
                + Integer.toString(hairID) + Integer.toString(skinID);
        final BufferedImageIcon bii = AvatarImageCache.getCachedImage(name);
        return ImageTransformer.getTransformedImage(bii,
                AvatarImageManager.MONSTER_IMAGE_SIZE);
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final String normalName = ImageTransformer.normalizeName(name);
            final URL url = AvatarImageManager.LOAD_CLASS.getResource(
                    AvatarImageManager.LOAD_PATH + normalName + ".png");
            final BufferedImage image = ImageIO.read(url);
            return new BufferedImageIcon(image);
        } catch (final IOException ie) {
            return null;
        } catch (final NullPointerException np) {
            return null;
        } catch (final IllegalArgumentException ia) {
            return null;
        }
    }
}
