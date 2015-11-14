package me.pullar.tigur.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.List;

import me.pullar.tigur.R;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;
import me.pullar.tigur.ui.fragment.ImageHolder;

/**
 * Created by jamespullar on 11/8/15.
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageHolder> implements View.OnClickListener {

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
        Image currentImage = images.get(position);

        holder.infoTitle.setText(currentImage.getTitle().toString());
        holder.infoViews.setText("Views: " + NumberFormat.getNumberInstance().format(currentImage.getViews()).toString());
        holder.infoDateTime.setText(DateFormat.getDateInstance().format(currentImage.getDatetime()));
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
                .into(holder.image);
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
