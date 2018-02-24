/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.manager.asset;

public class SoundConstants {
    // Public Sound Constants
    public static final int SOUND_ATTACK_HIT = 0;
    public static final int SOUND_AXE_HIT = 1;
    public static final int SOUND_BLINDNESS = 2;
    public static final int SOUND_BOLT_SPELL = 3;
    public static final int SOUND_BOSS_DIE = 4;
    public static final int SOUND_BUBBLE_SPELL = 5;
    public static final int SOUND_BUFF_1 = 6;
    public static final int SOUND_BUFF_2 = 7;
    public static final int SOUND_CLICK = 8;
    public static final int SOUND_COLD_SPELL = 9;
    public static final int SOUND_CONFUSION_SPELL = 10;
    public static final int SOUND_COUNTER = 11;
    public static final int SOUND_CRITICAL_HIT = 12;
    public static final int SOUND_DAGGER_HIT = 13;
    public static final int SOUND_DANGER = 14;
    public static final int SOUND_DEATH = 15;
    public static final int SOUND_DEBUFF_1 = 16;
    public static final int SOUND_DEBUFF_2 = 17;
    public static final int SOUND_DEFEATED = 18;
    public static final int SOUND_DISPEL_EFFECT = 19;
    public static final int SOUND_DOWN = 20;
    public static final int SOUND_DRAIN_SPELL = 21;
    public static final int SOUND_DUMBFOUND_SPELL = 22;
    public static final int SOUND_ERROR = 23;
    public static final int SOUND_EXPLODE_SPELL = 24;
    public static final int SOUND_FAILED = 25;
    public static final int SOUND_FATAL_ERROR = 26;
    public static final int SOUND_FIGHT = 27;
    public static final int SOUND_FIREBALL_SPELL = 28;
    public static final int SOUND_FOCUS_SPELL = 29;
    public static final int SOUND_FREEZE_SPELL = 30;
    public static final int SOUND_FUMBLE = 31;
    public static final int SOUND_HAMMER_HIT = 32;
    public static final int SOUND_WALK_ICE = 33;
    public static final int SOUND_LEVEL_UP = 34;
    public static final int SOUND_MELT_SPELL = 35;
    public static final int SOUND_MISSED = 36;
    public static final int SOUND_MONSTER_HIT = 37;
    public static final int SOUND_NEXT_ROUND = 38;
    public static final int SOUND_PLAYER_UP = 39;
    public static final int SOUND_RUN_AWAY = 40;
    public static final int SOUND_SHOP = 41;
    public static final int SOUND_SPELL_SELECT = 42;
    public static final int SOUND_STAFF_HIT = 43;
    public static final int SOUND_STEAM_SPELL = 44;
    public static final int SOUND_SWORD_HIT = 45;
    public static final int SOUND_TRANSACT = 46;
    public static final int SOUND_UP = 47;
    public static final int SOUND_VICTORY = 48;
    public static final int SOUND_WALK = 49;
    public static final int SOUND_WAND_HIT = 50;
    public static final int SOUND_WEAKNESS_SPELL = 51;
    public static final int SOUND_WIN_GAME = 52;
    public static final int SOUND_ZAP_SPELL = 53;
    public static final int SOUND_HEAL_SPELL = 54;
    public static final int SOUND_DOOR_CLOSES = 55;
    public static final int SOUND_DOOR_OPENS = 56;
    public static final int SOUND_EQUIP = 57;
    public static final int SOUND_INTRO = 58;
    public static final int SOUND_SPECIAL = 59;
    private static final String[] SOUND_NAMES = SoundDataManager.getSoundData();

    // Private constructor
    private SoundConstants() {
        // Do nothing
    }

    static String getSoundName(final int ID) {
        return SoundConstants.SOUND_NAMES[ID];
    }
}