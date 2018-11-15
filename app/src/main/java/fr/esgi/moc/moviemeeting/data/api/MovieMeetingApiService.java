package fr.esgi.moc.moviemeeting.data.api;



import android.support.annotation.Nullable;

import java.util.List;

import fr.esgi.moc.moviemeeting.data.dtos.Credentials;
import fr.esgi.moc.moviemeeting.data.dtos.Meeting;
import fr.esgi.moc.moviemeeting.data.dtos.MeetingFromApi;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface MovieMeetingApiService {

    @POST("./auth/")
    Call<User> getToken(@Body Credentials credentials);

    @GET("./movies/{id}")
    Call<List<Movie>> getMovieByID(@Path("id") int movieId, @Header("x-api-key") String token);

    @GET("./movies/with-latest-meetings/")
    Call<List<Movie>> getAllRecentMeetingMovies(@Header("x-api-key") String token, @Query("page") int page);

    @GET("./movies/playing/")
    Call<List<Movie>> getAllPlayingMovies(@Header("x-api-key") String token, @Query("page") int page);

    @GET("./movies/upcoming/")
    Call<List<Movie>> getAllUpcomingMovies(@Header("x-api-key") String token, @Query("page") int page);

    @GET("./meetings/{id}")
    Call<MeetingFromApi> getMeetingByID(@Path("id") int meetingId, @Header("x-api-key") String token);

    @GET("./meetings/for/{filmId}")
    Call<List<MeetingFromApi>> getMeetingByMovieID(@Path("filmId") int movieId, @Header("x-api-key") String token);

    @POST("./meetings/for/{filmId}")
    Call<Void> addMeeting(@Path("filmId") int movieId, @Body Meeting meeting, @Header("x-api-key") String token);

    @PUT("./meetings/join/{meetingId}")
    Call<Void> joinMeeting(@Path("meetingId") int meetingId, @Body int idUser, @Header("x-api-key") String token);

}
