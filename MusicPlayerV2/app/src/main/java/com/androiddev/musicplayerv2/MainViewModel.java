package com.androiddev.musicplayerv2;

import android.os.Environment;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private MutableLiveData<String> searchBar;

    public MainViewModel() {
        searchBar = new MutableLiveData<>();
        searchBar.setValue("");
    }

    public void setSearchBar(String titre){
        this.searchBar.setValue(titre);
    }

    public LiveData<String> getSearchBarText() {
        return searchBar;
    }

}
