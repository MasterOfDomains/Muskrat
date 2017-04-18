package com.jsrwares.muskrat.media.list.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.models.AudioModel;
import com.jsrwares.muskrat.media.models.Model;

import java.util.ArrayList;
import java.util.List;

public class AudioRecyclerViewAdapter extends RecyclerView.Adapter<AudioRecyclerViewAdapter.ViewHolder> {

    private final List<AudioModel.Item> mValues;
    private final MediaItemFragment.MediaItemFragmentInterface mListener;

    @SuppressWarnings("unchecked")
    public AudioRecyclerViewAdapter(List<? extends Model.Item> items,
                                    MediaItemFragment.MediaItemFragmentInterface listener) {
        mValues = (ArrayList<AudioModel.Item>) items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_audio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTitleView.setText(mValues.get(position).getTitle());
        holder.mArtistView.setText(mValues.get(position).getArtist());
        holder.mAlbumView.setText(mValues.get(position).getAlbum());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("Joe", "mValues=" + mValues);
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTitleView;
        public final TextView mArtistView;
        public final TextView mAlbumView;
        public Model.Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleView = (TextView) view.findViewById(R.id.tv_audiolistitem_title);
            mArtistView = (TextView) view.findViewById(R.id.tv_audiolistitem_artist);
            mAlbumView = (TextView) view.findViewById(R.id.tv_audiolistitem_album);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitleView.getText() + "'";
        }
    }
}
