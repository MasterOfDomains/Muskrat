package com.jsrwares.muskrat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsrwares.muskrat.media.MediaFunction;
import com.jsrwares.muskrat.media.background.AudioService;
import com.jsrwares.muskrat.media.controllers.Controller;

public class SlidingTabFragment extends Fragment {

    private Controller mController;

    static final String KEY_TAB_POSITION = "tabposition";

    public static SlidingTabFragment getInstance(int position) {
        SlidingTabFragment myFragment = new SlidingTabFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_TAB_POSITION, position);
        myFragment.setArguments(args);
        return myFragment;
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
            case KeyEvent.KEYCODE_HEADSETHOOK:
                getActivity().startService(new Intent(AudioService.ACTION_TOGGLE_PLAYBACK));
                return true;
        }
        return false;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Note to self: You can use any of onCreate(), onCreateView(), or onActivityCreated() as
        // the Fragment's equivalent of onRestoreInstanceState()
        View layout = null;
        Bundle bundle = getArguments();
        if (bundle != null) {
            int tabPosition = bundle.getInt(KEY_TAB_POSITION);
            MediaFunction function = MediaFunction.values()[tabPosition];
            mController = function.getController();
            int parentLayout = R.layout.fragment_sliding_tab;
            int stub = R.id.slidingTabContent;

            Bundle tabSettings = new Bundle();
            tabSettings.putInt("Parent Layout", parentLayout);
            tabSettings.putInt("Stub ID", stub);

            if (mController != null) {
                layout = mController.initTab(this, inflater, container, tabSettings);
            }

            if (savedInstanceState != null) {
                mController.restoreInstanceState(savedInstanceState);
            }
        }
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mController.processActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mController.saveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
        mController.setAsBackground();
    }
}