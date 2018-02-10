/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.creatures.party;

import java.io.IOException;

import studio.ignitionigloogames.chrystalz.VersionException;
import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.chrystalz.creatures.StatConstants;
import studio.ignitionigloogames.chrystalz.dungeon.FormatConstants;
import studio.ignitionigloogames.chrystalz.items.ItemInventory;
import studio.ignitionigloogames.chrystalz.prefs.PreferencesManager;
import studio.ignitionigloogames.common.experience.ExperienceEquation;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class PartyMember extends AbstractCreature {
    // Fields
    private final String name;
    private static final int START_GOLD = 0;
    private static final double BASE_COEFF = 10.0;

    // Constructors
    PartyMember() {
        super(0);
        this.name = "Player";
        this.setLevel(1);
        this.setStrength(StatConstants.GAIN_STRENGTH);
        this.setBlock(StatConstants.GAIN_BLOCK);
        this.setVitality(StatConstants.GAIN_VITALITY);
        this.setAgility(StatConstants.GAIN_AGILITY);
        this.setLuck(StatConstants.GAIN_LUCK);
        this.setAttacksPerRound(1);
        this.healFully();
        this.setGold(PartyMember.START_GOLD);
        this.setExperience(0L);
        final ExperienceEquation nextLevelEquation = new ExperienceEquation(3,
                1, 0, true);
        final double value = PartyMember.BASE_COEFF;
        nextLevelEquation.setCoefficient(1, value);
        nextLevelEquation.setCoefficient(2, value);
        nextLevelEquation.setCoefficient(3, value);
        this.setToNextLevel(nextLevelEquation);
    }

    // Methods
    public String getXPString() {
        return this.getExperience() + "/" + this.getToNextLevelValue();
    }

    // Transformers
    @Override
    protected void levelUpHook() {
        this.offsetStrength(StatConstants.GAIN_STRENGTH);
        this.offsetBlock(StatConstants.GAIN_BLOCK);
        this.offsetVitality(StatConstants.GAIN_VITALITY);
        this.offsetAgility(StatConstants.GAIN_AGILITY);
        this.offsetLuck(StatConstants.GAIN_LUCK);
        this.healFully();
    }

    private void loadPartyMember(final int newLevel, final int chp,
            final int newGold, final int newLoad,
            final long newExperience) {
        this.setLevel(newLevel);
        this.setCurrentHP(chp);
        this.setGold(newGold);
        this.setLoad(newLoad);
        this.setExperience(newExperience);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getSpeed() {
        final int difficulty = PreferencesManager.getGameDifficulty();
        final int base = this.getBaseSpeed();
        if (difficulty == PreferencesManager.DIFFICULTY_VERY_EASY) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_FASTEST);
        } else if (difficulty == PreferencesManager.DIFFICULTY_EASY) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_FAST);
        } else if (difficulty == PreferencesManager.DIFFICULTY_NORMAL) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_NORMAL);
        } else if (difficulty == PreferencesManager.DIFFICULTY_HARD) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_SLOW);
        } else if (difficulty == PreferencesManager.DIFFICULTY_VERY_HARD) {
            return (int) (base * AbstractCreature.SPEED_ADJUST_SLOWEST);
        } else {
            return (int) (base * AbstractCreature.SPEED_ADJUST_NORMAL);
        }
    }

    public void onDeath(final int penalty) {
        this.offsetExperiencePercentage(penalty);
        this.healFully();
        this.setGold(0);
    }

    public static PartyMember read(final FileIOReader worldFile)
            throws IOException {
        final int version = worldFile.readByte();
        if (version < FormatConstants.CHARACTER_FORMAT_2) {
            throw new VersionException(
                    "Invalid character version found: " + version);
        }
        final int strength = worldFile.readInt();
        final int block = worldFile.readInt();
        final int agility = worldFile.readInt();
        final int vitality = worldFile.readInt();
        final int luck = worldFile.readInt();
        final int lvl = worldFile.readInt();
        final int cHP = worldFile.readInt();
        final int gld = worldFile.readInt();
        final int apr = worldFile.readInt();
        final int load = worldFile.readInt();
        final long exp = worldFile.readLong();
        final PartyMember pm = PartyManager.getNewPCInstance();
        pm.setStrength(strength);
        pm.setBlock(block);
        pm.setAgility(agility);
        pm.setVitality(vitality);
        pm.setLuck(luck);
        pm.setAttacksPerRound(apr);
        pm.setItems(ItemInventory.readItemInventory(worldFile));
        pm.loadPartyMember(lvl, cHP, gld, load, exp);
        return pm;
    }

    public void write(final FileIOWriter worldFile) throws IOException {
        worldFile.writeByte(FormatConstants.CHARACTER_FORMAT_LATEST);
        worldFile.writeInt(this.getStrength());
        worldFile.writeInt(this.getBlock());
        worldFile.writeInt(this.getAgility());
        worldFile.writeInt(this.getVitality());
        worldFile.writeInt(this.getLuck());
        worldFile.writeInt(this.getLevel());
        worldFile.writeInt(this.getCurrentHP());
        worldFile.writeInt(this.getGold());
        worldFile.writeInt(this.getAttacksPerRound());
        worldFile.writeInt(this.getLoad());
        worldFile.writeLong(this.getExperience());
        this.getItems().writeItemInventory(worldFile);
    }

    @Override
    protected BufferedImageIcon getInitialImage() {
        // FIXME: This does not work right now
        return null;
    }

    @Override
    public void loadCreature() {
        // Do nothing
    }
}
