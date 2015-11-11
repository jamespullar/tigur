package me.pullar.tigur.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import me.pullar.tigur.ui.activity.MainActivity;
import me.pullar.tigur.ui.fragment.ImageHolder;
import me.pullar.tigur.R;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> implements Parcelable {

    Context mContext;
    List<Image> images;

    public ImageAdapter(Images images) {
        this.images = images.getImages();
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_holder, parent, false);
        ImageHolder imageViewHolder = new ImageHolder(view);
        mContext = parent.getContext();
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, int position) {
        holder.infoTitle.setText(images.get(position).getTitle().toString());
        holder.infoViews.setText("Views: " + images.get(position).getViews().toString());

        Picasso.with(mContext)
                .load(images.get(position).getLink())
                .fit()
                .centerInside()
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ImageHolder", "Clicked image " + holder.infoTitle);
                Snackbar.make(v, R.string.on_click, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("ListImage", (ArrayList<? extends Parcelable>) images);
    }


}
