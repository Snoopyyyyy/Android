package com.tutsplus.backgroundaudio;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

public class AudioPlayer implements AudioManager.OnAudioFocusChangeListener {

    private static AudioPlayer audioPlayer;
    private Context context;
    private MediaPlayer mediaPlayer;
    private Uri lastUri;
    private MediaPlayer.OnCompletionListener onCompletionlistener;

    public void setOnCompletionlistener(MediaPlayer.OnCompletionListener onCompletionlistener) {
        this.onCompletionlistener = onCompletionlistener;
        if (mediaPlayer != null)
            mediaPlayer.setOnCompletionListener(onCompletionlistener);
    }

    public static AudioPlayer get(Context context){
        if (audioPlayer == null)
            audioPlayer = new AudioPlayer(context);
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(audioPlayer, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED)
            release();
        return audioPlayer;
    }

    public static void release(){
        if (audioPlayer == null) return;
        if (audioPlayer.mediaPlayer == null) return;
        audioPlayer.mediaPlayer.release();
        audioPlayer.mediaPlayer = null;
    }

    private AudioPlayer(Context context) {
        this.context = context;
    }

    public void loadUri(Context context, Uri uri) {
        if (uri != null) {
            this.context = context;
            this.lastUri = uri;
        }
        load();
    }

    private void load() {
        if (lastUri == null) return;
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying())
                mediaPlayer.stop();
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, lastUri);
        if (onCompletionlistener != null)
            this.mediaPlayer.setOnCompletionListener(onCompletionlistener);
    }

    @Override
    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mediaPlayer == null) load();
                else if (!mediaPlayer.isPlaying())
                    mediaPlayer.start(); // Sinon on reprend où on en était
                mediaPlayer.setVolume(1.0f, 1.0f);
                System.out.println("Gain");
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                if (mediaPlayer == null) break;
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop(); // On arrête la musique en cours
                release();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                if (mediaPlayer == null) break;
                if (mediaPlayer.isPlaying())
                    mediaPlayer.pause();
                break;
            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                if (mediaPlayer == null) break;
                if (mediaPlayer.isPlaying())
                    mediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    public void play() {
        if (mediaPlayer == null) load();
        if (mediaPlayer != null) mediaPlayer.start();
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer=null;
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public boolean isPlaying() {
        if(mediaPlayer == null)
            return false;
        return mediaPlayer.isPlaying();
    }

    public int getCurrentPosition(){
        if(mediaPlayer != null)
            return mediaPlayer.getCurrentPosition();
        return 0;
    }

    public int getDuration(){
        if(mediaPlayer != null)
            return mediaPlayer.getDuration();
        return 0;
    }

    public void seekTo(int i){
        if(mediaPlayer != null)
            mediaPlayer.seekTo(i);
    }


}