package com.example.npreszler.gospel_library_shuffle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentMediaListInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentMediaList extends Fragment {

    private OnFragmentMediaListInteractionListener mListener;
    private MediaListRecyclerAdapter adapter;
    private DatabaseReference mDatabaseRef;
    private List<MediaPiece> mediaPieces;

    public FragmentMediaList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media_list, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvMediaPieces);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MediaListRecyclerAdapter(new ArrayList<MediaPiece>(), mListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

        mDatabaseRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("mediaPieces");
        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mediaPieces = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    MediaPiece mediaPiece = snapshot.getValue(MediaPiece.class);
                    mediaPieces.add(mediaPiece);
                }
                adapter.addItems(mediaPieces);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
//                throw databaseError.toException();
                Log.w("test", "onCanceled failed", databaseError.toException());
            }
        });

        Button btnShuffle = view.findViewById(R.id.btnShuffle);
        btnShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<MediaPiece> selectedMediaPieces = new ArrayList<>();
                for (int i : mListener.getSelectedMediaPiecesIndices()) {
                    selectedMediaPieces.add(mediaPieces.get(i));
                }
                Collections.shuffle(selectedMediaPieces);
                mListener.onShuffleClicked(selectedMediaPieces);
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnFragmentMediaListInteractionListener) {
            mListener = (OnFragmentMediaListInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnFragmentMediaListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentMediaListInteractionListener {
        void onMediaPieceClicked(int i);
        void onShuffleClicked(List<MediaPiece> selectedMediaPieces);
        HashSet<Integer> getSelectedMediaPiecesIndices();
    }
}
