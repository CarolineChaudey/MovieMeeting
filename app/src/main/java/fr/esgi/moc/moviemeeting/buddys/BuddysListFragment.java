package fr.esgi.moc.moviemeeting.buddys;


import android.content.Context;
import android.icu.lang.UScript;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import fr.esgi.moc.moviemeeting.meetings.MeetingDetailsAdapter;
import fr.esgi.moc.moviemeeting.movies.MoviesListAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BuddysListFragment extends Fragment {


    @BindView(R.id.rvw_buddys)
    RecyclerView rvw_buddys;

    private MovieMeetingApiProvider provider;
    private MeetingDetailsAdapter adapter;
    private List<User> buddys = new ArrayList<User>();

    private Context context;

    private User user;

    public BuddysListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_buddys_list, container, false);
        ButterKnife.bind(this, view);
        context = getActivity().getApplicationContext();
        provider = new MovieMeetingApiProvider();

        adapter = new MeetingDetailsAdapter(context, buddys);

        rvw_buddys.setItemAnimator(new DefaultItemAnimator());
        rvw_buddys.setAdapter(adapter);


        user = SharedPreferencesManager.getUser(getActivity());

        loadBuddys();

        // Inflate the layout for this fragment
        return view;
    }

    public void loadBuddys(){

        Call<List<User>> response = provider.getBuddys(user);
        response.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                if (response.code() == 200) {
                    //Movie movie = response.body();
                    List<User> lbuddys = response.body();
                    // movies.add(movie);


                    buddys = lbuddys;
                    adapter.updateData(buddys);
                    adapter.notifyDataSetChanged();
                    //  Log.d("GET", movie.getOriginal_title());
                } else {
                    Log.e("ERRL", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

                Log.e("ERR", t.getMessage());
            }
        });

    }

}
