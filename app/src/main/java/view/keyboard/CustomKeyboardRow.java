package view.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.Collections;

import model.OnKeyPressedListener;

/**
 * Created by Hossein on 12/29/2016.
 */

public class CustomKeyboardRow extends LinearLayout {
    private ArrayList<Character> chars;
    private ArrayList<Key> keys ;
    private OnKeyPressedListener onKeyPressedListener;

    public CustomKeyboardRow(Context context) {
        this(context , null);

    }

    public CustomKeyboardRow(Context context, AttributeSet attrs) {
        this(context, attrs , -1);
    }

    public CustomKeyboardRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    private void initializeView() {

        keys = new ArrayList<>();
        this.setOrientation(HORIZONTAL);

        setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM );

        Key key ;

        for (int i = 0 ; i < chars.size() ; i++){
            key = new Key(getContext());
            key.setChar(chars.get(i));
            key.setOnKeyPressedListener(onKeyPressedListener);
            keys.add(key);
            this.addView(key );
        }
    }



    public void setAlphabets(ArrayList<Character> chars){
        this.chars = chars ;
        Collections.shuffle(this.chars);
        initializeView();
    }


    public void resetKeys() {

        for (int i = 0 ; i < keys.size() ; i++ ){
            keys.get(i).keyState(true);
        }
    }

    public void setOnKeyClickListener(OnKeyPressedListener onKeyPressedListener) {
        this.onKeyPressedListener = onKeyPressedListener;
    }


}


