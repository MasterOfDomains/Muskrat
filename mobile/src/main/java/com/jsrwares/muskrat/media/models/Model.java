package com.jsrwares.muskrat.media.models;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.jsrwares.muskrat.media.list.MediaItemFragment;

import java.util.HashMap;
import java.util.List;

public abstract class Model {

    protected View layout;
    protected HashMap<Button, Integer> views = new HashMap<>();

    protected Model(View layout) {
        this.layout = layout;
    }

    public abstract int getListLayoutResource();

    public abstract RecyclerView.Adapter getListAdapter(List<? extends Item> items,
                                                        MediaItemFragment.MediaItemFragmentInterface listener);

    protected Button wireUpButton(int resId) {
        Button button = (Button) layout.findViewById(resId);
        views.put(button, resId);
        return button;
    }

    public void addButtonListener(View.OnClickListener listener) {
        for (View view : views.keySet()) {
            if (view instanceof Button)
                view.setOnClickListener(listener);
        }
    }

    public abstract static class Item {
    }
}
