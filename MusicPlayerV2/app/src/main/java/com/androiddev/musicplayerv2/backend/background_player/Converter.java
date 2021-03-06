package com.androiddev.musicplayerv2.backend.background_player;

import android.net.Uri;
import android.support.v4.media.MediaDescriptionCompat;

import java.io.File;
import java.util.ArrayList;

public class Converter {

    public static MediaDescriptionCompat StringToMDC(String str){
        String[] part = str.split("::");
        if(!new File(part[0]).exists()) return null;
        MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
        builder.setDescription(part[0]);
        builder.setTitle(part[1]);
        builder.setSubtitle(part[2]);
        builder.setMediaUri(Uri.fromFile(new File(part[0])));
        return builder.build();
    }

    public static String MDCToString(MediaDescriptionCompat mdc){
        return mdc.getDescription() + "::" +
                mdc.getTitle() + "::" +
                mdc.getSubtitle();
    }

    public static String getArtiste(String replace) {
        try{
            String[] part = replace.split(" - ");
            return part[0];
        }catch (Exception e){
           return "Anonymous";
        }
    }

    public static ArrayList<File> getMusic(File dossier){
        ArrayList<File> music = new ArrayList<>();
        if(dossier.isDirectory()){
            if(dossier.listFiles() != null){
                for(File f : dossier.listFiles()){
                    music.addAll(getMusic(f));
                }
            }
        }else{
            String name = dossier.getName();
            String ext = name.split("\\.")[name.split("\\.").length-1];
            if(ext.toUpperCase().equalsIgnoreCase("MP3")){
                music.add(dossier);
            }
        }
        return music;
    }

}
