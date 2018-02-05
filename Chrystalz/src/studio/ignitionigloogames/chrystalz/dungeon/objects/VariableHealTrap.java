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

public class VariableHealTrap extends AbstractTrap {
    // Fields
    private static final int MIN_HEALING = 1;

    // Constructors
    public VariableHealTrap() {
        super(ObjectImageConstants.OBJECT_IMAGE_VARIABLE_HEAL_TRAP);
    }

    @Override
    public String getName() {
        return "Variable Heal Trap";
    }

    @Override
    public String getPluralName() {
        return "Variable Heal Traps";
    }

    @Override
    public void postMoveAction(final boolean ie, final int dirX,
            final int dirY) {
        int maxHealing = PartyManager.getParty().getLeader().getMaximumHP() / 5;
        if (maxHealing < VariableHealTrap.MIN_HEALING) {
            maxHealing = VariableHealTrap.MIN_HEALING;
        }
        final RandomRange healingGiven = new RandomRange(
                VariableHealTrap.MIN_HEALING, maxHealing);
        PartyManager.getParty().getLeader().heal(healingGiven.generate());
        SoundManager.playSound(SoundConstants.SOUND_HEAL);
        Chrystalz.getApplication().getGameManager();
        GameLogicManager.decay();
    }

    @Override
    public String getDescription() {
        return "Variable Heal Traps heal you when stepped on, then disappear.";
    }
}