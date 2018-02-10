/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.game;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.DrawGrid;
import studio.ignitionigloogames.chrystalz.assetmanagers.ImageTransformer;
import studio.ignitionigloogames.chrystalz.assetmanagers.MusicConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.MusicManager;
import studio.ignitionigloogames.chrystalz.assetmanagers.ObjectImageManager;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonConstants;
import studio.ignitionigloogames.chrystalz.dungeon.DungeonManager;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Darkness;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Player;
import studio.ignitionigloogames.chrystalz.dungeon.objects.Wall;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.ImageColorConstants;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

class GameGUIManager {
    // Fields
    private JFrame outputFrame;
    private Container borderPane;
    private JLabel messageLabel;
    private GameViewingWindowManager vwMgr = null;
    private final StatGUI sg;
    private DrawGrid drawGrid;
    private GameDraw outputPane;
    private boolean knm;
    private boolean deferredRedraw;
    boolean eventFlag;
    private static Darkness DARK = new Darkness();

    // Constructors
    public GameGUIManager() {
        this.deferredRedraw = false;
        this.eventFlag = true;
        this.sg = new StatGUI();
    }

    // Methods
    public void updateStats() {
        this.sg.updateStats();
    }

    public void enableEvents() {
        this.outputFrame.setEnabled(true);
        this.eventFlag = true;
    }

    public void disableEvents() {
        this.outputFrame.setEnabled(false);
        this.eventFlag = false;
    }

    void initViewManager() {
        if (this.vwMgr == null) {
            this.vwMgr = Chrystalz.getApplication().getGameManager()
                    .getViewManager();
            this.setUpGUI();
        }
    }

    void viewingWindowSizeChanged() {
        this.setUpGUI();
        this.updateGameGUI();
        this.deferredRedraw = true;
    }

    public JFrame getOutputFrame() {
        return this.outputFrame;
    }

    public void showOutput() {
        final Application app = Chrystalz.getApplication();
        if (MusicManager.isMusicPlaying()) {
            MusicManager.stopMusic();
        }
        MusicManager.playMusic(MusicConstants.MUSIC_DUNGEON);
        if (!this.outputFrame.isVisible()) {
            app.getMenuManager().setGameMenus();
            this.outputFrame.setVisible(true);
            this.outputFrame.setJMenuBar(app.getMenuManager().getMainMenuBar());
            if (this.deferredRedraw) {
                this.deferredRedraw = false;
                this.redrawDungeon();
            }
            this.updateStats();
        }
    }

    public void hideOutput() {
        if (this.outputFrame != null) {
            this.outputFrame.setVisible(false);
        }
    }

    public void setStatusMessage(final String msg) {
        this.messageLabel.setText(msg);
    }

    private void resetBorderPane() {
        this.borderPane.removeAll();
        this.borderPane.add(this.outputPane, BorderLayout.CENTER);
        this.borderPane.add(this.messageLabel, BorderLayout.NORTH);
        this.borderPane.add(this.sg.getStatsPane(), BorderLayout.EAST);
    }

    public void redrawDungeon() {
        // Draw the maze, if it is visible
        if (this.outputFrame.isVisible()) {
            final Application app = Chrystalz.getApplication();
            final Dungeon m = app.getDungeonManager().getDungeon();
            int x, y, u, v;
            int xFix, yFix;
            boolean visible;
            u = m.getPlayerLocationX();
            v = m.getPlayerLocationY();
            final AbstractGameObject wall = new Wall();
            for (x = this.vwMgr.getViewingWindowLocationX(); x <= this.vwMgr
                    .getLowerRightViewingWindowLocationX(); x++) {
                for (y = this.vwMgr.getViewingWindowLocationY(); y <= this.vwMgr
                        .getLowerRightViewingWindowLocationY(); y++) {
                    xFix = x - this.vwMgr.getViewingWindowLocationX();
                    yFix = y - this.vwMgr.getViewingWindowLocationY();
                    visible = app.getDungeonManager().getDungeon()
                            .isSquareVisible(u, v, y, x);
                    try {
                        if (visible) {
                            final AbstractGameObject obj1 = m
                                    .getCell(y, x, m.getPlayerLocationZ(),
                                            DungeonConstants.LAYER_GROUND)
                                    .gameRenderHook(y, x,
                                            m.getPlayerLocationZ());
                            final AbstractGameObject obj2 = m
                                    .getCell(y, x, m.getPlayerLocationZ(),
                                            DungeonConstants.LAYER_OBJECT)
                                    .gameRenderHook(y, x,
                                            m.getPlayerLocationZ());
                            final BufferedImageIcon img1 = ObjectImageManager
                                    .getImage(obj1.getName(),
                                            obj1.getGameBaseID(),
                                            AbstractGameObject
                                                    .getTemplateColor());
                            final BufferedImageIcon img2 = ObjectImageManager
                                    .getImage(obj2.getName(),
                                            obj2.getGameBaseID(),
                                            AbstractGameObject
                                                    .getTemplateColor());
                            if (u == y && v == x) {
                                final AbstractGameObject obj3 = new Player()
                                        .gameRenderHook(y, x,
                                                m.getPlayerLocationZ());
                                final BufferedImageIcon img3 = ObjectImageManager
                                        .getImage(obj3.getName(),
                                                obj3.getGameBaseID(),
                                                AbstractGameObject
                                                        .getTemplateColor());
                                this.drawGrid.setImageCell(ImageTransformer
                                        .getVirtualCompositeImage(img1, img2,
                                                img3,
                                                ImageTransformer
                                                        .getGraphicSize()),
                                        xFix, yFix);
                            } else {
                                this.drawGrid.setImageCell(
                                        ImageTransformer.getCompositeImage(img1,
                                                img2,
                                                ImageTransformer
                                                        .getGraphicSize()),
                                        xFix, yFix);
                            }
                        } else {
                            this.drawGrid.setImageCell(
                                    ObjectImageManager.getImage(
                                            GameGUIManager.DARK.getName(),
                                            GameGUIManager.DARK.getBaseID(),
                                            ImageColorConstants.COLOR_NONE),
                                    xFix, yFix);
                        }
                    } catch (final ArrayIndexOutOfBoundsException ae) {
                        final AbstractGameObject obj = wall.gameRenderHook(y, x,
                                m.getPlayerLocationZ());
                        this.drawGrid.setImageCell(
                                ObjectImageManager.getImage(obj.getName(),
                                        obj.getGameBaseID(),
                                        AbstractGameObject.getTemplateColor()),
                                xFix, yFix);
                    } catch (final NullPointerException np) {
                        final AbstractGameObject obj = wall.gameRenderHook(y, x,
                                m.getPlayerLocationZ());
                        this.drawGrid.setImageCell(
                                ObjectImageManager.getImage(obj.getName(),
                                        obj.getGameBaseID(),
                                        AbstractGameObject.getTemplateColor()),
                                xFix, yFix);
                    }
                }
            }
            if (this.knm) {
                this.knm = false;
            } else {
                this.setStatusMessage(" ");
            }
            this.outputPane.repaint();
            this.outputFrame.pack();
            this.showOutput();
        }
    }

    public void keepNextMessage() {
        this.knm = true;
    }

    void updateGameGUI() {
        this.resetBorderPane();
        this.sg.updateImages();
        this.sg.updateStats();
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        this.borderPane = new Container();
        this.borderPane.setLayout(new BorderLayout());
        this.messageLabel = new JLabel(" ");
        this.messageLabel.setOpaque(true);
        this.outputFrame = new JFrame("TallerTower");
        final Image iconlogo = Application.getIconLogo();
        this.outputFrame.setIconImage(iconlogo);
        this.drawGrid = new DrawGrid(PreferencesManager.getViewingWindowSize());
        this.outputPane = new GameDraw(this.drawGrid);
        this.outputFrame.setContentPane(this.borderPane);
        this.outputFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.outputFrame.setResizable(false);
        this.outputFrame.addKeyListener(handler);
        this.outputFrame.addWindowListener(handler);
        this.outputPane.addMouseListener(handler);
    }

    private class EventHandler
            implements KeyListener, WindowListener, MouseListener {
        EventHandler() {
            // Do nothing
        }

        @Override
        public void keyPressed(final KeyEvent e) {
            if (GameGUIManager.this.eventFlag) {
                if (!PreferencesManager.oneMove()) {
                    this.handleMovement(e);
                }
            }
        }

        @Override
        public void keyReleased(final KeyEvent e) {
            if (GameGUIManager.this.eventFlag) {
                if (PreferencesManager.oneMove()) {
                    this.handleMovement(e);
                }
            }
        }

        @Override
        public void keyTyped(final KeyEvent e) {
            // Do nothing
        }

        public void handleMovement(final KeyEvent e) {
            try {
                final GameLogicManager glm = Chrystalz.getApplication()
                        .getGameManager();
                final int keyCode = e.getKeyCode();
                switch (keyCode) {
                case KeyEvent.VK_LEFT:
                    if (e.isShiftDown()) {
                        glm.updatePositionRelative(-1, -1, 0);
                    } else {
                        glm.updatePositionRelative(-1, 0, 0);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (e.isShiftDown()) {
                        glm.updatePositionRelative(-1, 1, 0);
                    } else {
                        glm.updatePositionRelative(0, 1, 0);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (e.isShiftDown()) {
                        glm.updatePositionRelative(1, 1, 0);
                    } else {
                        glm.updatePositionRelative(1, 0, 0);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (e.isShiftDown()) {
                        glm.updatePositionRelative(1, -1, 0);
                    } else {
                        glm.updatePositionRelative(0, -1, 0);
                    }
                    break;
                case KeyEvent.VK_ENTER:
                    if (e.isShiftDown()) {
                        glm.updatePositionRelative(0, 0, 0);
                    }
                    break;
                case KeyEvent.VK_NUMPAD7:
                case KeyEvent.VK_Q:
                    glm.updatePositionRelative(-1, -1, 0);
                    break;
                case KeyEvent.VK_NUMPAD8:
                case KeyEvent.VK_W:
                    glm.updatePositionRelative(0, -1, 0);
                    break;
                case KeyEvent.VK_NUMPAD9:
                case KeyEvent.VK_E:
                    glm.updatePositionRelative(1, -1, 0);
                    break;
                case KeyEvent.VK_NUMPAD4:
                case KeyEvent.VK_A:
                    glm.updatePositionRelative(-1, 0, 0);
                    break;
                case KeyEvent.VK_NUMPAD5:
                case KeyEvent.VK_S:
                    glm.updatePositionRelative(0, 0, 0);
                    break;
                case KeyEvent.VK_NUMPAD6:
                case KeyEvent.VK_D:
                    glm.updatePositionRelative(1, 0, 0);
                    break;
                case KeyEvent.VK_NUMPAD1:
                case KeyEvent.VK_Z:
                    glm.updatePositionRelative(-1, 1, 0);
                    break;
                case KeyEvent.VK_NUMPAD2:
                case KeyEvent.VK_X:
                    glm.updatePositionRelative(0, 1, 0);
                    break;
                case KeyEvent.VK_NUMPAD3:
                case KeyEvent.VK_C:
                    glm.updatePositionRelative(1, 1, 0);
                    break;
                default:
                    break;
                }
            } catch (final Exception ex) {
                Chrystalz.getErrorLogger().logError(ex);
            }
        }

        // Handle windows
        @Override
        public void windowActivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosed(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent we) {
            try {
                final Application app = Chrystalz.getApplication();
                boolean success = false;
                if (app.getDungeonManager().getDirty()) {
                    success = DungeonManager.saveGame();
                    if (success) {
                        app.getGameManager().exitGame();
                    }
                } else {
                    app.getGameManager().exitGame();
                }
            } catch (final Exception ex) {
                Chrystalz.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void windowDeactivated(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent we) {
            // Do nothing
        }

        @Override
        public void windowOpened(final WindowEvent we) {
            // Do nothing
        }

        // handle mouse
        @Override
        public void mousePressed(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseReleased(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseClicked(final MouseEvent e) {
            try {
                final GameLogicManager gm = Chrystalz.getApplication()
                        .getGameManager();
                if (e.isShiftDown()) {
                    final int x = e.getX();
                    final int y = e.getY();
                    gm.identifyObject(x, y);
                }
            } catch (final Exception ex) {
                Chrystalz.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void mouseEntered(final MouseEvent e) {
            // Do nothing
        }

        @Override
        public void mouseExited(final MouseEvent e) {
            // Do nothing
        }
    }
}
