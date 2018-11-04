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
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
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
    private MoviesListAdapter adapter;
    private List<Movie> movies = new ArrayList<Movie>();
    private Context context;

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


        adapter = new MoviesListAdapter(context,movies);



        rvw_last_events.setItemAnimator(new DefaultItemAnimator());
        rvw_last_events.setAdapter(adapter);


        rvw_now_playing.setItemAnimator(new DefaultItemAnimator());
        rvw_now_playing.setAdapter(adapter);


        rvw_upcoming.setItemAnimator(new DefaultItemAnimator());
        rvw_upcoming.setAdapter(adapter);
        // on affiche les 10 1ers
            for (int i = 1; i <= 10; i++) {
                Call<List<Movie>> response = provider.getMovieByID(i);
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
}
