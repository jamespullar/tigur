package me.pullar.tigur;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    CardView cardView;
    ImageView image;
    TextView infoTitle;
    TextView infoViews;

    public ImageHolder(View view) {
        super(view);
        cardView = (CardView) view.findViewById(R.id.image_content);
        image = (ImageView) view.findViewById(R.id.image);
        infoTitle = (TextView) view.findViewById(R.id.image_info_title);
        infoViews = (TextView) view.findViewById(R.id.image_info_views);
    }

    @Override
    public void onClick(View v) {

    }
}
