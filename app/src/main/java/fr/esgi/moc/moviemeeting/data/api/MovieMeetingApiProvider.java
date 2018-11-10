package fr.esgi.moc.moviemeeting.data.api;

import fr.esgi.moc.moviemeeting.data.dtos.Credentials;
import fr.esgi.moc.moviemeeting.data.dtos.Meeting;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;

import java.util.List;

import fr.esgi.moc.moviemeeting.data.dtos.User;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieMeetingApiProvider {

    // change to your web service host
    private static final String BASE_URL = "http://192.168.0.24:8000";
    public static final String BASEIMG_URL = "http://image.tmdb.org/t/p/w185/";

    private MovieMeetingApiService service;

    public MovieMeetingApiProvider() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
        service = retrofit.create(MovieMeetingApiService.class);
    }

    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
        return okBuilder.build();
    }

    public Call<User> connect(Credentials credentials) {
        return service.getToken(credentials);
    }

   /* public Call<User> register(CandidateUser candidate) {
        return pmnService.register(candidate);
    }

    public Call<Void> logout(String token) {
        return pmnService.logout(token);
    }

    public Call<Void> checkToken(String token) {
        return pmnService.checkToken(token);
    }
*/
    public Call<List<Movie>> getMovieByID(int id, User user) {
        return service.getMovieByID(id, user.getToken());
    }

    public Call<Meeting> getMeetingByID(int id, User user){
        return service.getMeetingByID(id, user.getToken());
    }

    public Call<List<Meeting>> getMeetingByMovieID(int movieId, User user){
        return service.getMeetingByMovieID(movieId, user.getToken());
    }



}
