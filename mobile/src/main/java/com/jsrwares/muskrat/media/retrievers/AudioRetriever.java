package com.jsrwares.muskrat.media.retrievers;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.jsrwares.muskrat.media.models.AudioModel;

/**
 * Created by Joe on 5/10/2016.
 */
public class AudioRetriever extends Retriever {

    public AudioRetriever(ContentResolver cr) {
        super(cr);
    }

    @Override
    public void prepare() {
        Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; // e.g. SD card
        String[] projection = null;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " = 1";
        String[] selectionArgs = null;
        String sortOrder = null;
        mCursor = mContentResolver.query(uri, projection, selection, selectionArgs, sortOrder);
        Log.i(TAG, "Query finished. " + (mCursor == null ? "Returned NULL." : "Returned a cursor."));

        if (mCursor == null) {
            Log.e(TAG, "Failed to retrieve music: cursor is null :-(");
            return;
        }
        if (!mCursor.moveToFirst()) {
            Log.e(TAG, "Failed to move cursor to first row (no query results).");
            return;
        }

        // retrieve the indices of the columns where the ID, title, etc. of the song are
        int artistColumn = mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        int titleColumn = mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        int albumColumn = mCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);
        int durationColumn = mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
        int idColumn = mCursor.getColumnIndex(MediaStore.Audio.Media._ID);

        Log.i(TAG, "Title column index: " + String.valueOf(titleColumn));
        Log.i(TAG, "ID column index: " + String.valueOf(titleColumn));

        // add each song to mItems
        do {
            Log.i(TAG, "ID: " + mCursor.getString(idColumn) + " Title: " + mCursor.getString(titleColumn));
            mItems.add(new AudioModel.Item(
                    mCursor.getLong(idColumn),
                    mCursor.getString(artistColumn),
                    mCursor.getString(titleColumn),
                    mCursor.getString(albumColumn),
                    mCursor.getLong(durationColumn)));
        } while (mCursor.moveToNext());
    }
}
