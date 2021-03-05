package com.tutsplus.backgroundaudio;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MediaSessionCallback extends MediaSessionCompat.Callback {

    private final List<MediaSessionCompat.QueueItem> queue;
    private int queueId;
    private MediaSessionCompat mediaSession;
    private AudioPlayer mediaPlayer;
    private Context context;

    public MediaSessionCallback(MediaSessionCompat mediaSession, Context context){
        this.queue = new ArrayList<>();
        this.queueId = MediaSessionCompat.QueueItem.UNKNOWN_ID;
        this.mediaSession = mediaSession;
        this.mediaPlayer = AudioPlayer.get(context);
        this.context = context;
        this.mediaPlayer.setOnCompletionlistener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                onSkipToNext();
            }
        });
    }

    @Override
    public void onPrepare() {
        super.onPrepare();
        if (queueId < 0 && queue.isEmpty()) return;
        Uri uri = queue.get(queueId).getDescription().getMediaUri();
        mediaPlayer.loadUri(context,uri);
        MainActivity.getInstance().reload(queue.get(queueId).getDescription().getTitle().toString());
    }

    @Override
    public void onPlay() {
        super.onPlay();
        onPrepare();
        mediaPlayer.play();
    }

    @Override
    public void onPause() {
        super.onPause();
        mediaPlayer.pause();
    }

    @Override
    public void onSkipToNext() {
        super.onSkipToNext();
        queueId = queueId+1 % queue.size();
        System.out.println(queueId);
        onPlay();
    }

    @Override
    public void onSkipToPrevious() {
        super.onSkipToPrevious();
        queueId = (queueId > 0) ? queueId-1 : queue.size()-1;
        onPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    @Override
    public void onSeekTo(long pos) {
        super.onSeekTo(pos);
        mediaPlayer.seekTo((int) pos);
    }

    @Override
    public void onAddQueueItem(MediaDescriptionCompat description) {
        super.onAddQueueItem(description);
        queue.add(new MediaSessionCompat.QueueItem(description,queue.size()));
        queueId = (queueId == -1) ? 0 : queueId;
        mediaSession.setQueue(queue);
    }

    @Override
    public void onRemoveQueueItem(MediaDescriptionCompat description) {
        super.onRemoveQueueItem(description);
        List<MediaSessionCompat.QueueItem> newQueue = new ArrayList<>();
        for(MediaSessionCompat.QueueItem item : queue){
            MediaDescriptionCompat itemDescription = item.getDescription();
            if(itemDescription != description)
                newQueue.add(new MediaSessionCompat.QueueItem(itemDescription,newQueue.size()));
        }
        queue.clear();
        queue.addAll(newQueue);
        queueId = (queue.isEmpty() || queue.size() <= queueId ) ? -1 : queueId;
        mediaSession.setQueue(queue);
    }
}
