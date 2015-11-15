package me.pullar.tigur.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.support.v7.internal.view.menu.ActionMenuItemView;
import android.widget.ImageView;

import java.util.List;

import me.pullar.tigur.R;
import me.pullar.tigur.api.ImgurApi;
import me.pullar.tigur.api.RestClient;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;
import me.pullar.tigur.ui.adapter.ImageAdapter;
import me.pullar.tigur.ui.fragment.ImageFragment;
import me.pullar.tigur.ui.fragment.SubredditDialogFragment;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity
        implements ImageFragment.OnFragmentInteractionListener, ViewTreeObserver.OnScrollChangedListener {
    private static final String BUNDLE_RECYCLER_LAYOUT = "MainActivity.recycler.layout";

    private static Context mContext;

    private ImgurApi imgurApi;
    private Call<Images> getImages;

    private boolean mVisible;
    private ImageAdapter mImageAdapter;
    private List<Image> mImageList;
    private RecyclerView mRvImageContent;
    private LinearLayoutManager mLinearLayoutManager;
    private static FragmentManager mFragmentManager;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private boolean mImageFragmentVisible;
    private ImageFragment imageFragment;
    private SwipeRefreshLayout swipeRefresh;
    private Toolbar mToolBar;
    private float mActionBarHeight;
    private boolean mSubredditDialogVisible;
    private ImageView mRefreshIcon;
    private Menu mMenu;
    private MenuItem mMenuRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        mImageFragmentVisible = false;
        mSubredditDialogVisible = false;

        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolBar);

        mRvImageContent = (RecyclerView) findViewById(R.id.rv_image_content);
        mRefreshIcon = (ImageView) findViewById(R.id.action_refresh);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);

        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mGridLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        mGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        imgurApi = RestClient.getClient();
        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRvImageContent.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        } else {
            loadImages();
        }

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshImages();
            }
        });

        mRvImageContent.getViewTreeObserver().addOnScrollChangedListener(this);

    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRvImageContent.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    public static Context getContext() {
        return mContext;
    }

    public static FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    private void loadImages() {
        getImages = imgurApi.getImages();
        getImages.enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Response<Images> response, Retrofit retrofit) {
                Log.d("MainActivity", "Response Code = " + response.code());
                Log.d("MainActivity", "Response Body = " + response.body().getImages());
                if (response.isSuccess()) {
                    mImageAdapter = new ImageAdapter(response.body());
                    mRvImageContent.setLayoutManager(chooseLayoutManager());
                    mRvImageContent.setAdapter(mImageAdapter);

                    // Get our refresh item from the menu
                    mMenuRefresh = mMenu.findItem(R.id.action_refresh);
                    if (mMenuRefresh.getActionView() != null) {
                        // Remove the animation.
                        mMenuRefresh.getActionView().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mMenuRefresh.getActionView().clearAnimation();
                                mMenuRefresh.setActionView(null);
                            }
                        }, R.integer.anim_refresh_duration);
                    }

                    // Reset swipe refresh state
                    swipeRefresh.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });
    }

    private void refreshImages() {
        getImages.cancel();
        getImages = imgurApi.getImages();

        loadImages();
    }

    @Override
    public FragmentManager getFragmentManager() {
        mFragmentManager = super.getFragmentManager();
        return super.getFragmentManager();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private RecyclerView.LayoutManager chooseLayoutManager() {
        int orientation = getResources().getConfiguration().orientation;

        // Gets orientation from resources. Vertical = 1, Horizontal = 2
        if (orientation == 1) {
//            mRvImageContent.setLayoutManager(mLinearLayoutManager);
            return mLinearLayoutManager;
        } else if (orientation == 2) {
//            mRvImageContent.setLayoutManager(mGridLayoutManager);
            return mGridLayoutManager;
        }
        return null;
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        mRvImageContent.setLayoutManager(chooseLayoutManager());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRvImageContent.getLayoutManager().onSaveInstanceState());
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }
        mImageFragmentVisible = !mImageFragmentVisible;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.image_menu, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_refresh:
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                ImageView iv = (ImageView)inflater.inflate(R.layout.iv_refresh, null);
                Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refresh);
                rotation.setRepeatCount(Animation.INFINITE);
                iv.startAnimation(rotation);
                item.setActionView(iv);
                mRvImageContent.smoothScrollToPosition(0);
                refreshImages();
                break;
            case R.id.menu_choose_subreddit:
                Snackbar.make(findViewById(android.R.id.content), R.string.on_click, Snackbar.LENGTH_LONG).show();
                showSubredditDialog();
                break;
            case R.id.menu_about:
                Snackbar.make(findViewById(android.R.id.content), R.string.on_click, Snackbar.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    private void showSubredditDialog() {
        if (!mSubredditDialogVisible) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(android.R.id.content, new SubredditDialogFragment(), null);
            ft.commit();
        }
        mSubredditDialogVisible = !mSubredditDialogVisible;
    }

    @Override
    public void onScrollChanged() {
    }

}
