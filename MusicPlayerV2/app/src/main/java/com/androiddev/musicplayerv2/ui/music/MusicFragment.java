package com.androiddev.musicplayerv2.ui.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.androiddev.musicplayerv2.MainActivity;
import com.androiddev.musicplayerv2.R;

public class MusicFragment extends Fragment {

    private MusicVM homeViewModel;
    private MainActivity main;

    public MusicFragment(MainActivity main) {
        this.main = main;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(MusicVM.class);
        View root = inflater.inflate(R.layout.fragment_music, container, false);
        return root;
    }
}