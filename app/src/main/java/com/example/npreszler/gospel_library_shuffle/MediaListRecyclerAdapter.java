package com.example.npreszler.gospel_library_shuffle;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

public class MediaListRecyclerAdapter
        extends RecyclerView.Adapter<MediaListRecyclerAdapter.ViewHolder> {

    private final List<MediaPiece> mediaPieces;
    private final FragmentMediaList.OnFragmentMediaListInteractionListener mCallback;

    public MediaListRecyclerAdapter(List<MediaPiece> mediaPieceList,
                                FragmentMediaList.OnFragmentMediaListInteractionListener listener) {
        mediaPieces = mediaPieceList;
        mCallback = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.media_piece_recycler_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final MediaPiece mediaPiece = mediaPieces.get(position);
        if (mediaPiece != null) {
            holder.mediaPiece = mediaPiece;
            holder.txvMediaPieceName.setText(mediaPiece.title);
            holder.cbMediaPiece.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onMediaPieceClicked(position);
                }
            });
//            holder.mView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (mCallback != null) {
//                        holder.cbMediaPiece.toggle();
//                        mCallback.onMediaPieceClicked(position);
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return mediaPieces.size();
    }

    public void addItems(List<MediaPiece> items) {
        mediaPieces.clear();
        mediaPieces.addAll(items);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View mView;
        public CheckBox cbMediaPiece;
        public TextView txvMediaPieceName;
        public MediaPiece mediaPiece;

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            txvMediaPieceName = itemView.findViewById(R.id.txvMediaPieceName);
            cbMediaPiece = itemView.findViewById(R.id.cbMediaPiece);
        }
    }
}
