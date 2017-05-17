package com.jsrwares.muskrat.media.retrievers;

import android.content.ContentResolver;
import android.database.Cursor;

import com.jsrwares.muskrat.media.models.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Retriever {

    final String TAG = "Retriever";

    protected ContentResolver contentResolver;
    protected Cursor cursor;

    // the items (tracks) we have queried
    protected List<Model.Item> items = new ArrayList<Model.Item>();

    /**
     * Loads music data. This method may take long, so be sure to call it asynchronously without
     * blocking the main thread.
     */
    public abstract void prepare();

    private Random random = new Random();

    public Retriever(ContentResolver cr) {
        contentResolver = cr;
    }

    public ContentResolver getContentResolver() {
        return contentResolver;
    }

    /**
     * Returns all Items
     */
    public ArrayList<Model.Item> getItems() {
        return (ArrayList) items;
    }

    /**
     * Returns a random Item. If there are no items available, returns null.
     */
    public Model.Item getRandomItem() {
        if (items.size() <= 0) return null;
        return items.get(random.nextInt(items.size()));
    }
}
