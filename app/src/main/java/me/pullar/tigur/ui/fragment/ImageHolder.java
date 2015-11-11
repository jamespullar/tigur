package me.pullar.tigur.ui.fragment;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.annotations.SerializedName;

import me.pullar.tigur.R;
import me.pullar.tigur.ui.activity.MainActivity;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public ImageView image;
    public TextView infoTitle;
    public TextView infoViews;

    public ImageHolder(View view) {
        super(view);
        cardView = (CardView) view.findViewById(R.id.image_content);
        image = (ImageView) view.findViewById(R.id.image);
        infoTitle = (TextView) view.findViewById(R.id.image_info_title);
        infoViews = (TextView) view.findViewById(R.id.image_info_views);
    }

}
