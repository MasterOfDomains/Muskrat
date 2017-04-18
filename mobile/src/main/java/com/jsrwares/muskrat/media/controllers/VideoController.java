package com.jsrwares.muskrat.media.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
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
//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediacontrollers/VideoController.java
//=======
import com.jsrwares.muskrat.media.MediaChooserActivity;
import com.jsrwares.muskrat.media.MediaFunction;
import com.jsrwares.muskrat.media.models.Model;
import com.jsrwares.muskrat.media.models.VideoModel;
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/controllers/VideoController.java

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

    private ProgressDialog mProgressDialog;
//    private Uri mNowPlaying = null;

//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediacontrollers/VideoController.java
//    String mVidAddress = "android.resource://com.jsrwares.muskrat/" + R.raw.samplevid;
////    String mVidAddress = "android.resource://com.jsrwares.muskrat/raw/samplevid";
//
//=======
    private boolean mTabIsActive = false;
    private Bundle mTabChangeBundle = null;
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/controllers/VideoController.java
    private int mVideoPosition = 0;
    private boolean mIsPrepared = false;
    private boolean mIsPreparing = false;

//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediacontrollers/VideoController.java
//    private boolean tabIsActive = false;
//=======
    public VideoController() {
        mFunction = MediaFunction.VIDEO;
    }
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/controllers/VideoController.java

    private class PreparedListener implements MediaPlayer.OnPreparedListener {

//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediacontrollers/VideoController.java
//        @Override
//        public void onPrepared(MediaPlayer mp) {
//            progressDialog.dismiss();
//            mIsPrepared = true;
//            mIsPreparing = false;
//            mVideoView.seekTo(mVideoPosition);
//            mVideoView.start();
//        }
//    }
//
//    private void prepareVideo(String address) {
//        mIsPrepared = false;
//        mIsPreparing = true;
//        progressDialog = new ProgressDialog(mFragment.getContext());
//        progressDialog.setTitle("Joe's Sample Video");
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(false);
//        progressDialog.show();
//
//        mVideoView.setMediaController(mVideoControl);
//        Uri vidUri = Uri.parse(mVidAddress);
//=======
//        mPlayerStub = (ViewStub) mStubContent.findViewById(PLAYER_STUB_ID);
//        mPlayerStub.setLayoutResource(PLAYER_LAYOUT);
//        mPlayerStubContent = inflater.inflate(PLAYER_LAYOUT, (ViewGroup) mParentLayout, true);
//        mVideoModel = new VideoModel(mPlayerStubContent);
//
//        mVideoView = (VideoView) mPlayerStubContent.findViewById(R.id.videoPlayerView);
//        mVideoControl = new MediaController(mFragment.getContext());
//        mVideoControl.setAnchorView(mVideoView);
//
//        mVideoView.setMediaController(mVideoControl);
//        mVideoView.setOnPreparedListener(this);
//    }

    @Override
    public void onPrepared(MediaPlayer mp) {
//        mProgressDialog.dismiss();
        mVideoView.seekTo(mVideoPosition);
        mVideoView.start();
    }}

    private void prepareVideo(String address) {
//        mProgressDialog = new mProgressDialog(mFragment.getContext());
//        mProgressDialog.setTitle("Joe's Sample Video");
//        mProgressDialog.setMessage("Loading...");
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.show();

        if (address == null)
            address = "android.resource://com.jsrwares.muskrat/raw/samplevid";
//            address = "android.resource://com.jsrwares.muskrat/" + R.raw.samplevid;
        Uri vidUri = Uri.parse(address);
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/controllers/VideoController.java
        mVideoView.setVideoURI(vidUri);

        mVideoView.requestFocus();
        mVideoView.setOnPreparedListener(new PreparedListener());
    }

    @Override
//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediacontrollers/VideoController.java
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

//=======
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

//        if (mNowPlaying == null) {
//            Intent intent = new Intent(mFragment.getActivity(), MediaChooserActivity.class);
//            Bundle intentBundle = new Bundle();
////            intentBundle.putInt(MediaChooserActivity.MEDIA_TYPE_KEY, MediaChooserActivity.VIDEO);
//            intentBundle.putInt(MediaChooserActivity.MEDIA_TYPE_KEY, mMediaFunction.ordinal());
//            intent.putExtras(intentBundle);
//            mFragment.startActivityForResult(intent, PICK_VIDEO_FILE_REQUEST);
//        }
//        if (mNowPlaying == null) {
//            startChooseMediaActivity(PICK_VIDEO_FILE_REQUEST);
//        }
        mTabIsVisible = true;
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/controllers/VideoController.java
    }
//
    @Override
    public void setAsBackground() {
        super.setAsBackground();
        Log.d("Background", "Video to the background...");
        if (mTabIsActive) {
            mTabIsActive = false;
            mVideoView.pause();
//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediacontrollers/VideoController.java
//=======
            mTabChangeBundle = new Bundle();
            saveInstanceState(mTabChangeBundle);
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/controllers/VideoController.java
        }
        mVideoPosition = mVideoView.getCurrentPosition();
        mVideoView.setOnPreparedListener(null);
        mIsPrepared = false;
    }

    @Override
//    public void setAsActive() {
//        if (!tabIsActive) {
//            tabIsActive = true;
//            prepareVideo(mVidAddress);
//        }
//    }

//    @Override
    public void processActivityResult(int requestCode, int resultCode, Intent data) {
//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediacontrollers/VideoController.java
//
//=======
        if (requestCode == PICK_VIDEO_FILE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                VideoModel.Item item = data.getParcelableExtra("Item");
                if (item != null) {
//                    Toast.makeText(mFragment.getContext(), item.toString(), Toast.LENGTH_LONG).show();
                    mNowPlaying = item;
                }
//                ArrayList<VideoModel.Item> items = data.getParcelableArrayListExtra("Items");
//                Log.d("Joe", "Videos: " + items.size());
            }
            else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

//    @Override
    public Model getModel() {
        return mVideoModel;
    }

//    @Override
    public void chooseMedia() {
        if (mTabIsVisible)
            startChooseMediaActivity(PICK_VIDEO_FILE_REQUEST);
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/controllers/VideoController.java
    }

    public void saveInstanceState(Bundle outState) {
        int position = mVideoView.getCurrentPosition();
        if (position != 0)
            outState.putInt(POSITION_KEY, position);
    }

//    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {
        mVideoPosition = savedInstanceState.getInt(POSITION_KEY);
    }

    @Override
    public String toString() {
        return "This is Video Controller";
    }
}
