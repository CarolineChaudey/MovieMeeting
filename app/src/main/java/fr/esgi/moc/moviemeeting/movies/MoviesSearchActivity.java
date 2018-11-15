package fr.esgi.moc.moviemeeting.movies;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.SharedPreferencesManager;
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Meeting;
import fr.esgi.moc.moviemeeting.data.dtos.MeetingFromApi;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import fr.esgi.moc.moviemeeting.meetings.MeetingsListActivity;
import fr.esgi.moc.moviemeeting.meetings.MeetingsListAdapter;
import fr.esgi.moc.moviemeeting.navigation.NavigationActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesSearchActivity extends AppCompatActivity implements MoviesListAdapter.Listener{


    @BindView(R.id.rvw_searched)
    RecyclerView rvw_searched;


    private MovieMeetingApiProvider provider;
    private MoviesListAdapter adapter;
    private List<Movie> movies_searched = new ArrayList<Movie>();

    private User user;
    private String keyword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_search);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        rvw_searched.setLayoutManager(new GridLayoutManager(this, 2));

        provider = new MovieMeetingApiProvider();

        user = SharedPreferencesManager.getUser(this);
        Serializable content = getIntent().getSerializableExtra(NavigationActivity.KEYWORD_KEY);

        if (content != null) {
            keyword = (String) content;

            adapter = new MoviesListAdapter(this,movies_searched, "MOVIE_SEARCHED");
            this.adapter.setListener(this);

            rvw_searched.setItemAnimator(new DefaultItemAnimator());
            rvw_searched.setAdapter(adapter);


        }

    }

    @Override
    public void onResume() {

        super.onResume();
        reloadData();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    public void reloadData(){
        Call<List<Movie>> response = provider.getMovieContainName(keyword, user);
        response.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {

                if (response.code() == 200) {
                    List<Movie> lmovies = response.body();

                    movies_searched = lmovies;
                    adapter.eraseData(movies_searched);
                    adapter.notifyDataSetChanged();

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
        Intent intent = new Intent(this, MeetingsListActivity.class);
        intent.putExtra(MoviesListFragment.MOVIE_KEY, movie);
        startActivity(intent);
    }

    @Override
    public void onPosition(String rv_type, int position) {

    }
}
