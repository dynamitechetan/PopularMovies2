

package dynamitechetan.popularmovies2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import dynamitechetan.popularmovies2.Fragment.DetailActivityFragment;
import dynamitechetan.popularmovies2.Fragment.MainActivityFragment;
import dynamitechetan.popularmovies2.API.Movies;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.MovieClickListener {
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail_container) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                        .beginTransaction()

                        .replace(R.id.movie_detail_container,
                                new DetailActivityFragment(),
                                DETAILFRAGMENT_TAG)

                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onItemSelected(Movies.Movie movie) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(Utilities.MOVIE_PARCEL, movie);

            DetailActivityFragment detailActivityFragment = new DetailActivityFragment();
            detailActivityFragment.setArguments(args);


            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movie_detail_container, detailActivityFragment, DETAILFRAGMENT_TAG)
                    .commit();

        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(Utilities.MOVIE_PARCEL, movie);
            startActivity(intent);
        }
    }
}