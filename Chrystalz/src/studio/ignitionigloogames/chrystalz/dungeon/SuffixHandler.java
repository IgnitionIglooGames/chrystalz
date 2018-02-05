package studio.ignitionigloogames.chrystalz.dungeon;

import java.io.IOException;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.game.FileHooks;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class SuffixHandler implements SuffixIO {
    @Override
    public void readSuffix(final FileIOReader reader, final int formatVersion)
            throws IOException {
        Chrystalz.getApplication().getGameManager();
        FileHooks.loadGameHook(reader);
    }

    @Override
    public void writeSuffix(final FileIOWriter writer) throws IOException {
        Chrystalz.getApplication().getGameManager();
        FileHooks.saveGameHook(writer);
    }
}
