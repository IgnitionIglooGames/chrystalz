package studio.ignitionigloogames.chrystalz.dungeon;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public interface PrefixIO {
    void writePrefix(FileIOWriter writer) throws IOException;

    int readPrefix(FileIOReader reader) throws IOException;
}
