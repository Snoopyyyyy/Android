package com.androiddev.musicplayerv2;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.androiddev.musicplayerv2.ui.music.MusicFragment;
import com.androiddev.musicplayerv2.ui.music_playlist.MusicPlaylistFragment;
import com.androiddev.musicplayerv2.ui.ytmp3.Ytmp3Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(R.id.navigation_music, R.id.navigation_playlist, R.id.navigation_ytmp3).build();
        this.initNavBar();
    }

    private void initNavBar() {
       BottomNavigationView botNav =findViewById(R.id.nav_view);
       botNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
           @SuppressLint("NonConstantResourceId")
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               Fragment frag;
               switch(item.getItemId()){
                   case R.id.item_nav_music:
                       frag = new MusicFragment();
                       ((TextView) findViewById(R.id.NameFrag)).setText(R.string.menu_music);
                       break;
                   case R.id.item_nav_playlist:
                       frag = new MusicPlaylistFragment();
                       ((TextView) findViewById(R.id.NameFrag)).setText(R.string.menu_music_playlist);
                       break;
                   case R.id.item_nav_ytmp3:
                       frag = new Ytmp3Fragment();
                       ((TextView) findViewById(R.id.NameFrag)).setText(R.string.menu_download);
                       break;
                   default:
                       frag = null;
                       break;
               }
               if(frag != null){
                   FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                   ft.replace(R.id.hostFragement,frag);
                   ft.addToBackStack(null);
                   ft.commit();
               }
               return true;
           }
       });
    }
}