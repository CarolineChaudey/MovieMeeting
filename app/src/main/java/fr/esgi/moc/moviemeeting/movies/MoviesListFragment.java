package fr.esgi.moc.moviemeeting.movies;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.SharedPreferencesManager;
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import fr.esgi.moc.moviemeeting.meetings.MeetingsListActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListFragment extends Fragment implements MoviesListAdapter.Listener {

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

    private int page_recent_meetings = 1;
    private int page_now_playing = 1;
    private int page_upcoming = 1;

    public static final String MOVIE_KEY = "movieSelect";

    public static final String RECENT_MEETINGS = "RECENT_MEETINGS";
    public static final String NOW_PLAYING = "NOW_PLAYING";
    public static final String UPCOMING = "UPCOMING";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_movies_list, container, false);
        ButterKnife.bind(this, view);
        context = getActivity().getApplicationContext();
        provider = new MovieMeetingApiProvider();

        adapter_last_events = new MoviesListAdapter(context,movies_last_events, RECENT_MEETINGS);
        adapter_now_playing = new MoviesListAdapter(context,movies_now_playing, NOW_PLAYING);
        adapter_upcoming = new MoviesListAdapter(context, movies_upcoming, UPCOMING);

        this.adapter_last_events.setListener(this);
        this.adapter_now_playing.setListener(this);
        this.adapter_upcoming.setListener(this);

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

        Call<List<Movie>> response = provider.getAllRecentMeetingMovies(user, page_recent_meetings);
        response.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if (response.code() == 200) {
                    //Movie movie = response.body();
                    List<Movie> movies = response.body();
                    // movies.add(movie);
                    adapter_last_events.updateData(movies);
                    adapter_last_events.notifyDataSetChanged();
                  //  Log.d("GET", movie.getOriginal_title());
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
        Call<List<Movie>> response = provider.getAllPlayingMovies(user, page_now_playing);
        response.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if (response.code() == 200) {
                    //Movie movie = response.body();
                    List<Movie> movies = response.body();
                    // movies.add(movie);
                    adapter_now_playing.updateData(movies);
                    adapter_now_playing.notifyDataSetChanged();
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
        Call<List<Movie>> response = provider.getAllUpcomingMovies(user, page_upcoming);
        response.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if (response.code() == 200) {
                    //Movie movie = response.body();
                    List<Movie> movies = response.body();
                    // movies.add(movie);
                    adapter_upcoming.updateData(movies);
                    adapter_upcoming.notifyDataSetChanged();
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

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this.getActivity(), MeetingsListActivity.class);
        intent.putExtra(MOVIE_KEY, movie);
        startActivity(intent);
    }

    @Override
    public void onPosition(String rv_type, int position) {

            switch(rv_type){
                case "RECENT_MEETINGS":
                    if(position == 8*page_recent_meetings) {
                        loadLastEventMovies();
                        page_recent_meetings++;
                    }
                    break;
                case "NOW_PLAYING":

                    if(position == 8*page_now_playing) {
                        loadNowPlayingMovies();
                        page_now_playing++;
                    }
                    break;
                case "UPCOMING":
                    if(position == 8*page_upcoming) {
                        loadUpcomingMovies();
                        page_upcoming++;
                    }
                    break;
                default:
                    break;
            }

    }
}
