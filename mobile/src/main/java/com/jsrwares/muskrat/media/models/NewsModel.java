package com.jsrwares.muskrat.media.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.list.adapters.NewsRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Joe on 5/26/2016.
 */
public class NewsModel extends Model {

    private static final int NEWS_LAYOUT_RESOURCE = R.layout.fragment_item_news;

    public NewsModel(View layout) {
        super(layout);
    }

    @Override
    public int getListLayoutResource() {
        return NEWS_LAYOUT_RESOURCE;
    }

    @Override
    public RecyclerView.Adapter getListAdapter(List<? extends Item> items, MediaItemFragment.MediaItemFragmentInterface listener) {
        return new NewsRecyclerViewAdapter(items, listener);
    }
}
