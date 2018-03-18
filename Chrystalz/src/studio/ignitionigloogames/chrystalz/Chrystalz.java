/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz;

import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;
import studio.ignitionigloogames.common.errorlogger.ErrorLogger;

public class Chrystalz {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "Chrystalz";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@ignitionigloogames.com\n"
            + "Subject: Chrystalz Bug Report";
    private static final String ERROR_TITLE = "Chrystalz Error";
    private static final ErrorLogger elog = new ErrorLogger(
            Chrystalz.PROGRAM_NAME);
    private static final int BATTLE_MAZE_SIZE = 16;

    // Methods
    public static Application getApplication() {
        return Chrystalz.application;
    }

    public static int getBattleDungeonSize() {
        return Chrystalz.BATTLE_MAZE_SIZE;
    }

    public static ErrorLogger getErrorLogger() {
        // Display error message
        CommonDialogs.showErrorDialog(Chrystalz.ERROR_MESSAGE,
                Chrystalz.ERROR_TITLE);
        return Chrystalz.elog;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(Chrystalz.BATTLE_MAZE_SIZE,
                Chrystalz.BATTLE_MAZE_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            Chrystalz.preInit();
            // Integrate with host platform
            NativeIntegration.hookLAF(Chrystalz.PROGRAM_NAME);
            Chrystalz.application = new Application();
            Chrystalz.application.postConstruct();
            Application.playLogoSound();
            Chrystalz.application.getGUIManager().showGUI();
            // Register platform hooks
            NativeIntegration.hookAbout(Chrystalz.application.getAboutDialog(),
                    Chrystalz.application.getAboutDialog().getClass()
                            .getMethod("showAboutDialog"));
            NativeIntegration.hookPreferences(PreferencesManager.class,
                    PreferencesManager.class.getMethod("showPrefs"));
            NativeIntegration.hookQuit(Chrystalz.application.getGUIManager(),
                    Chrystalz.application.getGUIManager().getClass()
                            .getMethod("quitHandler"));
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(Chrystalz.PROGRAM_NAME);
            CommonDialogs.setIcon(Application.getMicroLogo());
        } catch (final Throwable t) {
            Chrystalz.getErrorLogger().logError(t);
        }
    }
}
