package view.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import org.greenrobot.eventbus.EventBus;

import events.EventPlaySoundEffect;
import model.OnBackSpaceListener;

/**
 * Created by Hossein on 12/29/2016.
 */

public class BlankTextView extends LinearLayout implements View.OnClickListener {
    private TextView blankText;
    private OnBackSpaceListener onBackSpaceListener;
    private CustomKeyboard customKeyBoard;

    public BlankTextView(Context context) {
        this(context, null);
    }

    public BlankTextView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BlankTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(getContext(), R.layout.blank_text_view, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        blankText = (TextView) findViewById(R.id.text_blank);
        View backSpace = findViewById(R.id.back_space);
        backSpace.setOnClickListener(this);

    }

    public void setKeyboard(CustomKeyboard customKeyBoard) {
        this.customKeyBoard = customKeyBoard;

        customKeyBoard.setBlankTextView(this);
    }


    public String getText(){
        return blankText.getText().toString();
    }


    public void addCharacter(String character) {
        blankText.setText(blankText.getText() + character);
    }

    public void setOnBackSpaceClickListener(OnBackSpaceListener onBackSpaceListener) {
        this.onBackSpaceListener = onBackSpaceListener;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.back_space:

                EventBus.getDefault().post(new EventPlaySoundEffect("clear.mp3"));

                blankText.setText("");

                if (onBackSpaceListener != null)
                    onBackSpaceListener.onBackSpace();

                break;


        }
    }


}


