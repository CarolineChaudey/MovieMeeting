package fr.esgi.moc.moviemeeting.movies;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import fr.esgi.moc.moviemeeting.R;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;

public class MoviesListAdapter extends BaseAdapter {
    private Context context;
    private List<Movie> movies;
    private final String BASEIMG_URL = "http://image.tmdb.org/t/p/w185/";



    public MoviesListAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
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


            if(movies.get(position) != null){
                //imageView.setImageResource(mThumbIds[position]);
                //Log.i("image url : ", BASEIMG_URL + movies.get(position).getPoster_path());
                String imgUrl = BASEIMG_URL + movies.get(position).getPoster_path();
                Picasso.get().load(imgUrl).into(imageView);

            }


        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    public void updateData(Movie movie) {

        this.movies.add(movie);
    }

    @Override
    public int getCount() {
        return movies.size();
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