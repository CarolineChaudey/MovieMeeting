package fr.esgi.moc.moviemeeting.navigation;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.movies.MoviesListActivity;

public class NavigationActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private NavPageAdapter navPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);


        List<Fragment> fragments = new Vector<>();

        fragments.add(Fragment.instantiate(this, MoviesListActivity.class.getName()));
        fragments.add(Fragment.instantiate(this, MoviesListActivity.class.getName()));
        fragments.add(Fragment.instantiate(this, MoviesListActivity.class.getName()));
        navPageAdapter = new NavPageAdapter(super.getSupportFragmentManager(), fragments);


        viewPager.setAdapter(navPageAdapter);


        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_movies_list),
                        R.color.colorAccent
                ).title("Movies")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_buddys_list),
                        R.color.colorPrimary
                ).title("Buddys")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.mipmap.ic_profile),
                        R.color.colorAccent
                ).title("Profile")
                        .build()
        );

        navigationTabBar.setModels(models);

        navigationTabBar.setViewPager(viewPager, 0);





    }
}
