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
    boolean selectedMod = false;
    boolean mediaMod = false;
    int count = 0;
    private AdapterOnCLickListener listener;
    private Integer noteposs;


    public MediaAdapter(AdapterOnCLickListener listener, Integer noteposs) {
        this.noteposs = noteposs;
        this.listener = listener;
    }

    public void startSelectedMod() {
        selectedMod = true;
        notifyDataSetChanged();
    }

    public void closeSelectedMod() {

        selectedMod = false;
        notifyDataSetChanged();
    }

    @Override
    public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MediaAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.resim_item, parent, false), listener, noteposs);
    }


    @Override
    public void onBindViewHolder(final MediaAdapter.ViewHolder holder, final int position) {
        try {
            Picasso.get().load(new File(list2.get(position).getPath())).centerCrop().fit().into(holder.imgVItem);
            holder.bind(selectedMod);


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


        @BindView(R.id.imgVItem)
        public ImageView imgVItem;

        @BindView(R.id.imgCancel)
        public ImageView imgCancel;

        ViewHolder(View view, final AdapterOnCLickListener listener, final Integer noteposs) {

            super(view);

            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, getAdapterPosition());
                }
            });
            imgVItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noteposs != -1)
                        listener.onClickMedia(v, noteposs);
                    else
                        listener.onClickMedia(v, getAdapterPosition());

                }
            });

            imgVItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {

                    listener.onLongClickMedia(v, getAdapterPosition());

                    return true;
                }
            });

            imgCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDeleteClick(v, getAdapterPosition());
                }
            });


        }

        public void bind(boolean selectmod) {
            if (selectmod) {
                imgCancel.setVisibility(View.VISIBLE);
            } else
                imgCancel.setVisibility(View.GONE);
        }
    }
}