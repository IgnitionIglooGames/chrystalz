/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.game;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import studio.ignitionigloogames.chrystalz.assetmanagers.StatImageConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.StatImageManager;
import studio.ignitionigloogames.chrystalz.creatures.party.Party;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyMember;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

class StatGUI {
    // Fields
    private Container statsPane;
    private JLabel hpLabel;
    private JLabel goldLabel;
    private JLabel attackLabel;
    private JLabel defenseLabel;
    private JLabel xpLabel;
    private JLabel levelLabel;

    // Constructors
    StatGUI() {
        this.setUpGUI();
    }

    // Methods
    Container getStatsPane() {
        return this.statsPane;
    }

    void updateStats() {
        final Party party = PartyManager.getParty();
        if (party != null) {
            final PartyMember pc = party.getLeader();
            if (pc != null) {
                this.hpLabel.setText(pc.getHPString());
                this.goldLabel.setText(Integer.toString(pc.getGold()));
                this.attackLabel.setText(Integer.toString(pc.getAttack()));
                this.defenseLabel.setText(Integer.toString(pc.getDefense()));
                this.xpLabel.setText(pc.getXPString());
                this.levelLabel.setText(party.getTowerLevelString());
            }
        }
    }

    private void setUpGUI() {
        this.statsPane = new Container();
        this.statsPane.setLayout(new GridLayout(7, 1));
        this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.goldLabel = new JLabel("", null, SwingConstants.LEFT);
        this.attackLabel = new JLabel("", null, SwingConstants.LEFT);
        this.defenseLabel = new JLabel("", null, SwingConstants.LEFT);
        this.xpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.levelLabel = new JLabel("", null, SwingConstants.LEFT);
        this.statsPane.add(this.hpLabel);
        this.statsPane.add(this.goldLabel);
        this.statsPane.add(this.attackLabel);
        this.statsPane.add(this.defenseLabel);
        this.statsPane.add(this.xpLabel);
        this.statsPane.add(this.levelLabel);
    }

    void updateImages() {
        final BufferedImageIcon hpImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_HEALTH);
        this.hpLabel.setIcon(hpImage);
        final BufferedImageIcon goldImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_GOLD);
        this.goldLabel.setIcon(goldImage);
        final BufferedImageIcon attackImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_ATTACK);
        this.attackLabel.setIcon(attackImage);
        final BufferedImageIcon defenseImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_DEFENSE);
        this.defenseLabel.setIcon(defenseImage);
        final BufferedImageIcon xpImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_XP);
        this.xpLabel.setIcon(xpImage);
        final BufferedImageIcon levelImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_LEVEL);
        this.levelLabel.setIcon(levelImage);
    }
}
