/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.assetmanagers.ObjectImageConstants;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractMarker;

public class NoteObject extends AbstractMarker {
    // Constructors
    public NoteObject() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_NOTE;
    }

    @Override
    public String getName() {
        return "Dungeon Note";
    }

    @Override
    public String getPluralName() {
        return "Dungeon Notes";
    }

    @Override
    public String getDescription() {
        return "";
    }
}