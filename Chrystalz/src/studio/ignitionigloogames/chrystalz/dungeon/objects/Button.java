/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.assetmanagers.ObjectImageConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundManager;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractTrigger;
import studio.ignitionigloogames.common.random.RandomRange;

public class Button extends AbstractTrigger {
    // Constructors
    public Button() {
        super();
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_BUTTON;
    }

    @Override
    public String getName() {
        return "Button";
    }

    @Override
    public String getPluralName() {
        return "Buttons";
    }

    @Override
    public String getDescription() {
        return "Buttons toggle Walls On and Walls Off.";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        SoundManager.playSound(SoundConstants.SOUND_BUTTON);
        final Application app = Chrystalz.getApplication();
        app.getDungeonManager().getDungeon().fullScanButton(this.getLayer());
        app.getGameManager().redrawDungeon();
    }

    @Override
    public boolean shouldGenerateObject(final Dungeon maze, final int row,
            final int col, final int floor, final int level, final int layer) {
        // Generate Buttons at 50% rate
        final RandomRange reject = new RandomRange(1, 100);
        return reject.generate() < 50;
    }
}