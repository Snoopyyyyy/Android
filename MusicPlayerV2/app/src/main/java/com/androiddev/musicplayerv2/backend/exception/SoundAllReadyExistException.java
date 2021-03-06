package com.androiddev.musicplayerv2.backend.exception;

public class SoundAllReadyExistException extends MusicPlayerException {

    private final String error;

    public SoundAllReadyExistException(String error){
        this.error = error;
    }

    @Override
    public String getError() {
        return "la music "+error+" existe déjâ";
    }
}
