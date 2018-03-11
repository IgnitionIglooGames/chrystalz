/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.game;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Empty;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Wall;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.TypeConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundConstants;
import studio.ignitionigloogames.chrystalz.manager.asset.SoundManager;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

final class MovementTask extends Thread {
    // Fields
    private final GameViewingWindowManager vwMgr;
    private final GameGUI gui;
    private AbstractGameObject saved;
    private boolean proceed;
    private boolean relative;
    private int moveX, moveY, moveZ;

    // Constructors
    public MovementTask(final GameViewingWindowManager view,
            final GameGUI gameGUI) {
        this.setName("Movement Handler");
        this.vwMgr = view;
        this.gui = gameGUI;
        this.saved = new Empty();
    }

    // Methods
    @Override
    public void run() {
        try {
            while (true) {
                this.waitForWork();
                if (this.relative) {
                    this.updatePositionRelative(this.moveX, this.moveY,
                            this.moveZ);
                }
                if (!this.relative) {
                    this.updatePositionAbsolute(this.moveX, this.moveY,
                            this.moveZ);
                }
            }
        } catch (final Throwable t) {
            Chrystalz.getErrorLogger().logError(t);
        }
    }

    private synchronized void waitForWork() {
        try {
            this.wait();
        } catch (final InterruptedException e) {
            // Ignore
        }
    }

    public synchronized void moveRelative(final int x, final int y,
            final int z) {
        this.moveX = x;
        this.moveY = y;
        this.moveZ = z;
        this.relative = true;
        this.notify();
    }

    public synchronized void moveAbsolute(final int x, final int y,
            final int z) {
        this.moveX = x;
        this.moveY = y;
        this.moveZ = z;
        this.relative = false;
        this.notify();
    }

    public boolean tryAbsolute(final int x, final int y, final int z) {
        try {
            final Application app = Chrystalz.getApplication();
            final Dungeon m = app.getDungeonManager().getDungeon();
            final AbstractGameObject below = m.getCell(m.getPlayerLocationX(),
                    m.getPlayerLocationY(), m.getPlayerLocationZ(),
                    DungeonConstants.LAYER_GROUND);
            final AbstractGameObject nextBelow = m.getCell(x, y, z,
                    DungeonConstants.LAYER_GROUND);
            final AbstractGameObject nextAbove = m.getCell(x, y, z,
                    DungeonConstants.LAYER_OBJECT);
            return MovementTask.checkSolidAbsolute(this.saved, below, nextBelow,
                    nextAbove);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return false;
        }
    }

    public void stopMovement() {
        this.proceed = false;
    }

    void fireStepActions() {
        final Dungeon m = Chrystalz.getApplication().getDungeonManager()
                .getDungeon();
        final int px = m.getPlayerLocationX();
        final int py = m.getPlayerLocationY();
        final int pz = m.getPlayerLocationZ();
        m.updateVisibleSquares(px, py, pz);
        m.tickTimers(pz);
        this.gui.updateStats();
        MovementTask.checkGameOver();
    }

    private static boolean checkSolidAbsolute(final AbstractGameObject inside,
            final AbstractGameObject below, final AbstractGameObject nextBelow,
            final AbstractGameObject nextAbove) {
        final boolean insideSolid = inside.isSolid();
        final boolean belowSolid = below.isSolid();
        final boolean nextBelowSolid = nextBelow.isSolid();
        final boolean nextAboveSolid = nextAbove.isSolid();
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return true;
        } else {
            return false;
        }
    }

    private void updatePositionRelative(final int dirX, final int dirY,
            final int dirZ) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        int px = m.getPlayerLocationX();
        int py = m.getPlayerLocationY();
        int pz = m.getPlayerLocationZ();
        int fX = dirX;
        int fY = dirY;
        final int fZ = dirZ;
        this.proceed = false;
        AbstractGameObject below = null;
        AbstractGameObject nextBelow = null;
        AbstractGameObject nextAbove = new Wall();
        do {
            try {
                try {
                    below = m.getCell(px, py, pz,
                            DungeonConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    below = new Empty();
                }
                try {
                    nextBelow = m.getCell(px + fX, py + fY, pz + fZ,
                            DungeonConstants.LAYER_GROUND);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextBelow = new Empty();
                }
                try {
                    nextAbove = m.getCell(px + fX, py + fY, pz + fZ,
                            DungeonConstants.LAYER_OBJECT);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    nextAbove = new Wall();
                }
                try {
                    this.proceed = nextAbove.preMoveAction(true, px + fX,
                            py + fY);
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.proceed = true;
                }
            } catch (final NullPointerException np) {
                this.proceed = false;
                nextAbove = new Empty();
            }
            if (this.proceed) {
                m.savePlayerLocation();
                this.vwMgr.saveViewingWindow();
                try {
                    if (MovementTask.checkSolid(this.saved, below, nextBelow,
                            nextAbove)) {
                        AbstractGameObject groundInto;
                        m.offsetPlayerLocationX(fX);
                        m.offsetPlayerLocationY(fY);
                        px += fX;
                        py += fY;
                        this.vwMgr.offsetViewingWindowLocationX(fY);
                        this.vwMgr.offsetViewingWindowLocationY(fX);
                        app.getDungeonManager().setDirty(true);
                        app.saveFormerMode();
                        this.fireStepActions();
                        this.redrawDungeon();
                        if (app.modeChanged()) {
                            this.proceed = false;
                        }
                        if (this.proceed) {
                            this.saved = m.getCell(px, py, pz,
                                    DungeonConstants.LAYER_OBJECT);
                            groundInto = m.getCell(px, py, pz,
                                    DungeonConstants.LAYER_GROUND);
                            if (groundInto.overridesDefaultPostMove()) {
                                groundInto.postMoveAction(false, px, py);
                                if (!this.saved.isOfType(
                                        TypeConstants.TYPE_PASS_THROUGH)) {
                                    this.saved.postMoveAction(false, px, py);
                                }
                            } else {
                                this.saved.postMoveAction(false, px, py);
                            }
                        }
                    } else {
                        // Move failed - object is solid in that direction
                        MovementTask.fireMoveFailedActions(px + fX, py + fY,
                                this.saved, below, nextBelow, nextAbove);
                        this.fireStepActions();
                    }
                } catch (final ArrayIndexOutOfBoundsException ae) {
                    this.vwMgr.restoreViewingWindow();
                    m.restorePlayerLocation();
                    // Move failed - attempted to go outside the maze
                    nextAbove.moveFailedAction(false, px, py);
                    app.showMessage("Can't go that way");
                    nextAbove = new Empty();
                    this.proceed = false;
                }
                this.fireStepActions();
            } else {
                // Move failed - pre-move check failed
                nextAbove.moveFailedAction(false, px + fX, py + fY);
                this.fireStepActions();
                this.proceed = false;
            }
            px = m.getPlayerLocationX();
            py = m.getPlayerLocationY();
            pz = m.getPlayerLocationZ();
        } while (this.checkLoopCondition(below, nextBelow, nextAbove));
    }

    private boolean checkLoopCondition(final AbstractGameObject below,
            final AbstractGameObject nextBelow,
            final AbstractGameObject nextAbove) {
        return this.proceed && !nextBelow.hasFriction() && MovementTask
                .checkSolid(this.saved, below, nextBelow, nextAbove);
    }

    private static boolean checkSolid(final AbstractGameObject inside,
            final AbstractGameObject below, final AbstractGameObject nextBelow,
            final AbstractGameObject nextAbove) {
        final boolean insideSolid = inside.isSolid();
        final boolean belowSolid = below.isSolid();
        final boolean nextBelowSolid = nextBelow.isSolid();
        final boolean nextAboveSolid = nextAbove.isSolid();
        if (insideSolid || belowSolid || nextBelowSolid || nextAboveSolid) {
            return false;
        } else {
            return true;
        }
    }

    private static void fireMoveFailedActions(final int x, final int y,
            final AbstractGameObject inside, final AbstractGameObject below,
            final AbstractGameObject nextBelow,
            final AbstractGameObject nextAbove) {
        final boolean insideSolid = inside.isSolid();
        final boolean belowSolid = below.isSolid();
        final boolean nextBelowSolid = nextBelow.isSolid();
        final boolean nextAboveSolid = nextAbove.isSolid();
        if (insideSolid) {
            inside.moveFailedAction(false, x, y);
        }
        if (belowSolid) {
            below.moveFailedAction(false, x, y);
        }
        if (nextBelowSolid) {
            nextBelow.moveFailedAction(false, x, y);
        }
        if (nextAboveSolid) {
            nextAbove.moveFailedAction(false, x, y);
        }
    }

    private void updatePositionAbsolute(final int x, final int y, final int z) {
        final Application app = Chrystalz.getApplication();
        final Dungeon m = app.getDungeonManager().getDungeon();
        try {
            m.getCell(x, y, z, DungeonConstants.LAYER_OBJECT)
                    .preMoveAction(true, x, y);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            // Ignore
        } catch (final NullPointerException np) {
            // Ignore
        }
        m.savePlayerLocation();
        this.vwMgr.saveViewingWindow();
        try {
            if (!m.getCell(x, y, z, DungeonConstants.LAYER_OBJECT).isSolid()) {
                m.setPlayerLocationX(x);
                m.setPlayerLocationY(y);
                m.setPlayerLocationZ(z);
                this.vwMgr.setViewingWindowLocationX(m.getPlayerLocationY()
                        - GameViewingWindowManager.getOffsetFactorX());
                this.vwMgr.setViewingWindowLocationY(m.getPlayerLocationX()
                        - GameViewingWindowManager.getOffsetFactorY());
                this.saved = m.getCell(m.getPlayerLocationX(),
                        m.getPlayerLocationY(), m.getPlayerLocationZ(),
                        DungeonConstants.LAYER_OBJECT);
                app.getDungeonManager().setDirty(true);
                this.saved.postMoveAction(false, x, y);
                final int px = m.getPlayerLocationX();
                final int py = m.getPlayerLocationY();
                final int pz = m.getPlayerLocationZ();
                m.updateVisibleSquares(px, py, pz);
                this.redrawDungeon();
            }
        } catch (final ArrayIndexOutOfBoundsException ae) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            app.showMessage("Can't go outside the maze");
        } catch (final NullPointerException np) {
            m.restorePlayerLocation();
            this.vwMgr.restoreViewingWindow();
            app.showMessage("Can't go outside the maze");
        }
    }

    private static void checkGameOver() {
        if (!PartyManager.getParty().isAlive()) {
            SoundManager.playSound(SoundConstants.SOUND_DEFEATED);
            CommonDialogs.showDialog(
                    "You have died! You lose 10% of your experience and all your Gold, but you are healed fully.");
            PartyManager.getParty().getLeader().onDeath(-10);
        }
    }

    private void redrawDungeon() {
        this.gui.redrawDungeon();
    }
}
