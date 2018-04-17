package com.example.npreszler.gospel_library_shuffle;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MainActivity extends AppCompatActivity
            implements FragmentMediaList.OnFragmentMediaListInteractionListener,
                        FragmentMediaPlayer.OnFragmentMediaPlayerInteractionListener {

    FragmentManager fm;
    private static final int RC_SIGN_IN = 123;
    public HashSet<Integer> selectedMediaPiecesIndices = new HashSet<>();
    public ArrayList<MediaPiece> playlist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.frameMainActivity, new FragmentMediaList(), "fragMediaList")
                .commit();



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

//    public boolean isConnectedToInternet(){
//        ConnectivityManager connectivity = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (connectivity != null)
//        {
//            NetworkInfo[] info = connectivity.getAllNetworkInfo();
//            if (info != null)
//                for (int i = 0; i < info.length; i++)
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
//                    {
//                        return true;
//                    }
//
//        }
//        return false;
//    }

    @Override
    public void onMediaPieceClicked(int i) {
        if (selectedMediaPiecesIndices.contains(i)) {
            selectedMediaPiecesIndices.remove(i);
        }
        else {
            selectedMediaPiecesIndices.add(i);
        }
    }

    @Override
    public void onShuffleClicked(List<MediaPiece> selectedMediaPieces) {
        playlist = (ArrayList<MediaPiece>) selectedMediaPieces;
        fm.beginTransaction()
//                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frameMainActivity, new FragmentMediaPlayer(), "fragMediaPlayer")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public HashSet<Integer> getSelectedMediaPiecesIndices() {
        return selectedMediaPiecesIndices;
    }

    @Override
    public List<MediaPiece> getPlaylist() {
        return playlist;
    }
}
