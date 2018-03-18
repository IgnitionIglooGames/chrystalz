package studio.ignitionigloogames.chrystalz.battle;

import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;

public abstract class AbstractMapBattle {
    protected boolean boss = false;
    protected boolean finalBoss = false;

    public abstract BattleCharacter getBattlers();

    public final boolean isBossBattle() {
        return this.boss;
    }

    public final boolean isFinalBossBattle() {
        return this.finalBoss;
    }

    public static AbstractMapBattle createBattle() {
        return new MapBattle();
    }

    public static AbstractMapBattle createBossBattle() {
        return new MapBossBattle();
    }

    public static AbstractMapBattle createFinalBossBattle() {
        return new MapFinalBossBattle();
    }
}