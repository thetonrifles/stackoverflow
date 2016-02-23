package com.thetonrifles.stackoverflow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thetonrifles.stackoverflow.volley.ImageUtils;
import com.thetonrifles.stackoverflow.volley.MyImageView;

public class ImageFragment extends Fragment {

    private MyImageView mImageView;

    public static ImageFragment newInstance(String url) {
        ImageFragment fragment = new ImageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("image_url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_image, container, false);
        mImageView = (MyImageView) layout.findViewById(R.id.img_photo);
        return layout;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String url = getArguments().getString("image_url");
        ImageUtils.getInstance().setPic(mImageView, url);
    }

}
