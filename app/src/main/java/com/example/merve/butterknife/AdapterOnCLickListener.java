package com.example.merve.butterknife;

import android.view.View;

/**
 * Created by merve on 21.03.2018.
 */

public interface AdapterOnCLickListener {
    void onClick(View view, int position);

    void onClickMedia(View view, int position);

    void onClickCardView(View view, int position);

    void onLongClick(View view, int position);

    void onLongClickMedia(View view, int position);
}
