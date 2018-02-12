/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.prefs;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.manager.asset.LogoManager;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

class PreferencesGUIManager {
    // Fields
    private JFrame prefFrame;
    JCheckBox music;
    private JCheckBox sound;
    private JCheckBox checkUpdatesStartup;
    private JCheckBox moveOneAtATime;
    private ButtonGroup viewingWindowGroup;
    private JRadioButton[] viewingWindowChoices;
    private JComboBox<String> difficultyPicker;
    static final int[] VIEWING_WINDOW_SIZES = new int[] { 7, 9, 11, 13, 15, 17,
            19, 21, 23, 25 };
    private static final int[] VIEWING_WINDOW_TRIGGERS = new int[] { 0, 0, 0,
            950, 1075, 1200, 1325, 1450, 1575, 1700 };
    static final int DEFAULT_SIZE_INDEX = 2;
    static final int DEFAULT_VIEWING_WINDOW_SIZE = PreferencesGUIManager.VIEWING_WINDOW_SIZES[PreferencesGUIManager.DEFAULT_SIZE_INDEX];
    private static final String[] VIEWING_WINDOW_SIZE_NAMES = new String[] {
            "Tiny", "Small", "Medium", "Large", "Huge", "Tiny HD", "Small HD",
            "Medium HD", "Large HD", "Huge HD" };
    private static final String[] DIFFICULTY_NAMES = new String[] { "Very Easy",
            "Easy", "Normal", "Hard", "Very Hard" };
    private static final int GRID_LENGTH = 11;

    // Constructors
    public PreferencesGUIManager() {
        this.setUpGUI();
        this.setDefaultPrefs();
    }

    // Methods
    public JFrame getPrefFrame() {
        if (this.prefFrame != null && this.prefFrame.isVisible()) {
            return this.prefFrame;
        } else {
            return null;
        }
    }

    public void showPrefs() {
        final Application app = Chrystalz.getApplication();
        if (app.getMode() == Application.STATUS_BATTLE) {
            // Deny
            CommonDialogs.showTitledDialog(
                    "Preferences may NOT be changed in the middle of battle!",
                    "Battle");
        } else {
            app.setMode(Application.STATUS_PREFS);
            if (System.getProperty("os.name").startsWith("Mac OS X")) {
                this.prefFrame
                        .setJMenuBar(app.getMenuManager().getMainMenuBar());
            }
            app.getMenuManager().setPrefMenus();
            this.prefFrame.setVisible(true);
            final int formerMode = app.getFormerMode();
            app.restoreFormerMode();
            if (formerMode == Application.STATUS_GUI) {
                app.getGUIManager().hideGUITemporarily();
            } else if (formerMode == Application.STATUS_GAME) {
                app.getGameManager().hideOutput();
            }
        }
    }

    public void hidePrefs() {
        final Application app = Chrystalz.getApplication();
        this.prefFrame.setVisible(false);
        PreferencesManager.writePrefs();
        final int formerMode = app.getFormerMode();
        if (formerMode == Application.STATUS_GUI) {
            app.getGUIManager().showGUI();
        } else if (formerMode == Application.STATUS_GAME) {
            app.getGameManager().showOutput();
        }
    }

    private void loadPrefs() {
        this.music.setSelected(PreferencesManager.getMusicEnabled());
        this.checkUpdatesStartup
                .setSelected(PreferencesManager.shouldCheckUpdatesAtStartup());
        this.moveOneAtATime.setSelected(PreferencesManager.oneMove());
        try {
            this.viewingWindowChoices[PreferencesManager
                    .getViewingWindowSizeIndex()].setSelected(true);
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            this.viewingWindowChoices[PreferencesGUIManager.DEFAULT_SIZE_INDEX]
                    .setSelected(true);
            PreferencesManager.setViewingWindowSizeIndex(
                    PreferencesGUIManager.DEFAULT_SIZE_INDEX);
            PreferencesManager.writePrefs();
        }
        this.sound.setSelected(PreferencesManager.getSoundsEnabled());
        this.difficultyPicker
                .setSelectedIndex(PreferencesManager.getGameDifficulty());
    }

    public void setPrefs() {
        PreferencesManager.setMusicEnabled(this.music.isSelected());
        PreferencesManager.setCheckUpdatesAtStartup(
                this.checkUpdatesStartup.isSelected());
        PreferencesManager.setOneMove(this.moveOneAtATime.isSelected());
        final int vwSize = PreferencesManager.getViewingWindowSizeIndex();
        final int newSize = this.viewingWindowGroup.getSelection()
                .getMnemonic();
        PreferencesManager.setViewingWindowSizeIndex(newSize);
        if (vwSize != newSize) {
            Chrystalz.getApplication().getGameManager()
                    .viewingWindowSizeChanged();
            Chrystalz.getApplication().resetBattleGUI();
        }
        PreferencesManager.setSoundsEnabled(this.sound.isSelected());
        PreferencesManager
                .setGameDifficulty(this.difficultyPicker.getSelectedIndex());
        this.hidePrefs();
    }

    public final void setDefaultPrefs() {
        PreferencesManager.readPrefs();
        this.loadPrefs();
    }

    private void setUpGUI() {
        final EventHandler handler = new EventHandler();
        this.prefFrame = new JFrame("Preferences");
        final Image iconlogo = LogoManager.getIconLogo();
        this.prefFrame.setIconImage(iconlogo);
        final JTabbedPane prefTabPane = new JTabbedPane();
        final Container mainPrefPane = new Container();
        final Container mediaPane = new Container();
        final Container miscPane = new Container();
        final Container viewPane = new Container();
        prefTabPane.setOpaque(true);
        final Container buttonPane = new Container();
        final JButton prefsOK = new JButton("OK");
        prefsOK.setDefaultCapable(true);
        this.prefFrame.getRootPane().setDefaultButton(prefsOK);
        final JButton prefsCancel = new JButton("Cancel");
        prefsCancel.setDefaultCapable(false);
        this.viewingWindowGroup = new ButtonGroup();
        this.viewingWindowChoices = new JRadioButton[PreferencesGUIManager.VIEWING_WINDOW_TRIGGERS.length];
        final Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        final int minSS = Math.min(ss.width, ss.height);
        for (int z = 0; z < PreferencesGUIManager.VIEWING_WINDOW_TRIGGERS.length; z++) {
            this.viewingWindowChoices[z] = new JRadioButton(
                    PreferencesGUIManager.VIEWING_WINDOW_SIZE_NAMES[z]);
            this.viewingWindowChoices[z].setMnemonic(z);
            this.viewingWindowGroup.add(this.viewingWindowChoices[z]);
            if (minSS < PreferencesGUIManager.VIEWING_WINDOW_TRIGGERS[z]) {
                this.viewingWindowChoices[z].setEnabled(false);
            }
        }
        this.music = new JCheckBox("Enable music", true);
        this.sound = new JCheckBox("Enable sounds", true);
        this.checkUpdatesStartup = new JCheckBox("Check for Updates at Startup",
                true);
        this.moveOneAtATime = new JCheckBox("One Move at a Time", true);
        this.difficultyPicker = new JComboBox<>(
                PreferencesGUIManager.DIFFICULTY_NAMES);
        this.prefFrame.setContentPane(mainPrefPane);
        this.prefFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.prefFrame.addWindowListener(handler);
        mainPrefPane.setLayout(new BorderLayout());
        this.prefFrame.setResizable(false);
        mediaPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        mediaPane.add(this.music);
        mediaPane.add(this.sound);
        miscPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        miscPane.add(this.checkUpdatesStartup);
        miscPane.add(this.moveOneAtATime);
        miscPane.add(new JLabel("Game Difficulty"));
        miscPane.add(this.difficultyPicker);
        viewPane.setLayout(
                new GridLayout(PreferencesGUIManager.GRID_LENGTH, 1));
        viewPane.add(new JLabel("Viewing Window Size"));
        for (int z = 0; z < PreferencesGUIManager.VIEWING_WINDOW_TRIGGERS.length; z++) {
            viewPane.add(this.viewingWindowChoices[z]);
        }
        buttonPane.setLayout(new FlowLayout());
        buttonPane.add(prefsOK);
        buttonPane.add(prefsCancel);
        prefTabPane.addTab("Media", null, mediaPane, "Media");
        prefTabPane.addTab("View", null, viewPane, "View");
        prefTabPane.addTab("Misc.", null, miscPane, "Misc.");
        mainPrefPane.add(prefTabPane, BorderLayout.CENTER);
        mainPrefPane.add(buttonPane, BorderLayout.SOUTH);
        prefsOK.addActionListener(handler);
        prefsCancel.addActionListener(handler);
        this.prefFrame.pack();
    }

    private class EventHandler implements ActionListener, WindowListener {
        EventHandler() {
            // Do nothing
        }

        // Handle buttons
        @Override
        public void actionPerformed(final ActionEvent e) {
            try {
                final PreferencesGUIManager pm = PreferencesGUIManager.this;
                final String cmd = e.getActionCommand();
                if (cmd.equals("OK")) {
                    pm.setPrefs();
                } else if (cmd.equals("Cancel")) {
                    pm.hidePrefs();
                }
            } catch (final Exception ex) {
                Chrystalz.getErrorLogger().logError(ex);
            }
        }

        @Override
        public void windowOpened(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowClosing(final WindowEvent e) {
            final PreferencesGUIManager pm = PreferencesGUIManager.this;
            pm.hidePrefs();
        }

        @Override
        public void windowClosed(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowIconified(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeiconified(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowActivated(final WindowEvent e) {
            // Do nothing
        }

        @Override
        public void windowDeactivated(final WindowEvent e) {
            // Do nothing
        }
    }
}
