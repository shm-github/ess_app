package view.arranging_sentence;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import java.util.ArrayList;

import util.Utils;

/**
 * Created by Hossein on 12/31/2016.
 */

public class ArrangingRow extends LinearLayout {
    private int viewWidth;
    private int viewHeight;
    int width;
    int height;

    public ArrangingRow(Context context) {
        this(context, null);
    }

    public ArrangingRow(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ArrangingRow(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(getContext() , R.layout.arranging_row_layout , this );

        initialize();

    }


    private void initialize() {
        setOrientation(HORIZONTAL);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.topMargin = Utils.convertDpToPx(getContext(), 5);
        layoutParams.bottomMargin = Utils.convertDpToPx(getContext(), 5);
        setLayoutParams(layoutParams);
        setGravity(Gravity.LEFT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setLayoutDirection(LAYOUT_DIRECTION_LTR);
        }


    }


    public boolean isPossibleAddView(View view, LayoutParams layoutParams) {


        int j = 0;

        view.measure(0, 0);   //must call measure
        j = j + view.getMeasuredWidth();
        j = j + layoutParams.leftMargin;
        j = j + layoutParams.rightMargin;

        int l = getChildCount();

        for (int i = 0; i < getChildCount(); i++) {

            getChildAt(i).measure(0, 0);
            j = j + getChildAt(i).getMeasuredWidth();

            LayoutParams params = (LayoutParams) getChildAt(i).getLayoutParams();
            j = j + params.leftMargin;
            j = j + params.rightMargin;
        }

//        measure(0 , 0);

//        int meas = getMeasuredWidth() ;
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        int mWidth = displayMetrics.widthPixels - (Utils.convertDpToPx(getContext(), 15) * 2);
        if (mWidth >= j) {
            return true;
        } else
            return false;

    }


    public void setOnViewClickListener() {

    }

    public ArrayList<String> getRowStrings() {
        ArrayList<String> st = new ArrayList<>();
        for (int i = 0 ; i < getChildCount() ; i++){
            TextView tv = (TextView) getChildAt(i);
            tv.setOnClickListener(null);
            st.add( tv.getText().toString() );
        }

        return st ;
    }
}
