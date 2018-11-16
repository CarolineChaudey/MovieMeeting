package fr.esgi.moc.moviemeeting.meetings;

import android.content.Context;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.DateFormatter;
import fr.esgi.moc.moviemeeting.data.dtos.Meeting;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;

public class MeetingsListAdapter extends RecyclerView.Adapter<MeetingsListAdapter.MyViewHolder> {
    private Context context;
    private List<Meeting> meetings;
    private static final String TAG = "MeetingsListAdapter";


    private Listener listener;

    DateFormatter df;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.meeting_title)
        TextView meetingTitle;
        @BindView(R.id.meeting_date)
        TextView meetingDate;
        @BindView(R.id.meeting_desc)
        TextView meetingDesc;
        @BindView(R.id.meeting_nb_person)
        TextView meetingNbPerson;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Log.d(TAG, "view holder called");
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public MeetingsListAdapter(Context context, List<Meeting> meetings) {
        this.context = context;
        this.meetings = meetings;
    }


    public void updateData(List<Meeting> meetings) {

        this.meetings = meetings;
    }


    public Object getItem(int position) {
        return null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_meetings, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Meeting meeting = meetings.get(position);


       // holder.meetingTitle.setText(meeting.getTitle());
        //holder.meetingDate.setText(meeting.getMeetingDate());

        df = new DateFormatter(meeting.getMeetingDate());


        holder.meetingDesc.setText(meeting.getDescription());
        holder.meetingDate.setText(df.getFormatDate());
        //holder.meetingNbPerson.setText();


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) listener.onMeetingClick(meeting);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }


    public interface Listener {
        void onMeetingClick(Meeting meeting);
    }
}