package fr.esgi.moc.moviemeeting.meetings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import fr.esgi.moc.moviemeeting.movies.MoviesListFragment;
import fr.esgi.moc.moviemeeting.newMeeting.NewMeetingActivity;
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

    @BindView(R.id.tvw_no_meetings)
    TextView no_meetings;

    @BindView(R.id.meetingsProgressBar)
    ProgressBar meetingsProgressBar;

    @BindView(R.id.fab_addMeeting)
    FloatingActionButton fab_addMeeting;



    private MovieMeetingApiProvider provider;
    private List<Meeting> meetings = new ArrayList<Meeting>();
    private MeetingsListAdapter adapter;

    private Movie movie;

    public static final String MEETING_KEY = "meetingSelect";

    private Context context;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        ButterKnife.bind(this);
        no_meetings.setVisibility(View.GONE);
        rvw_meetings.setVisibility(View.GONE);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        context = this;

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

            user = SharedPreferencesManager.getUser(this);


        }

        fab_addMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NewMeetingActivity.class);
                intent.putExtra(MoviesListFragment.MOVIE_KEY, movie);
                startActivity(intent);

            }
        });


    }

    @Override
    public void onResume() {

        super.onResume();
        meetingsProgressBar.setVisibility(View.VISIBLE);
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
        Call<List<Meeting>> response = provider.getMeetingByMovieID(movie.getIdMovie(), user);
        response.enqueue(new Callback<List<Meeting>>() {
            @Override
            public void onResponse(Call<List<Meeting>> call, Response<List<Meeting>> response) {

                meetingsProgressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    List<Meeting> lmeetings = response.body();

                    meetings = lmeetings;
                    Log.d("SIZE", String.valueOf(meetings.size()));
                    if(meetings.size() == 0){
                        no_meetings.setVisibility(View.VISIBLE);
                        rvw_meetings.setVisibility(View.GONE);
                    } else {
                        no_meetings.setVisibility(View.GONE);
                        rvw_meetings.setVisibility(View.VISIBLE);
                    }
                    adapter.updateData(meetings);
                    adapter.notifyDataSetChanged();

                } else {
                    Log.e("ERRL", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Meeting>> call, Throwable t) {

                Log.e("ERR", t.getMessage());
            }
        });
    }



}
