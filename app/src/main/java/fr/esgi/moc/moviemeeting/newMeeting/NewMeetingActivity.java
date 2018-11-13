package fr.esgi.moc.moviemeeting.newMeeting;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.SharedPreferencesManager;
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Meeting;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import fr.esgi.moc.moviemeeting.movies.MoviesListFragment;
import retrofit2.Callback;
import retrofit2.Response;

public class NewMeetingActivity extends AppCompatActivity {

    @BindView(R.id.edt_meeting_title_add)
    EditText edt_meeting_title_add;

    @BindView(R.id.edt_meeting_desc_add)
    EditText edt_meeting_desc_add;

    @BindView(R.id.btn_create_meeting)
    Button btn_create_meeting;

    MovieMeetingApiProvider provider;

    Movie movie;
    Meeting meeting;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_meeting);
        ButterKnife.bind(this);
        provider = new MovieMeetingApiProvider();
        meeting = new Meeting();

        user = SharedPreferencesManager.getUser(this);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Log.d("CREATE", "ez");

    }

    @OnClick(R.id.btn_create_meeting) public void clickOnCreating() {

        Log.d("BUTTON", "ez");
        Date currentTime = Calendar.getInstance().getTime();

        meeting.setCreationDate(currentTime.toString());
        meeting.setMeetingDate(currentTime.toString());
        meeting.setDescription(edt_meeting_desc_add.getText().toString());
        meeting.setTitle(edt_meeting_title_add.getText().toString());

        Serializable content = getIntent().getSerializableExtra(MoviesListFragment.MOVIE_KEY);

        if (content != null) {
            movie = (Movie) content;
            Log.d("id", String.valueOf(movie.getIdMovie()));
            retrofit2.Call<Void> apiResponse =  provider.addMeeting(movie.getIdMovie(), meeting, user);
            apiResponse.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                    Log.d("CREATE", "onResponse");
                    if (response.code() == 201) {
                        Log.d("CREATE", "201");
                        //onSuccess();
                        finish();
                    } else {
                        Log.d("BUG", response.code() + " " + response.message() );
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<Void> call, Throwable t) {
                    Log.d("CREATE", t.getMessage());
                }
            });

        }

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



}
