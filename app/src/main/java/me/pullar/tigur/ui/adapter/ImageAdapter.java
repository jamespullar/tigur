package me.pullar.tigur.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import me.pullar.tigur.R;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;
import me.pullar.tigur.ui.fragment.ImageHolder;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> implements View.OnClickListener {

    private ImageView imageView;
    Context mContext;
    List<Image> images;
    private OnItemClickListener mOnItemClickListener;

    public ImageAdapter(Images images) {
        this.images = images.getImages();
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_holder, parent, false);
        ImageHolder imageViewHolder = new ImageHolder(view, mOnItemClickListener);
        mContext = parent.getContext();
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(final ImageHolder holder, int position) {
        holder.infoTitle.setText(images.get(position).getTitle().toString());
        holder.infoViews.setText("Views: " + images.get(position).getViews().toString());

        Glide.with(mContext)
                .load(images.get(position).getLink())
                .asBitmap()
                .centerCrop()
                .into(holder.image);

//        holder.image.setOnClickListener(mOnItemClickListener);
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
