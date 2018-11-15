package fr.esgi.moc.moviemeeting.meetings;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.SharedPreferencesManager;
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Meeting;
import fr.esgi.moc.moviemeeting.data.dtos.MeetingFromApi;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;
import fr.esgi.moc.moviemeeting.data.dtos.User;
import fr.esgi.moc.moviemeeting.movies.MoviesListFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetingDetailsActivity extends AppCompatActivity {

    @BindView(R.id.tvw_meeting_desc_detail)
    TextView tvw_meeting_desc_detail;

    @BindView(R.id.rvw_members)
    RecyclerView rvw_members;

    @BindView(R.id.usersProgressBar)
    ProgressBar usersProgressBar;

    @BindView(R.id.btn_join_meeting)
    Button btn_join_meeting;


    private MovieMeetingApiProvider provider;
    private MeetingDetailsAdapter adapter;

    Meeting meeting;

    private User user;
    private List<User> participants;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_details);
        ButterKnife.bind(this);

        provider = new MovieMeetingApiProvider();
        user = SharedPreferencesManager.getUser(this);

        rvw_members.setVisibility(View.GONE);
        usersProgressBar.setVisibility(View.VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);



        Serializable content = getIntent().getSerializableExtra(MeetingsListActivity.MEETING_KEY);

        if (content != null) {

            meeting = (Meeting) content;

            participants = meeting.getParticipants();
           // Log.d("USERS", meeting.getUsers().toString());

            adapter = new MeetingDetailsAdapter(this,participants);
            rvw_members.setItemAnimator(new DefaultItemAnimator());
            rvw_members.setAdapter(adapter);


            actionBar.setTitle(meeting.getTitle());

            tvw_meeting_desc_detail.setText(meeting.getDescription());

        }
    }

    @Override
    public void onResume() {

        super.onResume();
        usersProgressBar.setVisibility(View.VISIBLE);
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
    @OnClick(R.id.btn_join_meeting)
    public void joinOrQuitMeeting(){

        if(checkUserIsIn()){
            if(participants.size() == 1){
                Toast.makeText(this, "Impossible vous êtes le dernier", Toast.LENGTH_SHORT);
            }

        } else {
            joinMeeting();
        }

    }

    public boolean checkUserIsIn(){

        for(User participant : participants){
            if(participant.getPseudo().contentEquals(user.getPseudo())){

                btn_join_meeting.setBackgroundResource(R.color.textTheme);
                btn_join_meeting.setTextColor(getResources().getColor(R.color.colorWhiter));
                btn_join_meeting.setText(R.string.str_quit_meeting);

                return true;
            }else {
                btn_join_meeting.setBackgroundResource(R.color.colorPrimaryWhiter);
                btn_join_meeting.setTextColor(getResources().getColor(R.color.textTheme));
                btn_join_meeting.setText(R.string.str_join_meeting);
            }
        }

        return false;

    }


    public void joinMeeting(){

        Call<Void> response = provider.joinMeeting(meeting.getIdMeeting(), user);
        response.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
                Log.d("CREATE", "onResponse");
                if (response.code() == 200) {
                    Log.d("CREATE", "201");
                    //onSuccess();
                    //finish();
                    reloadData();
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

    public void reloadData(){
        Call<MeetingFromApi> response = provider.getMeetingByID(meeting.getIdMeeting(), user);
        response.enqueue(new Callback<MeetingFromApi>() {
            @Override
            public void onResponse(Call<MeetingFromApi> call, Response<MeetingFromApi> response) {

                usersProgressBar.setVisibility(View.GONE);
                if (response.code() == 200) {
                    MeetingFromApi lmeeting = response.body();


                    participants = lmeeting.getUsers();

                    Log.d("participant", participants.get(0).toString());

                    rvw_members.setVisibility(View.VISIBLE);

                    adapter.updateData(participants);
                    adapter.notifyDataSetChanged();

                    checkUserIsIn();

                } else {
                    Log.e("ERRL", response.message());
                }
            }

            @Override
            public void onFailure(Call<MeetingFromApi> call, Throwable t) {

                Log.e("ERR", t.getMessage());
            }
        });
    }
}
