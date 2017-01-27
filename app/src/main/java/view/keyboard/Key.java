package view.keyboard;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import org.greenrobot.eventbus.EventBus;

import events.EventPlaySoundEffect;
import model.OnKeyPressedListener;
import util.Utils;

/**
 * Created by Hossein on 12/29/2016.
 */

public class Key extends TextView implements View.OnTouchListener {


    private OnKeyPressedListener onKeyPressedListener;

    public Key(Context context) {
        this(context, null);
    }

    public Key(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public Key(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.setOnTouchListener(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(Utils.convertDpToPx(getContext(), 50), Utils.convertDpToPx(getContext(), 50));
        params.setMargins(Utils.convertDpToPx(getContext(), 3), Utils.convertDpToPx(getContext(), 3), Utils.convertDpToPx(getContext(), 3), Utils.convertDpToPx(getContext(), 3));
        setLayoutParams(params);

        setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        setTextColor(Color.BLACK);
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.key_bg);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setElevation(Utils.convertDpToPx(getContext(), 5));
        }
    }


    public void setChar(char keyChar) {

        this.setText(String.valueOf(keyChar));
    }

    public void keyState(boolean state) {
        setEnabled(state);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        switch (motionEvent.getAction()) {


            case MotionEvent.ACTION_DOWN:

                EventBus.getDefault().post(new EventPlaySoundEffect("button_tick.mp3"));

                onKeyPressedListener.onKeyPressed(this.getText().toString());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.animate().translationYBy(-25).scaleYBy(0.2f).scaleXBy(0.1f).translationZBy(15).setDuration(20).start();

                } else {
                    view.animate().translationYBy(-25).scaleYBy(0.2f).scaleXBy(0.1f).setDuration(20).start();
                }

                break;


            case MotionEvent.ACTION_UP:

                keyState(false);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.animate().translationY(0).scaleY(1f).scaleX(1f).translationZ(0).setDuration(100).start();

                } else {
                    view.animate().translationY(0).scaleY(1f).scaleX(1f).setDuration(100).start();
                }


                break;
        }
        return true;
    }


    public void setOnKeyPressedListener(OnKeyPressedListener onKeyPressedListener) {
        this.onKeyPressedListener = onKeyPressedListener;
    }
}
