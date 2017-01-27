package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.ExercisePagerFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.VocabulariesPagerFragment;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class MainPagerAdapter extends FragmentStatePagerAdapter {

    private final static int COUNT = 3;

    private final static int VOCABULARIES = 0;
    private final static int EXERCISE = 1;

    public MainPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(final int position) {
        switch (position) {
            case EXERCISE:
                return new ExercisePagerFragment();
            case VOCABULARIES:
            default:
                return new VocabulariesPagerFragment();
        }
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
