
package dynamitechetan.popularmovies2;

import android.app.Activity;
import android.os.Bundle;

import dynamitechetan.popularmovies2.Fragment.SettingsFragment;


public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }
}