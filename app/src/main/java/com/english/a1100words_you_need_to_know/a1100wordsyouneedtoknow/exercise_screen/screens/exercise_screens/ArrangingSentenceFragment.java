package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.ufreedom.uikit.FloatingText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import database.Sentence;
import events.EventPlaySoundEffect;
import events.EventTTSRequest;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.OnViewClickListener;
import util.Utils;
import view.arranging_sentence.ArrangingSentenceView;


public class ArrangingSentenceFragment extends Fragment implements View.OnClickListener {


    private ArrangingSentenceView mainSentenceView;
    private ArrangingSentenceView viewsHolderSentenceView;
    private Sentence sentence;
    private Button button;
    private boolean checked = false;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;

    public ArrangingSentenceFragment() {
        // Required empty public constructor
    }


    public static ArrangingSentenceFragment newInstance(Sentence sentence) {
        ArrangingSentenceFragment fragment = new ArrangingSentenceFragment();
        fragment.setSentence(sentence);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_arranging_sentence, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);

        initializeFloatText();

        mainSentenceView = (ArrangingSentenceView) view.findViewById(R.id.arrange_sentence_view);
        mainSentenceView.setOnViewClickListener(new OnViewClickListener() {
            @Override
            public void onViewClick(View view) {
                viewsHolderSentenceView.add(view , (LinearLayout.LayoutParams) view.getLayoutParams());
                checkIfAllWordsArranged();

            }
        });



        viewsHolderSentenceView = (ArrangingSentenceView) view.findViewById(R.id.not_arrange_sentence_view);
        viewsHolderSentenceView.setOnViewClickListener(new OnViewClickListener() {
            @Override
            public void onViewClick(View view) {
                mainSentenceView.add(view , (LinearLayout.LayoutParams) view.getLayoutParams());
                checkIfAllWordsArranged();

            }
        });




        ArrayList<String> list = new ArrayList<>( Arrays.asList( sentence.getSentence_eng().split(" ") ) );
        Collections.shuffle(list);
        TextView textView;
        for (int i = 0; i < list.size() ; i++) {
            textView = new TextView(getContext());
            textView.setText(list.get(i));
            textView.setMaxLines(1);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            textView.setTextColor(Color.WHITE);
            textView.setBackgroundResource(R.drawable.single_choice_selected_correct);
            textView.setPadding(Utils.convertDpToPx(getContext(), 10), Utils.convertDpToPx(getContext(), 10), Utils.convertDpToPx(getContext(), 10), Utils.convertDpToPx(getContext(), 10));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.leftMargin = Utils.convertDpToPx(getContext(), 3);
            layoutParams.rightMargin = Utils.convertDpToPx(getContext(), 3);
            textView.setLayoutParams(layoutParams);

            viewsHolderSentenceView.add(textView, layoutParams);
        }
    }

    private void checkIfAllWordsArranged() {
       if(viewsHolderSentenceView.getViewsCount() == 0)
           button.setEnabled(true);
        else
           button.setEnabled(false);

    }


    public void setSentence(Sentence sentence) {
        this.sentence = sentence;
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
                    boolean answer = mainSentenceView.checkAnswer( sentence.getSentence_eng() );
                    button.setText("Next");
                    checked = true;

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new EventTTSRequest( sentence.getSentence_eng() ));
                        }
                    }, 1000);

                    EventBus.getDefault().post(new EventPlaySoundEffect("check.mp3"));
                    EventBus.getDefault().post(new EventUpdateFlag(sentence.getSentence_eng() , sentence.getSentence_per()));

                    if (answer) {
                        floatingPoint.startFloating(mainSentenceView);
                        floatingText.startFloating(mainSentenceView);
                    } else {
                        floatingTextFailed.startFloating(mainSentenceView);
                    }

                } else
                    EventBus.getDefault().post(new EventUpdateProgress());
                break;


        }
    }



}
