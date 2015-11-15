package me.pullar.tigur.ui.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import me.pullar.tigur.R;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;
import me.pullar.tigur.ui.activity.MainActivity;
import me.pullar.tigur.ui.fragment.ImageFragment;
import me.pullar.tigur.ui.viewholder.ImageHolder;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> implements View.OnClickListener {

    Context mContext;
    List<Image> images;
    private MainActivity mMainActivity;
    private OnItemClickListener mOnItemClickListener;

    public ImageAdapter(Images images) {
        this.images = images.getImages();
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_holder, parent, false);
        ImageHolder imageViewHolder = new ImageHolder(view, mOnItemClickListener);
        mContext = parent.getContext();
        mMainActivity = (MainActivity) mContext;
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, int position) {
        final Image currentImage = images.get(position);

        holder.infoTitle.setText(currentImage.getTitle().toString());
        holder.infoViews.setText("Views: " + NumberFormat.getNumberInstance().format(currentImage.getViews()).toString());
        holder.infoSection.setText(currentImage.getSection().toString());

        if (currentImage.getDescription() != null) {
            holder.infoDescription.setText(currentImage.getDescription().toString());
            holder.infoDescription.setVisibility(View.VISIBLE);
            holder.infoLine.setVisibility(View.VISIBLE);
        } else {
            holder.infoDescription.setText("");
            holder.infoDescription.setVisibility(View.GONE);
            holder.infoLine.setVisibility(View.GONE);
        }

        Glide.with(mContext)
                .load(currentImage.getLink())
                .asBitmap()
                .centerCrop()
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = mMainActivity.getFragmentManager();
                ImageFragment imageFragment;
                if (fm != null) {
                    if (currentImage.isAnimated()) {
                        Log.d("ImageAdapter", "Image Link = " + currentImage.getLink());
                        imageFragment = ImageFragment.newInstance(currentImage.getLink());

                    } else {
                        imageFragment = ImageFragment.newInstance(currentImage.getLink());
                    }
                    FragmentTransaction ft = MainActivity.getmFragmentManager().beginTransaction();
                    ft.replace(android.R.id.content, imageFragment).addToBackStack(null);
                    ft.commit();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    @Override
    public void onClick(View v) {
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick();
    }

}
