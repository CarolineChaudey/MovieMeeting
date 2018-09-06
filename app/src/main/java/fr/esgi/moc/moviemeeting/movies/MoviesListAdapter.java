package fr.esgi.moc.moviemeeting.movies;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;

public class MoviesListAdapter extends BaseAdapter {
    private Context context;
    private final int[] mThumbIds;
    private List<Movie> movies;



    public MoviesListAdapter(Context context, int[] mThumbIds) {
        this.context = context;
        this.mThumbIds = mThumbIds;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            // get layout from mobile.xml
            gridView = inflater.inflate(R.layout.cell_movies, null);

            // set image based on selected text
            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.movie_poster);


            imageView.setImageResource(mThumbIds[position]);


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    public void updateData(List<Movie> movies) {
        this.movies = movies;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}