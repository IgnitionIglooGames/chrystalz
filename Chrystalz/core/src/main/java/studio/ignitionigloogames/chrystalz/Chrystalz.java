package studio.ignitionigloogames.chrystalz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import org.mini2Dx.core.game.BasicGame;
import org.mini2Dx.core.graphics.Graphics;
import studio.ignitionigloogames.chrystalz.creatures.AbstractCreature;
import studio.ignitionigloogames.common.dialogs.CommonDialogs;
import studio.ignitionigloogames.common.errorlogger.ErrorLogger;

public class Chrystalz extends BasicGame {
	public static final String GAME_IDENTIFIER = "studio.ignitionigloogames.chrystalz";

	private Texture texture;
	
	@Override
    public void initialise() {
    	texture = new Texture("mini2Dx.png");
    }
    
    @Override
    public void update(float delta) {
    
    }
    
    @Override
    public void interpolate(float alpha) {
    
    }
    
    @Override
    public void render(Graphics g) {
		g.drawTexture(texture, 0f, 0f);
    }

    // Constants
    private static Application application;
    private static final String PROGRAM_NAME = "Chrystalz";
    private static final String ERROR_MESSAGE = "Perhaps a bug is to blame for this error message.\n"
            + "Include the error log with your bug report.\n"
            + "Email bug reports to: products@ignitionigloogames.com\n"
            + "Subject: Chrystalz Bug Report";
    private static final String ERROR_TITLE = "Chrystalz Error";
    private static final ErrorLogger elog = new ErrorLogger(
            Chrystalz.PROGRAM_NAME);
    private static final int BATTLE_MAZE_SIZE = 16;

    // Methods
    public static Application getApplication() {
        return Chrystalz.application;
    }

    public static int getBattleDungeonSize() {
        return Chrystalz.BATTLE_MAZE_SIZE;
    }

    public static ErrorLogger getErrorLogger() {
        // Display error message
        CommonDialogs.showErrorDialog(Chrystalz.ERROR_MESSAGE,
                Chrystalz.ERROR_TITLE);
        return Chrystalz.elog;
    }

    public static void preInit() {
        // Compute action cap
        AbstractCreature.computeActionCap(Chrystalz.BATTLE_MAZE_SIZE,
                Chrystalz.BATTLE_MAZE_SIZE);
    }

    public static void main(final String[] args) {
        try {
            // Pre-Init
            Chrystalz.preInit();
            Chrystalz.application = new Application();
            Chrystalz.application.postConstruct();
            Application.playLogoSound();
            Chrystalz.application.getGUIManager().showGUI();
            // Set up Common Dialogs
            CommonDialogs.setDefaultTitle(Chrystalz.PROGRAM_NAME);
            CommonDialogs.setIcon(Application.getMicroLogo());
        } catch (final Throwable t) {
            Chrystalz.getErrorLogger().logError(t);
        }
    }
}
