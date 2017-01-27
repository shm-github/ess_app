package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager;


import android.support.v7.widget.CardView;

public interface CardAdapter {

    int MAX_ELEVATION_FACTOR = 8;

    float getBaseElevation();

    CardView getCardViewAt(int position);

    int getCount();
}
