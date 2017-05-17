package com.jsrwares.muskrat.media.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.list.adapters.AudioRecyclerViewAdapter;
import com.jsrwares.muskrat.media.models.AudioModel;
import com.jsrwares.muskrat.media.background.AudioService;
import com.jsrwares.muskrat.media.MediaFunction;
import com.jsrwares.muskrat.media.models.Model;
import com.jsrwares.muskrat.media.models.VideoModel;

import java.util.List;

public class AudioController extends Controller implements View.OnClickListener {

    private AudioModel mAudioModel;
    private static final int PICK_AUDIO_FILE_REQUEST = 1;

    private static final int TAB_NUMBER_VIEW_ID = R.id.textPlayerTab;
    private static final int CHILD_LAYOUT = R.layout.content_player_tab;
    private static final int PLAYER_STUB_ID = R.id.playerStub;
    private static final int PLAYER_LAYOUT = R.layout.content_player_audio;

    private ViewStub mPlayerStub;
    private View mPlayerStubContent;

    private Uri nowPlaying = null;

    private TextView mTabNumberView = null;

    public AudioController() {
        mFunction = MediaFunction.AUDIO;
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
        mAudioModel = new AudioModel(mPlayerStubContent);
        mAudioModel.addButtonListener(this);

//        Intent pickAudioIntent = new Intent(mFragment.getActivity(), MediaChooserActivity.class);
//        mFragment.startActivityForResult(pickAudioIntent, PICK_AUDIO_FILE_REQUEST);
    }

    @Override
    public void setAsVisible() {
        mTabIsVisible = true;
    }

    @Override
    public void setAsBackground() {
        super.setAsBackground();
        Log.d("Background", "Audio to the background...");
    }

    @Override
    public void saveInstanceState(Bundle outState) {}

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {}

    @Override
    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_AUDIO_FILE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                AudioModel.Item item = data.getParcelableExtra("Item");
                if (item != null) {
//                    Toast.makeText(mFragment.getContext(), item.toString(), Toast.LENGTH_LONG).show();
                    mNowPlaying = item;
                }
            }
            else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public Model getModel() {
        return mAudioModel;
    }

    @Override
    public void chooseMedia() {
        if (mTabIsVisible)
            startChooseMediaActivity(PICK_AUDIO_FILE_REQUEST);
    }

    @Override
    public String toString() {
        return "This is Audio Controller";
    }

    @Override
    public void onClick(View v) {
        if (v == mAudioModel.mPlayButton) {
            Intent intent = new Intent(AudioService.ACTION_PLAY);
            intent.setPackage(v.getContext().getPackageName());
            mFragment.getActivity().startService(intent);
        }
        else if (v == mAudioModel.mPauseButton)
            mFragment.getActivity().startService(new Intent(AudioService.ACTION_PAUSE));
        else if (v == mAudioModel.mSkipButton)
            mFragment.getActivity().startService(new Intent(AudioService.ACTION_SKIP));
        else if (v == mAudioModel.mRewindButton)
            mFragment.getActivity().startService(new Intent(AudioService.ACTION_REWIND));
        else if (v == mAudioModel.mStopButton)
            mFragment.getActivity().startService(new Intent(AudioService.ACTION_STOP));
        else if (v == mAudioModel.mEjectButton) {
            showUrlDialog();
        }
    }

    /**
     * Shows an alert dialog where the user can input a URL. After showing the dialog, if the user
     * confirms, sends the appropriate intent to the {@link AudioService} to cause that URL to be
     * played.
     */

    void showUrlDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mFragment.getActivity());
        alertBuilder.setTitle(R.string.url_manual_input_title);
        alertBuilder.setMessage(R.string.url_entry_instruction);
        final EditText input = new EditText(mFragment.getActivity());
        alertBuilder.setView(input);

        input.setText(mAudioModel.SUGGESTED_URL);

        alertBuilder.setPositiveButton("Play!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {
                // Send an intent with the URL of the song to play. This is expected by
                // MusicService.
                Intent i = new Intent(AudioService.ACTION_URL);
                Uri uri = Uri.parse(input.getText().toString());
                i.setData(uri);
                mFragment.getActivity().startService(i);
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {}
        });

        alertBuilder.show();
    }
}
