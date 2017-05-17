package com.jsrwares.muskrat.media.models;

import android.content.ContentUris;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.list.adapters.AudioRecyclerViewAdapter;

import java.util.List;

public class AudioModel extends Model {

    public Button playButton;
    public Button pauseButton;
    public Button skipButton;
    public Button rewindButton;
    public Button stopButton;
    public Button ejectButton;

    public static final String SUGGESTED_URL = "http://www.vorbis.com/music/Epoq-Lepidoptera.ogg";
    private static final int AUDIO_LAYOUT_RESOURCE = R.layout.fragment_item_audio_list;

    public AudioModel(View layout) {
        super(layout);
        playButton = wireUpButton(R.id.playbutton);
        pauseButton = wireUpButton(R.id.pausebutton);
        skipButton = wireUpButton(R.id.skipbutton);
        rewindButton = wireUpButton(R.id.rewindbutton);
        stopButton = wireUpButton(R.id.stopbutton);
        ejectButton = wireUpButton(R.id.ejectbutton);
    }

    @Override
    public int getListLayoutResource() {
        return AUDIO_LAYOUT_RESOURCE;
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List<? extends Model.Item> items,
                                               MediaItemFragment.MediaItemFragmentInterface listener) {
        return new AudioRecyclerViewAdapter(items, listener);
    }

    public static class Item extends Model.Item {
        long mmId;
        String mmArtist;
        String mmTitle;
        String mmAlbum;
        long mmDuration;

        public Item(long id, String artist, String title, String album, long duration) {
            mmId = id;
            mmArtist = artist;
            mmTitle = title;
            mmAlbum = album;
            mmDuration = duration;
        }

        public long getId() {
            return mmId;
        }

        public String getArtist() {
            return mmArtist;
        }

        public String getTitle() {
            return mmTitle;
        }

        public String getAlbum() {
            return mmAlbum;
        }

        public long getDuration() {
            return mmDuration;
        }

        public Uri getURI() {
            return ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mmId);
        }

        @Override
        public String toString() {
            return mmTitle + " by " + mmArtist;
        }
    }
}
