package view.multi_choice_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import java.util.ArrayList;
import java.util.Collections;

import model.OnSelectListener;
import util.Utils;


public class MultiChoiceView extends LinearLayout implements OnSelectListener {

    public static final int SHOW_PERSIAN_DEFINITION = 0;
    public static final int SHOW_WORD = 1;
    public static final int SHOW_ENGLISH_DEFINITION = 2;
    private Utils.DayObject correctDayObject;
    private ArrayList<Utils.DayObject> falseDayObjects;
    private OnSelectListener onSelectListener;
    private ArrayList<SingleChoiceAbleView> singleChoiceAbles;
    private Utils.DayObject currentSelectedDayObject;
    private SingleChoiceAbleView currentSelectedSingleChoice;
    private int showMode;

    public MultiChoiceView(Context context) {
        this(context, null);
    }

    public MultiChoiceView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public MultiChoiceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initializeView();
    }

    private void initializeView() {
        singleChoiceAbles = new ArrayList<SingleChoiceAbleView>();

        inflate(getContext(), R.layout.multi_choice_view_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        singleChoiceAbles.add((SingleChoiceAbleView) findViewById(R.id.single_choice_able1));
        singleChoiceAbles.add((SingleChoiceAbleView) findViewById(R.id.single_choice_able2));
        singleChoiceAbles.add((SingleChoiceAbleView) findViewById(R.id.single_choice_able3));

    }

    public void setChoiceAbleItems(Utils.DayObject correctDayObject, ArrayList<Utils.DayObject> falseDayObjects, int showMode) {

        falseDayObjects.add(correctDayObject);

        Collections.shuffle(falseDayObjects);
        Collections.shuffle(singleChoiceAbles);

        this.correctDayObject = correctDayObject;
        this.falseDayObjects = falseDayObjects;
        this.showMode = showMode ;

        for(int i =0 ; i< falseDayObjects.size() ; i++){
            singleChoiceAbles.get(i).setDayObject(falseDayObjects.get(i) , falseDayObjects.get(i).equals(correctDayObject) , showMode);

            singleChoiceAbles.get(i).setOnSelectListener(this);
        }
    }


    public boolean checkAnswer(){
        if(currentSelectedDayObject.getWord().getId() == correctDayObject.getWord().getId()){
            currentSelectedSingleChoice.showCorrectState();
            return true ;

        }else {
            currentSelectedSingleChoice.showWrongState();

            for(int i = 0 ; i< singleChoiceAbles.size() ; i++){
                if(singleChoiceAbles.get(i).isTrue())
                    singleChoiceAbles.get(i).showCorrectState();
            }
            return false;
        }

    }


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public void onSelect(SingleChoiceAbleView singleChoiceAbleView, Utils.DayObject dayObject) {
        currentSelectedSingleChoice = singleChoiceAbleView ;
        currentSelectedDayObject = dayObject;

        for(int i = 0 ; i< singleChoiceAbles.size() ; i++){
            if(singleChoiceAbles.get(i).getWord_id() == dayObject.getWord().getId())
                continue;
            singleChoiceAbles.get(i).selectView(false);
        }

        if(onSelectListener != null)
            onSelectListener.onSelect(singleChoiceAbleView ,  dayObject);
    }
}
