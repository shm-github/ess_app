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
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.ufreedom.uikit.FloatingText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import events.EventPlaySound;
import events.EventPlaySoundEffect;
import events.EventTTSRequest;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.OnSelectListener;
import util.Utils;
import view.multi_choice_view.MultiChoiceView;
import view.multi_choice_view.SingleChoiceAbleView;


public class WordDefinitionMultiChoiceFragment extends Fragment implements View.OnClickListener, OnSelectListener {

    public static final int SHOW_ENGLISH_DEF = 1;
    public static final int SHOW_PERSIAN_DEF = 2;
    private Utils.DayObject correctDayObject;
    private ArrayList<Utils.DayObject> wrongDayObjects;
    private Button button;
    private TextView tv_question;
    private boolean checked = false;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;
    private SingleChoiceAbleView currentSelectedSingleChoice;
    private MultiChoiceView multiChoiceView;
    private int choicesState;


    public WordDefinitionMultiChoiceFragment() {
        // Required empty public constructor
    }


    public static WordDefinitionMultiChoiceFragment newInstance(Utils.DayObject correctDayObject, ArrayList<Utils.DayObject> wrongDayObjects, int state) {
        WordDefinitionMultiChoiceFragment fragment = new WordDefinitionMultiChoiceFragment();
        fragment.setData(correctDayObject, wrongDayObjects, state);
        return fragment;
    }

    private void setData(Utils.DayObject correctDayObject, ArrayList<Utils.DayObject> wrongDayObjects, int state) {
        this.correctDayObject = correctDayObject;
        this.wrongDayObjects = wrongDayObjects;
        this.choicesState = state;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_multi_choice, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFloatText();

        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);

        tv_question = (TextView) view.findViewById(R.id.tv_question);

        if (choicesState == SHOW_PERSIAN_DEF)
            tv_question.setText("ترجمه واژه " + correctDayObject.getWord().getWord() + " چیست؟");

        else if (choicesState == SHOW_ENGLISH_DEF)
            tv_question.setText("کدام مورد توصیف گر واژه " + correctDayObject.getWord().getWord() + " است؟");



        multiChoiceView = (MultiChoiceView) view.findViewById(R.id.multi_choice_view);
        multiChoiceView.setOnSelectListener(this);

        if (choicesState == SHOW_PERSIAN_DEF)
            multiChoiceView.setChoiceAbleItems(correctDayObject, wrongDayObjects, MultiChoiceView.SHOW_PERSIAN_DEFINITION);

        else if (choicesState == SHOW_ENGLISH_DEF)
            multiChoiceView.setChoiceAbleItems(correctDayObject, wrongDayObjects, MultiChoiceView.SHOW_ENGLISH_DEFINITION);

    }


    private void initializeFloatText() {
        floatingPoint = Utils.getFloatingText(getActivity(), Color.LTGRAY, 100, "+1000", 100, 100);

        floatingText = Utils.getFloatingText(getActivity(), Color.GREEN, 100, " آفرین!", 100, 200);

        floatingTextFailed = Utils.getFloatingText(getActivity(), Color.RED, 100, " بیشتر دقت کن!", 100, 100);

        floatingPoint.attach2Window(); // let FloatingText attached to the Window
        floatingText.attach2Window(); // let FloatingText attached to the Window
        floatingTextFailed.attach2Window(); // let FloatingText attached to the Window
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

                            if (choicesState == SHOW_PERSIAN_DEF)
                                EventBus.getDefault().post(new EventPlaySound(correctDayObject.getPronunciation().getFile_name()));

                            else if (choicesState == SHOW_ENGLISH_DEF)
                                EventBus.getDefault().post(new EventTTSRequest(correctDayObject.getWord().getWord() + " means " +correctDayObject.getWord().getEng_def()));

                        }
                    }, 1000);

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
        }
    }

    @Override
    public void onSelect(SingleChoiceAbleView singleChoiceAbleView, Utils.DayObject dayObject) {
        currentSelectedSingleChoice = singleChoiceAbleView;
        button.setEnabled(true);
    }
}
