package com.example.npreszler.gospel_library_shuffle;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMediaPlayer extends Fragment {

    private OnFragmentMediaPlayerInteractionListener mCallback;

    public FragmentMediaPlayer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media_player, container, false);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentMediaPlayerInteractionListener) {
            mCallback = (OnFragmentMediaPlayerInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentMediaListInteractionListener");
        }
    }

    public interface OnFragmentMediaPlayerInteractionListener {
        List<MediaPiece> getPlaylist();
    }

}
