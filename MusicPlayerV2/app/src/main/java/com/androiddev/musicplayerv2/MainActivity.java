package com.androiddev.musicplayerv2;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androiddev.musicplayerv2.ui.FullScreenPlayerFragment;
import com.androiddev.musicplayerv2.ui.MusicPlayer;
import com.androiddev.musicplayerv2.ui.music.MusicFragment;
import com.androiddev.musicplayerv2.ui.music_playlist.MusicPlaylistFragment;
import com.androiddev.musicplayerv2.ui.ytmp3.Ytmp3Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.ui.AppBarConfiguration;

public class MainActivity extends AppCompatActivity {

    private TextView name;
    private BottomNavigationView nav_view;
    private FragmentContainerView fragContainer;
    private FragmentContainerView fragContainerSound;
    private Fragment last;

    private ConstraintLayout.LayoutParams backup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_music, R.id.navigation_playlist, R.id.navigation_ytmp3).build();
        this.name = findViewById(R.id.NameFrag);
        this.fragContainer = findViewById(R.id.hostFragement);
        this.fragContainerSound = findViewById(R.id.music_player_fragment);
        last = new MusicFragment(this);
        this.initNavBar(this);
        this.initPlayMusic(this);
        this.setFragContainer(R.id.hostFragement,last);
        this.setFragContainer(R.id.music_player_fragment,new MusicPlayer(this));
    }

    private void setFragContainer(int idFrag,Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(idFrag,fragment);
        ft.commit();
    }

    private void initPlayMusic(MainActivity main) {
        FragmentContainerView music_player = findViewById(R.id.music_player_fragment);
        music_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nav_view.getVisibility() != View.GONE){
                    setFragContainer(R.id.music_player_fragment,new FullScreenPlayerFragment(main));

                    backup = (ConstraintLayout.LayoutParams) fragContainerSound.getLayoutParams();
                    Display display = getWindowManager().getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    int height = size.y;
                    ConstraintLayout.LayoutParams param = new ConstraintLayout.LayoutParams(width,height);
                    fragContainerSound.setLayoutParams(param);

                    nav_view.setVisibility(View.GONE);
                    name.setVisibility(View.GONE);
                    fragContainer.setVisibility(View.GONE);
                }
            }
        });
    }

    public void showAll(){
        fragContainerSound.setLayoutParams(backup);
        nav_view.setVisibility(View.VISIBLE);
        name.setVisibility(View.VISIBLE);
        fragContainer.setVisibility(View.VISIBLE);
        setFragContainer(R.id.music_player_fragment,new MusicPlayer(this));
        setFragContainer(R.id.hostFragement,last);
    }

    private void initNavBar(MainActivity main) {
       nav_view = findViewById(R.id.nav_view);
       nav_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @SuppressLint("NonConstantResourceId")
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               switch(item.getItemId()){
                   case R.id.item_nav_music:
                       last = new MusicFragment(main);
                       name.setText(R.string.menu_music);
                       break;
                   case R.id.item_nav_playlist:
                       last = new MusicPlaylistFragment(main);
                       name.setText(R.string.menu_music_playlist);
                       break;
                   case R.id.item_nav_ytmp3:
                       last = new Ytmp3Fragment(main);
                       name.setText(R.string.menu_download);
                       break;
                   default:
                       last = null;
                       break;
               }
               if(last != null){
                   setFragContainer(R.id.hostFragement,last);
               }
               return true;
           }
       });
    }
}