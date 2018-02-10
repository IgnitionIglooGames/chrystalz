/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.dungeon;

import java.io.File;

import javax.swing.JFrame;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.games.GameLoadTask;
import studio.ignitionigloogames.chrystalz.dungeon.games.GameSaveTask;
import studio.ignitionigloogames.common.fileio.FilenameChecker;

public final class DungeonManager {
    // Fields
    private Dungeon gameDungeon;
    private boolean loaded, isDirty;
    private static final String MAC_PREFIX = "HOME";
    private static final String WIN_PREFIX = "APPDATA";
    private static final String UNIX_PREFIX = "HOME";
    private static final String MAC_DIR = "/Library/Application Support/Putty Software/TallerTower/Games/";
    private static final String WIN_DIR = "\\Putty Software\\TallerTower\\Games\\";
    private static final String UNIX_DIR = "/.puttysoftware/tallertower/games/";

    // Constructors
    public DungeonManager() {
        this.loaded = false;
        this.isDirty = false;
        this.gameDungeon = null;
    }

    // Methods
    public Dungeon getDungeon() {
        return this.gameDungeon;
    }

    public void setDungeon(final Dungeon newDungeon) {
        this.gameDungeon = newDungeon;
    }

    public void handleDeferredSuccess(final boolean value,
            final boolean versionError, final File triedToLoad) {
        if (value) {
            this.setLoaded(true);
        }
        if (versionError) {
            triedToLoad.delete();
        }
        this.setDirty(false);
        Chrystalz.getApplication().getGameManager().stateChanged();
        Chrystalz.getApplication().getMenuManager().checkFlags();
    }

    public AbstractGameObject getGameObject(final int x, final int y,
            final int z, final int e) {
        try {
            return this.gameDungeon.getCell(x, y, z, e);
        } catch (final ArrayIndexOutOfBoundsException ae) {
            return null;
        }
    }

    public boolean getLoaded() {
        return this.loaded;
    }

    public void setLoaded(final boolean status) {
        final Application app = Chrystalz.getApplication();
        this.loaded = status;
        app.getMenuManager().checkFlags();
    }

    public boolean getDirty() {
        return this.isDirty;
    }

    public void setDirty(final boolean newDirty) {
        final Application app = Chrystalz.getApplication();
        this.isDirty = newDirty;
        final JFrame frame = app.getOutputFrame();
        if (frame != null) {
            frame.getRootPane().putClientProperty("Window.documentModified",
                    Boolean.valueOf(newDirty));
        }
        app.getMenuManager().checkFlags();
    }

    public boolean loadGame() {
        boolean saved = true;
        String filename;
        if (this.getDirty()) {
            saved = DungeonManager.saveGame();
        }
        if (saved) {
            final String returnVal = "save";
            final File file = new File(DungeonManager.getGameDirectory()
                    + returnVal + Extension.getGameExtensionWithPeriod());
            filename = file.getAbsolutePath();
            DungeonManager.loadFile(filename);
        }
        return false;
    }

    private static void loadFile(final String filename) {
        if (FilenameChecker.isFilenameOK(DungeonManager.getNameWithoutExtension(
                DungeonManager.getFileNameOnly(filename)))) {
            final GameLoadTask llt = new GameLoadTask(filename);
            llt.start();
        }
    }

    public static boolean saveGame() {
        String filename = "";
        String extension;
        String returnVal = "save";
        extension = Extension.getGameExtensionWithPeriod();
        final File file = new File(
                DungeonManager.getGameDirectory() + returnVal + extension);
        filename = file.getAbsolutePath();
        if (FilenameChecker.isFilenameOK(returnVal)) {
            // Make sure folder exists
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            DungeonManager.saveFile(filename);
        }
        return false;
    }

    private static void saveFile(final String filename) {
        final String sg = "Saved Game";
        Chrystalz.getApplication().showMessage("Saving " + sg + " file...");
        final GameSaveTask lst = new GameSaveTask(filename);
        lst.start();
    }

    private static String getGameDirectoryPrefix() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return System.getenv(DungeonManager.MAC_PREFIX);
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return System.getenv(DungeonManager.WIN_PREFIX);
        } else {
            // Other - assume UNIX-like
            return System.getenv(DungeonManager.UNIX_PREFIX);
        }
    }

    private static String getGameDirectoryName() {
        final String osName = System.getProperty("os.name");
        if (osName.indexOf("Mac OS X") != -1) {
            // Mac OS X
            return DungeonManager.MAC_DIR;
        } else if (osName.indexOf("Windows") != -1) {
            // Windows
            return DungeonManager.WIN_DIR;
        } else {
            // Other - assume UNIX-like
            return DungeonManager.UNIX_DIR;
        }
    }

    private static String getGameDirectory() {
        final StringBuilder b = new StringBuilder();
        b.append(DungeonManager.getGameDirectoryPrefix());
        b.append(DungeonManager.getGameDirectoryName());
        return b.toString();
    }

    private static String getNameWithoutExtension(final String s) {
        String ext = null;
        final int i = s.lastIndexOf('.');
        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(0, i);
        } else {
            ext = s;
        }
        return ext;
    }

    private static String getFileNameOnly(final String s) {
        String fno = null;
        final int i = s.lastIndexOf(File.separatorChar);
        if (i > 0 && i < s.length() - 1) {
            fno = s.substring(i + 1);
        } else {
            fno = s;
        }
        return fno;
    }
}
