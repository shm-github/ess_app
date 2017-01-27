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

import events.EventPlayComplete;
import events.EventPlaySound;
import events.EventPlaySoundEffect;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.OnTypeCompleteListener;
import util.Utils;
import view.keyboard.BlankTextView;
import view.keyboard.CustomKeyboard;

import static com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R.id.pv;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SoundTypeWordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SoundTypeWordFragment extends Fragment implements View.OnClickListener , OnTypeCompleteListener{


    private CustomKeyboard customKeyBoard;
    private Utils.DayObject dayObject;
    private Button button;
    private boolean checked = false;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;
    private PulseView pulseView;
    private BlankTextView blankTextView;

    public SoundTypeWordFragment() {
        // Required empty public constructor
    }


    public static SoundTypeWordFragment newInstance(Utils.DayObject dayObject) {
        SoundTypeWordFragment fragment = new SoundTypeWordFragment();
        fragment.setData(dayObject);
        return fragment;
    }

    private void setData(Utils.DayObject dayObject) {
        this.dayObject = dayObject;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sound_type_word, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFloatText();

        pulseView = (PulseView) view.findViewById(R.id.pv);
        pulseView.setOnClickListener(this);

        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);


        customKeyBoard = (CustomKeyboard) view.findViewById(R.id.custom_key_board);
        customKeyBoard.setKeyBoardTextRange(dayObject.getWord().getWord());
        customKeyBoard.setOnTypeCompleteListener(this);

        blankTextView = (BlankTextView) view.findViewById(R.id.blank_text_view);
        blankTextView.setKeyboard(customKeyBoard);
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
                            EventBus.getDefault().post(new EventPlaySound(dayObject.getPronunciation().getFile_name()));
                        }
                    } , 1000);

                    EventBus.getDefault().post(new EventPlaySoundEffect("check.mp3"));
                    EventBus.getDefault().post(new EventUpdateFlag(dayObject.getWord().getWord(), dayObject.getWord().getPer_def()));

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


