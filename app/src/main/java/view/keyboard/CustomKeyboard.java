package view.keyboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;

import model.OnBackSpaceListener;
import model.OnKeyPressedListener;
import model.OnTypeCompleteListener;

/**
 * Created by Hossein on 12/29/2016.
 */

public class CustomKeyboard extends LinearLayout implements OnKeyPressedListener, OnBackSpaceListener {
    private String keyBoardTextRange;
    private BlankTextView blankTextView;
    private String vocabulary;
    private OnTypeCompleteListener onTypeCompleteListener;
    ArrayList<CustomKeyboardRow> keyboardRows ;

    public CustomKeyboard(Context context) {
        this(context, null);

    }

    public CustomKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CustomKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    private void initializeView() {
        this.setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        keyboardRows = new ArrayList<>();

        CustomKeyboardRow row;

        ArrayList<Character> wordChars = convertTextToChar(keyBoardTextRange);

        ArrayList<Character> separatedWordChars = new ArrayList<>();

        for (int i = 0; i < wordChars.size(); ) {

            row = new CustomKeyboardRow(getContext());

            int j = i;
            int end = i + 5;

            if (end > wordChars.size())
                end = wordChars.size();

            separatedWordChars.clear();

            for (; j < end; j++) {
                separatedWordChars.add(wordChars.get(j));
            }

            row.setOnKeyClickListener(this);
            row.setAlphabets(separatedWordChars);
            this.addView(row, getLayoutParam());

            keyboardRows.add(row);

            i = i + 5;

        }

    }


    private FrameLayout.LayoutParams getLayoutParam() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, com.mikepenz.iconics.utils.Utils.convertDpToPx(getContext(), 80));

        return params;
    }


    private ArrayList<Character> convertTextToChar(String text) {

        vocabulary = text;
        ArrayList<Character> chars = new ArrayList<>();

        for (int i = 0; i < text.length(); i++) {
            chars.add(text.charAt(i));
        }
        return chars;
    }


    public void setKeyBoardTextRange(String keyBoardTextRange) {

        this.keyBoardTextRange = keyBoardTextRange;

        initializeView();

    }

    public void setBlankTextView(BlankTextView blankTextView) {
        this.blankTextView = blankTextView;
        blankTextView.setOnBackSpaceClickListener(this);
    }

    public boolean checkAnswer() {

        if (vocabulary.equals(blankTextView.getText()))
            return true;
        else
            return false;
    }


    @Override
    public void onKeyPressed(String character) {
        blankTextView.addCharacter(character);

        if (blankTextView.getText().length() == vocabulary.length())
            onTypeCompleteListener.onTypeComplete(true);

    }

    @Override
    public void onBackSpace() {
        onTypeCompleteListener.onTypeComplete(false);

        resetAllKeys();
    }


    private void resetAllKeys() {
        for(int i = 0 ; i < keyboardRows.size() ; i++){
            keyboardRows.get(i).resetKeys();
        }
    }


    public void setOnTypeCompleteListener(OnTypeCompleteListener onTypeCompleteListener) {
        this.onTypeCompleteListener = onTypeCompleteListener;
    }
}
