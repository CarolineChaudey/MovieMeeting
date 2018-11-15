package fr.esgi.moc.moviemeeting.account;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.login.LoginActivity;
import fr.esgi.moc.moviemeeting.navigation.NavigationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AcountFragment extends Fragment {


    @BindView(R.id.btn_quit)
    ImageButton btn_quit;

    public AcountFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_acount, container, false);
        ButterKnife.bind(this, view);

        return view;
    }


    @OnClick(R.id.btn_quit)
    public void quitApp(){
        Intent myIntent = new Intent(getContext(), LoginActivity.class);

        this.startActivity(myIntent);
        getActivity().finish();
    }

}
