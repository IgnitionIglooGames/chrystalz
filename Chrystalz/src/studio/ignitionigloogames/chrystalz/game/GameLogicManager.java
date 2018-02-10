/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.game;

import javax.swing.JFrame;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.assetmanagers.ImageTransformer;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.GenerateTask;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Empty;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Wall;

public final class GameLogicManager {
    // Fields
    private boolean savedGameFlag;
    private final GameViewingWindowManager vwMgr;
    private boolean stateChanged;
    private final GameGUIManager gui;
    private final MovementTask mt;

    // Constructors
    public GameLogicManager() {
        this.vwMgr = new GameViewingWindowManager();
        this.gui = new GameGUIManager();
        this.mt = new MovementTask(this.vwMgr, this.gui);
        this.mt.start();
        this.savedGameFlag = false;
        this.stateChanged = true;
    }

    // Methods
    public boolean newGame() {
        final JFrame owner = Chrystalz.getApplication().getOutputFrame();
        if (this.savedGameFlag) {
            if (PartyManager.getParty() != null) {
                return true;
            } else {
                return PartyManager.createParty(owner);
            }
        } else {
            return PartyManager.createParty(owner);
        }
    }

    public void enableEvents() {
        this.mt.fireStepActions();
        this.gui.enableEvents();
    }

    public void disableEvents() {
        this.gui.disableEvents();
    }

    public void stopMovement() {
        this.mt.stopMovement();
    }

    public void viewingWindowSizeChanged() {
        this.gui.viewingWindowSizeChanged();
        this.resetViewingWindow();
    }

    public void stateChanged() {
        this.stateChanged = true;
    }

    public GameViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    public void setSavedGameFlag(final boolean value) {
        this.savedGameFlag = value;
    }

    public void setStatusMessage(final String msg) {
        this.gui.setStatusMessage(msg);
    }

    public void updatePositionRelative(final int dirX, final int dirY,
            final int dirZ) {
        this.mt.moveRelative(dirX, dirY, dirZ);
    }

    public boolean tryUpdatePositionAbsolute(final int x, final int y,
            final int z) {
        return this.mt.tryAbsolute(x, y, z);
    }

    public void updatePositionAbsolute(final int x, final int y, final int z) {
        this.mt.moveAbsolute(x, y, z);
    }

    public void redrawDungeon() {
        this.gui.redrawDungeon();
    }

    public void resetViewingWindowAndPlayerLocation() {
        GameLogicManager.resetPlayerLocation();
        this.resetViewingWindow();
    }

    public void resetViewingWindow() {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (m != null && this.vwMgr != null) {
            this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                    - GameViewingWindowManager.getOffsetFactorX());
            this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                    - GameViewingWindowManager.getOffsetFactorY());
        }
    }

    public static void resetPlayerLocation() {
        GameLogicManager.resetPlayerLocation(0);
    }

    public static void resetPlayerLocation(final int level) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (m != null) {
            m.switchLevel(level);
            m.setPlayerToStart();
        }
    }

    public void goToLevelOffset(final int level) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        final boolean levelExists = m.doesLevelExistOffset(level);
        this.stopMovement();
        if (levelExists) {
            new LevelLoadTask(level).start();
        } else {
            new GenerateTask(false).start();
        }
    }

    public void exitGame() {
        this.stateChanged = true;
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        // Restore the maze
        m.restore();
        m.resetVisibleSquares();
        final boolean playerExists = m.doesPlayerExist();
        if (playerExists) {
            this.resetViewingWindowAndPlayerLocation();
        } else {
            app.getDungeonManager().setLoaded(false);
        }
        // Reset saved game flag
        this.savedGameFlag = false;
        app.getDungeonManager().setDirty(false);
        // Exit game
        this.hideOutput();
        app.getGUIManager().showGUI();
    }

    public JFrame getOutputFrame() {
        return this.gui.getOutputFrame();
    }

    public static void decay() {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        m.setCell(new Empty(), m.getPlayerLocationX(), m.getPlayerLocationY(),
                m.getPlayerLocationZ(), DungeonConstants.LAYER_OBJECT);
    }

    public static void morph(final AbstractGameObject morphInto) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        m.setCell(morphInto, m.getPlayerLocationX(), m.getPlayerLocationY(),
                m.getPlayerLocationZ(), morphInto.getLayer());
    }

    public void keepNextMessage() {
        this.gui.keepNextMessage();
    }

    public void identifyObject(final int x, final int y) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        final int xOffset = this.vwMgr.getViewingWindowLocationX()
                - GameViewingWindowManager.getOffsetFactorX();
        final int yOffset = this.vwMgr.getViewingWindowLocationY()
                - GameViewingWindowManager.getOffsetFactorY();
        final int destX = x / ImageTransformer.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationX() - xOffset + yOffset;
        final int destY = y / ImageTransformer.getGraphicSize()
                + this.vwMgr.getViewingWindowLocationY() + xOffset - yOffset;
        final int destZ = m.getPlayerLocationZ();
        try {
            final AbstractGameObject target1 = m.getCell(destX, destY, destZ,
                    DungeonConstants.LAYER_GROUND);
            final AbstractGameObject target2 = m.getCell(destX, destY, destZ,
                    DungeonConstants.LAYER_OBJECT);
            target1.determineCurrentAppearance(destX, destY, destZ);
            target2.determineCurrentAppearance(destX, destY, destZ);
            final String gameName1 = target1.getGameName();
            final String gameName2 = target2.getGameName();
            Chrystalz.getApplication()
                    .showMessage(gameName2 + " on " + gameName1);
            SoundManager.playSound(SoundConstants.SOUND_IDENTIFY);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            final Wall wall = new Wall();
            wall.determineCurrentAppearance(destX, destY, destZ);
            Chrystalz.getApplication().showMessage(wall.getGameName());
            SoundManager.playSound(SoundConstants.SOUND_IDENTIFY);
        }
    }

    public void playDungeon() {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        if (app.getDungeonManager().getLoaded()) {
            this.gui.initViewManager();
            app.getGUIManager().hideGUI();
            if (this.stateChanged) {
                // Initialize only if the maze state has changed
                app.getDungeonManager().getDungeon().switchLevel(
                        app.getDungeonManager().getDungeon().getStartLevel());
                this.stateChanged = false;
            }
            // Make sure message area is attached to the border pane
            this.gui.updateGameGUI();
            // Make sure initial area player is in is visible
            final int px = m.getPlayerLocationX();
            final int py = m.getPlayerLocationY();
            final int pz = m.getPlayerLocationZ();
            m.updateVisibleSquares(px, py, pz);
            this.showOutput();
            this.redrawDungeon();
        }
    }

    public void showOutput() {
        Chrystalz.getApplication().setMode(Application.STATUS_GAME);
        this.gui.showOutput();
    }

    public void hideOutput() {
        this.stopMovement();
        this.gui.hideOutput();
    }
}
