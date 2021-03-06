package com.androiddev.musicplayerv2.backend.sound_data;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;

import com.androiddev.musicplayerv2.backend.exception.PlayListAllReadyExistException;
import com.androiddev.musicplayerv2.backend.exception.PlayListNotExistException;
import com.androiddev.musicplayerv2.backend.exception.SoundAllReadyExistException;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayListManager {
    private List<PlayList> list;
    private File saveFile;

    public PlayListManager(Context context){
        this.saveFile = new File(context.getCacheDir().getAbsolutePath()+File.separator+"playListManager.json");
        this.list = new ArrayList<>();
        System.out.println(this.saveFile.getAbsolutePath());
        try {
            if (!this.saveFile.exists()) {
                boolean newFile = this.saveFile.createNewFile();
                if (!newFile)
                    throw new IOException("creation save file failed");
            } else {
                init();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void init() throws IOException, JSONException {
        StringBuilder builder = new StringBuilder();
        BufferedReader buf = new BufferedReader(new FileReader(this.saveFile));
        String str;
        while((str = buf.readLine()) != null){
            builder.append(str);
        }
        str = builder.toString();
        JSONArray playListJSON = new JSONArray(str);
        for (int i = 0; i < playListJSON.length(); i++) {
            list.add(new PlayList(playListJSON.getJSONObject(i)));
        }
    }

    public void saveFile() throws Exception {
        JSONArray playListJSON = new JSONArray();
        for(PlayList pl : list){
            playListJSON.put(pl.save());
        }
        FileWriter writer = new FileWriter(this.saveFile);
        writer.write(playListJSON.toString());
        writer.flush();
        writer.close();
    }

    public void addSongToPlayList(String name,File f) throws SoundAllReadyExistException, PlayListNotExistException {
        PlayList pl = getPL(name);
        if(pl == null)
            throw new PlayListNotExistException(name);
        else
            pl.addSound(f);
    }

    @NotNull
    @Override
    public String toString() {
        return "PlayListManager{" + "list=" + list + ", saveFile=" + saveFile + '}';
    }

    public void create(String str) throws PlayListAllReadyExistException {
        if(getPL(str) != null)
            throw new PlayListAllReadyExistException(str);
        list.add(new PlayList(str));
    }

    public PlayList getPL(String name){
        for (PlayList pl: list ) {
            if(pl.getName().equalsIgnoreCase(name))
                return pl;
        }
        return null;
    }
}
