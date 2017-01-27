package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.adapter.ExercisePagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import events.EventPlaySoundEffect;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import view.FloatingFlag;
import view.NonScrollableViewPager;


public class ExercisePagerFragment extends Fragment {

    private NumberProgressBar progressBar;
    private NonScrollableViewPager viewPager;
    private ExercisePagerAdapter exercisePagerAdapter;
    private FloatingFlag floatingFlag;

    public ExercisePagerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pager_exercise, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = (NumberProgressBar) view.findViewById(R.id.numberProgressBar);
        progressBar.setMax(100);

        floatingFlag = (FloatingFlag) view.findViewById(R.id.flag);


        viewPager = (NonScrollableViewPager) view.findViewById(R.id.exercise_viewpager);
        exercisePagerAdapter = new ExercisePagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(exercisePagerAdapter);
        viewPager.setOffscreenPageLimit(1);
    }


    @Subscribe
    public void updateProgress(EventUpdateProgress event) {

        int progressStep = progressBar.getMax() / exercisePagerAdapter.getCount();

        if (exercisePagerAdapter.getCount() > (viewPager.getCurrentItem() + 1)) {
            progressBar.setProgress(progressBar.getProgress() + progressStep );
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1 , true);
            floatingFlag.setVisibility(View.GONE);

            EventBus.getDefault().post(new EventPlaySoundEffect("next.mp3"));

        }
    }


    @Subscribe
    public void showFlag(EventUpdateFlag event) {
        floatingFlag.setData(event.getVocabulary(), event.getDefinition());
        floatingFlag.setVisibility(View.VISIBLE);


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


}
