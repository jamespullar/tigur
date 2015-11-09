package me.pullar.tigur;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.ListIterator;

import me.pullar.tigur.api.ImgurApi;
import me.pullar.tigur.api.RestClient;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;

    private ImgurApi imgurApi;
    private Call<Images> getImages;

    private View mScreen;
    private View mImage;
    private boolean mVisible;
    private List<Image> mImageList;
    private ListIterator<Image> mImageIterator;
    private RelativeLayout mSplash;
    private Image mCurrentImage;
    private View mImageInfo;
    private TextView mImageInfoTitle;
    private boolean mImageInfoDisplayed;
    private CardView mImageContent;
    private TextView mImageInfoViews;
    private RecyclerView mRvImageContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mScreen = findViewById(android.R.id.content);
        mRvImageContent = (RecyclerView) findViewById(R.id.rv_image_content);
        mImageContent = (CardView) findViewById(R.id.image_content);
        mImage = findViewById(R.id.image);
        mSplash = (RelativeLayout) findViewById(R.id.splash);
        mImageInfo = findViewById(R.id.image_info_overlay);
        mImageInfoTitle = (TextView) findViewById(R.id.image_info_title);
        mImageInfoViews = (TextView) findViewById(R.id.image_info_views);
        mImageInfoDisplayed = false;

        mRvImageContent = (RecyclerView) findViewById(R.id.rv_image_content);
        mRvImageContent.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        loadImages();

//        mImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(mImageInfoDisplayed) {
//                    hideImageInfo();
//                }
//            }
//        });

//        mImage.setOnTouchListener(new OnSwipeTouchListener(this) {
//            @Override
//            public void onSwipeLeft() {
//                displayNextImage();
//            }
//
//            @Override
//            public void onSwipeRight() {
//                displayPreviousImage();
//            }
//
//            @Override
//            public void onSwipeUp() {
//                if (!mImageInfoDisplayed) {
//                    showImageInfo();
//                }
//            }
//
//            @Override
//            public void onSwipeDown() {
//                if (mImageInfoDisplayed) {
//                    hideImageInfo();
//                } else {
//                    Snackbar.make(mImage, R.string.swipe_down, Snackbar.LENGTH_LONG).show();
//                }
//            }
//        });
    }

    private void loadImages(){
        imgurApi = RestClient.getClient();
        getImages = imgurApi.getImages();

        getImages.enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Response<Images> response, Retrofit retrofit) {
                Log.d("MainActivity", "Response Code = " + response.code());
                Log.d("MainActivity", "Response Body = " + response.body().getImages());
                if (response.isSuccess()) {
                    mImageList = response.body().getImages();
//                    mImageIterator = mImageList.listIterator();

                    mRvImageContent.setAdapter(new ImageAdapter(response.body()));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });
    }

    private void displayNextImage() {
        // If the splash screen is visible, remove it from the screen
        if (mSplash.isShown()) {
            mSplash.setVisibility(View.GONE);
        }

        // Grab the next image from the image list if one exists, otherwise reset and loop
        if (mImageList != null) {
            if (mImageIterator.hasNext()) {
                hideImageInfo();
                mCurrentImage = mImageIterator.next();
                Picasso.with(getApplicationContext())
                        .load(mCurrentImage.getLink())
                        .fit()
                        .centerInside()
                        .into((ImageView) mImage);
            } else {
                mImageIterator = mImageList.listIterator();
                displayNextImage(); // Loop to beginning of images when you reach the end
            }
        }
    }

    private void displayPreviousImage() {
        if (mSplash.isShown()) {
            mSplash.setVisibility(View.GONE);
        }
        if (mImageList != null) {
            if (mImageIterator.hasPrevious()) {
                hideImageInfo();
                mCurrentImage = mImageIterator.previous();
                Picasso.with(getApplicationContext())
                        .load(mCurrentImage.getLink())
                        .fit()
                        .centerInside()
                        .into((ImageView) mImage);
            } else {
                mImageIterator = mImageList.listIterator();
            }
        }
    }

    private void toggleImageInfo() {
        if (mCurrentImage != null) {
            if (!mImageInfoDisplayed) {
                showImageInfo();
            } else {
                hideImageInfo();
            }
        }
    }

    private void showImageInfo() {
        int opacity = 200;
        mImageInfo.setBackgroundColor(opacity * 0x1000000); // Black with a variable alpha
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mImageContent.getWidth(), mImageContent.getHeight());
        params.gravity = Gravity.CENTER;
        mImageInfo.setLayoutParams(params);
        mImageInfo.setVisibility(View.VISIBLE);
        mImageInfoTitle.setText(mCurrentImage.getTitle());
        mImageInfoViews.setText("Views: " + mCurrentImage.getViews().toString());
        mImageInfo.invalidate();
        mImageInfoDisplayed = true;
    }

    private void hideImageInfo() {
        if (mCurrentImage != null) {
            mImageInfo.setVisibility(View.GONE);
            mImageInfoDisplayed = false;
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mImage.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };

    private final Handler mHideHandler = new Handler();
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
