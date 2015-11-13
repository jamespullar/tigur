package me.pullar.tigur.ui.fragment;

import android.app.FragmentTransaction;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import me.pullar.tigur.R;
import me.pullar.tigur.ui.adapter.ImageAdapter;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public CardView cardView;
    public ImageView image;
    public TextView infoTitle;
    public TextView infoViews;
    private ImageAdapter.OnItemClickListener mOnItemClickListener;

    public ImageHolder(View view) {
        super(view);
    }

    public ImageHolder(View view, final ImageAdapter.OnItemClickListener listener) {
        super(view);

        final Context context = view.getContext();
        cardView = (CardView) view.findViewById(R.id.image_content);
        image = (ImageView) view.findViewById(R.id.image);
        infoTitle = (TextView) view.findViewById(R.id.image_info_title);
        infoViews = (TextView) view.findViewById(R.id.image_info_views);

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
    }

    public void setOnItemClickListener(ImageAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = (ImageAdapter.OnItemClickListener) listener;
    }

}
