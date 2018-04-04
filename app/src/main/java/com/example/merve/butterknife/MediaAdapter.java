package com.example.merve.butterknife;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
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


    public MediaAdapter(AdapterOnCLickListener listener) {
        this.listener = listener;
    }

    @Override
    public MediaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MediaAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.resim_item, parent, false), listener);
    }

    public void asd() {
        selectedMod = true;

    }

    @Override
    public void onBindViewHolder(final MediaAdapter.ViewHolder holder, final int position) {
        try {
            Picasso.get().load(new File(list2.get(position).getPath())).centerCrop().fit().into(holder.imgVItem);
            if (selectedMod) {
                holder.checkboxImage.setVisibility(View.VISIBLE);
                if (holder.checkboxImage.isClickable()) {
                    holder.checkboxImage.setEnabled(true);
                    holder.checkboxImage.setClickable(true);
                    holder.checkboxImage.notify();
                } else {
                    holder.checkboxImage.setVisibility(View.GONE);
                    holder.checkboxImage.setEnabled(false);
                    holder.checkboxImage.setClickable(false);

                }
                notifyDataSetChanged();
            }

            holder.checkboxImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!holder.checkboxImage.isClickable()) {
                        holder.checkboxImage.setVisibility(View.VISIBLE);
                        holder.checkboxImage.setEnabled(true);
                        holder.checkboxImage.setClickable(true);
                        holder.checkboxImage.notify();
                    } else {
                        holder.checkboxImage.setVisibility(View.GONE);
                        holder.checkboxImage.setEnabled(false);
                        holder.checkboxImage.setClickable(false);

                    }
                }
            });



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


        @BindView(R.id.checkboxImage)
        public CheckBox checkboxImage;
        @BindView(R.id.imgVItem)
        public ImageView imgVItem;


        ViewHolder(View view, final AdapterOnCLickListener listener) {

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

                    listener.onClickMedia(v, getAdapterPosition());
                }
            });

            imgVItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {

                    listener.onLongClickMedia(v, getAdapterPosition());
//                    new AlertDialog.Builder(v.getContext())
//                            .setTitle("Delete")
//                            .setMessage("Are you want to delete ?")
//                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            try {
//
//                                                database.mediaDao().DeleteMedia(list2.get(position));
////                                                        MediaAdapter.this.notifyDataSetChanged();
//
//
//                                            } catch (Exception e) {
//                                                Log.e("hata", e.toString());
//                                            }
//
//
//                                        }
//                                    }).start();
//
//                                }
//                            })
//                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            }).show();
//

                    return true;
                }
            });



        }
    }
}