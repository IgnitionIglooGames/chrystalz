/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz;

import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.UIManager;

import apple.dts.samplecode.osxadapter.OSXAdapter;

public class NativeIntegration {
    // Constructor
    private NativeIntegration() {
        // Do nothing
    }

    // Methods
    public static void hookLAF(final String programName) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            // Tell the UIManager to use the platform native look and
            // feel
            try {
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
            } catch (final Exception e) {
                // Do nothing
            }
            // Mac OS X-specific stuff
            System.setProperty(
                    "studio.apple.mrj.application.apple.menu.about.name",
                    programName);
            System.setProperty("apple.laf.useScreenMenuBar", "true");
        } else if (System.getProperty("os.name").startsWith("Windows")) {
            // Windows-specific stuff
            try {
                // Tell the UIManager to use the platform native look and
                // feel
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
                // Hint to the UI that the L&F is decorated
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (final Exception e) {
                // Do nothing
            }
        } else {
            // All other platforms
            try {
                // Tell the UIManager to use the Nimbus look and feel
                UIManager.setLookAndFeel(
                        "studio.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
                // Hint to the UI that the L&F is decorated
                JFrame.setDefaultLookAndFeelDecorated(true);
            } catch (final Exception e) {
                // Do nothing
            }
        }
    }

    public static void hookQuit(final Object o, final Method m) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            OSXAdapter.setQuitHandler(o, m);
        }
    }

    public static void hookPreferences(final Object o, final Method m) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            OSXAdapter.setPreferencesHandler(o, m);
        }
    }

    public static void hookAbout(final Object o, final Method m) {
        if (System.getProperty("os.name").startsWith("Mac OS X")) {
            OSXAdapter.setAboutHandler(o, m);
        }
    }
}
