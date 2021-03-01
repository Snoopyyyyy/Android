package com.androiddev.musicplayerv2.ui.ytmp3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Ytmp3VM extends ViewModel {

    private MutableLiveData<String> mText;

    public Ytmp3VM() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}