package studio.ignitionigloogames.chrystalz;

import org.mini2Dx.ios.IOSMini2DxConfig;
import org.robovm.apple.foundation.NSAutoreleasePool;
import org.robovm.apple.uikit.UIApplication;

import com.badlogic.gdx.backends.iosrobovm.IOSMini2DxGame;
import studio.ignitionigloogames.chrystalz.Chrystalz;

public class IOSLauncher extends IOSMini2DxGame.Delegate {
    @Override
    protected IOSMini2DxGame createApplication() {
        IOSMini2DxConfig config = new IOSMini2DxConfig(Chrystalz.GAME_IDENTIFIER);
        return new IOSMini2DxGame(new Chrystalz(), config);
    }

    public static void main(String[] argv) {
        NSAutoreleasePool pool = new NSAutoreleasePool();
        UIApplication.main(argv, null, IOSLauncher.class);
        pool.close();
    }
}
