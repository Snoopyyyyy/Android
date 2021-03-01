package com.androiddev.musicplayerv2.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androiddev.musicplayerv2.MainActivity;
import com.androiddev.musicplayerv2.R;

public class FullScreenPlayerFragment extends Fragment implements View.OnClickListener {

    private MainActivity main;

    public FullScreenPlayerFragment(MainActivity main) {
        this.main = main;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_screen_player, container, false);
        ImageButton reduce = view.findViewById(R.id.frag_full_screen_reduce);
        reduce.setOnClickListener(this);
        LinearLayout top = view.findViewById(R.id.linearLayout_full_screen);
        top.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        main.showAll();
    }
}