/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.creatures.monsters;

import studio.ignitionigloogames.common.images.BufferedImageIcon;

abstract class AbstractBothRandomMonster extends AbstractMonster {
    // Constructors
    AbstractBothRandomMonster() {
        super();
        this.image = this.getInitialImage();
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        // FIXME: This does not work right
        return null;
    }

    @Override
    public void loadCreature() {
        this.image = this.getInitialImage();
    }
}
