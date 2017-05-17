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

    private Retriever retriever;
    public final static String MEDIA_TYPE_KEY = "Media Type";

    private MediaFunction function;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int mediaTypeOrdinal = getIntent().getExtras().getInt(MEDIA_TYPE_KEY);
        function = MediaFunction.values()[mediaTypeOrdinal];
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        switch (function) {
            case AUDIO:
                retriever = new AudioRetriever(getContentResolver());
                break;
            case VIDEO:
                retriever = new VideoRetriever(getContentResolver());
                break;
            case NEWS:
                retriever = new NewsRetriever(getContentResolver());
                break;
        }

        (new PrepareMediaRetrieverTask(retriever, this)).execute();
    }

    @Override
    public void onMediaRetrieverPrepared() {
        setContentView(R.layout.activity_media_chooser);
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
        Toast.makeText(this, item.toString(), Toast.LENGTH_LONG).show();
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("Item", item);
//        returnIntent.putParcelableArrayListExtra("Items", items);
//        setResult(Activity.RESULT_OK, returnIntent);
    }
}
