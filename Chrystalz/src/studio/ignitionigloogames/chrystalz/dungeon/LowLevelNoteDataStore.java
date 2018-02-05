/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.dungeon;

import studio.ignitionigloogames.common.llds.LowLevelObjectDataStore;

class LowLevelNoteDataStore extends LowLevelObjectDataStore {
    // Constructor
    LowLevelNoteDataStore(final int... shape) {
        super(shape);
    }

    // Methods
    public Note getNote(final int... loc) {
        return (Note) this.getCell(loc);
    }

    public void setNote(final Note obj, final int... loc) {
        this.setCell(obj, loc);
    }
}
