
package dynamitechetan.popularmovies2.API;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


public interface TmdbInterface {
    @GET("/discover/movie")
    void getMovieList(
            @Query("sort_by") String sortBy,
            @Query("vote_count.gte") String voteCount,
            Callback<Movies> callback
    );

    @GET("/movie/{id}/videos")
    void getTrailerList(
            @Path("id") int movieId,
            Callback<Trailers> callback
    );

    @GET("/movie/{id}/reviews")
    void getReviewList(
            @Path("id") int movieId,
            Callback<Reviews> callback
    );
}