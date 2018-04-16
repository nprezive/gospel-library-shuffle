package com.example.npreszler.gospel_library_shuffle;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
            implements FragmentMediaList.OnFragmentMediaListInteractionListener {

    private static final int RC_SIGN_IN = 123;
    private DatabaseReference mDatabaseRef;
    private ArrayList<MediaPiece> mediaPieces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseRef = FirebaseDatabase.getInstance()
                .getReference()
                .child("mediaPieces");

        Button loadData = (Button) findViewById(R.id.btnLoadData);

        mDatabaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mediaPieces = new ArrayList<MediaPiece>();
                for (DataSnapshot mediaPieceSnapshot : dataSnapshot.getChildren()) {
//                    Log.d("test", mediaPieceSnapshot.getValue().toString());
                    MediaPiece mediaPiece = mediaPieceSnapshot.getValue(MediaPiece.class);
                    Log.d("test", mediaPiece.toString());
                    mediaPieces.add(mediaPiece);
                }

                if(mediaPieces == null) {
                    Log.d("test", "mediaPieces is null");
                }
                else {
                    Log.d("test", mediaPieces.toString());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting data failed, log a message
                Log.w("test", "onCanceled failed", databaseError.toException());
            }
        });










        // test - play stream
//        loadData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "http://media2.ldscdn.org/assets/scriptures/the-old-testament/2015-11-5010-psalm-023-male-voice-64k-eng.mp3";
//                MediaPlayer mediaPlayer = new MediaPlayer();
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                try {
//                    mediaPlayer.setDataSource(url);
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                mediaPlayer.start();
//            }
//        });



//        // Choose authentication providers
//        List<AuthUI.IdpConfig> providers = Arrays.asList(
//                new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());
//
//        // Create and launch sign-in intent
//        startActivityForResult(
//                AuthUI.getInstance()
//                        .createSignInIntentBuilder()
//                        .setAvailableProviders(providers)
//                        .build(),
//                RC_SIGN_IN);
    }

    public boolean isConnectedToInternet(){
        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
