package com.jsrwares.muskrat.media.controllers;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.MediaFunction;
import com.jsrwares.muskrat.media.models.Model;
import com.jsrwares.muskrat.media.models.VideoModel;

public class VideoController extends Controller {

    private VideoModel mVideoModel;
    private static final String POSITION_KEY = "Position";
    private static final int PICK_VIDEO_FILE_REQUEST = 1;

    private static final int TAB_NUMBER_VIEW_ID = R.id.textPlayerTab;
    private static final int CHILD_LAYOUT = R.layout.content_player_tab;
    private static final int PLAYER_STUB_ID = R.id.playerStub;
    private static final int PLAYER_LAYOUT = R.layout.content_player_video;

    private ViewStub mPlayerStub;
    private View mPlayerStubContent;
    private TextView mTabNumberView = null;
    private VideoView mVideoView;
    private MediaController mVideoControl;

    private boolean mTabIsActive = false;
    private Bundle mTabChangeBundle = null;
    private int mVideoPosition = 0;
    private boolean mIsPrepared = false;
    private boolean mIsPreparing = false;

    public VideoController() {
        mFunction = MediaFunction.VIDEO;
    }

    private class PreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            mVideoView.seekTo(mVideoPosition);
            mVideoView.start();
        }
    }

    private void prepareVideo(String address) {
        if (address == null)
            address = "android.resource://com.jsrwares.muskrat/raw/samplevid";
        Uri vidUri = Uri.parse(address);
        mVideoView.setVideoURI(vidUri);

        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new PreparedListener());
    }

    @Override
    protected void setupMedia(LayoutInflater inflater) {
        mStub.setLayoutResource(CHILD_LAYOUT);
        mStubContent = inflater.inflate(CHILD_LAYOUT, (ViewGroup) mParentLayout, true);
        mTabNumberView = (TextView) mStubContent.findViewById(TAB_NUMBER_VIEW_ID);
        mTabNumberView.setText(toString());

        mPlayerStub = (ViewStub) mStubContent.findViewById(PLAYER_STUB_ID);
        mPlayerStub.setLayoutResource(PLAYER_LAYOUT);
        mPlayerStubContent = inflater.inflate(PLAYER_LAYOUT, (ViewGroup) mParentLayout, true);
        mVideoView = (VideoView) mPlayerStubContent.findViewById(R.id.videoPlayerView);
        mVideoControl = new MediaController(mFragment.getContext());
        mVideoControl.setAnchorView(mVideoView);
    }

    public void setAsVisible() {
        if (!mTabIsActive) {
            mTabIsActive = true;
            prepareVideo(null);
            if (mTabChangeBundle != null) {
                restoreInstanceState(mTabChangeBundle);
                mTabChangeBundle = null;
                mVideoView.seekTo(mVideoPosition);
            }
        }

        mTabIsVisible = true;
    }

    @Override
    public void setAsBackground() {
        super.setAsBackground();
        Log.d("Background", "Video to the background...");
        if (mTabIsActive) {
            mTabIsActive = false;
            mVideoView.pause();
            mTabChangeBundle = new Bundle();
            saveInstanceState(mTabChangeBundle);
        }
        mVideoPosition = mVideoView.getCurrentPosition();
        mVideoView.setOnPreparedListener(null);
        mIsPrepared = false;
    }

//    @Override
//    public void setAsActive() {
//        if (!tabIsActive) {
//            tabIsActive = true;
//            prepareVideo(mVidAddress);
//        }
//    }

    @Override
    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_VIDEO_FILE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                VideoModel.Item item = data.getParcelableExtra("Item");
                if (item != null) {
                    mNowPlaying = item;
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public Model getModel() {
        return mVideoModel;
    }

    @Override
    public void chooseMedia() {
        if (mTabIsVisible)
            startChooseMediaActivity(PICK_VIDEO_FILE_REQUEST);
    }

    public void saveInstanceState(Bundle outState) {
        int position = mVideoView.getCurrentPosition();
        if (position != 0)
            outState.putInt(POSITION_KEY, position);
    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {
        mVideoPosition = savedInstanceState.getInt(POSITION_KEY);
    }

    @Override
    public String toString() {
        return "This is Video Controller";
    }
}
