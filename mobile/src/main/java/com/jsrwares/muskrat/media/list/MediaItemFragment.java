package com.jsrwares.muskrat.media.list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsrwares.muskrat.media.MediaChooserActivity;
import com.jsrwares.muskrat.media.models.Model;

import java.util.ArrayList;

/**
 * <p><!-- Joe Rogers -->
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link MediaItemFragmentInterface}
 * interface.
 */
public class MediaItemFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "Column Count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private MediaItemFragmentInterface mListener;

    private MediaChooserActivity activity;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MediaItemFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MediaItemFragment newInstance(int columnCount) {
        MediaItemFragment fragment = new MediaItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        MediaItemFragmentInterface activityInterface = (MediaItemFragmentInterface) activity;
        int listLayoutResource = activityInterface.getModel().getListLayoutResource();
//        View view = inflater.inflate(R.layout.fragment_item_media_list, container, false);
//        View view = inflater.inflate(listLayoutResource, container, false);
        View view = inflater.inflate(listLayoutResource, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
//            recyclerView.setAdapter(new AudioRecyclerViewAdapter(activity.getItems(), mListener));
            RecyclerView.Adapter adapter = activityInterface.getModel()
                    .getListAdapter(activityInterface.getItems(), mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MediaChooserActivity) context;
        if (context instanceof MediaItemFragmentInterface) {
            mListener = (MediaItemFragmentInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
//    public interface OnListFragmentInteractionListener {
//        void onListFragmentInteraction(Model.Item item);
//    }

    /**
     * Interface for communication with containing Activity.
     */
    public interface MediaItemFragmentInterface {
        Model getModel();

        ArrayList<? extends Model.Item> getItems();

        void onListFragmentInteraction(Model.Item item);
    }
}
