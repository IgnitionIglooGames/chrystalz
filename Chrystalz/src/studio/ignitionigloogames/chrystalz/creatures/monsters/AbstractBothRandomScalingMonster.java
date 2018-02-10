/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.creatures.monsters;

import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.random.RandomLongRange;
import studio.ignitionigloogames.common.random.RandomRange;

abstract class AbstractBothRandomScalingMonster
        extends AbstractBothRandomMonster {
    // Constants
    private static final int STAT_MULT_VERY_EASY = 2;
    private static final int STAT_MULT_EASY = 3;
    private static final int STAT_MULT_NORMAL = 5;
    private static final int STAT_MULT_HARD = 7;
    private static final int STAT_MULT_VERY_HARD = 9;
    private static final double EXP_MULT_VERY_EASY = 1.2;
    private static final double EXP_MULT_EASY = 1.1;
    private static final double EXP_MULT_NORMAL = 1.0;
    private static final double EXP_MULT_HARD = 0.9;
    private static final double EXP_MULT_VERY_HARD = 0.8;

    // Constructors
    AbstractBothRandomScalingMonster() {
        super();
    }

    @Override
    public void loadCreature() {
        final int newLevel = PartyManager.getParty().getTowerLevel() + 1;
        this.setLevel(newLevel);
        this.setVitality(this.getInitialVitality());
        this.setCurrentHP(this.getMaximumHP());
        this.setStrength(this.getInitialStrength());
        this.setBlock(this.getInitialBlock());
        this.setAgility(this.getInitialAgility());
        this.setLuck(this.getInitialLuck());
        this.setGold(AbstractBothRandomScalingMonster.getInitialGold());
        this.setExperience((long) (this.getInitialExperience()
                * this.adjustForLevelDifference()));
        this.setAttacksPerRound(1);
        this.image = this.getInitialImage();
    }

    private int getInitialStrength() {
        final RandomRange r = new RandomRange(1,
                Math.max(this.getLevel() * AbstractBothRandomScalingMonster
                        .getStatMultiplierForDifficulty(), 1));
        return r.generate();
    }

    private int getInitialBlock() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * AbstractBothRandomScalingMonster
                        .getStatMultiplierForDifficulty());
        return r.generate();
    }

    private long getInitialExperience() {
        int minvar, maxvar;
        minvar = (int) (this.getLevel()
                * AbstractMonster.MINIMUM_EXPERIENCE_RANDOM_VARIANCE);
        maxvar = (int) (this.getLevel()
                * AbstractMonster.MAXIMUM_EXPERIENCE_RANDOM_VARIANCE);
        final RandomLongRange r = new RandomLongRange(minvar, maxvar);
        final long expbase = PartyManager.getParty().getPartyMaxToNextLevel();
        final long factor = this.getBattlesToNextLevel();
        return (int) (expbase / factor
                + r.generate() * this.adjustForLevelDifference()
                        * AbstractBothRandomScalingMonster
                                .getExpMultiplierForDifficulty());
    }

    private static int getInitialGold() {
        final RandomRange r = new RandomRange(0, 1);
        return r.generate();
    }

    private int getInitialAgility() {
        final RandomRange r = new RandomRange(1,
                Math.max(this.getLevel() * AbstractBothRandomScalingMonster
                        .getStatMultiplierForDifficulty(), 1));
        return r.generate();
    }

    private int getInitialVitality() {
        final RandomRange r = new RandomRange(1,
                Math.max(this.getLevel() * AbstractBothRandomScalingMonster
                        .getStatMultiplierForDifficulty(), 1));
        return r.generate();
    }

    private int getInitialLuck() {
        final RandomRange r = new RandomRange(0,
                this.getLevel() * AbstractBothRandomScalingMonster
                        .getStatMultiplierForDifficulty());
        return r.generate();
    }

    private static int getStatMultiplierForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return AbstractBothRandomScalingMonster.STAT_MULT_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return AbstractBothRandomScalingMonster.STAT_MULT_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return AbstractBothRandomScalingMonster.STAT_MULT_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return AbstractBothRandomScalingMonster.STAT_MULT_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return AbstractBothRandomScalingMonster.STAT_MULT_VERY_HARD;
        } else {
            return AbstractBothRandomScalingMonster.STAT_MULT_NORMAL;
        }
    }

    private static double getExpMultiplierForDifficulty() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return AbstractBothRandomScalingMonster.EXP_MULT_VERY_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return AbstractBothRandomScalingMonster.EXP_MULT_EASY;
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return AbstractBothRandomScalingMonster.EXP_MULT_NORMAL;
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return AbstractBothRandomScalingMonster.EXP_MULT_HARD;
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return AbstractBothRandomScalingMonster.EXP_MULT_VERY_HARD;
        } else {
            return AbstractBothRandomScalingMonster.EXP_MULT_NORMAL;
        }
    }
}
