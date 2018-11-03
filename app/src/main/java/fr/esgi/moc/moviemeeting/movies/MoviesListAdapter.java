package fr.esgi.moc.moviemeeting.movies;

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
import fr.esgi.moc.moviemeeting.data.dtos.Movie;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder> {
    private Context context;
    private List<Movie> movies;
    private final String BASEIMG_URL = "http://image.tmdb.org/t/p/w185/";
    private static final String TAG = "MoviesListAdapter";


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.movie_poster)
        ImageView moviePoster;
        @BindView(R.id.movie_title)
        TextView movieTitle;
        @BindView(R.id.movie_author)
        TextView movieAuthor;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            Log.d(TAG, "view holder called");
        }
    }

    public MoviesListAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }


    public void updateData(Movie movie) {

        this.movies.add(movie);
    }


    public Object getItem(int position) {
        return null;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cell_movies, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Movie movie = movies.get(position);


        String imgUrl = BASEIMG_URL + movie.getPoster_path();
        Picasso.get().load(imgUrl).into(holder.moviePoster);
        holder.movieTitle.setText(movie.getTitle());

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

}