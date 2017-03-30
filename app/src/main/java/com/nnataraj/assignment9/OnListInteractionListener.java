package com.nnataraj.assignment9;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nagaprasad on 3/22/17.
 */

public interface OnListInteractionListener {
    void onOverflowMenuClick(View view, Movie item, int position);
}
