/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.dungeon;

import java.io.IOException;

import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;
import studio.ignitionigloogames.common.llds.CloneableObject;

public class Note extends CloneableObject {
    // Fields
    private String contents;

    // Constructor
    public Note() {
        this.contents = "Empty Note";
    }

    // Methods
    public String getContents() {
        return this.contents;
    }

    public void setContents(final String newContents) {
        this.contents = newContents;
    }

    @Override
    public Note clone() {
        final Note copy = (Note) super.clone();
        copy.contents = this.contents;
        return copy;
    }

    static Note readNote(final FileIOReader reader) throws IOException {
        final Note mn = new Note();
        mn.contents = reader.readString();
        return mn;
    }

    void writeNote(final FileIOWriter writer) throws IOException {
        writer.writeString(this.contents);
    }
}
