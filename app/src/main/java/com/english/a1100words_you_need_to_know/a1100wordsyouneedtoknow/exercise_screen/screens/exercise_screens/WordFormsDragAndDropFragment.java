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

import events.EventPlaySoundEffect;
import events.EventUpdateFlag;
import events.EventUpdateProgress;
import model.OnDragAblesArrangeListener;
import util.Utils;
import view.drag_drop_view.DragAndDropView;
import view.drag_drop_view.FalseHolderView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WordFormsDragAndDropFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WordFormsDragAndDropFragment extends Fragment implements View.OnClickListener , OnDragAblesArrangeListener {

    private Utils.DayObject dayObject;
    private Button button;
    private boolean checked = false;
    private FloatingText floatingPoint;
    private FloatingText floatingText;
    private FloatingText floatingTextFailed;
    private DragAndDropView dragAndDropView;
    private FalseHolderView falseHolderView;
    private StringBuilder wordFormsBuilder;

    public WordFormsDragAndDropFragment() {
        // Required empty public constructor
    }


    public static WordFormsDragAndDropFragment newInstance(Utils.DayObject dayObject) {
        WordFormsDragAndDropFragment fragment = new WordFormsDragAndDropFragment();
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
        return inflater.inflate(R.layout.fragment_word_forms_drag_and_drop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeFloatText();

        button = (Button) view.findViewById(R.id.check_next);
        button.setOnClickListener(this);

         dragAndDropView = (DragAndDropView) view.findViewById(R.id.drag_drop_view);
        dragAndDropView.setData(dayObject);

         falseHolderView = (FalseHolderView) view.findViewById(R.id.false_holder_view);
        falseHolderView.setData(dayObject);
        falseHolderView.setOnDragAblesArrangeListener(this);

         wordFormsBuilder = new StringBuilder();
        for(int i = 0 ; i < dayObject.getWordForms().size() ; i++){
            if (dayObject.getWordForms().get(i).getIs_adj() != null && dayObject.getWordForms().get(i).getIs_adj())
                wordFormsBuilder.append("Adjective: ");

            else if (dayObject.getWordForms().get(i).getIs_verb() != null && dayObject.getWordForms().get(i).getIs_verb())
                wordFormsBuilder.append("Verb: ");

            else if (dayObject.getWordForms().get(i).getIs_noun() != null && dayObject.getWordForms().get(i).getIs_noun())
                wordFormsBuilder.append("Noun: ");

            else if (dayObject.getWordForms().get(i).getIs_adv() != null && dayObject.getWordForms().get(i).getIs_adv())
                wordFormsBuilder.append("Adverb: ");

            wordFormsBuilder.append(dayObject.getWordForms().get(i).getWord());
            wordFormsBuilder.append("\n");
        }


    }


    private void initializeFloatText() {
        floatingPoint = Utils.getFloatingText(getActivity(), Color.LTGRAY, 100, "+1000", 100, 100);

        floatingText = Utils.getFloatingText(getActivity(), Color.GREEN, 100, " آفرین!", 100, 200);

        floatingTextFailed = Utils.getFloatingText(getActivity(), Color.RED, 100, " بیشتر دقت کن!", 100, 100);

        floatingPoint.attach2Window(); // let FloatingText attached to the Window
        floatingText.attach2Window(); // let FloatingText attached to the Window
        floatingTextFailed.attach2Window(); // let FloatingText attached to the Window
    }

    public void setDayObject(Utils.DayObject dayObject) {
        this.dayObject = dayObject;
    }


    public void onDragAblesArrange(boolean state) {
        button.setEnabled(state);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.check_next:
                if (!checked) {
                    boolean answer = dragAndDropView.checkAnswers();
                    button.setText("Next");
                    checked = true;

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
//                            EventBus.getDefault().post(new EventTTSRequest(dayObject.getSentences().get(0).getSentence_eng()));
                        }
                    }, 1000);

                    EventBus.getDefault().post(new EventPlaySoundEffect("check.mp3"));
                    EventBus.getDefault().post(new EventUpdateFlag("Vocabulary Forms", wordFormsBuilder.toString()));

                    if (answer) {
                        floatingPoint.startFloating(dragAndDropView);
                        floatingText.startFloating(dragAndDropView);
                    } else {
                        floatingTextFailed.startFloating(dragAndDropView);
                    }

                } else
                    EventBus.getDefault().post(new EventUpdateProgress());
                break;


        }
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
