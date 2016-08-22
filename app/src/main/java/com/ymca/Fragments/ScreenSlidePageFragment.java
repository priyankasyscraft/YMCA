/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ymca.Fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ymca.AppManager.DataManager;
import com.ymca.ImageCache.ImageLoader;
import com.ymca.ModelClass.SliderModelClass;
import com.ymca.R;

/**
 * A fragment representing a single step in a wizard. The fragment shows a dummy title indicating
 * the page number, along with some dummy text.
 * <p/>
 * <p>This class is used by the {@link CardFlipActivity} and {@link
 * ScreenSlideActivity} samples.</p>
 */
@SuppressWarnings("ALL")

@SuppressLint("ValidFragment")
public class ScreenSlidePageFragment extends Fragment {
    /**
     * The argument key for the page number this fragment represents.
     */
    public static final String ARG_PAGE = "page";

    /**
     * The fragment's page number, which is set to the argument value for {@link #ARG_PAGE}.
     */
    private int mPageNumber;

    private ArrayList<SliderModelClass> banner_url;

    private Context context;

    private int pos;
    private ImageLoader imageLoader;

    /**
     * Factory method for this fragment class. Constructs a new fragment for the given page number.
     */
//    
//    public static ScreenSlidePageFragment create(int pageNumber) {
//        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, pageNumber);
//        fragment.setArguments(args);
//        return fragment;
//    }
    public ScreenSlidePageFragment() {
    }

    public ScreenSlidePageFragment(ArrayList<SliderModelClass> bannerImage,
                                   FragmentActivity activity, int position) {

        this.banner_url = bannerImage;
        this.context = activity;
        this.pos = position;
        imageLoader = new ImageLoader(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.image_viewpager_layout, container, false);

        ImageView imgSlider = (ImageView) rootView.findViewById(R.id.viewPagerItem_image1);
        if (context != null && banner_url.get(pos) != null && !banner_url.get(pos).equals("")) {

            Glide.with(context)
                    .load(banner_url.get(pos).getSliderImgUrl())
                    .into(imgSlider);
            DataManager.getInstance().hideProgressMessage();
        }
        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
