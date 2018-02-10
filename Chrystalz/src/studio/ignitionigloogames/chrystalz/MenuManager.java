/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import studio.ignitionigloogames.chrystalz.dungeon.DungeonManager;
import studio.ignitionigloogames.chrystalz.dungeon.GenerateTask;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;

public class MenuManager {
    // Fields
    JMenuBar mainMenuBar;
    private JMenuItem fileOpenGame, fileClose, fileSaveGame, filePreferences,
            fileExit;
    private JMenuItem gameNewGame;
    private JMenuItem helpAbout;
    private KeyStroke fileOpenGameAccel, fileCloseAccel, fileSaveGameAccel,
            filePreferencesAccel;
    private KeyStroke gameNewGameAccel;
    private final EventHandler handler;

    // Constructors
    public MenuManager() {
        this.handler = new EventHandler();
        this.createAccelerators();
        this.createMenus();
        this.setInitialMenuState();
    }

    // Methods
    public JMenuBar getMainMenuBar() {
        return this.mainMenuBar;
    }

    public void setGameMenus() {
        this.fileOpenGame.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.gameNewGame.setEnabled(false);
        this.checkFlags();
    }

    public void setPrefMenus() {
        this.fileOpenGame.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(false);
        this.gameNewGame.setEnabled(false);
    }

    public void setHelpMenus() {
        this.fileOpenGame.setEnabled(false);
        this.fileClose.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(false);
        this.gameNewGame.setEnabled(false);
    }

    public void setMainMenus() {
        this.fileOpenGame.setEnabled(true);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.gameNewGame.setEnabled(true);
        this.checkFlags();
    }

    public void checkFlags() {
        final Application app = Chrystalz.getApplication();
        if (app.getDungeonManager().getDirty()) {
            this.setMenusDirtyOn();
        } else {
            this.setMenusDirtyOff();
        }
        if (app.getDungeonManager().getLoaded()) {
            this.setMenusLoadedOn();
        } else {
            this.setMenusLoadedOff();
        }
    }

    private void setMenusDirtyOn() {
        this.fileSaveGame.setEnabled(true);
    }

    private void setMenusDirtyOff() {
        this.fileSaveGame.setEnabled(false);
    }

    private void setMenusLoadedOn() {
        final Application app = Chrystalz.getApplication();
        if (app.getMode() == Application.STATUS_GUI) {
            this.fileClose.setEnabled(false);
        } else {
            this.fileClose.setEnabled(true);
        }
    }

    private void setMenusLoadedOff() {
        this.fileClose.setEnabled(false);
    }

    private void createAccelerators() {
        int modKey;
        if (System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            modKey = InputEvent.META_DOWN_MASK;
        } else {
            modKey = InputEvent.CTRL_DOWN_MASK;
        }
        this.fileOpenGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_O, modKey);
        this.fileCloseAccel = KeyStroke.getKeyStroke(KeyEvent.VK_W, modKey);
        this.fileSaveGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_S, modKey);
        this.filePreferencesAccel = KeyStroke.getKeyStroke(KeyEvent.VK_COMMA,
                modKey);
        this.gameNewGameAccel = KeyStroke.getKeyStroke(KeyEvent.VK_N, modKey);
    }

    private void createMenus() {
        this.mainMenuBar = new JMenuBar();
        final JMenu fileMenu = new JMenu("File");
        final JMenu gameMenu = new JMenu("Game");
        final JMenu helpMenu = new JMenu("Help");
        this.fileOpenGame = new JMenuItem("Open Game...");
        this.fileOpenGame.setAccelerator(this.fileOpenGameAccel);
        this.fileClose = new JMenuItem("Close");
        this.fileClose.setAccelerator(this.fileCloseAccel);
        this.fileSaveGame = new JMenuItem("Save Game...");
        this.fileSaveGame.setAccelerator(this.fileSaveGameAccel);
        this.fileExit = new JMenuItem("Exit");
        this.filePreferences = new JMenuItem("Preferences...");
        this.filePreferences.setAccelerator(this.filePreferencesAccel);
        this.gameNewGame = new JMenuItem("New Game");
        this.gameNewGame.setAccelerator(this.gameNewGameAccel);
        this.helpAbout = new JMenuItem("About TallerTower...");
        this.fileOpenGame.addActionListener(this.handler);
        this.fileClose.addActionListener(this.handler);
        this.fileSaveGame.addActionListener(this.handler);
        this.fileExit.addActionListener(this.handler);
        this.filePreferences.addActionListener(this.handler);
        this.gameNewGame.addActionListener(this.handler);
        this.helpAbout.addActionListener(this.handler);
        fileMenu.add(this.fileOpenGame);
        fileMenu.add(this.fileClose);
        fileMenu.add(this.fileSaveGame);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            fileMenu.add(this.filePreferences);
            fileMenu.add(this.fileExit);
        }
        gameMenu.add(this.gameNewGame);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            helpMenu.add(this.helpAbout);
        }
        this.mainMenuBar.add(fileMenu);
        this.mainMenuBar.add(gameMenu);
        if (!System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            this.mainMenuBar.add(helpMenu);
        }
    }

    private void setInitialMenuState() {
        this.fileOpenGame.setEnabled(true);
        this.fileClose.setEnabled(false);
        this.fileSaveGame.setEnabled(false);
        this.fileExit.setEnabled(true);
        this.filePreferences.setEnabled(true);
        this.gameNewGame.setEnabled(true);
        this.helpAbout.setEnabled(true);
    }

    private class EventHandler implements ActionListener {
        public EventHandler() {
            // Do nothing
        }

        // Handle menus
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final String cmd = e.getActionCommand();
                final Application app = Chrystalz.getApplication();
                boolean loaded = false;
                if (cmd.equals("Open Game...")) {
                    loaded = app.getDungeonManager().loadGame();
                    app.getDungeonManager().setLoaded(loaded);
                } else if (cmd.equals("Close")) {
                    // Close the window
                    if (app.getMode() == Application.STATUS_GAME) {
                        boolean saved = true;
                        if (app.getDungeonManager().getDirty()) {
                            saved = DungeonManager.saveGame();
                        }
                        if (saved) {
                            app.getGameManager().exitGame();
                        }
                    }
                } else if (cmd.equals("Save Game...")) {
                    if (app.getDungeonManager().getLoaded()) {
                        app.getDungeonManager();
                        DungeonManager.saveGame();
                    }
                } else if (cmd.equals("Exit")) {
                    // Exit program
                    System.exit(0);
                } else if (cmd.equals("Preferences...")) {
                    // Show preferences dialog
                    PreferencesManager.showPrefs();
                } else if (cmd.equals("New Game")) {
                    // Start a new game
                    final boolean proceed = app.getGameManager().newGame();
                    if (proceed) {
                        new GenerateTask(true).start();
                    }
                } else if (cmd.equals("About TallerTower...")) {
                    app.getAboutDialog().showAboutDialog();
                }
                MenuManager.this.checkFlags();
            } catch (final Throwable t) {
                Chrystalz.getErrorLogger().logError(t);
            }
        }
    }
}
