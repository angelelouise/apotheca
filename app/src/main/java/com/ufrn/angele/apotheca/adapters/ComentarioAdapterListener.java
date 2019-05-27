package com.ufrn.angele.apotheca.adapters;

import android.view.View;

public interface ComentarioAdapterListener {
    void voteOnClick(View v, int position);
    void downvoteOnClick(View v, int position);
}
