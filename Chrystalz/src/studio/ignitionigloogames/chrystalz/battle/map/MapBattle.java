/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle.map;

import studio.ignitionigloogames.chrystalz.creatures.monsters.MonsterFactory;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;

public class MapBattle {
    // Fields
    private final BattleCharacter monster;

    // Constructors
    public MapBattle() {
        super();
        this.monster = new BattleCharacter(
                MonsterFactory.getNewMonsterInstance());
    }

    // Methods
    public BattleCharacter getBattlers() {
        return this.monster;
    }
}
