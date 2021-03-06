package com.androiddev.musicplayerv2.backend.exception;

public class PlayListAllReadyExistException extends MusicPlayerException{

    private final String error;

    public PlayListAllReadyExistException(String error){
        this.error = error;
    }

    @Override
    public String getError(){
        return "une playlist "+this.error+" exist déjâ";
    }
}
