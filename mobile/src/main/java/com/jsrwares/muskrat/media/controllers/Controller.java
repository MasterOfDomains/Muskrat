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

    protected MediaFunction function;
    protected Fragment fragment;
    protected View parentLayout;
    protected ViewStub stub;
    protected View stubContent;

    private Retriever retriever;
    private boolean isInitialized = false;

    protected Model.Item nowPlaying = null;
    protected boolean tabIsVisible = false;

    protected abstract void setupMedia(LayoutInflater inflater);

    private static int currentTab = 0;

    public abstract void saveInstanceState(Bundle outState);

    public abstract void restoreInstanceState(Bundle savedInstanceState);

    public abstract void setAsVisible();

    public abstract void processActivityResult(int requestCode, int resultCode, Intent data);

    //    public abstract Model getModel();
    public abstract void chooseMedia();

    @Override
    public void onMediaRetrieverPrepared() {
        fragment.getActivity().setContentView(R.layout.activity_media_chooser);
    }

    @Override
    public Model getModel() {
        return function.getController().getModel();
    }

    @Override
    public ArrayList<? extends Model.Item> getItems() {
        return retriever.getItems();
    }

    @Override
    public void onListFragmentInteraction(Model.Item item) {
//        Toast.makeText(this, item.toString(), Toast.LENGTH_LONG).show();

//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("Item", item);
//        returnIntent.putParcelableArrayListExtra("Items", items);
//        setResult(Activity.RESULT_OK, returnIntent);
    }

    public void setAsBackground() {
        isInitialized = false;
    }

    protected void startChooseMediaActivity(int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), MediaChooserActivity.class);
        Bundle intentBundle = new Bundle();
//            intentBundle.putInt(MediaChooserActivity.MEDIA_TYPE_KEY, MediaChooserActivity.VIDEO);
        intentBundle.putInt(MediaChooserActivity.MEDIA_TYPE_KEY, function.ordinal());
        intent.putExtras(intentBundle);
        fragment.startActivityForResult(intent, requestCode);
    }

    public View initTab(Fragment fragment, LayoutInflater inflater, ViewGroup container,
                        Bundle settings) {

        if (!isInitialized) {
            this.fragment = fragment;
            int parentLayout = settings.getInt("Parent Layout");
            this.parentLayout = inflater.inflate(parentLayout, container, false);
            int stubId = settings.getInt("Stub ID");
            stub = (ViewStub) this.parentLayout.findViewById(stubId);
            setupMedia(inflater);
            isInitialized = true;
            function = MediaFunction.values()[currentTab];
//            switch (function) {
//                case AUDIO:
//                    retriever = new AudioRetriever(fragment.getActivity().getContentResolver());
//                    break;
//                case VIDEO:
//                    retriever = new VideoRetriever(fragment.getActivity().getContentResolver());
//                    break;
//                case NEWS:
//                    retriever = new NewsRetriever(fragment.getActivity().getContentResolver());
//                    break;
//            }
//            (new PrepareMediaRetrieverTask(retriever, this)).execute();
        }
        return parentLayout;
    }

    @Override
    public String toString() {
        return "This is Super Controller";
    }

    public static class OnPageChangeListenerImpl implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {
            Log.d("Joe", "Controller.onPageSelected()");
            Log.d("Joe", "currentTab=" + currentTab);
            MediaFunction function;
            if (position != currentTab) {
                int previousTab = currentTab;
                currentTab = position;
                function = MediaFunction.values()[previousTab];
                function.getController().setAsBackground();
            }
            function = MediaFunction.values()[currentTab];
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
