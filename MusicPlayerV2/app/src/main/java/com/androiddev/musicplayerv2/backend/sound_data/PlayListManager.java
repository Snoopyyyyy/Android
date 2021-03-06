package com.androiddev.musicplayerv2.backend.sound_data;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.androiddev.musicplayerv2.backend.exception.PlayListAllReadyExistException;
import com.androiddev.musicplayerv2.backend.exception.PlayListNotExistException;
import com.androiddev.musicplayerv2.backend.exception.SoundAllReadyExistException;
import com.androiddev.musicplayerv2.ui.music_playlist.MusicPlaylistFragment;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayListManager {
    private final List<PlayList> list;
    private final File saveFile;

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
        JSONObject obj = new JSONObject(builder.toString());
        JSONArray playListJSON = obj.getJSONArray("playList");
        for (int i = 0; i < playListJSON.length(); i++) {
            list.add(new PlayList(playListJSON.getJSONObject(i)));
        }
        buf.close();
    }

    public void saveFile() throws Exception {
        JSONObject plyListObj = new JSONObject();
        JSONArray playListJSON = new JSONArray();
        for(PlayList pl : list){
            playListJSON.put(pl.save());
        }
        plyListObj.put("playList",playListJSON);
        FileWriter writer = new FileWriter(this.saveFile);
        writer.write(plyListObj.toString());
        writer.flush();
        writer.close();
    }

    public void addSongToPlayList(String name,File f) throws SoundAllReadyExistException, PlayListNotExistException {
        PlayList pl = getPL(name);
        if(pl == null)
            throw new PlayListNotExistException(name);
        else {
            try {
                pl.addSound(f);
                saveFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "PlayListManager{" + "list=" + list + ", saveFile=" + saveFile + '}';
    }

    public void create(String str) throws PlayListAllReadyExistException {
        if(getPL(str) != null)
            throw new PlayListAllReadyExistException(str);
        else {
            list.add(new PlayList(str));
            try {
                saveFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public PlayList getPL(String name){
        for (PlayList pl: list ) {
            if(pl.getName().equalsIgnoreCase(name))
                return pl;
        }
        return null;
    }

    public List<PlayList> getAll() {
        return this.list;
    }
}
