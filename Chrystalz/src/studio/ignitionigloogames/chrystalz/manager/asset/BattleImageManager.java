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

public class BattleImageManager {
    private static final String DEFAULT_LOAD_PATH = "/assets/images/objects/";
    private static String LOAD_PATH = BattleImageManager.DEFAULT_LOAD_PATH;
    private static Class<?> LOAD_CLASS = BattleImageManager.class;

    /**
     *
     * @param name
     * @param baseID
     * @param transformColor
     * @return
     */
    public static BufferedImageIcon getImage(final String name,
            final int baseID, final int transformColor) {
        // Get it from the cache
        final String baseName = ObjectImageConstants.getObjectImageName(baseID);
        final BufferedImageIcon bii = BattleImageCache.getCachedImage(name,
                baseName);
        return ImageTransformer.templateTransformImage(bii, transformColor,
                BattleImageManager.getGraphicSize());
    }

    static BufferedImageIcon getUncachedImage(final String name) {
        try {
            final String normalName = ImageTransformer.normalizeName(name);
            final URL url = BattleImageManager.LOAD_CLASS.getResource(
                    BattleImageManager.LOAD_PATH + normalName + ".png");
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

    public static int getGraphicSize() {
        return 64;
    }
}