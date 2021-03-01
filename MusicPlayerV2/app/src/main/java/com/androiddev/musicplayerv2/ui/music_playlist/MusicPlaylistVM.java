package com.androiddev.musicplayerv2.ui.music_playlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MusicPlaylistVM extends ViewModel {

    private MutableLiveData<String> mText;

    public MusicPlaylistVM() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}