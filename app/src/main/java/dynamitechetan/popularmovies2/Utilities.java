
package dynamitechetan.popularmovies2;

import android.app.Activity;
import android.graphics.Typeface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dynamitechetan.popularmovies2.API.Movies;

public class Utilities {
    public static final String MOVIE_PARCEL = "MOVIE_PARCEL";
    public static final String RESTORED_STATE = "RESTORED_STATE";

    public static final String STANDARD_BACKDROP_SIZE = Movies.Movie.SIZE_W780;
    public static final String STANDARD_POSTER_SIZE   = Movies.Movie.SIZE_W185;

    public static final int STANDARD_SECOND_HEADLINE_TYPEFACE = Typeface.BOLD;

    public Utilities() {
    }


    public static String DateFormat(String dateToFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = formatter.parse(dateToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter = new SimpleDateFormat("dd MMM yyyy");

        return formatter.format(date);
    }

    public static int getIntFromResourceFile(Activity activity, int fieldName) {
        return activity.getResources().getInteger(fieldName);
    }
}
