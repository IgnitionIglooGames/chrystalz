/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz;

import java.awt.Image;

import javax.swing.JFrame;

import studio.ignitionigloogames.chrystalz.assetmanagers.LogoManager;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundManager;
import studio.ignitionigloogames.chrystalz.battle.AbstractBattle;
import studio.ignitionigloogames.chrystalz.battle.map.turn.MapTurnBattleLogic;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonManager;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.GameObjectList;
import studio.ignitionigloogames.chrystalz.game.GameLogicManager;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.chrystalz.shops.Shop;
import studio.ignitionigloogames.chrystalz.shops.ShopTypes;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public final class Application {
    // Fields
    private AboutDialog about;
    private GameLogicManager gameMgr;
    private DungeonManager mazeMgr;
    private MenuManager menuMgr;
    private GUIManager guiMgr;
    private final GameObjectList objects;
    private Shop weapons, armor, healer;
    private MapTurnBattleLogic mapTurnBattle;
    private int currentMode;
    private int formerMode;
    private static final int VERSION_MAJOR = 1;
    private static final int VERSION_MINOR = 0;
    private static final int VERSION_BUGFIX = 0;
    public static final int STATUS_GUI = 0;
    public static final int STATUS_GAME = 1;
    public static final int STATUS_BATTLE = 2;
    public static final int STATUS_PREFS = 3;
    public static final int STATUS_NULL = 4;

    // Constructors
    public Application() {
        this.objects = new GameObjectList();
        this.currentMode = Application.STATUS_NULL;
        this.formerMode = Application.STATUS_NULL;
    }

    // Methods
    void postConstruct() {
        // Create Managers
        this.about = new AboutDialog(Application.getVersionString());
        this.guiMgr = new GUIManager();
        this.menuMgr = new MenuManager();
        this.mapTurnBattle = new MapTurnBattleLogic();
        this.weapons = new Shop(ShopTypes.SHOP_TYPE_WEAPONS);
        this.armor = new Shop(ShopTypes.SHOP_TYPE_ARMOR);
        this.healer = new Shop(ShopTypes.SHOP_TYPE_HEALER);
        // Cache Logo
        this.guiMgr.updateLogo();
    }

    public void setMode(final int newMode) {
        this.formerMode = this.currentMode;
        this.currentMode = newMode;
    }

    public int getMode() {
        return this.currentMode;
    }

    public int getFormerMode() {
        return this.formerMode;
    }

    public boolean modeChanged() {
        return this.formerMode != this.currentMode;
    }

    public void saveFormerMode() {
        this.formerMode = this.currentMode;
    }

    public void restoreFormerMode() {
        this.currentMode = this.formerMode;
    }

    public void showMessage(final String msg) {
        if (this.currentMode == Application.STATUS_GAME) {
            this.getGameManager().setStatusMessage(msg);
        } else if (this.currentMode == Application.STATUS_BATTLE) {
            this.getBattle().setStatusMessage(msg);
        }
    }

    public MenuManager getMenuManager() {
        return this.menuMgr;
    }

    public GUIManager getGUIManager() {
        return this.guiMgr;
    }

    public GameLogicManager getGameManager() {
        if (this.gameMgr == null) {
            this.gameMgr = new GameLogicManager();
        }
        return this.gameMgr;
    }

    public DungeonManager getDungeonManager() {
        if (this.mazeMgr == null) {
            this.mazeMgr = new DungeonManager();
        }
        return this.mazeMgr;
    }

    public AboutDialog getAboutDialog() {
        return this.about;
    }

    public static BufferedImageIcon getMicroLogo() {
        return LogoManager.getMicroLogo();
    }

    public static Image getIconLogo() {
        return LogoManager.getIconLogo();
    }

    public static void playLogoSound() {
        SoundManager.playSound(SoundConstants.SOUND_LOGO);
    }

    private static String getVersionString() {
        return Application.VERSION_MAJOR + "." + Application.VERSION_MINOR + "."
                + Application.VERSION_BUGFIX;
    }

    public JFrame getOutputFrame() {
        try {
            if (this.getMode() == Application.STATUS_PREFS) {
                return PreferencesManager.getPrefFrame();
            } else if (this.getMode() == Application.STATUS_GUI) {
                return this.getGUIManager().getGUIFrame();
            } else if (this.getMode() == Application.STATUS_GAME) {
                return this.getGameManager().getOutputFrame();
            } else if (this.getMode() == Application.STATUS_BATTLE) {
                return this.getBattle().getOutputFrame();
            } else {
                return null;
            }
        } catch (final NullPointerException npe) {
            return null;
        }
    }

    public GameObjectList getObjects() {
        return this.objects;
    }

    public Shop getGenericShop(final int shopType) {
        this.getGameManager().stopMovement();
        switch (shopType) {
        case ShopTypes.SHOP_TYPE_ARMOR:
            return this.armor;
        case ShopTypes.SHOP_TYPE_HEALER:
            return this.healer;
        case ShopTypes.SHOP_TYPE_WEAPONS:
            return this.weapons;
        default:
            // Invalid shop type
            return null;
        }
    }

    public AbstractBattle getBattle() {
        return this.mapTurnBattle;
    }

    public void resetBattleGUI() {
        this.mapTurnBattle.resetGUI();
    }
}
