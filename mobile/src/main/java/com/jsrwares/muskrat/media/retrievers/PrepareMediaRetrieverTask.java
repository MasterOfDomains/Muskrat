package com.jsrwares.muskrat.media.retrievers;

import android.os.AsyncTask;

/**
 * Asynchronous task that prepares a MusicRetriever. This asynchronous task essentially calls
 * {@link Retriever} on a
 * {@link Retriever}, which may take some time
 * to run. Upon finishing, it notifies the indicated {@link MediaRetrieverPreparedListener}.
 */

public class PrepareMediaRetrieverTask extends AsyncTask<Void, Void, Void> {
    Retriever mRetriever;
    MediaRetrieverPreparedListener mListener;

    public PrepareMediaRetrieverTask(Retriever retriever,
                                     MediaRetrieverPreparedListener listener) {
        mRetriever = retriever;
        mListener = listener;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        mRetriever.prepare();
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        mListener.onMediaRetrieverPrepared();
    }

    public interface MediaRetrieverPreparedListener {
        void onMediaRetrieverPrepared();
    }
}