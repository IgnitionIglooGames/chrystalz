package studio.ignitionigloogames.chrystalz.prefs;

import java.awt.desktop.PreferencesEvent;
import java.awt.desktop.PreferencesHandler;

public class PrefsRequestHandler implements PreferencesHandler {
    public PrefsRequestHandler() {
        // Do nothing
    }

    @Override
    public void handlePreferences(final PreferencesEvent pe) {
        PreferencesManager.showPrefs();
    }
}
