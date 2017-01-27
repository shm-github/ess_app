package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.days_list.days_pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.widget.CardView;
import android.view.ViewGroup;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class DayFragmentPagerAdapter extends FragmentStatePagerAdapter implements CardAdapter {

    private List<DayFragment> mFragments;
    private float mBaseElevation;

    public DayFragmentPagerAdapter(FragmentManager fm, float baseElevation) {
        super(fm);
        mFragments = new ArrayList<>();
        mBaseElevation = baseElevation;

        for(int i = 0; i< 5; i++){
            addCardFragment(DayFragment.getInstance(i));
        }
    }

    @Override
    public float getBaseElevation() {
        return mBaseElevation;
    }

    @Override
    public CardView getCardViewAt(int position) {
        return mFragments.get(position).getCardView();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Object fragment = super.instantiateItem(container, position);
        mFragments.set(position, (DayFragment) fragment);
        return fragment;
    }

    public void addCardFragment(DayFragment fragment) {
        mFragments.add(fragment);
    }

}
