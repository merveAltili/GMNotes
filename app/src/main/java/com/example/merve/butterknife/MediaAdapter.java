package com.example.merve.butterknife;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.merve.butterknife.db.Entity.MediaEntity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by merve on 26.03.2018.
 */

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {
    List<MediaEntity> list2 = new ArrayList<>();
    private AdapterOnCLickListener listener;

    public MediaAdapter(AdapterOnCLickListener listener) {
        this.listener = listener;
    }

    @Override
    public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MediaAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(MediaAdapter.ViewHolder holder, int position) {
        try {

            Picasso.get().load(new File(list2.get(position).getPath())).into(holder.noteImg);
        } catch (Exception ex) {
            Log.e("hata", ex.toString());
        }
    }

    public void setList2(List<MediaEntity> list2) {
        this.list2.clear();
        this.list2.addAll(list2);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list2.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.noteImg)
        ImageView noteImg;


        ViewHolder(View view, final AdapterOnCLickListener listener) {

            super(view);

            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, getAdapterPosition());
                }
            });

        }
    }
}
