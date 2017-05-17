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

    private VideoModel videoModel;
    private static final String POSITION_KEY = "Position";
    private static final int PICK_VIDEO_FILE_REQUEST = 1;

    private static final int TAB_NUMBER_VIEW_ID = R.id.textPlayerTab;
    private static final int CHILD_LAYOUT = R.layout.content_player_tab;
    private static final int PLAYER_STUB_ID = R.id.playerStub;
    private static final int PLAYER_LAYOUT = R.layout.content_player_video;

    private ViewStub playerStub;
    private View playerStubContent;
    private TextView tabNumberView = null;
    private VideoView videoView;
    private MediaController videoControl;

    private boolean tabIsActive = false;
    private Bundle tabChangeBundle = null;
    private int videoPosition = 0;
    private boolean isPrepared = false;
    private boolean isPreparing = false;

    public VideoController() {
        function = MediaFunction.VIDEO;
    }

    private class PreparedListener implements MediaPlayer.OnPreparedListener {

        @Override
        public void onPrepared(MediaPlayer mp) {
            videoView.seekTo(videoPosition);
            videoView.start();
        }
    }

    private void prepareVideo(String address) {
        if (address == null)
            address = "android.resource://com.jsrwares.muskrat/raw/samplevid";
        Uri vidUri = Uri.parse(address);
        videoView.setVideoURI(vidUri);

        videoView.requestFocus();
        videoView.setOnPreparedListener(new PreparedListener());
    }

    @Override
    protected void setupMedia(LayoutInflater inflater) {
        stub.setLayoutResource(CHILD_LAYOUT);
        stubContent = inflater.inflate(CHILD_LAYOUT, (ViewGroup) parentLayout, true);
        tabNumberView = (TextView) stubContent.findViewById(TAB_NUMBER_VIEW_ID);
        tabNumberView.setText(toString());

        playerStub = (ViewStub) stubContent.findViewById(PLAYER_STUB_ID);
        playerStub.setLayoutResource(PLAYER_LAYOUT);
        playerStubContent = inflater.inflate(PLAYER_LAYOUT, (ViewGroup) parentLayout, true);
        videoView = (VideoView) playerStubContent.findViewById(R.id.videoPlayerView);
        videoControl = new MediaController(fragment.getContext());
        videoControl.setAnchorView(videoView);
    }

    public void setAsVisible() {
        if (!tabIsActive) {
            tabIsActive = true;
            prepareVideo(null);
            if (tabChangeBundle != null) {
                restoreInstanceState(tabChangeBundle);
                tabChangeBundle = null;
                videoView.seekTo(videoPosition);
            }
        }

        tabIsVisible = true;
    }

    @Override
    public void setAsBackground() {
        super.setAsBackground();
        Log.d("Background", "Video to the background...");
        if (tabIsActive) {
            tabIsActive = false;
            videoView.pause();
            tabChangeBundle = new Bundle();
            saveInstanceState(tabChangeBundle);
        }
        videoPosition = videoView.getCurrentPosition();
        videoView.setOnPreparedListener(null);
        isPrepared = false;
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
                    nowPlaying = item;
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public Model getModel() {
        return videoModel;
    }

    @Override
    public void chooseMedia() {
        if (tabIsVisible)
            startChooseMediaActivity(PICK_VIDEO_FILE_REQUEST);
    }

    public void saveInstanceState(Bundle outState) {
        int position = videoView.getCurrentPosition();
        if (position != 0)
            outState.putInt(POSITION_KEY, position);
    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {
        videoPosition = savedInstanceState.getInt(POSITION_KEY);
    }

    @Override
    public String toString() {
        return "This is Video Controller";
    }
}
