/*  TallerTower: An RPG
Copyright (C) 2008-2012 Eric Ahnell

Any questions should be directed to the author via email at: products@puttysoftware.com
 */
package studio.ignitionigloogames.chrystalz.dungeon;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.WindowConstants;

import studio.ignitionigloogames.chrystalz.Application;
import studio.ignitionigloogames.chrystalz.Chrystalz;
import studio.ignitionigloogames.chrystalz.assetmanagers.LogoManager;
import studio.ignitionigloogames.chrystalz.creatures.party.PartyManager;
import studio.ignitionigloogames.chrystalz.dungeon.abc.AbstractGameObject;
import studio.ignitionigloogames.chrystalz.dungeon.utilities.ImageColorConstants;
import studio.ignitionigloogames.common.random.RandomRange;

public class GenerateTask extends Thread {
    // Fields
    private final JFrame generateFrame;
    private final boolean scratch;

    // Constructors
    public GenerateTask(final boolean startFromScratch) {
        this.scratch = startFromScratch;
        this.setName("Level Generator");
        this.generateFrame = new JFrame("Generating...");
        this.generateFrame.setIconImage(LogoManager.getIconLogo());
        final JProgressBar loadBar = new JProgressBar();
        loadBar.setIndeterminate(true);
        this.generateFrame.getContentPane().add(loadBar);
        this.generateFrame.setResizable(false);
        this.generateFrame
                .setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        this.generateFrame.pack();
    }

    // Methods
    @Override
    public void run() {
        try {
            this.generateFrame.setVisible(true);
            final Application app = Chrystalz.getApplication();
            Dungeon gameDungeon = app.getDungeonManager().getDungeon();
            if (!this.scratch) {
                app.getGameManager().disableEvents();
            } else {
                gameDungeon = new Dungeon();
                app.getDungeonManager().setDungeon(gameDungeon);
            }
            gameDungeon.addLevel(Dungeon.getMaxRows(), Dungeon.getMaxColumns(),
                    Dungeon.getMaxFloors());
            gameDungeon.fillLevelRandomly();
            final RandomRange rR = new RandomRange(0, Dungeon.getMaxRows() - 1);
            final RandomRange rC = new RandomRange(0,
                    Dungeon.getMaxColumns() - 1);
            final RandomRange rF = new RandomRange(0,
                    Dungeon.getMaxFloors() - 1);
            if (this.scratch) {
                int startR, startC, startF;
                do {
                    startR = rR.generate();
                    startC = rC.generate();
                    startF = rF.generate();
                } while (gameDungeon.getCell(startR, startC, startF,
                        DungeonConstants.LAYER_OBJECT).isSolid());
                gameDungeon.setStartRow(startR);
                gameDungeon.setStartColumn(startC);
                gameDungeon.setStartFloor(startF);
                app.getDungeonManager().setLoaded(true);
                final boolean playerExists = gameDungeon.doesPlayerExist();
                if (playerExists) {
                    gameDungeon.setPlayerToStart();
                    app.getGameManager().resetViewingWindow();
                }
            } else {
                int startR, startC, startF;
                do {
                    startR = rR.generate();
                    startC = rC.generate();
                    startF = rF.generate();
                } while (gameDungeon.getCell(startR, startC, startF,
                        DungeonConstants.LAYER_OBJECT).isSolid());
                gameDungeon.setPlayerLocationX(startR);
                gameDungeon.setPlayerLocationY(startC);
                gameDungeon.setPlayerLocationZ(startF);
                PartyManager.getParty().offsetTowerLevel(1);
            }
            gameDungeon.save();
            // Final cleanup
            AbstractGameObject.setTemplateColor(ImageColorConstants
                    .getColorForLevel(PartyManager.getParty().getTowerLevel()));
            if (this.scratch) {
                app.getGameManager().stateChanged();
                app.getGameManager().playDungeon();
            } else {
                app.getGameManager().resetViewingWindow();
                app.getGameManager().enableEvents();
                app.getGameManager().redrawDungeon();
            }
        } catch (final Throwable t) {
            Chrystalz.getErrorLogger().logError(t);
        } finally {
            this.generateFrame.setVisible(false);
        }
    }
}
