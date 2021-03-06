package com.androiddev.musicplayerv2.ui.music_playlist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.androiddev.musicplayerv2.R;
import com.androiddev.musicplayerv2.backend.sound_data.PlayList;

public class ModificationPlayListDetail extends AppCompatActivity {

    private String playListName;
    private Bitmap icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modification_play_list_detail);
        Intent intent = getIntent();
        playListName = intent.getStringExtra("PlayList.name");
        EditText name = findViewById(R.id.activity_modification_play_list_titre);
        name.setText(playListName);

        Button cancel = findViewById(R.id.activity_modification_play_list_off);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });

        Button valider = findViewById(R.id.activity_modification_play_list_on);
        valider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result.name",name.getText().toString());
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}