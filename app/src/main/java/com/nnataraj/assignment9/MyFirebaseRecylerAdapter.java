package com.nnataraj.assignment9;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class MyFirebaseRecylerAdapter extends FirebaseRecyclerAdapter<Movie, MyFirebaseRecylerAdapter.MovieViewHolder> {

    private Context mContext;
    private OnListInteractionListener mListener;

    public MyFirebaseRecylerAdapter(Class<Movie> modelClass, int modelLayout,
                                    Class<MovieViewHolder> holder, DatabaseReference ref, Context context) {
        super(modelClass, modelLayout, holder, ref);
        this.mContext = context;
    }

    void setOnListInteractionListener(OnListInteractionListener mListener) {
        this.mListener = mListener;
    }

    @Override
    protected void populateViewHolder(MovieViewHolder movieViewHolder, final Movie movie, final int i) {
        movieViewHolder.mTitle.setText(movie.getName());
        movieViewHolder.mDescription.setText(movie.getDescription());
        movieViewHolder.mYear.setText(movie.getYear());
        Picasso.with(mContext).load(movie.getUrl()).into(movieViewHolder.mIcon);
        movieViewHolder.mRatingBar.setRating((Float.parseFloat(movie.getRating())) / 2f);

        movieViewHolder.mOverflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onOverflowMenuClick(v, movie, i);
            }
        });
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        final View mView;
        final TextView mTitle;
        final TextView mDescription;
        final ImageView mIcon;
        final TextView mYear;
        final RatingBar mRatingBar;
        final ImageView mOverflow;

        public MovieViewHolder(View view) {
            super(view);
            mView = view;
            mTitle = (TextView) view.findViewById(R.id.title);
            mDescription = (TextView) view.findViewById(R.id.description);
            mIcon = (ImageView) view.findViewById(R.id.movie_icon);
            mYear = (TextView) view.findViewById(R.id.year);
            mRatingBar = (RatingBar) view.findViewById(R.id.ratingBar);
            mOverflow = (ImageView) view.findViewById(R.id.menu_moreoverflow);
        }
    }


}
