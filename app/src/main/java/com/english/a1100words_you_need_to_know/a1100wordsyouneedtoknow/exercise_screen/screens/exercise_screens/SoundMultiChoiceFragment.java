package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.gigamole.library.PulseView;
import com.ufreedom.uikit.FloatingText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import events.EventPlayComplete;
import events.EventPlaySound;
import events.EventPlaySoundEffect;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.OnSelectListener;
import util.Utils;
import view.multi_choice_view.MultiChoiceView;
import view.multi_choice_view.SingleChoiceAbleView;

import static com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R.id.pv;


public class SoundMultiChoiceFragment extends Fragment implements View.OnClickListener, OnSelectListener {

    private Utils.DayObject correctDayObject;
    private ArrayList<Utils.DayObject> wrongDayObjects;
    private Button button;
    private boolean checked = false;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;
    private SingleChoiceAbleView currentSelectedSingleChoice;
    private MultiChoiceView multiChoiceView;
    private PulseView pulseView;

    public SoundMultiChoiceFragment() {
        // Required empty public constructor
    }


    public static SoundMultiChoiceFragment newInstance(Utils.DayObject correctDayObject, ArrayList<Utils.DayObject> wrongDayObjects) {
        SoundMultiChoiceFragment fragment = new SoundMultiChoiceFragment();
        fragment.setData(correctDayObject, wrongDayObjects);
        return fragment;
    }

    private void setData(Utils.DayObject correctDayObject, ArrayList<Utils.DayObject> wrongDayObjects) {
        this.correctDayObject = correctDayObject;
        this.wrongDayObjects = wrongDayObjects;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sound_multi_choice, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFloatText();

        pulseView = (PulseView) view.findViewById(R.id.pv);
        pulseView.setOnClickListener(this);

        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);


        multiChoiceView = (MultiChoiceView) view.findViewById(R.id.multi_choice_view);
        multiChoiceView.setOnSelectListener(this);

        multiChoiceView.setChoiceAbleItems(correctDayObject, wrongDayObjects, MultiChoiceView.SHOW_PERSIAN_DEFINITION);
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    private void initializeFloatText() {
        floatingPoint = Utils.getFloatingText(getActivity(), Color.LTGRAY, 100, "+1000", 100, 100);

        floatingText = Utils.getFloatingText(getActivity(), Color.GREEN, 100, " آفرین!", 100, 200);

        floatingTextFailed = Utils.getFloatingText(getActivity(), Color.RED, 100, " بیشتر دقت کن!", 100, 100);

        floatingPoint.attach2Window(); // let FloatingText attached to the Window
        floatingText.attach2Window(); // let FloatingText attached to the Window
        floatingTextFailed.attach2Window(); // let FloatingText attached to the Window
    }

    @Subscribe
    public void onPlayComplete(EventPlayComplete event){
        if(pulseView != null)
            pulseView.finishPulse();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.check_next:
                if (!checked) {
                    boolean answer = multiChoiceView.checkAnswer();
                    button.setText("Next");
                    checked = true;

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new EventPlaySound(correctDayObject.getPronunciation().getFile_name()));
                        }
                    } , 1000);

                    EventBus.getDefault().post(new EventPlaySoundEffect("check.mp3"));
                    EventBus.getDefault().post(new EventUpdateFlag(correctDayObject.getWord().getWord(), correctDayObject.getWord().getPer_def()));

                    if (answer) {
                        floatingPoint.startFloating(currentSelectedSingleChoice);
                        floatingText.startFloating(currentSelectedSingleChoice);
                    } else {
                        floatingTextFailed.startFloating(currentSelectedSingleChoice);
                    }

                } else
                    EventBus.getDefault().post(new EventUpdateProgress());
                break;


            case pv:
                if (correctDayObject.getPronunciation() != null) {

                    if (pulseView != null)
                        pulseView.startPulse();

                    EventBus.getDefault().post(new EventPlaySound(correctDayObject.getPronunciation().getFile_name()));
                }

                break;
        }
    }

    @Override
    public void onSelect(SingleChoiceAbleView singleChoiceAbleView, Utils.DayObject dayObject) {
        currentSelectedSingleChoice = singleChoiceAbleView;
        button.setEnabled(true);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
