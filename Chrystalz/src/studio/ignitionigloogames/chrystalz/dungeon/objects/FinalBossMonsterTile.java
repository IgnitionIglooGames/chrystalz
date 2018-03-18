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

public class FinalBossMonsterTile extends AbstractMovingObject {
    // Constructors
    public FinalBossMonsterTile() {
        super(false);
        this.setSavedObject(new Empty());
        this.activateTimer(1);
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        if (Chrystalz.getApplication().getMode() != Application.STATUS_BATTLE) {
            Chrystalz.getApplication().getBattle().doFinalBossBattle();
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
        return ObjectImageConstants.FINAL_BOSS;
    }

    @Override
    public String getName() {
        return "Final Boss Monster";
    }

    @Override
    public String getPluralName() {
        return "Final Boss Monsters";
    }

    @Override
    public String getDescription() {
        return "Final Boss Monsters are extremely dangerous. Encountering one starts a final boss battle.";
    }
}
