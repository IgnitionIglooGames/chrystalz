/*  TallerTower: An RPG
Copyright (C) 2008-2010 Eric Ahnell

Any questions should be directed to the author via email at: TallerTower@worldwizard.net
 */
package studio.ignitionigloogames.chrystalz.dungeon.objects;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.assetmanagers.ObjectImageConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractTrap;
import studio.ignitionigloogames.chrystalz.game.GameLogicManager;
import studio.ignitionigloogames.common.random.RandomRange;

public class VariableHurtTrap extends AbstractTrap {
    // Fields
    private static final int MIN_DAMAGE = 1;

    // Constructors
    public VariableHurtTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_VARIABLE_HURT_TRAP);
    }

    @Override
    public String getName() {
        return "Variable Hurt Trap";
    }

    @Override
    public String getPluralName() {
        return "Variable Hurt Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        int maxDamage = PartyManager.getParty().getLeader().getMaximumHP() / 5;
        if (maxDamage < VariableHurtTrap.MIN_DAMAGE) {
            maxDamage = VariableHurtTrap.MIN_DAMAGE;
        }
        final RandomRange damageDealt = new RandomRange(
                VariableHurtTrap.MIN_DAMAGE, maxDamage);
        PartyManager.getParty().getLeader().doDamage(damageDealt.generate());
        SoundManager.playSound(SoundConstants.SOUND_BARRIER);
        Chrystalz.getApplication().getGameManager();
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Variable Hurt Traps hurt you when stepped on, then disappear.";
    }
}