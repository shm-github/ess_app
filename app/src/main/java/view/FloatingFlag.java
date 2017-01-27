package view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

/**
 * Created by Hossein on 12/26/2016.
 */

public class FloatingFlag extends LinearLayout {
    private  AttributeSet attrs;
    private TextView tv_vocabulary;
    private TextView tv_definition;
    private int _xDelta;
    private int _yDelta;

    public FloatingFlag(Context context) {
        this(context , null);
    }

    public FloatingFlag(Context context, AttributeSet attrs) {
        this(context , attrs , -1);
        this.attrs = attrs;
    }

    public FloatingFlag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();
    }

    private void initializeView() {
        inflate(getContext() , R.layout.floating_flag_layout , this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tv_vocabulary = (TextView) findViewById(R.id.flag_word);
        tv_definition = (TextView) findViewById(R.id.flag_definition);

    }


    public void setData(String vocabulary , String definition){

        if(tv_definition == null || tv_vocabulary == null)
            return;

        tv_vocabulary.setText(vocabulary);
        tv_definition.setText(definition);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                FrameLayout.LayoutParams lParams = (FrameLayout.LayoutParams) getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;


                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
                layoutParams.topMargin = Y - _yDelta;
                setLayoutParams(layoutParams);
                break;
        }
        invalidate();
        return true;
    }
}
