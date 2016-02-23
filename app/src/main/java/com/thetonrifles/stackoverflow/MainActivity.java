package com.thetonrifles.stackoverflow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final String[] URLS = {
            "http://www.thirdyearabroad.com/images/stories/reviews/Cities/rome.jpg",
            "http://www.omicsgroup.com/conferences/ACS/conference/photos/5505-CityGuideImage.jpg",
            "https://www.raileurope.com/cms-images/499/398/italy-rome-colosseum.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new ImagePagerAdapter(getSupportFragmentManager()));
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