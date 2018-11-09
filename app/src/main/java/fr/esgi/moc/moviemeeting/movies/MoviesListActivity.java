package fr.esgi.moc.moviemeeting.movies;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.SharedPreferencesManager;
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListActivity extends Fragment {

    @BindView(R.id.rvw_last_event)
    RecyclerView rvw_last_events;

    @BindView(R.id.rvw_now_playing)
    RecyclerView rvw_now_playing;

    @BindView(R.id.rvw_upcoming)
    RecyclerView rvw_upcoming;

    private MovieMeetingApiProvider provider;
    private MoviesListAdapter adapter_last_events, adapter_now_playing, adapter_upcoming;
    private List<Movie> movies_last_events = new ArrayList<Movie>();
    private List<Movie> movies_now_playing = new ArrayList<Movie>();
    private List<Movie> movies_upcoming = new ArrayList<Movie>();
    private Context context;

    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_movies_list, container, false);
        ButterKnife.bind(this, view);
        context = getActivity().getApplicationContext();
        provider = new MovieMeetingApiProvider();
        int[] imgs = new int[10];
        for(int i = 0; i < imgs.length; i++){
            imgs[i] = R.drawable.background_login;
        }


        adapter_last_events = new MoviesListAdapter(context,movies_last_events);
        adapter_now_playing = new MoviesListAdapter(context,movies_now_playing);
        adapter_upcoming = new MoviesListAdapter(context, movies_upcoming);



        rvw_last_events.setItemAnimator(new DefaultItemAnimator());
        rvw_last_events.setAdapter(adapter_last_events);


        rvw_now_playing.setItemAnimator(new DefaultItemAnimator());
        rvw_now_playing.setAdapter(adapter_now_playing);


        rvw_upcoming.setItemAnimator(new DefaultItemAnimator());
        rvw_upcoming.setAdapter(adapter_upcoming);

        user = SharedPreferencesManager.getUser(getActivity());

        loadLastEventMovies();
        loadNowPlayingMovies();
        loadUpcomingMovies();

        return view;
    }


    public void loadLastEventMovies(){

        Call<List<Movie>> response = provider.getAllRecentMeetingMovies(user);
        response.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if (response.code() == 200) {
                    Movie movie = response.body().get(0);
                    // movies.add(movie);
                    adapter_last_events.updateData(movie);
                    adapter_last_events.notifyDataSetChanged();
                    Log.d("GET", movie.getOriginal_title());
                } else {
                    Log.e("ERRL", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

                Log.e("ERR", t.getMessage());
            }
        });

    }

    public void loadNowPlayingMovies(){
        Call<List<Movie>> response = provider.getAllPlayingMovies(user);
        response.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if (response.code() == 200) {
                    Movie movie = response.body().get(0);
                    // movies.add(movie);
                    adapter_now_playing.updateData(movie);
                    adapter_now_playing.notifyDataSetChanged();
                    Log.d("GET", movie.getOriginal_title());
                } else {
                    Log.e("ERRL", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

                Log.e("ERR", t.getMessage());
            }
        });
    }

    public void loadUpcomingMovies(){
        Call<List<Movie>> response = provider.getAllUpcomingMovies(user);
        response.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if (response.code() == 200) {
                    Movie movie = response.body().get(0);
                    // movies.add(movie);
                    adapter_upcoming.updateData(movie);
                    adapter_upcoming.notifyDataSetChanged();
                    Log.d("GET", movie.getOriginal_title());
                } else {
                    Log.e("ERRL", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

                Log.e("ERR", t.getMessage());
            }
        });
    }
}
