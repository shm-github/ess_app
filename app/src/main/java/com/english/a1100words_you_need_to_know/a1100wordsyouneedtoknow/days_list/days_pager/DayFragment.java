package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.days_list.days_pager;

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
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.ExerciseActivity;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.CardAdapter;

import database.Date;


public class DayFragment extends Fragment implements View.OnClickListener {

    private CardView mCardView;
    private Button mButton;
    private Date day;
    private int dayId;
    private RatingBar mRatingBar;
    private TextView mProgress;
    private ImageView mLock;
    private TextView mTitle;

    public static DayFragment getInstance(Date date){
        DayFragment fragment = new DayFragment();
        fragment.setDate(date);
        return fragment ;
    }

    public static DayFragment getInstance(int date_id) {
        DayFragment fragment = new DayFragment();
        fragment.setDateId(date_id);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        mCardView = (CardView) view.findViewById(R.id.day_cardView);
        mButton = (Button) view.findViewById(R.id.start_day);
        mRatingBar = (RatingBar) view.findViewById(R.id.day_rate);
        mProgress = (TextView) view.findViewById(R.id.tv_day_progress);
        mLock = (ImageView) view.findViewById(R.id.iv_day_lock);
        mTitle = (TextView) view.findViewById(R.id.day_title);
        mButton.setOnClickListener(this);
        mCardView.setMaxCardElevation(mCardView.getCardElevation()
                * CardAdapter.MAX_ELEVATION_FACTOR);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(dayId <= 2)
            mRatingBar.setVisibility(View.VISIBLE);
        if(dayId == 3 )
            mProgress.setVisibility(View.VISIBLE);
        if(dayId > 3 )
            mLock.setVisibility(View.VISIBLE);

        if(dayId < 4)
            mTitle.setText("Day " + (dayId+1) );

        else if(dayId == 4)
            mTitle.setText("Day " + (dayId+1) +" : Review" );


        mRatingBar.setNumStars(3);
        mRatingBar.setRating(dayId % 3 );
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

    public void setDate(Date day){
        this.day = day ;
    }

    public void setDateId(int weekId) {
        this.dayId = weekId;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start_day:
                Intent intent = new Intent(getContext(), ExerciseActivity.class);
                startActivity(intent);
                break;
        }
    }
}
