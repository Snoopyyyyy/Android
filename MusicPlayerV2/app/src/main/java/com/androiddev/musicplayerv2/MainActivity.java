package com.androiddev.musicplayerv2;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.media.MediaDescriptionCompat;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androiddev.musicplayerv2.backend.background_player.Converter;
import com.androiddev.musicplayerv2.backend.exception.MusicPlayerException;
import com.androiddev.musicplayerv2.backend.exception.PlayListAllReadyExistException;
import com.androiddev.musicplayerv2.backend.exception.PlayListNotExistException;
import com.androiddev.musicplayerv2.backend.exception.SoundAllReadyExistException;
import com.androiddev.musicplayerv2.backend.sound_data.PlayListManager;
import com.androiddev.musicplayerv2.ui.FullScreenPlayerFragment;
import com.androiddev.musicplayerv2.ui.MusicPlayer;
import com.androiddev.musicplayerv2.ui.music.MusicFragment;
import com.androiddev.musicplayerv2.ui.music_playlist.MusicPlaylistFragment;
import com.androiddev.musicplayerv2.ui.ytmp3.Ytmp3Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView name;
    private BottomNavigationView nav_view;
    private FragmentContainerView fragContainer;
    private FragmentContainerView fragContainerSound;
    private Fragment last;
    private MainViewModel homeViewModel;

    private static MainActivity main;

    private ConstraintLayout.LayoutParams backup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isStoragePermissionGranted();
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_music, R.id.navigation_playlist, R.id.navigation_ytmp3).build();
        this.name = findViewById(R.id.NameFrag);
        this.fragContainer = findViewById(R.id.hostFragement);
        this.fragContainerSound = findViewById(R.id.music_player_fragment);
        this.homeViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        last = new MusicFragment(this);
        this.initNavBar(this);
        this.initPlayMusic(this);
        this.setFragContainer(R.id.hostFragement,last);
        this.setFragContainer(R.id.music_player_fragment,new MusicPlayer(this));
        main = this;
    }


    private void setFragContainer(int idFrag,Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(idFrag,fragment);
        ft.commit();
    }

    public void setMainFragment(Fragment frag,boolean hideNameBar){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.hostFragement,frag);
        ft.commit();
        if(hideNameBar){
            name.setVisibility(View.GONE);
            nav_view.setVisibility(View.GONE);
        }else{
            name.setVisibility(View.VISIBLE);
            nav_view.setVisibility(View.VISIBLE);
        }
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

    public MainViewModel getViewModel(){
        return this.homeViewModel;
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED) {
                Log.v("TAG","Permission is granted");
                return true;
            } else {
                Log.v("TAG","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                    Log.v("TAG","Permission is revoked success !");
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("TAG","Permission is granted");
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Log.v("TAG","Permission: "+permissions[0]+ "was "+grantResults[0]);
            //resume tasks needing this permission
        }
    }

    public Context requireContext() {
        return getApplicationContext();
    }

    public static MainActivity getMain(){
        return main;
    }
}