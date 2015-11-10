package me.pullar.tigur.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import me.pullar.tigur.ui.fragment.ImageHolder;
import me.pullar.tigur.R;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> {

    Context mContext;
    List<Image> images;

    ImageAdapter(Images images) {
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
    public void onBindViewHolder(ImageHolder holder, int position) {
        holder.infoTitle.setText(images.get(position).getTitle().toString());
        holder.infoViews.setText(images.get(position).getViews().toString());
        Picasso.with(mContext)
                .load(images.get(position).getLink())
                .fit()
                .centerInside()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

}
