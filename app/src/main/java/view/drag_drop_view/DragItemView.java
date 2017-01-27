package view.drag_drop_view;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Vibrator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import database.WordForm;
import util.Utils;

/**
 * Created by Hossein on 12/30/2016.
 */

public class DragItemView extends TextView implements View.OnTouchListener {

    private Vibrator vibrator;
    private WordForm wordForm;
    private HolderView holderView;

    public DragItemView(Context context) {
        this(context , null);
    }

    public DragItemView(Context context, AttributeSet attrs) {
        this(context, attrs , -1);
    }

    public DragItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initializeView();
    }

    private void initializeView() {

        this.setOnTouchListener(this);

         vibrator = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.WRAP_CONTENT , Utils.convertDpToPx(getContext(), 50));
        setLayoutParams(params);

        setMaxLines(1);
        setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
        setTextColor(Color.BLACK);
        setPadding(Utils.convertDpToPx(getContext() , 5) , Utils.convertDpToPx(getContext() , 2) , Utils.convertDpToPx(getContext() , 5) , Utils.convertDpToPx(getContext() , 2) );
        setBackgroundResource(R.drawable.select);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(Utils.convertDpToPx(getContext(), 5));
        }

    }

    public void disableDragListener() {
        setOnTouchListener(null);
    }


    public void setHolderView(HolderView holderView){
        this.holderView = holderView;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                //vibrate
                vibrator.vibrate(30);

                ClipData clipData = ClipData.newPlainText("" , "");
                DragShadowBuilder shadowBuilder = new DragShadowBuilder(view);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    startDragAndDrop(clipData, shadowBuilder, view, 0 );
                }else {
                    startDrag(clipData, shadowBuilder, view, 0 );
                }

                this.setVisibility(VISIBLE);
                break;


            case MotionEvent.ACTION_UP:

                break;
        }
        return true;
    }

    public WordForm getWordForm() {
        return wordForm;
    }

    public void setWordForm(WordForm wordForm) {
        this.wordForm = wordForm;

        this.setText(wordForm.getWord());
    }


}
