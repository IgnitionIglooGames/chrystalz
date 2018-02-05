package studio.ignitionigloogames.chrystalz.dungeon;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public interface SuffixIO {
    void writeSuffix(FileIOWriter writer) throws IOException;

    void readSuffix(FileIOReader reader, int formatVersion) throws IOException;
}
