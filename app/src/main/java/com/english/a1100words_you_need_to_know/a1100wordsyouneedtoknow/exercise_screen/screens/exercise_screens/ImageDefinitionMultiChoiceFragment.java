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
import com.ufreedom.uikit.FloatingText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import events.EventPlaySound;
import events.EventPlaySoundEffect;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.OnSelectListener;
import util.Utils;
import view.multi_choice_view.MultiChoiceView;
import view.multi_choice_view.SingleChoiceAbleView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImageDefinitionMultiChoiceFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageDefinitionMultiChoiceFragment extends Fragment implements View.OnClickListener, OnSelectListener {


    private Utils.DayObject correctDayObject;
    private ArrayList<Utils.DayObject> wrongDayObjects;
    private Button button;
    private boolean checked = false;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;
    private SingleChoiceAbleView currentSelectedSingleChoice;
    private MultiChoiceView multiChoiceView;

    public ImageDefinitionMultiChoiceFragment() {
        // Required empty public constructor
    }



    public static ImageDefinitionMultiChoiceFragment newInstance(Utils.DayObject correctDayObject, ArrayList<Utils.DayObject> wrongDayObjects) {
        ImageDefinitionMultiChoiceFragment fragment = new ImageDefinitionMultiChoiceFragment();
        fragment.setData(correctDayObject , wrongDayObjects);
        return fragment;
    }

    private void setData(Utils.DayObject correctDayObject, ArrayList<Utils.DayObject> wrongDayObjects) {
        this.correctDayObject = correctDayObject ;
        this.wrongDayObjects = wrongDayObjects;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_definition_multi_choice, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFloatText();

        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);

        multiChoiceView = (MultiChoiceView) view.findViewById(R.id.multi_choice_view);
        multiChoiceView.setOnSelectListener(this);

        CircleImageView circleImageView = (CircleImageView) view.findViewById(R.id.img);
        Utils.loadImageToIvFromPic(correctDayObject.getPic().getFile_name() , circleImageView);

        multiChoiceView.setChoiceAbleItems(correctDayObject , wrongDayObjects , MultiChoiceView.SHOW_WORD);
    }

    private void initializeFloatText() {
        floatingPoint =  Utils.getFloatingText(getActivity() , Color.LTGRAY , 100 , "+1000" ,100 , 100 );

        floatingText = Utils.getFloatingText(getActivity() , Color.GREEN , 100 , " آفرین!" ,100 , 200 );

        floatingTextFailed = Utils.getFloatingText(getActivity() , Color.RED , 100 , " بیشتر دقت کن!" ,100 , 100 );

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
                            EventBus.getDefault().post(new EventPlaySound(correctDayObject.getPronunciation().getFile_name()));
                        }
                    } , 1000);

                    EventBus.getDefault().post(new EventPlaySoundEffect("check.mp3"));
                    EventBus.getDefault().post(new EventUpdateFlag(correctDayObject.getWord().getWord() , correctDayObject.getWord().getPer_def()));

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
        currentSelectedSingleChoice = singleChoiceAbleView ;
        button.setEnabled(true);
    }
}
