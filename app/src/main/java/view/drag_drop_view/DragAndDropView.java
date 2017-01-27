package view.drag_drop_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.util.ArrayList;

import util.Utils;

/**
 * Created by Hossein on 12/30/2016.
 */

public class DragAndDropView extends LinearLayout {
    private Utils.DayObject dayObject;
    private ArrayList<HolderViewWrapper> holderViewWrappers;

    public DragAndDropView(Context context) {
        super(context);
    }

    public DragAndDropView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DragAndDropView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setData(Utils.DayObject dayObject){
        this.dayObject = dayObject;

        initializeView();
    }

    private void initializeView() {

         holderViewWrappers = new ArrayList<>();

        this.setOrientation(VERTICAL);
        HolderViewWrapper holderViewWrapper ;

        for (int i = 0 ; i < dayObject.getWordForms().size() ; i++){
            holderViewWrapper = new HolderViewWrapper(getContext());
            holderViewWrapper.setWordForm(dayObject.getWordForms().get(i));
            addView(holderViewWrapper);

            holderViewWrappers.add(holderViewWrapper);
        }
    }


    public boolean checkAnswers(){
        boolean answer = true ;
        for (int i = 0 ; i < holderViewWrappers.size() ; i++){
            answer = holderViewWrappers.get(i).checkAnswer();
        }
        return answer;
    }
}
