/*  TallerTower: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: TallerTower@worldwizard.net
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.assetmanagers.ObjectImageConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundManager;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractTrap;
import studio.ignitionigloogames.common.random.RandomRange;

public class WarpTrap extends AbstractTrap {
    // Constructors
    public WarpTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_WARP_TRAP);
    }

    @Override
    public String getName() {
        return "Warp Trap";
    }

    @Override
    public String getPluralName() {
        return "Warp Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        RandomRange rr, rc, rf;
        final Application app = Chrystalz.getApplication();
        int maxRow, maxCol, maxFloor, rRow, rCol, rFloor;
        maxRow = app.getDungeonManager().getDungeon().getRows() - 1;
        rr = new RandomRange(0, maxRow);
        maxCol = app.getDungeonManager().getDungeon().getColumns() - 1;
        rc = new RandomRange(0, maxCol);
        maxFloor = app.getDungeonManager().getDungeon().getFloors() - 1;
        rf = new RandomRange(0, maxFloor);
        app.getGameManager();
        do {
            rRow = rr.generate();
            rCol = rc.generate();
            rFloor = rf.generate();
        } while (app.getGameManager().tryUpdatePositionAbsolute(rRow, rCol,
                rFloor));
        app.getGameManager().updatePositionAbsolute(rRow, rCol, rFloor);
        SoundManager.playSound(SoundConstants.SOUND_TELEPORT);
    }

    @Override
    public String getDescription() {
        return "Warp Traps send anything that steps on one to a random location.";
    }
}
