package com.jsrwares.muskrat.media.retrievers;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.jsrwares.muskrat.media.models.VideoModel;

public class VideoRetriever extends Retriever {

    public VideoRetriever(ContentResolver cr) {
        super(cr);
    }

    @Override
    public void prepare() {
        Uri uri = android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI; // e.g. SD card
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        cursor = contentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        Log.i(TAG, "Query finished. " + (cursor == null ? "Returned NULL." : "Returned a cursor."));

        if (cursor == null) {
            Log.e(TAG, "Failed to retrieve videos: cursor is null :-(");
            return;
        }
        if (!cursor.moveToFirst()) {
            Log.e(TAG, "Failed to move cursor to first row (no query results).");
            return;
        }

        // retrieve the indices of the columns where the ID, title, etc. of the song are
        int artistColumn = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int titleColumn = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int durationColumn = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int idColumn = cursor.getColumnIndex(MediaStore.Audio.Media._ID);

        Log.i(TAG, "Title column index: " + String.valueOf(titleColumn));
        Log.i(TAG, "ID column index: " + String.valueOf(titleColumn));

        // add each song to items
        do {
            Log.i(TAG, "ID: " + cursor.getString(idColumn) + " Title: " + cursor.getString(titleColumn));
            items.add(new VideoModel.Item(
                    cursor.getLong(idColumn),
                    cursor.getString(artistColumn),
                    cursor.getString(titleColumn),
                    cursor.getLong(durationColumn)));
        } while (cursor.moveToNext());
    }
}
