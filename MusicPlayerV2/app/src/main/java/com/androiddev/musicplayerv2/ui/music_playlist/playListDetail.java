package com.androiddev.musicplayerv2.ui.music_playlist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.support.v4.media.MediaDescriptionCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androiddev.musicplayerv2.MainActivity;
import com.androiddev.musicplayerv2.MainViewModel;
import com.androiddev.musicplayerv2.R;
import com.androiddev.musicplayerv2.backend.recycler_view.RecyclerViewAdapter;
import com.androiddev.musicplayerv2.backend.sound_data.PlayList;
import com.androiddev.musicplayerv2.backend.sound_data.PlayListManager;


public class playListDetail extends Fragment {

    private MainActivity main;
    private PlayList playList;

    private ImageView icon;
    private TextView titre;

    public playListDetail(MainActivity main,PlayList playList) {
        this.main = main;
        this.playList = playList;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_play_list_detail, container, false);
        this.icon = root.findViewById(R.id.fragment_playlist_detail_icon);
        if(playList.getIconBitmap() != null)
            icon.setImageBitmap(playList.getIconBitmap());
        this.titre = root.findViewById(R.id.fragment_playlist_detail_name);
        this.titre.setText(playList.getName());
        ListView ls = root.findViewById(R.id.fragment_playlist_detail_list);
        ls.setAdapter(new ArrayAdapter<MediaDescriptionCompat>(getContext(),R.layout.music_list_row,R.id.music_list_row_titre,playList.getQueue()){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View w = super.getView(position, convertView, parent);
                MediaDescriptionCompat item = getItem(position);
                TextView titre = w.findViewById(R.id.music_list_row_titre);
                titre.setText(item.getTitle());

                TextView artiste = w.findViewById(R.id.music_list_row_artiste);
                artiste.setText(item.getSubtitle());
                return w;
            }
        });
        ImageButton back = root.findViewById(R.id.fragment_playlist_detail_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.setMainFragment(new MusicPlaylistFragment(main),false);
            }
        });

        ImageButton option = root.findViewById(R.id.fragment_playlist_detail_edit);
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ModificationPlayListDetail.class);
                intent.putExtra("PlayList.name",playList.getName());
                startActivityForResult(intent,1);
            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode != Activity.RESULT_CANCELED){
                playList.modifyName(data.getStringExtra("result.name"));
                this.titre.setText(playList.getName());
                try {
                    MusicPlaylistFragment.trySave();
                } catch (Exception ignored) {}
            }
        }
    }
}