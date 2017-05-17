package com.jsrwares.muskrat.media.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.TextView;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.MediaFunction;
import com.jsrwares.muskrat.media.background.AudioService;
import com.jsrwares.muskrat.media.models.AudioModel;
import com.jsrwares.muskrat.media.models.Model;

public class AudioController extends Controller implements View.OnClickListener {

    private AudioModel audioModel;
    private static final int PICK_AUDIO_FILE_REQUEST = 1;

    private static final int TAB_NUMBER_VIEW_ID = R.id.textPlayerTab;
    private static final int CHILD_LAYOUT = R.layout.content_player_tab;
    private static final int PLAYER_STUB_ID = R.id.playerStub;
    private static final int PLAYER_LAYOUT = R.layout.content_player_audio;

    private ViewStub playerStub;
    private View playerStubContent;

    private TextView tabNumberView = null;

    public AudioController() {
        function = MediaFunction.AUDIO;
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
        audioModel = new AudioModel(playerStubContent);
        audioModel.addButtonListener(this);

//        Intent pickAudioIntent = new Intent(fragment.getActivity(), MediaChooserActivity.class);
//        fragment.startActivityForResult(pickAudioIntent, PICK_AUDIO_FILE_REQUEST);
    }

    @Override
    public void setAsVisible() {
        tabIsVisible = true;
    }

    @Override
    public void setAsBackground() {
        super.setAsBackground();
        Log.d("Background", "Audio to the background...");
    }

    @Override
    public void saveInstanceState(Bundle outState) {
    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {
    }

    @Override
    public void processActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_AUDIO_FILE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                AudioModel.Item item = data.getParcelableExtra("Item");
                if (item != null) {
                    nowPlaying = item;
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        }
    }

    @Override
    public Model getModel() {
        return audioModel;
    }

    @Override
    public void chooseMedia() {
        if (tabIsVisible)
            startChooseMediaActivity(PICK_AUDIO_FILE_REQUEST);
    }

    @Override
    public String toString() {
        return "This is Audio Controller";
    }

    @Override
    public void onClick(View v) {
        if (v == audioModel.playButton) {
            Intent intent = new Intent(AudioService.ACTION_PLAY);
            intent.setPackage(v.getContext().getPackageName());
            fragment.getActivity().startService(intent);
        } else if (v == audioModel.pauseButton)
            fragment.getActivity().startService(new Intent(AudioService.ACTION_PAUSE));
        else if (v == audioModel.skipButton)
            fragment.getActivity().startService(new Intent(AudioService.ACTION_SKIP));
        else if (v == audioModel.rewindButton)
            fragment.getActivity().startService(new Intent(AudioService.ACTION_REWIND));
        else if (v == audioModel.stopButton)
            fragment.getActivity().startService(new Intent(AudioService.ACTION_STOP));
        else if (v == audioModel.ejectButton) {
            showUrlDialog();
        }
    }

    /**
     * Shows an alert dialog where the user can input a URL. After showing the dialog, if the user
     * confirms, sends the appropriate intent to the {@link AudioService} to cause that URL to be
     * played.
     */

    void showUrlDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(fragment.getActivity());
        alertBuilder.setTitle(R.string.url_manual_input_title);
        alertBuilder.setMessage(R.string.url_entry_instruction);
        final EditText input = new EditText(fragment.getActivity());
        alertBuilder.setView(input);

        input.setText(audioModel.SUGGESTED_URL);

        alertBuilder.setPositiveButton("Play!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {
                // Send an intent with the URL of the song to play. This is expected by
                // MusicService.
                Intent i = new Intent(AudioService.ACTION_URL);
                Uri uri = Uri.parse(input.getText().toString());
                i.setData(uri);
                fragment.getActivity().startService(i);
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dlg, int whichButton) {
            }
        });

        alertBuilder.show();
    }
}
