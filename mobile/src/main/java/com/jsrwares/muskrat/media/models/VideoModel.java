package com.jsrwares.muskrat.media.models;

import android.content.ContentUris;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.list.adapters.VideoRecyclerViewAdapter;

import java.util.List;

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
        long id;
        String artist;
        String title;
        long duration;

        public Item(long id, String artist, String title, long duration) {
            this.id = id;
            this.artist = artist;
            this.title = title;
            this.duration = duration;
        }

        public long getId() {
            return id;
        }

        public String getArtist() {
            return artist;
        }

        public String getTitle() {
            return title;
        }

        public long getDuration() {
            return duration;
        }

        public Uri getURI() {
            return ContentUris.withAppendedId(
                    android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
        }
    }
}
