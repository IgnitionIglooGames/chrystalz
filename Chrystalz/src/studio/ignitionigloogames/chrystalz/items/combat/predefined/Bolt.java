/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.items.combat.predefined;

import studio.ignitionigloogames.chrystalz.assetmanagers.SoundConstants;
import studio.ignitionigloogames.chrystalz.battle.BattleTarget;
import studio.ignitionigloogames.chrystalz.creatures.StatConstants;
import studio.ignitionigloogames.chrystalz.effects.Effect;
import studio.ignitionigloogames.chrystalz.items.combat.CombatItem;

public class Bolt extends CombatItem {
    public Bolt() {
        super("Bolt", 100, BattleTarget.ENEMY);
    }

    @Override
    protected void defineFields() {
        this.sound = SoundConstants.SOUND_BOLT;
        this.e = new Effect("Bolt", 1);
        this.e.setEffect(Effect.EFFECT_ADD, StatConstants.STAT_CURRENT_HP, -4);
        this.e.setScaleStat(StatConstants.STAT_LEVEL);
        this.e.setScaleFactor(1.0);
        this.e.setMessage(Effect.MESSAGE_INITIAL,
                "You throw a bolt at the enemy!");
        this.e.setMessage(Effect.MESSAGE_SUBSEQUENT,
                "The bolt ZAPS the enemy, dealing damage!");
    }
}
