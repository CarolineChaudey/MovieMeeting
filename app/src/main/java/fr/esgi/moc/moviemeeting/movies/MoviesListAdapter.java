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
import fr.esgi.moc.moviemeeting.data.api.MovieMeetingApiProvider;
import fr.esgi.moc.moviemeeting.data.dtos.Movie;

public class MoviesListAdapter extends RecyclerView.Adapter<MoviesListAdapter.MyViewHolder> {
    private Context context;
    private List<Movie> movies;
    private static final String TAG = "MoviesListAdapter";

    private String rv_type;


    private Listener listener;


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

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public MoviesListAdapter(Context context, List<Movie> movies, String rv_type) {
        this.context = context;
        this.movies = movies;
        this.rv_type = rv_type;
    }


    public void updateData(List<Movie> movies) {

        this.movies.addAll(movies);
    }

    public void eraseData(List<Movie> movies){
        this.movies = movies;
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


        String imgUrl = MovieMeetingApiProvider.BASEIMG_URL + movie.getPoster_path();
        Picasso.get().load(imgUrl).into(holder.moviePoster);
        holder.movieTitle.setText(movie.getTitle());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener != null) listener.onMovieClick(movie);
            }
        });

        listener.onPosition(rv_type, holder.getAdapterPosition());





    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public interface Listener {
        void onMovieClick(Movie movie);
        void onPosition(String rv_type, int position);
    }
}