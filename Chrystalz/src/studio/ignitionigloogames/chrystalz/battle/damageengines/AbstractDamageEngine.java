/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.battle.damageengines;

import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;

public abstract class AbstractDamageEngine {
    // Methods
    public abstract int computeDamage(AbstractCreature enemy,
            AbstractCreature acting);

    public abstract boolean enemyDodged();

    public abstract boolean weaponMissed();

    public abstract boolean weaponCrit();

    public abstract boolean weaponPierce();

    public abstract boolean weaponFumble();

    public static AbstractDamageEngine getPlayerInstance() {
        return new NormalDamageEngine();
    }

    public static AbstractDamageEngine getEnemyInstance() {
        return new NormalDamageEngine();
    }
}
