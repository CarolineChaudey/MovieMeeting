package fr.esgi.moc.moviemeeting.meetings;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.florent37.expansionpanel.ExpansionLayout;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.SharedPreferencesManager;
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Meeting;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import fr.esgi.moc.moviemeeting.movies.MoviesListAdapter;
import fr.esgi.moc.moviemeeting.movies.MoviesListFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingsListActivity extends AppCompatActivity {

    @BindView(R.id.expansionLayout)
    ExpansionLayout expansionLayout;

    @BindView(R.id.movie_poster_meetings)
    ImageView movie_poster_meetings;

    @BindView(R.id.movie_title_meetings)
    TextView movie_title_meetings;

    @BindView(R.id.rating)
    RatingBar rating;

    @BindView(R.id.rating_count)
    TextView rating_count;

    @BindView(R.id.movie_releaseDate_meetings)
    TextView movie_releaseDate_meetings;

    @BindView(R.id.movie_synopsys_meetings)
    TextView movie_synopsys_meetings;

    @BindView(R.id.rvw_meetings)
    RecyclerView rvw_meetings;

    private MovieMeetingApiProvider provider;
    private List<Meeting> meetings = new ArrayList<Meeting>();
    private MeetingsListAdapter adapter;

    private Movie movie;

    public static final String MEETING_KEY = "meetingSelect";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        ButterKnife.bind(this);

        provider = new MovieMeetingApiProvider();

        Serializable content = getIntent().getSerializableExtra(MoviesListFragment.MOVIE_KEY);

        if (content != null) {
            movie = (Movie) content;

            String imgUrl = MovieMeetingApiProvider.BASEIMG_URL + movie.getPoster_path();
            Picasso.get().load(imgUrl).into(movie_poster_meetings);
            movie_title_meetings.setText(movie.getTitle());
            rating.setRating((float)movie.getVote_average()/2);
            rating_count.setText(String.valueOf(movie.getVote_count()) + " votes");
            movie_releaseDate_meetings.setText(movie.getRelease_date().toString());
            if(movie.getOverview().isEmpty()){
                movie_synopsys_meetings.setText("Pas de synopsys");
            } else {
                movie_synopsys_meetings.setText(movie.getOverview());
            }

            adapter = new MeetingsListAdapter(this,meetings);

            rvw_meetings.setItemAnimator(new DefaultItemAnimator());
            rvw_meetings.setAdapter(adapter);

            User user = SharedPreferencesManager.getUser(this);

            for (int i = 1; i <= 7; i++) {
                Call<Meeting> response = provider.getMeetingByID(i, user);
                response.enqueue(new Callback<Meeting>() {
                    @Override
                    public void onResponse(Call<Meeting> call, Response<Meeting> response) {

                        if (response.code() == 200) {
                            Meeting meeting = response.body();
                             meetings.add(meeting);
                             //adapter.updateData(meeting);
                             adapter.notifyDataSetChanged();

                        } else {
                            Log.e("ERRL", response.message());
                        }
                    }

                    @Override
                    public void onFailure(Call<Meeting> call, Throwable t) {

                        Log.e("ERR", t.getMessage());
                    }
                });
            }


        }


        /*ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle("Liste des meetings");*/


    }



}
