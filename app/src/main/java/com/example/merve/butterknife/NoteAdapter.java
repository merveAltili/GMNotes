package com.example.merve.butterknife;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.merve.butterknife.db.Entity.NoteEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by merve on 08.03.2018.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<NoteEntity> list;

    public NoteAdapter(List<NoteEntity> list) {
        this.list = list;
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new NoteAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item, parent, false));
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int position) {
        holder.txt1.setText(list.get(position).getTitle());
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

   public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.txt1)
        TextView txt1;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
