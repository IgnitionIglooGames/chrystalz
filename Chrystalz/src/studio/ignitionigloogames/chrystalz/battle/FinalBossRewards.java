/*  Chrystalz: A dungeon-crawling, roguelike game
Licensed under MIT. See the LICENSE file for details.

All support is handled via the GitHub repository: https://github.com/IgnitionIglooGames/chrystalz
 */
package studio.ignitionigloogames.chrystalz.battle;

import javax.swing.JOptionPane;

import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;

public class FinalBossRewards {
    // Fields
    static final String[] rewardOptions = { "Attack", "Defense", "HP", "MP" };

    // Constructor
    private FinalBossRewards() {
        // Do nothing
    }

    // Methods
    public static void doRewards() {
        final PartyMember player = PartyManager.getParty().getLeader();
        String dialogResult = null;
        while (dialogResult == null) {
            dialogResult = (String) JOptionPane.showInputDialog(null,
                    "You get to increase a stat permanently.\nWhich Stat?",
                    "Boss Rewards", JOptionPane.QUESTION_MESSAGE, null,
                    FinalBossRewards.rewardOptions, FinalBossRewards.rewardOptions[0]);
        }
        if (dialogResult.equals(FinalBossRewards.rewardOptions[0])) {
            // Attack
            player.spendPointOnAttack();
        } else if (dialogResult.equals(FinalBossRewards.rewardOptions[1])) {
            // Defense
            player.spendPointOnDefense();
        } else if (dialogResult.equals(FinalBossRewards.rewardOptions[2])) {
            // HP
            player.spendPointOnHP();
        } else if (dialogResult.equals(FinalBossRewards.rewardOptions[3])) {
            // MP
            player.spendPointOnMP();
        }
        PartyManager.updatePostKill();
    }
}
