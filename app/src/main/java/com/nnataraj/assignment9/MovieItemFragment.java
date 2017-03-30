package com.nnataraj.assignment9;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A fragment representing a list of Items.
 */
public class MovieItemFragment extends Fragment {

    private MyFirebaseRecylerAdapter myFirebaseRecylerAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovieItemFragment() {
    }

    private class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = 20;
            }
            outRect.bottom = 20;
            outRect.left = 20;
            outRect.right = 20;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movieitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            final DatabaseReference movieData = FirebaseDatabase.getInstance().getReference().child("moviedata").getRef();
            myFirebaseRecylerAdapter = new MyFirebaseRecylerAdapter(Movie.class, R.layout.fragment_movieitem, MyFirebaseRecylerAdapter.MovieViewHolder.class, movieData, getContext());
            recyclerView.setAdapter(myFirebaseRecylerAdapter);
            recyclerView.addItemDecoration(new VerticalSpaceItemDecoration());
            myFirebaseRecylerAdapter.setOnListInteractionListener(new OnListInteractionListener() {
                @Override
                public void onOverflowMenuClick(View view, final Movie movieItem, final int position) {
                    PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_popup, popupMenu.getMenu());
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            switch (menuItem.getItemId()) {
                                case R.id.duplicate:
                                    Log.d("NAGA", "Duplicate " + movieItem.getId());
                                    Movie duplicate = movieItem;
                                    duplicate.setId(duplicate.getId()+"_new");
                                    movieData.child(duplicate.getId()).setValue(duplicate);
                                    return true;
                                case R.id.delete:
                                    Log.d("NAGA", "Delete " + movieItem.getId());
                                    movieData.child(movieItem.getId()).setValue(null);
                                    return true;
                            }
                            return false;
                        }
                    });
                    popupMenu.show();
                }
            });
        }
        return view;
    }

}
