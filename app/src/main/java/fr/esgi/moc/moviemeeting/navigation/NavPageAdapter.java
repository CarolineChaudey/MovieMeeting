package fr.esgi.moc.moviemeeting.navigation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class NavPageAdapter extends FragmentPagerAdapter {

    private final List<Fragment> fragments;

    //On fournit à l'adapter la liste des fragments à afficher
    public NavPageAdapter(FragmentManager fm, List fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}