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
import android.widget.ImageView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.gigamole.library.PulseView;
import com.ufreedom.uikit.FloatingText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import database.Pic;
import events.EventPlayComplete;
import events.EventPlaySound;
import events.EventPlaySoundEffect;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.ImageSelectListener;
import util.Utils;
import view.selectable_image_group.CheckableImageGroup;
import view.selectable_image_group.SelectableImage;

import static com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R.id.pv;

public class SoundImageGroupFragment extends Fragment implements View.OnClickListener, ImageSelectListener {

    Utils.DayObject dayObject;
    private ArrayList<Utils.DayObject> falseWords;
    private ArrayList<Utils.DayObject> falseDayObjects;
    private Button button;
    private CheckableImageGroup checkableImageGroup;
    private boolean checked = false;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;
    private SelectableImage currentSelectedImage;
    private ImageView iv_playSound;
    private PulseView pulseView;

    public SoundImageGroupFragment() {
        // Required empty public constructor
    }


    public static SoundImageGroupFragment newInstance(Utils.DayObject dayObject, ArrayList<Utils.DayObject> falseWords) {
        SoundImageGroupFragment fragment = new SoundImageGroupFragment();
        fragment.setData(dayObject, falseWords);
        return fragment;
    }

    private void setData(Utils.DayObject dayObject, ArrayList<Utils.DayObject> falseWords) {
        this.dayObject = dayObject;
        this.falseWords = falseWords;
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sound_image_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFloatText();

         pulseView = (PulseView) view.findViewById(pv);
        pulseView.setOnClickListener(this);

//        pulseView.setPulseColor(Color.BLACK);
//        pulseView.setPulseCount(5);
//        pulseView.setPulseMeasure(PulseView.PulseMeasure.WIDTH);
//        pulseView.setIconHeight(200);
//        pulseView.setIconWidth(200);
////        pulseView.setIconRes(R.drawable.icon);
//        pulseView.setPulseAlpha(70);
//        pulseView.setInterpolator(new LinearInterpolator());
//        pulseView.setPulseListener(new PulseView.PulseListener(...));

        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);

//        iv_playSound = (ImageView) view.findViewById(R.id.play_sound);
//        iv_playSound.setOnClickListener(this);

        checkableImageGroup = (CheckableImageGroup) view.findViewById(R.id.checkableImageGroup);
        checkableImageGroup.setImageStateListener(this);


        ArrayList<Pic> pics = new ArrayList<>();

        pics.add(dayObject.getPic());
        for (int i = 0; i < falseWords.size(); i++)
            pics.add(falseWords.get(i).getPic());

        checkableImageGroup.setImagesList(pics, dayObject.getWord());
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
    public void onItemSelect(long word_id, SelectableImage selectableImage) {
        currentSelectedImage = selectableImage;
        button.setEnabled(true);
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
                    boolean answer = checkableImageGroup.checkAnswer();
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
                        floatingPoint.startFloating(currentSelectedImage);
                        floatingText.startFloating(currentSelectedImage);
                    } else {
                        floatingTextFailed.startFloating(currentSelectedImage);
                    }

                } else
                    EventBus.getDefault().post(new EventUpdateProgress());

                break;

            case pv:
                if(dayObject.getPronunciation() != null){

                    if(pulseView != null)
                        pulseView.startPulse();

                    EventBus.getDefault().post(new EventPlaySound( dayObject.getPronunciation().getFile_name() ));
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
