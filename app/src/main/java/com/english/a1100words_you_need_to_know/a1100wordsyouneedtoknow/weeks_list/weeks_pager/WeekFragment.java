package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.days_list.DaysListActivity;

import database.Week;


public class WeekFragment extends Fragment implements View.OnClickListener {

    private CardView mCardView;
    private Button mStartButton;
    private Week week;
    private int weekId;
    private RatingBar mRatingBar;
    private TextView mProgress;
    private ImageView mLock;
    private TextView mTitle;


    public static WeekFragment getInstance(Week week){
        WeekFragment fragment = new WeekFragment();
        fragment.setWeek(week);
        return fragment ;
    }

    public static WeekFragment getInstance(int week_id) {
        WeekFragment fragment = new WeekFragment();
        fragment.setWeekId(week_id);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_week, container, false);
        mCardView = (CardView) view.findViewById(R.id.cardView);
        mStartButton = (Button) view.findViewById(R.id.start_week);
        mRatingBar = (RatingBar) view.findViewById(R.id.week_rate);
        mProgress = (TextView) view.findViewById(R.id.tv_week_progress);
        mLock = (ImageView) view.findViewById(R.id.iv_week_lock);
        mTitle = (TextView) view.findViewById(R.id.week_title);
        mStartButton.setOnClickListener(this);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(weekId <= 2)
            mRatingBar.setVisibility(View.VISIBLE);
        if(weekId == 3 )
            mProgress.setVisibility(View.VISIBLE);
        if(weekId > 3 )
            mLock.setVisibility(View.VISIBLE);

        mTitle.setText("Week " + (weekId+1) );

        mRatingBar.setNumStars(3);
        mRatingBar.setRating(weekId % 3 );
        mRatingBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
    }

    public CardView getCardView() {
        return mCardView;
    }

    public void setWeek(Week week){
        this.week = week ;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.start_week:
                Intent intent = new Intent(getContext() , DaysListActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void setWeekId(int weekId) {
        this.weekId = weekId;
    }
}
