/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.battle.map.turn;

import studio.ignitionigloogames.chrystalz.ai.map.MapAIContext;
import studio.ignitionigloogames.chrystalz.dungeon.Dungeon;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;

public class MapTurnBattleDefinitions {
    // Fields
    private BattleCharacter activeCharacter;
    private final BattleCharacter[] battlers;
    private final MapAIContext[] aiContexts;
    private Dungeon battleMap;
    private int battlerCount;
    private static final int MAX_BATTLERS = 100;

    // Constructors
    public MapTurnBattleDefinitions() {
        this.battlers = new BattleCharacter[MapTurnBattleDefinitions.MAX_BATTLERS];
        this.aiContexts = new MapAIContext[MapTurnBattleDefinitions.MAX_BATTLERS];
        this.battlerCount = 0;
    }

    // Methods
    public BattleCharacter[] getBattlers() {
        return this.battlers;
    }

    public void resetBattlers() {
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTemplate().isAlive()) {
                    battler.activate();
                    battler.resetAP();
                    battler.resetAttacks();
                    battler.resetLocation();
                }
            }
        }
    }

    public void roundResetBattlers() {
        for (final BattleCharacter battler : this.battlers) {
            if (battler != null) {
                if (battler.getTemplate().isAlive()) {
                    battler.resetAP();
                    battler.resetAttacks();
                }
            }
        }
    }

    public boolean addBattler(final BattleCharacter battler) {
        if (this.battlerCount < MapTurnBattleDefinitions.MAX_BATTLERS) {
            this.battlers[this.battlerCount] = battler;
            this.battlerCount++;
            return true;
        } else {
            return false;
        }
    }

    public MapAIContext[] getBattlerAIContexts() {
        return this.aiContexts;
    }

    public BattleCharacter getActiveCharacter() {
        return this.activeCharacter;
    }

    public void setActiveCharacter(final BattleCharacter bc) {
        this.activeCharacter = bc;
    }

    public Dungeon getBattleDungeon() {
        return this.battleMap;
    }

    public void setBattleDungeon(final Dungeon bMap) {
        this.battleMap = bMap;
    }

    public int findBattler(final String name) {
        return this.findBattler(name, 0, this.battlers.length);
    }

    private int findBattler(final String name, final int start,
            final int limit) {
        for (int x = start; x < limit; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getName().equals(name)) {
                    return x;
                }
            }
        }
        return -1;
    }

    public int findFirstBattlerOnTeam(final int teamID) {
        return this.findFirstBattlerOnTeam(teamID, 0, this.battlers.length);
    }

    private int findFirstBattlerOnTeam(final int teamID, final int start,
            final int limit) {
        for (int x = start; x < limit; x++) {
            if (this.battlers[x] != null) {
                if (this.battlers[x].getTeamID() == teamID) {
                    return x;
                }
            }
        }
        return -1;
    }
}
