package fr.esgi.moc.moviemeeting.navigation;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;



import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;
import devlight.io.library.ntb.NavigationTabBar;
import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.meetings.MeetingsListActivity;
import fr.esgi.moc.moviemeeting.movies.MoviesListFragment;
import fr.esgi.moc.moviemeeting.movies.MoviesSearchActivity;

public class NavigationActivity extends AppCompatActivity {

    @BindView(R.id.viewPager)
    ViewPager viewPager;

    /*@BindView(R.id.search_view)
    MaterialSearchView searchView;*/

    private NavPageAdapter navPageAdapter;
    private boolean showTitleSearch = false;

    private Context context;

    public static final String KEYWORD_KEY = "keywordEnter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);

        context = this;


        List<Fragment> fragments = new Vector<>();

        fragments.add(Fragment.instantiate(this, MoviesListFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MoviesListFragment.class.getName()));
        fragments.add(Fragment.instantiate(this, MoviesListFragment.class.getName()));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnSearchClickListener(new SearchView.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getSupportActionBar().setDisplayShowTitleEnabled(true);
                return false;
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                //TODO write your code what you want to perform on search

                Intent intent = new Intent(context, MoviesSearchActivity.class);
                intent.putExtra(KEYWORD_KEY, query);
                startActivity(intent);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //TODO write your code what you want to perform on search text change
                return true;
            }
        });
        return true;
    }






}
