package com.jsrwares.muskrat.media.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.MediaFunction;
import com.jsrwares.muskrat.media.models.Model;
import com.jsrwares.muskrat.media.models.NewsModel;

public class NewsController extends Controller {

    private NewsModel mNewsModel;
    private static final int PICK_NEWS_FILE_REQUEST = 1;

    private static final int TAB_NUMBER_VIEW_ID = R.id.textNewsTab;
    private static final int CHILD_LAYOUT = R.layout.content_news_tab;

    private TextView mTabNumberView = null;

    public NewsController() {
        mFunction = MediaFunction.NEWS;
    }

    @Override
    protected void setupMedia(LayoutInflater inflater) {
        mStub.setLayoutResource(CHILD_LAYOUT);
        mStubContent = inflater.inflate(CHILD_LAYOUT, (ViewGroup) mParentLayout, true);
        mNewsModel = new NewsModel(mStubContent);

        mTabNumberView = (TextView) mStubContent.findViewById(TAB_NUMBER_VIEW_ID);
        mTabNumberView.setText(toString());
    }

    @Override
    public void setAsBackground() {
        super.setAsBackground();
        Log.d("Background", "News to the background...");
    }

    @Override
    public void saveInstanceState(Bundle outState) {

    }

    @Override
    public void restoreInstanceState(Bundle savedInstanceState) {

    }

    @Override
//<<<<<<< Updated upstream:mobile/src/main/java/com/jsrwares/muskrat/mediacontrollers/NewsController.java
//    public void setAsActive() {
//
//=======
    public void setAsVisible() {
        mTabIsVisible = true;
//>>>>>>> Stashed changes:mobile/src/main/java/com/jsrwares/muskrat/media/controllers/NewsController.java
    }

    @Override
    public void processActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    public Model getModel() {
        return mNewsModel;
    }

    @Override
    public void chooseMedia() {
        if (mTabIsVisible)
            startChooseMediaActivity(PICK_NEWS_FILE_REQUEST);
    }

    @Override
    public String toString() {
        return "This is News Controller";
    }
}
