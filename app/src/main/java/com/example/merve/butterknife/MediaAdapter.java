package com.example.merve.butterknife;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.example.merve.butterknife.db.Entity.MediaEntity;
import com.hololo.library.photoviewer.PhotoViewer;
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
    Boolean selectedMod = false;

    private AdapterOnCLickListener listener;


//    public MediaAdapter(List<MediaEntity> list2) {
//        this.list2 = list2;
//    }

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
            holder.imgVItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(v.getContext(), DetailActivity.class);
                    i.putExtra("item", list2.get(position));
                    v.getContext().startActivity(i);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            MainActivity.database.notedao().getNoteById(list2.get(position).getNoteId());

                        }
                    }).start();
                }
            });

            holder.imgVItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    ArrayList<File> fileList = new ArrayList<File>();
                    for (int i = 0; i < list2.size(); i++) {

                        fileList.add(new File(list2.get(i).getPath()));
                    }

                    new PhotoViewer.Builder(v.getContext())
                            .file(fileList) // List of Uri, file or String url
                            .placeHolder(R.drawable.ic_launcher_background) // placeHolder for images
                            .position(position)

                            .build()
                            .show();
                }
            });
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


            holder.imgVItem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(final View v) {

                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Delete")
                            .setMessage("Are you want to delete ?")
                            .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {

                                                MainActivity.database.mediaDao().DeleteMedia(list2.get(position));
//                                                        MediaAdapter.this.notifyDataSetChanged();


                                            } catch (Exception e) {
                                                Log.e("hata", e.toString());
                                            }


                                        }
                                    }).start();

                                }
                            })
                            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();



                    return true;
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
        ImageView imgVItem;


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