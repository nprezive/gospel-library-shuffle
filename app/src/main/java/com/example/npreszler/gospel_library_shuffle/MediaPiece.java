package com.example.npreszler.gospel_library_shuffle;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class MediaPiece {

    public String title;
    public String url;
    public String type;

    public MediaPiece() {
        //default constructor
    }

    public MediaPiece(String title, String url, String type) {
        this.title = title;
        this.url = url;
        this.type = type;
    }

    @Override
    public String toString() {
        return title;
    }

    //    public enum MediaType {
//        CHAPTER, GC_TALK, HYMN
//    }
}
