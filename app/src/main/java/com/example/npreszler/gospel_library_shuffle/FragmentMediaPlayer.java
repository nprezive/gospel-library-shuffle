package com.example.npreszler.gospel_library_shuffle;


import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMediaPlayer extends Fragment {

    private OnFragmentMediaPlayerInteractionListener mCallback;
    private MediaPlayer mediaPlayer;
    private ArrayList<MediaPiece> playlist;
    private int currentlyPlayingTrack;
    private boolean isPreparing;
    private TextView txvMarquee;
    private SeekBar seekBar;

    public FragmentMediaPlayer() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_media_player, container, false);

        txvMarquee = view.findViewById(R.id.txvMarquee);
        seekBar = view.findViewById(R.id.sbProgresBar);
        ImageButton btnPlayPause = view.findViewById(R.id.btnPlayPause);
        ImageButton btnPrev = view.findViewById(R.id.btnPrev);
        ImageButton btnNext = view.findViewById(R.id.btnNext);

        playlist = (ArrayList<MediaPiece>) mCallback.getPlaylist();
        currentlyPlayingTrack = 0;

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPreparing = false;
                playPause();
            }
        });

        MediaPiece playingTrack = playlist.get(currentlyPlayingTrack);
        try {
            mediaPlayer.setDataSource(playingTrack.url);
            isPreparing = true;
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        txvMarquee.setText(playingTrack.title);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextTrack();
            }
        });






        //Button listeners
        btnPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPause();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextTrack();
            }
        });
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevTrack();
            }
        });

        return view;
    }

    private void playPause() {
        if(mediaPlayer == null || isPreparing)
            return;

        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        else {
            mediaPlayer.pause();
        }
    }

    private void nextTrack() {
        if (mediaPlayer == null)
            return;

        if (currentlyPlayingTrack + 1 >= playlist.size())
            return;

        MediaPiece playingTrack = playlist.get(++currentlyPlayingTrack);
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(playingTrack.url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        isPreparing = true;
        mediaPlayer.prepareAsync();
        txvMarquee.setText(playingTrack.title);
    }

    private void prevTrack() {
        if (mediaPlayer == null)
            return;

        if (mediaPlayer.getCurrentPosition() < 3000 && currentlyPlayingTrack - 1 >= 0) {
            MediaPiece playingTrack = playlist.get(--currentlyPlayingTrack);
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(playingTrack.url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            isPreparing = true;
            mediaPlayer.prepareAsync();
            txvMarquee.setText(playingTrack.title);
        }
        else {
            mediaPlayer.seekTo(0);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if(mediaPlayer != null)
            mediaPlayer.release();
        mediaPlayer = null;
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
