/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractMovingObject;
import studio.ignitionigloogames.chrystalz.manager.asset.ObjectImageConstants;
import studio.ignitionigloogames.common.random.RandomRange;

public class Monster extends AbstractMovingObject {
    // Constructors
    public Monster() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        if (Chrystalz.getApplication().getMode() != Application.STATUS_BATTLE) {
            Chrystalz.getApplication().getBattle().doBattle();
            Chrystalz.getApplication().getDungeonManager().getDungeon()
                    .postBattle(this, dirX, dirY, true);
        }
    }

    @Override
    public void timerExpiredAction(final int dirX, final int dirY) {
        // Move the monster
        final RandomRange r = new RandomRange(0, 7);
        final int move = r.generate();
        Chrystalz.getApplication().getDungeonManager().getDungeon()
                .updateMonsterPosition(move, dirX, dirY, this);
        this.activateTimer(1);
    }

    @Override
    public int getBaseID() {
        return ObjectImageConstants.OBJECT_IMAGE_NONE;
    }

    @Override
    public String getName() {
        return "Monster";
    }

    @Override
    public String getPluralName() {
        return "Monsters";
    }

    @Override
    public String getDescription() {
        return "Monsters are dangerous. Encountering one starts a battle.";
    }
}
