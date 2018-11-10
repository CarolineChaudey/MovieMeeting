package fr.esgi.moc.moviemeeting.data.api;



import java.util.List;

import fr.esgi.moc.moviemeeting.data.dtos.Credentials;
import fr.esgi.moc.moviemeeting.data.dtos.Meeting;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface MovieMeetingApiService {

    @POST("./auth/")
    Call<User> getToken(@Body Credentials credentials);

    @GET("./movies/{id}")
    Call<List<Movie>> getMovieByID(@Path("id") int movieId, @Header("x-api-key") String token);

    @GET("./meetings/{id}")
    Call<Meeting> getMeetingByID(@Path("id") int meetingId, @Header("x-api-key") String token);

    @GET("./meetings/for/{filmId}")
    Call<List<Meeting>> getMeetingByMovieID(@Path("filmId") int movieId, @Header("x-api-key") String token);

}
