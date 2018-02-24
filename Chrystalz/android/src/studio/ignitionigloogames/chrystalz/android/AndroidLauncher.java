package studio.ignitionigloogames.chrystalz.android;

import org.mini2Dx.android.AndroidMini2DxConfig;

import com.badlogic.gdx.backends.android.AndroidMini2DxGame;

import android.os.Bundle;

import studio.ignitionigloogames.chrystalz.Chrystalz;

public class AndroidLauncher extends AndroidMini2DxGame {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidMini2DxConfig config = new AndroidMini2DxConfig(Chrystalz.GAME_IDENTIFIER);
        initialize(new Chrystalz(), config);
    }
}
