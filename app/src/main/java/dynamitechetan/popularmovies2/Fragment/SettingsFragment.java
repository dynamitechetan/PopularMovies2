package dynamitechetan.popularmovies2.Fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import dynamitechetan.popularmovies2.R;


public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }
}