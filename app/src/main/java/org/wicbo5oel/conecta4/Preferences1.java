package org.wicbo5oel.conecta4;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class Preferences1 extends Activity {
    public final static String PLAY_MUSIC_KEY = "music";
    public final static boolean PLAY_MUSIC_DEFAULT = true;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_void);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        FragmentPreferences1 fragment = new FragmentPreferences1();
        fragmentTransaction.replace(android.R.id.content, fragment);

        fragmentTransaction.commit();
    }
}