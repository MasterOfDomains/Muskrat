package com.jsrwares.muskrat.media.list.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.models.Model;
import com.jsrwares.muskrat.media.models.VideoModel;

import java.util.ArrayList;
import java.util.List;

public class VideoRecyclerViewAdapter extends RecyclerView.Adapter<VideoRecyclerViewAdapter.ViewHolder> {

    private final List<VideoModel.Item> mValues;
    private final MediaItemFragment.MediaItemFragmentInterface mListener;

    @SuppressWarnings("unchecked")
    public VideoRecyclerViewAdapter(List<? extends Model.Item> items,
                                    MediaItemFragment.MediaItemFragmentInterface listener) {
        mValues = (ArrayList<VideoModel.Item>) items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        // TODO: This was copied straight from Audio. Needs to be customized
        public final View mView;
        public final TextView mTitleView;
        public final TextView mArtistView;
        public final TextView mDurationView;
        public Model.Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.tv_audiolistitem_title);
            mArtistView = (TextView) view.findViewById(R.id.tv_audiolistitem_artist);
            mDurationView = (TextView) view.findViewById(R.id.tv_audiolistitem_album);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
