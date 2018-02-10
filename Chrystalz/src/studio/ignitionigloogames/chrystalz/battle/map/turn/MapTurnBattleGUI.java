/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.battle.map.turn;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.DrawGrid;
import studio.ignitionigloogames.chrystalz.assetmanagers.BattleImageManager;
import studio.ignitionigloogames.chrystalz.assetmanagers.ImageTransformer;
import studio.ignitionigloogames.chrystalz.assetmanagers.LogoManager;
import studio.ignitionigloogames.chrystalz.assetmanagers.MusicConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.MusicManager;
import studio.ignitionigloogames.chrystalz.battle.AbstractBattle;
import studio.ignitionigloogames.chrystalz.battle.map.MapBattleDraw;
import studio.ignitionigloogames.chrystalz.battle.map.MapBattleViewingWindowManager;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Darkness;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Wall;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

class MapTurnBattleGUI {
    // Fields
    private JFrame battleFrame;
    private MapBattleDraw battlePane;
    private JLabel messageLabel;
    private final MapBattleViewingWindowManager vwMgr;
    private final MapTurnBattleStats bs;
    private DrawGrid drawGrid;
    boolean eventHandlersOn;
    private JButton end;
    private static final int MAX_TEXT = 1000;

    // Constructors
    MapTurnBattleGUI() {
        this.vwMgr = new MapBattleViewingWindowManager();
        this.bs = new MapTurnBattleStats();
        this.setUpGUI();
        this.eventHandlersOn = true;
    }

    // Methods
    JFrame getOutputFrame() {
        return this.battleFrame;
    }

    MapBattleViewingWindowManager getViewManager() {
        return this.vwMgr;
    }

    void clearStatusMessage() {
        this.messageLabel.setText(" ");
    }

    void setStatusMessage(final String msg) {
        if (this.messageLabel.getText().length() > MapTurnBattleGUI.MAX_TEXT) {
            this.clearStatusMessage();
        }
        if (!msg.isEmpty() && !msg.matches("\\s+")) {
            this.messageLabel.setText(msg);
        }
    }

    void showBattle() {
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        MusicManager.playMusic(MusicConstants.MUSIC_BATTLE);
        this.battleFrame.setVisible(true);
        this.battleFrame.setJMenuBar(
                Chrystalz.getApplication().getMenuManager().getMainMenuBar());
    }

    void hideBattle() {
        if (this.battleFrame != null) {
            this.battleFrame.setVisible(false);
        }
    }

    void redrawBattle(final MapTurnBattleDefinitions bd) {
        // Draw the battle, if it is visible
        if (this.battleFrame.isVisible()) {
            int x, y;
            int xFix, yFix;
            final int xView = this.vwMgr.getViewingWindowLocationX();
            final int yView = this.vwMgr.getViewingWindowLocationY();
            final int xlView = this.vwMgr.getLowerRightViewingWindowLocationX();
            final int ylView = this.vwMgr.getLowerRightViewingWindowLocationY();
            for (x = xView; x <= xlView; x++) {
                for (y = yView; y <= ylView; y++) {
                    xFix = x - xView;
                    yFix = y - yView;
                    try {
                        final BufferedImageIcon icon1 = bd.getBattleDungeon()
                                .getCell(y, x, 0, DungeonConstants.LAYER_GROUND)
                                .battleRenderHook();
                        final BufferedImageIcon icon2 = bd.getBattleDungeon()
                                .getCell(y, x, 0, DungeonConstants.LAYER_OBJECT)
                                .battleRenderHook();
                        this.drawGrid.setImageCell(
                                ImageTransformer.getCompositeImage(icon1, icon2,
                                        BattleImageManager.getGraphicSize()),
                                xFix, yFix);
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        final Wall wall = new Wall();
                        this.drawGrid.setImageCell(wall.battleRenderHook(),
                                xFix, yFix);
                    } catch (final NullPointerException np) {
                        final Wall wall = new Wall();
                        this.drawGrid.setImageCell(wall.battleRenderHook(),
                                xFix, yFix);
                    }
                }
            }
            this.battlePane.repaint();
            this.battleFrame.pack();
        }
    }

    void redrawOneBattleSquare(final MapTurnBattleDefinitions bd, final int x,
            final int y, final AbstractGameObject obj3) {
        // Draw the battle, if it is visible
        if (this.battleFrame.isVisible()) {
            try {
                int xFix, yFix;
                final int xView = this.vwMgr.getViewingWindowLocationX();
                final int yView = this.vwMgr.getViewingWindowLocationY();
                xFix = y - xView;
                yFix = x - yView;
                final BufferedImageIcon icon1 = bd.getBattleDungeon()
                        .getCell(x, y, 0, DungeonConstants.LAYER_GROUND)
                        .battleRenderHook();
                final BufferedImageIcon icon2 = bd.getBattleDungeon()
                        .getCell(x, y, 0, DungeonConstants.LAYER_OBJECT)
                        .battleRenderHook();
                final BufferedImageIcon icon3 = obj3.battleRenderHook();
                this.drawGrid.setImageCell(
                        ImageTransformer.getVirtualCompositeImage(icon1, icon2,
                                icon3, BattleImageManager.getGraphicSize()),
                        xFix, yFix);
                this.battlePane.repaint();
            } catch (final ArrayIndexOutOfBoundsException ae) {
                // Do nothing
            } catch (final NullPointerException np) {
                // Do nothing
            }
            this.battleFrame.pack();
        }
    }

    void updateStatsAndEffects(final MapTurnBattleDefinitions bd) {
        this.bs.updateStats(bd.getActiveCharacter());
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        final Container borderPane = new Container();
        final Container buttonPane = new Container();
        borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.battleFrame = new JFrame("Battle");
        this.battleFrame.setContentPane(borderPane);
        this.end = new JButton("End Turn");
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(this.end);
        this.end.setFocusable(false);
        this.end.addActionListener(handler);
        int modKey;
        if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            modKey = InputEvent.META_DOWN_MASK;
        } else {
            modKey = InputEvent.CTRL_DOWN_MASK;
        }
        this.end.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_E, modKey), "End Turn");
        this.end.getActionMap().put("End Turn", handler);
        this.battleFrame.setIconImage(LogoManager.getIconLogo());
        this.battleFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.battleFrame.setResizable(false);
        this.drawGrid = new DrawGrid(
                MapBattleViewingWindowManager.getViewingWindowSize());
        for (int x = 0; x < MapBattleViewingWindowManager
                .getViewingWindowSize(); x++) {
            for (int y = 0; y < MapBattleViewingWindowManager
                    .getViewingWindowSize(); y++) {
                final AbstractGameObject dark = new Darkness().gameRenderHook(y,
                        x, 0);
                this.drawGrid.setImageCell(BattleImageManager.getImage(
                        dark.getName(), dark.getGameBaseID(),
                        AbstractGameObject.getTemplateColor()), x, y);
            }
        }
        this.battlePane = new MapBattleDraw(this.drawGrid);
        borderPane.add(this.battlePane, BorderLayout.CENTER);
        borderPane.add(buttonPane, BorderLayout.WEST);
        borderPane.add(this.messageLabel, BorderLayout.NORTH);
        borderPane.add(this.bs.getStatsPane(), BorderLayout.EAST);
        this.battleFrame.addKeyListener(handler);
    }

    void turnEventHandlersOff() {
        this.eventHandlersOn = false;
        this.end.setEnabled(false);
    }

    void turnEventHandlersOn() {
        this.eventHandlersOn = true;
        this.end.setEnabled(true);
    }

    boolean areEventHandlersOn() {
        return this.eventHandlersOn;
    }

    private class EventHandler extends AbstractAction implements KeyListener {
        private static final long serialVersionUID = 20239525230523524L;

        public EventHandler() {
            // Do nothing
        }

        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final AbstractBattle b = Chrystalz.getApplication().getBattle();
                // Do Player Actions
                b.endTurn();
            } catch (final Throwable t) {
                Chrystalz.getErrorLogger().logError(t);
            }
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (!PreferencesManager.oneMove()) {
                this.handleMovement(e);
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            if (PreferencesManager.oneMove()) {
                this.handleMovement(e);
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }

        private void handleMovement(final KeyEvent e) {
            try {
                if (System.getProperty("os.name")
                        .equalsIgnoreCase("Mac OS X")) {
                    if (e.isMetaDown()) {
                        return;
                    }
                } else {
                    if (e.isControlDown()) {
                        return;
                    }
                }
                final AbstractBattle bl = Chrystalz.getApplication()
                        .getBattle();
                final MapTurnBattleGUI bg = MapTurnBattleGUI.this;
                if (bg.eventHandlersOn) {
                    final int keyCode = e.getKeyCode();
                    switch (keyCode) {
                    case KeyEvent.VK_NUMPAD4:
                    case KeyEvent.VK_LEFT:
                    case KeyEvent.VK_A:
                        bl.updatePosition(-1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD2:
                    case KeyEvent.VK_DOWN:
                    case KeyEvent.VK_X:
                        bl.updatePosition(0, 1);
                        break;
                    case KeyEvent.VK_NUMPAD6:
                    case KeyEvent.VK_RIGHT:
                    case KeyEvent.VK_D:
                        bl.updatePosition(1, 0);
                        break;
                    case KeyEvent.VK_NUMPAD8:
                    case KeyEvent.VK_UP:
                    case KeyEvent.VK_W:
                        bl.updatePosition(0, -1);
                        break;
                    case KeyEvent.VK_NUMPAD7:
                    case KeyEvent.VK_Q:
                        bl.updatePosition(-1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD9:
                    case KeyEvent.VK_E:
                        bl.updatePosition(1, -1);
                        break;
                    case KeyEvent.VK_NUMPAD3:
                    case KeyEvent.VK_C:
                        bl.updatePosition(1, 1);
                        break;
                    case KeyEvent.VK_NUMPAD1:
                    case KeyEvent.VK_Z:
                        bl.updatePosition(-1, 1);
                        break;
                    default:
                        break;
                    }
                }
            } catch (final Exception ex) {
                Chrystalz.getErrorLogger().logError(ex);
            }
        }
    }
}
