package com.tutsplus.backgroundaudio;

import android.Manifest;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.os.Build;
import android.os.RemoteException;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity {

    private static final int STATE_PAUSED = 0;
    private static final int STATE_PLAYING = 1;

    private static MainActivity main;

    private int mCurrentState;

    private MediaBrowserCompat mMediaBrowserCompat;
    private MediaControllerCompat mMediaControllerCompat;

    private Button mPlayPauseToggleButton;

    private MediaBrowserCompat.ConnectionCallback mMediaBrowserCompatConnectionCallback = new MediaBrowserCompat.ConnectionCallback() {

        @Override
        public void onConnected() {
            super.onConnected();
            try{
            mMediaControllerCompat = new MediaControllerCompat(MainActivity.this, mMediaBrowserCompat.getSessionToken());
                mMediaControllerCompat.registerCallback(mMediaControllerCompatCallback);
                setMediaController((MediaController) mMediaControllerCompat.getMediaController());
                getMediaController().getTransportControls().playFromMediaId(String.valueOf(R.raw.warner_tautz_off_broadway), null);
            } catch( Exception e) {
                e.printStackTrace();
            }
        }
    };

    private MediaControllerCompat.Callback mMediaControllerCompatCallback = new MediaControllerCompat.Callback() {

        @Override
        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            super.onPlaybackStateChanged(state);
            if( state == null ) {
                return;
            }

            switch( state.getState() ) {
                case PlaybackStateCompat.STATE_PLAYING: {
                    mCurrentState = STATE_PLAYING;
                    break;
                }
                case PlaybackStateCompat.STATE_PAUSED: {
                    mCurrentState = STATE_PAUSED;
                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        main = this;
        setContentView(R.layout.activity_main);

        mPlayPauseToggleButton = (Button) findViewById(R.id.button);
        Button but = findViewById(R.id.skip);
        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMediaController().getTransportControls().skipToNext();
                System.out.println("Skip");
            }
        });
        mMediaBrowserCompat = new MediaBrowserCompat(this, new ComponentName(this, BackgroundAudioService.class),
                mMediaBrowserCompatConnectionCallback, getIntent().getExtras());

        mMediaBrowserCompat.connect();

        mPlayPauseToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( mCurrentState == STATE_PAUSED ) {
                    getMediaController().getTransportControls().play();
                    mCurrentState = STATE_PLAYING;
                } else {
                    if( getMediaController().getPlaybackState().getState() == PlaybackState.STATE_PLAYING ) {
                        getMediaController().getTransportControls().pause();
                    }
                    mCurrentState = STATE_PAUSED;
                }
            }
        });
        isStoragePermissionGranted();
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

    public static MainActivity getInstance(){
        return main;
    }

    public void reload(String titre){
        ((TextView) findViewById(R.id.titre)).setText(titre);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if( getMediaController().getPlaybackState().getState() == PlaybackState.STATE_PLAYING ) {
            getMediaController().getTransportControls().pause();
        }

        mMediaBrowserCompat.disconnect();
    }
}
