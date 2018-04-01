--[[
Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
]]

function love.draw()
    love.graphics.print("Hello World", 400, 300)
end

--[[ Old Java Code...

import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.prefs.PrefsRequestHandler;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

public class Chrystalz {
    // Constants
    private static Application application;
    private static final int BATTLE_MAP_SIZE = 10;
    private static final int DUNGEON_BASE_SIZE = 24;
    private static final int DUNGEON_SIZE_INCREMENT = 2;

    // Methods
    public static Application getApplication() {
        return Chrystalz.application;
    }

    public static int getDungeonLevelSize(final int zoneID) {
        return Chrystalz.DUNGEON_BASE_SIZE
                + (zoneID * Chrystalz.DUNGEON_SIZE_INCREMENT);
    }

    public static int getBattleDungeonSize() {
        return Chrystalz.BATTLE_MAP_SIZE;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(Chrystalz.BATTLE_MAP_SIZE,
                Chrystalz.BATTLE_MAP_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            Chrystalz.preInit();
            // Integrate with host platform
            NativeIntegration.hookLAF(Chrystalz.PROGRAM_NAME);
            Chrystalz.application = new Application();
            Chrystalz.application.postConstruct();
            Chrystalz.application.getGUIManager();
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
]]
