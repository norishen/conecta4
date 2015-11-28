package org.wicbo5oel.conecta4;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class FragmentPreferences1 extends PreferenceFragment{

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings1);
    }
}