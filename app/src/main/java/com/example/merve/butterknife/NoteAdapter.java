package com.example.merve.butterknife;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.merve.butterknife.db.Entity.NoteEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by merve on 08.03.2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<NoteEntity> list=new ArrayList<>();
    private AdapterOnCLickListener listener;
    public NoteAddActivity noteAddActivity;

    public NoteAdapter(AdapterOnCLickListener listener) {
        this.listener = listener;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NoteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false), listener);
    }


    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        holder.txt1.setText(list.get(position).getTitle());
        holder.txt2.setText(list.get(position).getDetail());
        if (list.get(position).getColors() == 0) {

            holder.txt2.setTextColor(Color.BLACK);
            holder.crdview.setCardBackgroundColor(Color.WHITE);
        } else
            holder.crdview.setCardBackgroundColor(list.get(position).getColors());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(list.get(position).getDate());
        String date = formatter.format(calendar.getTime());
        holder.txt3.setText(date);


    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<NoteEntity> list) {
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

        @BindView(R.id.crdview)
        CardView crdview;

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