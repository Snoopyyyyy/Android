package com.androiddev.musicplayerv2.ui.ytmp3;

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

public class Ytmp3Fragment extends Fragment {

    private Ytmp3VM notificationsViewModel;
    private MainActivity main;

    public Ytmp3Fragment(MainActivity main) {
        this.main = main;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(Ytmp3VM.class);
        View root = inflater.inflate(R.layout.fragment_ytmp3, container, false);
        final TextView textView = root.findViewById(R.id.text_notifications);
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}