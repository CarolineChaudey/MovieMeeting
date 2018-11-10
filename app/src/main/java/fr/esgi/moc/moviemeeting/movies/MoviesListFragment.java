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
    private MoviesListAdapter adapter;
    private List<Movie> movies = new ArrayList<Movie>();
    private Context context;

    public static final String MOVIE_KEY = "movieSelect";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_movies_list, container, false);
        ButterKnife.bind(this, view);
        context = getActivity().getApplicationContext();
        provider = new MovieMeetingApiProvider();

        adapter = new MoviesListAdapter(context,movies);

        this.adapter.setListener(this);

        rvw_last_events.setItemAnimator(new DefaultItemAnimator());
        rvw_last_events.setAdapter(adapter);


        rvw_now_playing.setItemAnimator(new DefaultItemAnimator());
        rvw_now_playing.setAdapter(adapter);


        rvw_upcoming.setItemAnimator(new DefaultItemAnimator());
        rvw_upcoming.setAdapter(adapter);

        User user = SharedPreferencesManager.getUser(getActivity());

        // on affiche les 10 1ers
            for (int i = 1; i <= 10; i++) {
                Call<List<Movie>> response = provider.getMovieByID(i, user);
                response.enqueue(new Callback<List<Movie>>() {
                    @Override
                    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                        if (response.code() == 200) {
                            Movie movie = response.body().get(0);
                           // movies.add(movie);
                            adapter.updateData(movie);
                            adapter.notifyDataSetChanged();
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

            return view;
    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this.getActivity(), MeetingsListActivity.class);
        intent.putExtra(MOVIE_KEY, movie);
        startActivity(intent);
    }
}
