package com.jsrwares.muskrat.media.models;

import android.content.ContentUris;
import android.net.Uri;
//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediamodels/AudioModel.java
//=======
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/models/AudioModel.java
import android.view.View;
import android.widget.Button;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.list.adapters.AudioRecyclerViewAdapter;

import java.util.List;

public class AudioModel extends Model {

    public Button mPlayButton;
    public Button mPauseButton;
    public Button mSkipButton;
    public Button mRewindButton;
    public Button mStopButton;
    public Button mEjectButton;

    public static final String SUGGESTED_URL = "http://www.vorbis.com/music/Epoq-Lepidoptera.ogg";
    //    private static final int AUDIO_LAYOUT_RESOURCE = R.layout.fragment_item_audio;
    private static final int AUDIO_LAYOUT_RESOURCE = R.layout.fragment_item_audio_list;

    public AudioModel(View layout) {
        super(layout);
        mPlayButton = wireUpButton(R.id.playbutton);
        mPauseButton = wireUpButton(R.id.pausebutton);
        mSkipButton = wireUpButton(R.id.skipbutton);
        mRewindButton = wireUpButton(R.id.rewindbutton);
        mStopButton = wireUpButton(R.id.stopbutton);
        mEjectButton = wireUpButton(R.id.ejectbutton);

//        buttons[0] = mPlayButton;
//        buttons[1] = mPauseButton;
//        buttons[2] = mSkipButton;
//        buttons[3] = mRewindButton;
//        buttons[4] = mStopButton;
//        buttons[5] = mEjectButton;
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