package com.androiddev.musicplayerv2.backend.recycler_view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.v4.media.MediaDescriptionCompat;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.androiddev.musicplayerv2.MainActivity;
import com.androiddev.musicplayerv2.R;
import com.androiddev.musicplayerv2.backend.sound_data.PlayList;
import com.androiddev.musicplayerv2.ui.music_playlist.playListDetail;

public class RecyclerViewModelPL extends RecyclerView.ViewHolder {

    private final CardView card;
    private final ImageButton icon;
    private final TextView name;
    private final Context context;

    public RecyclerViewModelPL(@NonNull View itemView,Context context) {
        super(itemView);
        this.card = itemView.findViewById(R.id.row_rv_cardView);
        this.icon = itemView.findViewById(R.id.row_rv_icon);
        this.name = itemView.findViewById(R.id.row_rv_titre);
        this.context = context;
    }

    public void update(PlayList playList){
        this.name.setText(playList.getName());
        if(playList.getIconBitmap() == null){
            this.card.setBackground(context.getDrawable(R.drawable.shuffle_button));
        }else{
            this.icon.setImageBitmap(playList.getIconBitmap());
        }
        this.icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity main = MainActivity.getMain();
                Fragment frag = new playListDetail(main,playList);
                main.setMainFragment(frag,true);
            }
        });
    }
}
