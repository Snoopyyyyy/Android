package com.androiddev.musicplayerv2.backend.recycler_view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androiddev.musicplayerv2.R;
import com.androiddev.musicplayerv2.backend.sound_data.PlayList;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewModelPL> {

    private List<PlayList> list;

    public RecyclerViewAdapter(List<PlayList> pl){
        this.list = pl;
    }

    @NonNull
    @Override
    public RecyclerViewModelPL onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.play_list_rv_row, parent, false);

        return new RecyclerViewModelPL(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewModelPL holder, int position) {
        holder.update(list.get(position));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }
}
