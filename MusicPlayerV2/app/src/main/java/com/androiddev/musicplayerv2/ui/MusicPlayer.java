package com.androiddev.musicplayerv2.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androiddev.musicplayerv2.MainActivity;
import com.androiddev.musicplayerv2.R;

public class MusicPlayer extends Fragment {

    private MainActivity main;

    public MusicPlayer(MainActivity mainActivity) {
        this.main = mainActivity;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_music_player, container, false);
        return root;
    }
}