package me.pullar.tigur;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import me.pullar.tigur.api.ImgurApi;
import me.pullar.tigur.api.RestClient;
import me.pullar.tigur.api.model.Image;
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

    private View mContentView;
    private boolean mVisible;
    private Image mImage;
    private List<Image> mImageList;
    private ListIterator<Image> mImageIterator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);
        mImage = new Image();
        mImageList = new ArrayList<Image>();

        ImgurApi imgurApi = RestClient.getClient();
        Call<Image> call = imgurApi.loadImages();
        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Response<Image> response, Retrofit retrofit) {
                Log.d("MainActivity", "Status Code = " + response.code());
                if (response.isSuccess()) {
                    Image result = response.body();
                    Log.d("MainActivity", "response = " + new Gson().toJson(response));
                    mImageList.add(mImage);
//                    for(int i=0 ; i <= result.size(); i++){
//                        mImage.add(i, result.get(i));
//                    }
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MainActivity", t.toString());
            }
        });

//        mImages = new ArrayList<>();
//        mImages.add(R.drawable.image1);
//        mImages.add(R.drawable.image2);
//        mImages.add(R.drawable.image3);
//        mImages.add(R.drawable.image4);
//        mImages.add(R.drawable.image5);
//        mImages.add(R.drawable.image6);

        mImageIterator = mImageList.listIterator();

        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });

        mContentView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Snackbar.make(mContentView, R.string.long_press, Snackbar.LENGTH_LONG).show();
                return false;
            }
        });

        mContentView.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                Snackbar.make(mContentView, R.string.swipe_left, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSwipeRight() {
                Snackbar.make(mContentView, R.string.swipe_right, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSwipeUp() {
                Snackbar.make(mContentView, R.string.swipe_up, Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onSwipeDown() {
                Snackbar.make(mContentView, R.string.swipe_down, Snackbar.LENGTH_LONG).show();
            }
        });
    }

    private void loadImage(){
        if(mImageIterator.hasNext() && mImageList.size() > 0) {
            Picasso.with(this)
                    .load(mImageIterator.next().getLink())
                    .rotate(90)
                    .fit()
                    .centerInside()
                    .into((ImageView) mContentView);
        } else {
            mImageIterator = mImageList.listIterator();
            loadImage();
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
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
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
