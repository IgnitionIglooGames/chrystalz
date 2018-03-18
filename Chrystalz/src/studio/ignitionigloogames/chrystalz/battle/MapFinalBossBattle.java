/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle;

import studio.ignitionigloogames.chrystalz.creatures.monsters.MonsterFactory;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;

public class MapFinalBossBattle extends AbstractMapBattle {
    // Fields
    final BattleCharacter monster;

    // Constructors
    public MapFinalBossBattle() {
        super();
        this.boss = true;
        this.finalBoss = true;
        this.monster = new BattleCharacter(
                MonsterFactory.getNewFinalBossInstance());
    }

    // Methods
    @Override
    public BattleCharacter getBattlers() {
        return this.monster;
    }
}
