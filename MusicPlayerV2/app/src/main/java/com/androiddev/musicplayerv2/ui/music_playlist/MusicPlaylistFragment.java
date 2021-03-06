package com.androiddev.musicplayerv2.ui.music_playlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Magnifier;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.androiddev.musicplayerv2.MainActivity;
import com.androiddev.musicplayerv2.MainViewModel;
import com.androiddev.musicplayerv2.R;
import com.androiddev.musicplayerv2.backend.recycler_view.RecyclerViewAdapter;
import com.androiddev.musicplayerv2.backend.sound_data.PlayList;
import com.androiddev.musicplayerv2.backend.sound_data.PlayListManager;

import java.util.ArrayList;
import java.util.List;

public class MusicPlaylistFragment extends Fragment {

    private MainViewModel viewModel;
    private MainActivity main;
    private RecyclerViewAdapter adapter;
    private static PlayListManager playListManager;

    public MusicPlaylistFragment(MainActivity main) {
        this.main = main;
        playListManager = new PlayListManager(main.getApplicationContext());
        this.adapter = new RecyclerViewAdapter(playListManager.getAll());
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = main.getViewModel();
        View root = inflater.inflate(R.layout.fragment_playlist, container, false);
        RecyclerView rv = root.findViewById(R.id.fragment_playlist_list);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new GridLayoutManager(getActivity(),2));

        try{
            playListManager.saveFile();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return root;
    }

    public static void trySave() throws Exception {
        if(playListManager != null)
            playListManager.saveFile();
        throw new Exception("NULL");
    }


}