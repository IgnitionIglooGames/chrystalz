/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.creatures.genders;

public class Gender {
    private final int genderID;

    Gender(final int gid) {
        this.genderID = gid;
    }

    public int getGenderID() {
        return this.genderID;
    }
}
