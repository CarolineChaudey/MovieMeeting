package fr.esgi.moc.moviemeeting.meetings;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.dtos.User;

public class MeetingDetailsAdapter extends RecyclerView.Adapter<MeetingDetailsAdapter.MyViewHolder> {
    private Context context;
    private List<User> participants;
    private static final String TAG = "MeetingDetailsAdapter";


    private Listener listener;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvw_user_name_meeting)
        TextView tvw_user_name_meeting;


        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public MeetingDetailsAdapter(Context context, List<User> participants) {
        this.context = context;
        this.participants = participants;
    }


    public void updateData(List<User> participants) {

        this.participants = participants;
    }


    public Object getItem(int position) {
        return null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_users, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final User participant = participants.get(position);


        holder.tvw_user_name_meeting.setText(participant.getPseudo());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) listener.onMeetingClick(participant);
            }
        });

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }


    public interface Listener {
        void onMeetingClick(User participant);
    }

}