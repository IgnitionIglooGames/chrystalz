/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.creatures.party;

import java.io.IOException;

import studio.ignitionigloogames.chrystalz.assetmanagers.SoundConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.SoundManager;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;
import studio.ignitionigloogames.common.fileio.FileIOReader;
import studio.ignitionigloogames.common.fileio.FileIOWriter;

public class Party {
    // Fields
    private PartyMember members;
    private BattleCharacter battlers;
    private int leaderID;
    private int activePCs;
    private int towerLevel;

    // Constructors
    public Party() {
        this.members = null;
        this.leaderID = 0;
        this.activePCs = 0;
        this.towerLevel = 0;
    }

    // Methods
    private void generateBattleCharacters() {
        this.battlers = new BattleCharacter(this.members);
    }

    public BattleCharacter getBattleCharacters() {
        if (this.battlers == null) {
            this.generateBattleCharacters();
        }
        return this.battlers;
    }

    public long getPartyMaxToNextLevel() {
        return this.members.getToNextLevelValue();
    }

    public int getTowerLevel() {
        return this.towerLevel;
    }

    void resetTowerLevel() {
        this.towerLevel = 0;
    }

    public void offsetTowerLevel(final int offset) {
        if (this.towerLevel + offset > Dungeon.getMaxLevels()
                || this.towerLevel + offset < 0) {
            return;
        }
        this.towerLevel += offset;
    }

    public String getTowerLevelString() {
        return "Tower Level: " + (this.towerLevel + 1);
    }

    public PartyMember getLeader() {
        return this.members;
    }

    public void checkPartyLevelUp() {
        // Level Up Check
        if (this.members.checkLevelUp()) {
            this.members.levelUp();
            SoundManager.playSound(SoundConstants.SOUND_LEVEL_UP);
        }
    }

    public boolean isAlive() {
        return this.members.isAlive();
    }

    boolean addPartyMember(final PartyMember member) {
        this.members = member;
        return true;
    }

    static Party read(final FileIOReader worldFile) throws IOException {
        worldFile.readInt();
        final int lid = worldFile.readInt();
        final int apc = worldFile.readInt();
        final int lvl = worldFile.readInt();
        final Party pty = new Party();
        pty.leaderID = lid;
        pty.activePCs = apc;
        pty.towerLevel = lvl;
        final boolean present = worldFile.readBoolean();
        if (present) {
            pty.members = PartyMember.read(worldFile);
        }
        return pty;
    }

    void write(final FileIOWriter worldFile) throws IOException {
        worldFile.writeInt(1);
        worldFile.writeInt(this.leaderID);
        worldFile.writeInt(this.activePCs);
        worldFile.writeInt(this.towerLevel);
        if (this.members == null) {
            worldFile.writeBoolean(false);
        } else {
            worldFile.writeBoolean(true);
            this.members.write(worldFile);
        }
    }
}
