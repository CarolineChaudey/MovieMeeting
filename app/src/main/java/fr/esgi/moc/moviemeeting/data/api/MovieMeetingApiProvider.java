package fr.esgi.moc.moviemeeting.data.api;

import fr.esgi.moc.moviemeeting.data.dtos.Movie;

import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieMeetingApiProvider {

    // change to your web service host
    private static final String BASE_URL = "http://192.168.0.24:8000";

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

    /*public Call<User> connect(Credentials credentials) {
        return pmnService.getToken(credentials);
    }

    public Call<User> register(CandidateUser candidate) {
        return pmnService.register(candidate);
    }

    public Call<Void> logout(String token) {
        return pmnService.logout(token);
    }

    public Call<Void> checkToken(String token) {
        return pmnService.checkToken(token);
    }
*/
    public Call<List<Movie>> getMovieByID(int id) {
        return service.getMovieByID(id);
    }



}
