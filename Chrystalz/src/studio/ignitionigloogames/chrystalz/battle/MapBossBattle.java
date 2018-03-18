/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle;

import studio.ignitionigloogames.chrystalz.creatures.monsters.MonsterFactory;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;

public class MapBossBattle extends AbstractMapBattle {
    // Fields
    final BattleCharacter monster;

    // Constructors
    public MapBossBattle() {
        super();
        this.boss = true;
        this.monster = new BattleCharacter(MonsterFactory.getNewBossInstance());
    }

    // Methods
    @Override
    public BattleCharacter getBattlers() {
        return this.monster;
    }
}
