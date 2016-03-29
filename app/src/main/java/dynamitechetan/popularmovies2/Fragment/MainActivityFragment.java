package dynamitechetan.popularmovies2.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dynamitechetan.popularmovies2.R;
import dynamitechetan.popularmovies2.Utilities;
import dynamitechetan.popularmovies2.ProvidersAndDBhelpers.FavContract;
import dynamitechetan.popularmovies2.API.ImageAdapter;
import dynamitechetan.popularmovies2.API.Movies;
import dynamitechetan.popularmovies2.API.TmdbInterface;
import dynamitechetan.popularmovies2.API.TmdbService;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private ImageAdapter mMoviePosterAdapter;
    private ArrayList<Movies.Movie> mMoviesList;
    private boolean mRestoredState;

    public MainActivityFragment() {
    }

    public interface MovieClickListener {
        void onItemSelected(Movies.Movie movie);
    }

    @Override
    public void onStart() {
        super.onStart();
        fetchAndUpdateMovies();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(Utilities.RESTORED_STATE, mMoviesList);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mMoviesList = (ArrayList<Movies.Movie>) savedInstanceState.get(Utilities.RESTORED_STATE);
            mRestoredState = true;
        }
    }

    /**
     * Fetches movies data from TMDb API using Retrofit
     * http://square.github.io/retrofit/
     */
    private void fetchAndUpdateMovies() {
        TmdbService tmdbService = new TmdbService();
        RestAdapter retrofit = tmdbService.getRestAdapter();
        final TmdbInterface service = retrofit.create(TmdbInterface.class);

        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());

        String sortOrder = sharedPrefs.getString(
                getString(R.string.pref_sort_order_key),
                getString(R.string.pref_sort_order_most_popular)
        );

        if (!sortOrder.equals(getString(R.string.pref_sort_order_favorites))) {
            service.getMovieList(sortOrder, getString(R.string.pref_sort_min_vote_count), new Callback<Movies>() {
                @Override
                public void success(Movies movies, Response response) {
                    mMoviePosterAdapter.clear();
                    for (Movies.Movie movie : movies.results) {
                        mMoviePosterAdapter.add(movie.getPosterPath(Utilities.STANDARD_POSTER_SIZE));
                    }
                    mMoviesList = movies.results;
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(LOG_TAG, "Retrofit error: ", error);
                }
            });
        } else {
            new FetchFavoriteMoviesTask(getActivity()).execute();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        if (!mRestoredState) {
            mMoviePosterAdapter = new ImageAdapter(getActivity());
        } else {

            if (mMoviesList == null) {
                mRestoredState = false;
                return rootView;
            }

            ArrayList<String> moviePosters = new ArrayList<String>();
            for (Movies.Movie movie : mMoviesList) {
                moviePosters.add(movie.getPosterPath(Utilities.STANDARD_POSTER_SIZE));
            }
            mMoviePosterAdapter = new ImageAdapter(getActivity(), moviePosters);
        }

        GridView gridview = (GridView) rootView.findViewById(R.id.grid_view);
        gridview.setAdapter(mMoviePosterAdapter);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((MovieClickListener) getActivity()).onItemSelected(mMoviesList.get(position));
            }
        });
        return rootView;
    }

    private static final String[] MOVIE_COLUMNS = {
            FavContract.MovieEntry._ID,
            FavContract.MovieEntry.COLUMN_MOVIE_ID,
            FavContract.MovieEntry.COLUMN_BACKDROP_PATH,
            FavContract.MovieEntry.COLUMN_POSTER_PATH,
            FavContract.MovieEntry.COLUMN_ORIGINAL_TITLE,
            FavContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE,
            FavContract.MovieEntry.COLUMN_OVERVIEW,
            FavContract.MovieEntry.COLUMN_RELEASE_DATE,
            FavContract.MovieEntry.COLUMN_VOTE_AVERAGE,
            FavContract.MovieEntry.COLUMN_VOTE_COUNT,
            FavContract.MovieEntry.COLUMN_POPULARITY
    };

    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_BACKDROP_PATH = 2;
    public static final int COL_POSTER_PATH = 3;
    public static final int COL_ORIGINAL_TITLE = 4;
    public static final int COL_ORIGINAL_LANGUAGE = 5;
    public static final int COL_OVERVIEW = 6;
    public static final int COL_RELEASE_DATE = 7;
    public static final int COL_VOTE_AVERAGE = 8;
    public static final int COL_VOTE_COUNT = 9;
    public static final int COL_POPULARITY = 10;

    public class FetchFavoriteMoviesTask extends AsyncTask<Void, Void, List<Movies.Movie>> {

        private Context mContext;

        public FetchFavoriteMoviesTask(Context context) {
            mContext = context;
        }

        private List<Movies.Movie> getFavoriteMoviesDataFromCursor(Cursor cursor) {
            List<Movies.Movie> results = new ArrayList<>();
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    Movies.Movie movie = new Movies.Movie(cursor);
                    results.add(movie);
                } while (cursor.moveToNext());
                cursor.close();
            }
            return results;
        }

        @Override
        protected List<Movies.Movie> doInBackground(Void... params) {
            Cursor cursor = mContext.getContentResolver().query(
                    FavContract.MovieEntry.CONTENT_URI,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null
            );
            List<Movies.Movie> favoriteMovies = getFavoriteMoviesDataFromCursor(cursor);
            return favoriteMovies;
        }

        @Override
        protected void onPostExecute(List<Movies.Movie> movies) {
            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());

            if (!movies.isEmpty()) {
                if (mMoviePosterAdapter != null) {
                    mMoviePosterAdapter.clear();
                    for (Movies.Movie movie : movies) {
                        mMoviePosterAdapter.add(movie.getPosterPath(Utilities.STANDARD_POSTER_SIZE));
                    }
                } else {
                    mMoviePosterAdapter = new ImageAdapter(getActivity());
                }

                mMoviesList = new ArrayList<>();
                mMoviesList.addAll(movies);
            } else {

                SharedPreferences.Editor editor = sharedPrefs.edit();

                editor.putString(getString(R.string.pref_sort_order_key),
                        getString(R.string.pref_sort_order_most_popular));
                editor.apply();

                String currentPref = sharedPrefs.getString(
                        getString(R.string.pref_sort_order_key),
                        getString(R.string.pref_sort_order_most_popular)
                );

                Toast.makeText(
                        getActivity(),
                        getString(R.string.no_favored_movies),
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    }
}