package fr.esgi.moc.moviemeeting.data.api;



import java.util.List;

import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface MovieMeetingApiService {

    @GET("./movies/{id}")
    Call<List<Movie>> getMovieByID(@Path("id") int movieId);

}
