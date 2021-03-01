package com.androiddev.musicplayerv2.ui.music;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MusicVM extends ViewModel {

    private MutableLiveData<String> mText;

    public MusicVM() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}