package com.androiddev.musicplayerv2.backend.exception;

public class PlayListNotExistException extends MusicPlayerException {

    private final String error;

    public PlayListNotExistException(String error){
        this.error = error;
    }

    @Override
    public String getError() {
        return "la playlist "+this.error+" n'exist pas";
    }
}
