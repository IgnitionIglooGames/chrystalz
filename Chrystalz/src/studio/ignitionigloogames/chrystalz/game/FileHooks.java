package studio.ignitionigloogames.chrystalz.game;

import java.io.IOException;

import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class FileHooks {
    private FileHooks() {
        // Do nothing
    }

    public static void loadGameHook(final FileIOReader mapFile)
            throws IOException {
        PartyManager.loadGameHook(mapFile);
    }

    public static void saveGameHook(final FileIOWriter mapFile)
            throws IOException {
        PartyManager.saveGameHook(mapFile);
    }
}
