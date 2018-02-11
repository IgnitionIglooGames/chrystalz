/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz;

import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.prefs.PrefsRequestHandler;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;
import studio.ignitionigloogames.common.errorlogger.ErrorLogger;

public class Chrystalz {
    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "TallerTower";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@puttysoftware.com\n"
            + "Subject: TallerTower Bug Report";
    private static final String ERROR_TITLE = "TallerTower Error";
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
            NativeIntegration.hookAbout(Chrystalz.application.getAboutDialog());
            NativeIntegration.hookPreferences(new PrefsRequestHandler());
            NativeIntegration.hookQuit(Chrystalz.application.getGUIManager());
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(Chrystalz.PROGRAM_NAME);
            CommonDialogs.setIcon(Application.getMicroLogo());
        } catch (final Throwable t) {
            Chrystalz.getErrorLogger().logError(t);
        }
    }
}
