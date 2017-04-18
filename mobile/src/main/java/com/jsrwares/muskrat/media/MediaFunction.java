package com.jsrwares.muskrat.media;

import android.util.Log;

import com.jsrwares.muskrat.media.controllers.AudioController;
import com.jsrwares.muskrat.media.controllers.Controller;
import com.jsrwares.muskrat.media.controllers.NewsController;
import com.jsrwares.muskrat.media.controllers.VideoController;

public enum MediaFunction {
    // These must jibe with "tabs" string-array in strings.xml
    AUDIO(AudioController.class),
    VIDEO(VideoController.class),
    NEWS(NewsController.class);

    private final Class mControlClass;
    private Controller mController = null;

    MediaFunction(Class controller) {
        mControlClass = controller;
    }

    public Controller getController() {
        if (mController == null) {
            try {
                mController = (Controller) mControlClass.newInstance();
            } catch (java.lang.InstantiationException e) {
                Log.e("getController", "You can't create an instance of this class.");
            } catch (IllegalAccessException e) {
                Log.e("getController", "You don't have access to that class, field, method or constructor.");
            }
        }
        return mController;
    }
}