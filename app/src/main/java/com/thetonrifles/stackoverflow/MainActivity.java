package com.thetonrifles.stackoverflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.thetonrifles.stackoverflow.controls.PagerIndicatorView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private static final String[] URLS = {
            "http://www.thirdyearabroad.com/images/stories/reviews/Cities/rome.jpg",
            "http://www.omicsgroup.com/conferences/ACS/conference/photos/5505-CityGuideImage.jpg",
            "https://www.raileurope.com/cms-images/499/398/italy-rome-colosseum.jpg"
    };

    private ViewPager mPager;
    private PagerIndicatorView mIndicatorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIndicatorView = (PagerIndicatorView) findViewById(R.id.indicator);
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.addOnPageChangeListener(this);
        mPager.setAdapter(new ImagePagerAdapter(getSupportFragmentManager()));
        mIndicatorView.setPagesNumber(URLS.length, mPager.getCurrentItem());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mIndicatorView.setPagesNumber(URLS.length, mPager.getCurrentItem());
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ImagePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return ImageFragment.newInstance(URLS[position]);
        }

        @Override
        public int getCount() {
            return URLS.length;
        }

    }

}