package com.jsrwares.muskrat.media.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.MediaChooserActivity;
import com.jsrwares.muskrat.media.MediaFunction;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.models.Model;
import com.jsrwares.muskrat.media.retrievers.PrepareMediaRetrieverTask;
import com.jsrwares.muskrat.media.retrievers.Retriever;

import java.util.ArrayList;

public abstract class Controller implements
        MediaItemFragment.MediaItemFragmentInterface,
        PrepareMediaRetrieverTask.MediaRetrieverPreparedListener {

    protected MediaFunction mFunction;
    protected Fragment mFragment;
    protected View mParentLayout;
    protected ViewStub mStub;
    protected View mStubContent;

    private Retriever mRetriever;
    private boolean mIsInitialized = false;

    protected abstract void setupMedia(LayoutInflater inflater);

    protected Model.Item mNowPlaying = null;
    protected boolean mTabIsVisible = false;

    private static int sCurrentTab = 0;

    public abstract void saveInstanceState(Bundle outState);

    public abstract void restoreInstanceState(Bundle savedInstanceState);

    public abstract void setAsVisible();

    public abstract void processActivityResult(int requestCode, int resultCode, Intent data);

    //    public abstract Model getModel();
    public abstract void chooseMedia();

    @Override
    public void onMediaRetrieverPrepared() {
        mFragment.getActivity().setContentView(R.layout.activity_media_chooser);
    }

    @Override
    public Model getModel() {
        return mFunction.getController().getModel();
    }

    @Override
    public ArrayList<? extends Model.Item> getItems() {
        return mRetriever.getItems();
    }

    @Override
    public void onListFragmentInteraction(Model.Item item) {
//        Toast.makeText(this, item.toString(), Toast.LENGTH_LONG).show();

//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("Item", item);
//        returnIntent.putParcelableArrayListExtra("Items", mItems);
//        setResult(Activity.RESULT_OK, returnIntent);
    }

    public void setAsBackground() {
        mIsInitialized = false;
    }

    protected void startChooseMediaActivity(int requestCode) {
        Intent intent = new Intent(mFragment.getActivity(), MediaChooserActivity.class);
        Bundle intentBundle = new Bundle();
//            intentBundle.putInt(MediaChooserActivity.MEDIA_TYPE_KEY, MediaChooserActivity.VIDEO);
        intentBundle.putInt(MediaChooserActivity.MEDIA_TYPE_KEY, mFunction.ordinal());
        intent.putExtras(intentBundle);
        mFragment.startActivityForResult(intent, requestCode);
    }

    public View initTab(Fragment fragment, LayoutInflater inflater, ViewGroup container,
                        Bundle settings) {

        if (!mIsInitialized) {
            mFragment = fragment;
            int parentLayout = settings.getInt("Parent Layout");
            mParentLayout = inflater.inflate(parentLayout, container, false);
            int stubId = settings.getInt("Stub ID");
            mStub = (ViewStub) mParentLayout.findViewById(stubId);
            setupMedia(inflater);
            mIsInitialized = true;
            mFunction = MediaFunction.values()[sCurrentTab];
//            switch (mFunction) {
//                case AUDIO:
//                    mRetriever = new AudioRetriever(mFragment.getActivity().getContentResolver());
//                    break;
//                case VIDEO:
//                    mRetriever = new VideoRetriever(mFragment.getActivity().getContentResolver());
//                    break;
//                case NEWS:
//                    mRetriever = new NewsRetriever(mFragment.getActivity().getContentResolver());
//                    break;
//            }
//            (new PrepareMediaRetrieverTask(mRetriever, this)).execute();
        }
        return mParentLayout;
    }

    @Override
    public String toString() {
        return "This is Super Controller";
    }

    public static class OnPageChangeListenerImpl implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            Log.d("Joe", "Controller.onPageSelected()");
            Log.d("Joe", "sCurrentTab=" + sCurrentTab);
            MediaFunction function;
            if (position != sCurrentTab) {
                int previousTab = sCurrentTab;
                sCurrentTab = position;
                function = MediaFunction.values()[previousTab];
                function.getController().setAsBackground();
            }
            function = MediaFunction.values()[sCurrentTab];
            function.getController().setAsVisible();
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    }
}
