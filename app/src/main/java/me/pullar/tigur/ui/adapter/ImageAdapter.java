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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.pullar.tigur.R;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;
import me.pullar.tigur.ui.fragment.ImageHolder;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> implements Parcelable {

    Context mContext;
    List<Image> images;

    public ImageAdapter(Images images) {
        this.images = images.getImages();
    }

    protected ImageAdapter(Parcel in) {
        images = in.createTypedArrayList(Image.CREATOR);
    }

    public static final Creator<ImageAdapter> CREATOR = new Creator<ImageAdapter>() {
        @Override
        public ImageAdapter createFromParcel(Parcel in) {
            return new ImageAdapter(in);
        }

        @Override
        public ImageAdapter[] newArray(int size) {
            return new ImageAdapter[size];
        }
    };

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

        Glide.with(mContext)
                .load(images.get(position).getLink())
                .centerCrop()
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
