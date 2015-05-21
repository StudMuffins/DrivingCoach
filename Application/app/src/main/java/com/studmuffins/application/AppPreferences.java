package com.studmuffins.application;

import android.os.Bundle;
import android.preference.PreferenceActivity;
/**
 * Created by Pooria on 21/05/15.
 */
public class AppPreferences extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
