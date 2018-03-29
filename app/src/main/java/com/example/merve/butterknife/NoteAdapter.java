package com.example.merve.butterknife;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.merve.butterknife.db.Entity.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by merve on 08.03.2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public NoteAddActivity noteAddActivity;
    List<Note> list = new ArrayList<>();
    private AdapterOnCLickListener listener;
    private StaggeredGridLayoutManager layoutManager;

    public NoteAdapter(AdapterOnCLickListener listener) {
        this.listener = listener;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NoteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false), listener);
    }


    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        MediaAdapter mediaAdapter = new MediaAdapter();
        holder.recyclerViewImageItem.setAdapter(mediaAdapter);
        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
        holder.recyclerViewImageItem.setLayoutManager(layoutManager);
        mediaAdapter.setList2(list.get(position).mediaAdapterList);


        holder.txt1.setText(list.get(position).noteEntity.getTitle());

        holder.txt2.setText(list.get(position).noteEntity.getDetail());

        if (list.get(position).noteEntity.getColors() == 0) {

            holder.txt2.setTextColor(Color.BLACK);
            holder.crdview.setCardBackgroundColor(Color.WHITE);
        } else
            holder.crdview.setCardBackgroundColor(list.get(position).noteEntity.getColors());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(list.get(position).noteEntity.getDate());
        String date = formatter.format(calendar.getTime());
        holder.txt3.setText(date);
        holder.recyclerViewImageItem.hasOnClickListeners();
        holder.recyclerViewImageItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.getContext().startActivity(new Intent(v.getContext(), DetailActivity.class));
            }
        });

//        try {
//            if (list.get(position).mediaAdapterList.get(0).getPath() != null) {
//                Picasso.get().load(new File(list.get(position).mediaAdapterList.get(0).getPath())).fit().into(holder.noteImg);
//
//                holder.noteImg.setVisibility(View.VISIBLE);
//            } else {
//                holder.noteImg.setVisibility(View.GONE);
//            }
//        } catch (Exception ex) {
//            Log.e("hata", ex.toString());
//        }

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Note> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txtTitle2)
        TextView txt1;
        @BindView(R.id.txtDetail2)
        TextView txt2;
        @BindView(R.id.txtDate)
        TextView txt3;
        @BindView(R.id.noteImg)
        ImageView noteImg;
        @BindView(R.id.crdview)
        CardView crdview;
        @BindView(R.id.recyclerViewImageItem)
        RecyclerView recyclerViewImageItem;


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