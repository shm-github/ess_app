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

import database.Pic;
import database.Word;
import events.EventPlaySound;
import events.EventPlaySoundEffect;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.ImageSelectListener;
import util.Utils;
import view.selectable_image_group.CheckableImageGroup;
import view.selectable_image_group.SelectableImage;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordImageGroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordImageGroupFragment extends Fragment implements View.OnClickListener, ImageSelectListener {

    private ArrayList<Utils.DayObject> falseDayObjects;
    private Button button;
    private CheckableImageGroup checkableImageGroup;
    private boolean checked = false;
    private TextView title;
    private Utils.DayObject correctDayObject;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;
    private SelectableImage currentSelectedImage;

    public WordImageGroupFragment() {
        // Required empty public constructor
    }


    public static WordImageGroupFragment newInstance(Utils.DayObject word, ArrayList<Utils.DayObject> falseWordIds) {
        WordImageGroupFragment fragment = new WordImageGroupFragment();
        fragment.setDayObject(word, falseWordIds);
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
        return inflater.inflate(R.layout.fragment_word_image_group, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Pic> pics = new ArrayList<>();

        Word correctWord = correctDayObject.getWord();


        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);

        title = (TextView) view.findViewById(R.id.vocabulary);
        title.setText(correctWord.getWord());

        initializeFloatText();

        checkableImageGroup = (CheckableImageGroup) view.findViewById(R.id.checkableImageGroup);

        checkableImageGroup.setImageStateListener(this);


        pics.add( correctDayObject.getPic() );

        for (int i = 0; i < falseDayObjects.size(); i++)
            pics.add( falseDayObjects.get(i).getPic() );

        checkableImageGroup.setImagesList(pics, correctWord);
    }


    private void initializeFloatText() {
        floatingPoint =  Utils.getFloatingText(getActivity() , Color.LTGRAY , 100 , "+1000" ,100 , 100 );

        floatingText = Utils.getFloatingText(getActivity() , Color.GREEN , 100 , " آفرین!" ,100 , 200 );

        floatingTextFailed = Utils.getFloatingText(getActivity() , Color.RED , 100 , " بیشتر دقت کن!" ,100 , 100 );

        floatingPoint.attach2Window(); // let FloatingText attached to the Window
        floatingText.attach2Window(); // let FloatingText attached to the Window
        floatingTextFailed.attach2Window(); // let FloatingText attached to the Window
    }


    public void setDayObject(Utils.DayObject word, ArrayList<Utils.DayObject> falseWordIds) {
        this.correctDayObject = word;
        this.falseDayObjects = falseWordIds;
    }

    @Override
    public void onItemSelect(long word_id, SelectableImage selectableImage) {
        currentSelectedImage = selectableImage;
        button.setEnabled(true);
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
                            EventBus.getDefault().post(new EventPlaySound(correctDayObject.getPronunciation().getFile_name()));
                        }
                    } , 1000);

                    EventBus.getDefault().post(new EventPlaySoundEffect("check.mp3"));
                    EventBus.getDefault().post(new EventUpdateFlag(correctDayObject.getWord().getWord() , correctDayObject.getWord().getPer_def()));

                    if (answer) {
                        floatingPoint.startFloating(currentSelectedImage);
                        floatingText.startFloating(currentSelectedImage);
                    } else {
                        floatingTextFailed.startFloating(currentSelectedImage);
                    }

                } else
                    EventBus.getDefault().post(new EventUpdateProgress());

                break;
        }
    }


}
