package me.pullar.tigur.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import java.util.List;

import butterknife.Bind;
import me.pullar.tigur.R;
import me.pullar.tigur.api.ImgurApi;
import me.pullar.tigur.api.RestClient;
import me.pullar.tigur.api.model.Image;
import me.pullar.tigur.api.model.Images;
import me.pullar.tigur.ui.adapter.ImageAdapter;
import me.pullar.tigur.ui.fragment.ImageFragment;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static me.pullar.tigur.ui.adapter.ImageAdapter.OnItemClickListener;

public class MainActivity extends AppCompatActivity implements ImageFragment.OnFragmentInteractionListener {
    private static final String LIST_STATE_KEY = "image_list_key";

    private static Context mContext;

    private ImgurApi imgurApi;
    private Call<Images> getImages;

    private boolean mVisible;
    private ImageAdapter mImageAdapter;
    private List<Image> mImageList;
    private RecyclerView mRvImageContent;
    private LinearLayoutManager mLinearLayoutManager;
    private Parcelable mListState;
    private static FragmentManager mFragmentManager;
    private StaggeredGridLayoutManager mGridLayoutManager;
    private boolean mImageFragmentVisible;
    private ImageFragment imageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        setContentView(R.layout.activity_main);
        mRvImageContent = (RecyclerView) findViewById(R.id.rv_image_content);

        imgurApi = RestClient.getClient();
        getImages = imgurApi.getImages();

        mLinearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        mGridLayoutManager = new StaggeredGridLayoutManager(2, GridLayoutManager.VERTICAL);
        mGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mImageFragmentVisible = false;

        loadImages();
    }

    public static Context getContext() {
        return mContext;
    }

    public static FragmentManager getmFragmentManager() {
        return mFragmentManager;
    }

    private void loadImages(){
        getImages.enqueue(new Callback<Images>() {
            @Override
            public void onResponse(Response<Images> response, Retrofit retrofit) {
                Log.d("MainActivity", "Response Code = " + response.code());
                Log.d("MainActivity", "Response Body = " + response.body().getImages());
                if (response.isSuccess()) {
                    mImageAdapter = new ImageAdapter(response.body());
                    chooseLayoutManager();
                    mImageAdapter.setOnItemClickListener(new OnItemClickListener() {
                        @Override
                        public void onItemClick() {
                            imageFragment = ImageFragment.newInstance("this");
                            toggleImageFragment();
                        }
                    });

                    mRvImageContent.setAdapter(mImageAdapter);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("MainActivity", t.getMessage());
            }
        });
    }

    private void toggleImageFragment() {

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (mImageFragmentVisible) {
            ft.remove(imageFragment);
        } else {
            ft.replace(android.R.id.content, imageFragment).addToBackStack(null);
        }
        ft.commit();
        mImageFragmentVisible = !mImageFragmentVisible;

    }

    @Override
    public FragmentManager getFragmentManager() {
        mFragmentManager = super.getFragmentManager();
        return super.getFragmentManager();
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);

        mListState = mRvImageContent.getLayoutManager().onSaveInstanceState();
        state.putParcelable(LIST_STATE_KEY, mListState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);

        mListState = state.getParcelable(LIST_STATE_KEY);
        if (mListState != null) {
            mRvImageContent.getLayoutManager().onRestoreInstanceState(mListState);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void chooseLayoutManager() {
        int orientation = getResources().getConfiguration().orientation;

        // Gets orientation from resources. Vertical = 1, Horizontal = 2
        if (orientation == 1) {
            mRvImageContent.setLayoutManager(mLinearLayoutManager);
        } else if (orientation == 2) {
            mRvImageContent.setLayoutManager(mGridLayoutManager);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration config) {
        super.onConfigurationChanged(config);
        chooseLayoutManager();
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
    }

}
