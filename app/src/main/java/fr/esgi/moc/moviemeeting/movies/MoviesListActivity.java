package fr.esgi.moc.moviemeeting.movies;


import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;

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

public class MoviesListActivity extends AppCompatActivity {

    @BindView(R.id.movies_grid)
    GridView moviesGrid;
    private MovieMeetingApiProvider provider;
    List<Movie> movies = new ArrayList<Movie>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_list);
        ButterKnife.bind(this);
        provider = new MovieMeetingApiProvider();
        int[] imgs = new int[10];
        for(int i = 0; i < imgs.length; i++){
            imgs[i] = R.drawable.background_login;
        }
        moviesGrid.setAdapter(new MoviesListAdapter(this,imgs));

            Call<List<Movie>> response = provider.getMovieByID(1);
            response.enqueue(new Callback<List<Movie>>() {
                @Override
                public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {


                    if (response.code() == 200) {
                        Movie movie = response.body().get(0);
                        movies.add(movie);
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
