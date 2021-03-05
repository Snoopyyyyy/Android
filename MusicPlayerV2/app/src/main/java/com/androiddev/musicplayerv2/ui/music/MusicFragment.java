package com.androiddev.musicplayerv2.ui.music;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androiddev.musicplayerv2.MainActivity;
import com.androiddev.musicplayerv2.MainViewModel;
import com.androiddev.musicplayerv2.R;

import java.util.concurrent.TimeUnit;

public class MusicFragment extends Fragment {

    private MainViewModel homeViewModel;
    private MainActivity main;

    /* -- Graphic -- */
    private SearchView searchBar;
    private ListView listView;

    public MusicFragment(MainActivity main) {
        this.main = main;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = main.getViewModel();
        View root = inflater.inflate(R.layout.fragment_music, container, false);

        initSearchBar(root);

        return root;
    }

    private void initSearchBar(View root) {
        this.searchBar = root.findViewById(R.id.fragment_music_searchView);
        this.homeViewModel.getSearchBarText().observe(getViewLifecycleOwner(), titre -> {this.searchBar.setQuery(titre,false);});
        this.searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                homeViewModel.setSearchBar(query);
                searchBar.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                homeViewModel.setSearchBar(newText);
                return true;

            }
        });
    }
}