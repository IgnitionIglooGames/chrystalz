/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.spells;

import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundManager;
import studio.ignitionigloogames.chrystalz.battle.BattleTarget;
import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.effects.Effect;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;

public class SpellCaster {
    // Fields
    // Private Constructor
    private SpellCaster() {
        // Do nothing
    }

    public static boolean selectAndCastSpell(final AbstractCreature caster) {
        boolean result = false;
        final Spell s = SpellCaster.selectSpell(caster);
        if (s != null) {
            SpellCaster.castSpell(s, caster);
        }
        return result;
    }

    public static boolean castSpell(final Spell cast,
            final AbstractCreature caster) {
        if (cast != null) {
            final int casterMP = caster.getCurrentMP();
            final int cost = cast.getCost();
            if (casterMP >= cost) {
                // Cast Spell
                caster.drain(cost);
                final Effect b = cast.getEffect();
                // Play spell's associated sound effect, if it has one
                final int snd = cast.getSound();
                SoundManager.playSound(snd);
                b.resetEffect();
                final AbstractCreature target = SpellCaster.resolveTarget(cast,
                        caster.getTeamID());
                if (target.isEffectActive(b)) {
                    target.extendEffect(b, b.getInitialRounds());
                } else {
                    b.restoreEffect();
                    target.applyEffect(b);
                }
                return true;
            } else {
                // Not enough MP
                return false;
            }
        } else {
            return false;
        }
    }

    private static Spell selectSpell(final AbstractCreature caster) {
        final SpellBook book = caster.getSpellBook();
        if (book != null) {
            final String[] names = book.getAllSpellNames();
            final String[] displayNames = book.getAllSpellNamesWithCosts();
            if (names != null && displayNames != null) {
                // Play casting spell sound
                SoundManager.playSound(SoundConstants.SOUND_SPELL);
                String dialogResult = null;
                dialogResult = CommonDialogs.showInputDialog(
                        "Select a Spell to Cast", "Select Spell", displayNames,
                        displayNames[0]);
                if (dialogResult != null) {
                    int index;
                    for (index = 0; index < displayNames.length; index++) {
                        if (dialogResult.equals(displayNames[index])) {
                            break;
                        }
                    }
                    return book.getSpellByName(names[index]);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static AbstractCreature resolveTarget(final Spell cast,
            final int teamID) {
        final BattleTarget target = cast.getTarget();
        switch (target) {
        case SELF:
            if (teamID == AbstractCreature.TEAM_PARTY) {
                return PartyManager.getParty().getLeader();
            } else {
                return Chrystalz.getApplication().getBattle().getEnemy();
            }
        case ENEMY:
            if (teamID == AbstractCreature.TEAM_PARTY) {
                return Chrystalz.getApplication().getBattle().getEnemy();
            } else {
                return PartyManager.getParty().getLeader();
            }
        default:
            return null;
        }
    }
}
