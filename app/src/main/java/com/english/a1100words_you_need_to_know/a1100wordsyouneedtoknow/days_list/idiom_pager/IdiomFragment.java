package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.days_list.idiom_pager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.CardAdapter;


public class IdiomFragment extends Fragment {

    private CardView mCardView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_idiom, container, false);
        mCardView = (CardView) view.findViewById(R.id.cardView);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    public CardView getCardView() {
        return mCardView;
    }
}
