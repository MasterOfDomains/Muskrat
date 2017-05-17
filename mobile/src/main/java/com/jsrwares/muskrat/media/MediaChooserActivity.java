package com.jsrwares.muskrat.media;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.jsrwares.muskrat.R;
import com.jsrwares.muskrat.media.list.MediaItemFragment;
import com.jsrwares.muskrat.media.models.Model;
import com.jsrwares.muskrat.media.retrievers.AudioRetriever;
import com.jsrwares.muskrat.media.retrievers.NewsRetriever;
import com.jsrwares.muskrat.media.retrievers.PrepareMediaRetrieverTask;
import com.jsrwares.muskrat.media.retrievers.Retriever;
import com.jsrwares.muskrat.media.retrievers.VideoRetriever;

import java.util.ArrayList;

public class MediaChooserActivity extends AppCompatActivity implements
        PrepareMediaRetrieverTask.MediaRetrieverPreparedListener,
        MediaItemFragment.MediaItemFragmentInterface {

    private Retriever mRetriever;
    public final static String MEDIA_TYPE_KEY = "Media Type";

    private MediaFunction mFunction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mediaTypeOrdinal = getIntent().getExtras().getInt(MEDIA_TYPE_KEY);
        mFunction = MediaFunction.values()[mediaTypeOrdinal];
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switch (mFunction) {
            case AUDIO:
                mRetriever = new AudioRetriever(getContentResolver());
                break;
            case VIDEO:
                mRetriever = new VideoRetriever(getContentResolver());
                break;
            case NEWS:
                mRetriever = new NewsRetriever(getContentResolver());
                break;
        }

        (new PrepareMediaRetrieverTask(mRetriever, this)).execute();
    }

    @Override
    public void onMediaRetrieverPrepared() {
        setContentView(R.layout.activity_media_chooser);
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
        Toast.makeText(this, item.toString(), Toast.LENGTH_LONG).show();
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("Item", item);
//        returnIntent.putParcelableArrayListExtra("Items", mItems);
//        setResult(Activity.RESULT_OK, returnIntent);
    }
}
