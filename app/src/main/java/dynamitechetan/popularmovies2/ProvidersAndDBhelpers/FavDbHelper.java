package dynamitechetan.popularmovies2.ProvidersAndDBhelpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavDbHelper extends SQLiteOpenHelper {
    public static int DATABASE_VERSION = 2;
    public static String DATABASE_NAME = "movies.db";

    public FavDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " +
                FavContract.MovieEntry.TABLE_NAME + " (" +
                FavContract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FavContract.MovieEntry.COLUMN_MOVIE_ID + " TEXT NOT NULL, " +
                FavContract.MovieEntry.COLUMN_BACKDROP_PATH + " TEXT NOT NULL, " +
                FavContract.MovieEntry.COLUMN_POSTER_PATH + " TEXT NOT NULL, " +
                FavContract.MovieEntry.COLUMN_ORIGINAL_TITLE + " TEXT NOT NULL, " +
                FavContract.MovieEntry.COLUMN_ORIGINAL_LANGUAGE + " TEXT NOT NULL, " +
                FavContract.MovieEntry.COLUMN_OVERVIEW + " TEXT NOT NULL, " +
                FavContract.MovieEntry.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                FavContract.MovieEntry.COLUMN_VOTE_AVERAGE + " REAL NOT NULL, " +
                FavContract.MovieEntry.COLUMN_VOTE_COUNT + " INTEGER NOT NULL, " +
                FavContract.MovieEntry.COLUMN_POPULARITY + " REAL NOT NULL, " +
                " UNIQUE (" + FavContract.MovieEntry.COLUMN_MOVIE_ID + ") ON CONFLICT REPLACE);";

        sqLiteDatabase.execSQL(SQL_CREATE_MOVIE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FavContract.MovieEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}