/*  TallerTower: An RPG
Copyright (C) 2011-2012 Eric Ahnell


Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.battle.map.turn;

import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import studio.ignitionigloogames.chrystalz.assetmanagers.StatImageConstants;
import studio.ignitionigloogames.chrystalz.assetmanagers.StatImageManager;
import studio.ignitionigloogames.chrystalz.dungeon.objects.BattleCharacter;
import studio.ignitionigloogames.common.images.BufferedImageIcon;

public class MapTurnBattleStats {
    // Fields
    private Container statsPane;
    private JLabel nameLabel;
    private JLabel hpLabel;
    private JLabel attLabel;
    private JLabel defLabel;

    // Constructors
    public MapTurnBattleStats() {
        this.setUpGUI();
        this.updateIcons();
    }

    // Methods
    public Container getStatsPane() {
        return this.statsPane;
    }

    public void updateStats(final BattleCharacter bc) {
        this.nameLabel.setText(bc.getName());
        this.hpLabel.setText(bc.getTemplate().getHPString());
        this.attLabel.setText(Integer.toString(bc.getTemplate().getAttack()));
        this.defLabel.setText(Integer.toString(bc.getTemplate().getDefense()));
    }

    private void setUpGUI() {
        this.statsPane = new Container();
        this.statsPane.setLayout(new GridLayout(9, 1));
        this.nameLabel = new JLabel("", null, SwingConstants.LEFT);
        this.hpLabel = new JLabel("", null, SwingConstants.LEFT);
        this.attLabel = new JLabel("", null, SwingConstants.LEFT);
        this.defLabel = new JLabel("", null, SwingConstants.LEFT);
        this.statsPane.add(this.nameLabel);
        this.statsPane.add(this.hpLabel);
        this.statsPane.add(this.attLabel);
        this.statsPane.add(this.defLabel);
    }

    private void updateIcons() {
        final BufferedImageIcon nameImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_NAME);
        this.nameLabel.setIcon(nameImage);
        final BufferedImageIcon hpImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_HEALTH);
        this.hpLabel.setIcon(hpImage);
        final BufferedImageIcon attImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_ATTACK);
        this.attLabel.setIcon(attImage);
        final BufferedImageIcon defImage = StatImageManager
                .getImage(StatImageConstants.STAT_IMAGE_DEFENSE);
        this.defLabel.setIcon(defImage);
    }
}
