package dynamitechetan.popularmovies2.ProvidersAndDBhelpers;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavContract {
    public static final String CONTENT_AUTHORITY = "dynamitechetan.popularmovies2";
    public static final String PATH_MOVIE = "movie";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MovieEntry implements BaseColumns {

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_BACKDROP_PATH = "backdrop_path";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";
        public static final String COLUMN_ORIGINAL_LANGUAGE = "original_language";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_VOTE_COUNT = "vote_count";
        public static final String COLUMN_POPULARITY = "popularity";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}