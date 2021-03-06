package com.androiddev.musicplayerv2.backend.sound_data;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.media.MediaDescriptionCompat;

import com.androiddev.musicplayerv2.backend.background_player.Converter;
import com.androiddev.musicplayerv2.backend.exception.SoundAllReadyExistException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PlayList {
    private String name;
    private File icon;
    private List<MediaDescriptionCompat> soundList;

    public PlayList(String name){
        this.name = name;
        this.icon = null;
        this.soundList = new ArrayList<>();
    }

    public PlayList(JSONObject playlist) throws JSONException {
        this.name = playlist.getString("name");
        this.icon = new File(playlist.getString("icon"));
        this.soundList = new ArrayList<>();
        JSONArray soundListArray = playlist.getJSONArray("sound");
        for(int i=0;i<soundListArray.length();i++){
            String info = (String) soundListArray.get(i);
            MediaDescriptionCompat media = Converter.StringToMDC(info);
            if(media != null)
                this.soundList.add(media);
        }
    }

    public JSONObject save() throws JSONException {
        JSONArray array = new JSONArray();
        for (MediaDescriptionCompat media: soundList ) {
            array.put(Converter.MDCToString(media));
        }

        JSONObject obj = new JSONObject();
        obj.put("name",this.name);
        obj.put("icon",this.icon.getAbsoluteFile());
        obj.put("sound",array);
        return obj;
    }

    public void modifyName(String newName){
        this.name = newName;
    }
    public Bitmap getIconBitmap(){
        return BitmapFactory.decodeFile(icon.getAbsolutePath());
    }
    public void addSound(File sound) throws SoundAllReadyExistException {
        if(exist(sound)) throw new SoundAllReadyExistException(sound.getName().split("\\.")[0].replace("_"," "));
        else {
            MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
            builder.setTitle(sound.getName().split("\\.")[0].replace("_", " "));
            builder.setDescription(sound.getAbsolutePath());
            builder.setMediaUri(Uri.fromFile(sound));
            builder.setSubtitle(Converter.getArtiste(sound.getName().split("\\.")[0].replace("_", " ")));
            soundList.add(builder.build());
        }
    }

    public void removeSound(MediaDescriptionCompat media){
        soundList.remove(media);
    }

    public List<MediaDescriptionCompat> getQueue(){
        return this.soundList;
    }

    public String getName() {
        return name;
    }

    private boolean exist(File sound){
        for(MediaDescriptionCompat media : soundList){
            if(media.getDescription() != null) {
                if (media.getDescription().toString().equalsIgnoreCase(sound.getAbsolutePath())) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "PlayList{" +
                "name='" + name + '\'' +
                ", soundList=" + soundList +
                '}';
    }
}
