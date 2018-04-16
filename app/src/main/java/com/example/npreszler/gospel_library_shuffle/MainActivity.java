package com.example.npreszler.gospel_library_shuffle;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.HashSet;

public class MainActivity extends AppCompatActivity
            implements FragmentMediaList.OnFragmentMediaListInteractionListener {

    FragmentManager fm;
    private static final int RC_SIGN_IN = 123;
    public HashSet<Integer> selectedMediaPieces = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frameMainActivity, new FragmentMediaList(), "fragMediaList")
                .commit();


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

    @Override
    public void onMediaPieceClicked(int i) {
        if (selectedMediaPieces.contains(i)) {
            selectedMediaPieces.remove(i);
        }
        else {
            selectedMediaPieces.add(i);
        }
    }

    @Override
    public void onShuffleClicked() {
        Log.d("test", "shuffle the following: " + selectedMediaPieces.toString());
    }
}
