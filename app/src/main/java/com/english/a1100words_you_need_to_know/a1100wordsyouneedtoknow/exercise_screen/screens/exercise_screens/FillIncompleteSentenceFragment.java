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
import com.gigamole.library.PulseView;
import com.ufreedom.uikit.FloatingText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import events.EventPlayComplete;
import events.EventPlaySound;
import events.EventPlaySoundEffect;
import events.EventTTSRequest;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.OnTypeCompleteListener;
import util.Utils;
import view.keyboard.BlankTextView;
import view.keyboard.CustomKeyboard;

import static com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R.id.pv;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FillIncompleteSentenceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FillIncompleteSentenceFragment extends Fragment implements View.OnClickListener, OnTypeCompleteListener {

    private Utils.DayObject dayObject;
    private CustomKeyboard customKeyBoard;
    private Button button;
    private boolean checked = false;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;
    private PulseView pulseView;
    private BlankTextView blankTextView;
    private TextView tv_question;


    public FillIncompleteSentenceFragment() {
        // Required empty public constructor
    }


    public static FillIncompleteSentenceFragment newInstance(Utils.DayObject dayObject) {
        FillIncompleteSentenceFragment fragment = new FillIncompleteSentenceFragment();
        fragment.setDayObject(dayObject);
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
        return inflater.inflate(R.layout.fragment_fill_incomplete_sentence, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initializeFloatText();

        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);

        String[] strings = dayObject.getSentences().get(0).getSentence_eng().split(dayObject.getSentences().get(0).getDeleted_word());

        StringBuilder builder = new StringBuilder();
        builder.append(strings[0]);
        builder.append("_____");
        builder.append(strings[1]);


        tv_question = (TextView) view.findViewById(R.id.tv_question);
        tv_question.setText(builder.toString());


        customKeyBoard = (CustomKeyboard) view.findViewById(R.id.custom_key_board);
        customKeyBoard.setKeyBoardTextRange(dayObject.getSentences().get(0).getDeleted_word());
        customKeyBoard.setOnTypeCompleteListener(this);

        blankTextView = (BlankTextView) view.findViewById(R.id.blank_text_view);
        blankTextView.setKeyboard(customKeyBoard);
    }


    public void setDayObject(Utils.DayObject dayObject) {
        this.dayObject = dayObject;
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
    public void onPlayComplete(EventPlayComplete event) {
        if (pulseView != null)
            pulseView.finishPulse();
    }


    @Override
    public void onTypeComplete(boolean state) {
        button.setEnabled(state);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.check_next:
                if (!checked) {
                    boolean answer = customKeyBoard.checkAnswer();
                    button.setText("Next");
                    checked = true;

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            EventBus.getDefault().post(new EventTTSRequest(dayObject.getSentences().get(0).getSentence_eng()));
                        }
                    }, 1000);

                    EventBus.getDefault().post(new EventPlaySoundEffect("check.mp3"));
                    EventBus.getDefault().post(new EventUpdateFlag(dayObject.getSentences().get(0).getSentence_eng(), dayObject.getSentences().get(0).getSentence_per()));

                    if (answer) {
                        floatingPoint.startFloating(blankTextView);
                        floatingText.startFloating(blankTextView);
                    } else {
                        floatingTextFailed.startFloating(blankTextView);
                    }

                } else
                    EventBus.getDefault().post(new EventUpdateProgress());
                break;


            case pv:
                if (dayObject.getPronunciation() != null) {

                    if (pulseView != null)
                        pulseView.startPulse();

                    EventBus.getDefault().post(new EventPlaySound(dayObject.getPronunciation().getFile_name()));
                }

                break;
        }
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
