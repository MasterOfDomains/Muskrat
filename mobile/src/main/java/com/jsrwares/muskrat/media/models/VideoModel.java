package com.jsrwares.muskrat.media.models;

import android.content.ContentUris;
import android.net.Uri;
//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediamodels/VideoModel.java
//import android.view.View;
//
///**
// * Created by Joe on 5/16/2016.
// */
//=======
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.list.adapters.VideoRecyclerViewAdapter;

import java.util.List;

//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/models/VideoModel.java
public class VideoModel extends Model {

    private static final int VIDEO_LAYOUT_RESOURCE = R.layout.fragment_item_video;

    public VideoModel(View layout) {
        super(layout);
    }

    @Override
    public int getListLayoutResource() {
        return VIDEO_LAYOUT_RESOURCE;
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List<? extends Model.Item> items, MediaItemFragment.MediaItemFragmentInterface listener) {
        return new VideoRecyclerViewAdapter(items, listener);
    }

    public static class Item extends Model.Item {
        long mmId;
        String mmArtist;
        String mmTitle;
        long mmDuration;

        public Item(long id, String artist, String title, long duration) {
            mmId = id;
            mmArtist = artist;
            mmTitle = title;
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

        public long getDuration() {
            return mmDuration;
        }

        public Uri getURI() {
            return ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, mmId);
        }
    }
}
