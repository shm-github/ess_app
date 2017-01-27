package view.arranging_sentence;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

import model.OnViewClickListener;

/**
 * Created by Hossein on 1/1/2017.
 */

public class ArrangingSentenceView extends LinearLayout implements View.OnClickListener {

    ArrayList<ArrangingRow> rows;
    private OnViewClickListener onViewClickListener;
    private ArrayList<View> views;

    public ArrangingSentenceView(Context context) {
        this(context, null);
    }

    public ArrangingSentenceView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public ArrangingSentenceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initialize();

    }

    private void initialize() {
        setOrientation(VERTICAL);


    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();


    }

    public void add(View view, final LayoutParams layoutParams) {

        if (rows == null) {
            views = new ArrayList<>();
            rows = new ArrayList<>();
            setOrientation(VERTICAL);

        }

        views.add(view);

        boolean hasRoom = false;

        view.setOnClickListener(this);
        if (view.getParent() != null)
            ((LinearLayout) view.getParent()).removeView(view);


        if (rows.isEmpty()) {
            ArrangingRow row = new ArrangingRow(getContext());
            row.addView(view);
            rows.add(row);
            addView(row );
            return;
        }

        int lastEmptyRow = 0;

        for (int i = 0 ; i < rows.size() ; i++){
            if(rows.get(i).getChildCount() == 0) {
                lastEmptyRow = i > 0 ? i - 1 : i;
                break;
            }

        }

        for (int i = lastEmptyRow ; i < rows.size(); i++) {
            hasRoom = rows.get(i).isPossibleAddView(view, layoutParams);
            if (hasRoom) {
                rows.get(i).addView(view);
                break;
            }
        }

        if (!hasRoom) {
            ArrangingRow row = new ArrangingRow(getContext());
            row.addView(view);
            rows.add(row);
            addView(row );
        }


    }




    public void setOnViewClickListener(OnViewClickListener onViewClickListener) {
        this.onViewClickListener = onViewClickListener;
    }

    @Override
    public void onClick(View view) {
        if (onViewClickListener != null) {
            onViewClickListener.onViewClick(view);
            views.remove(view);


            for (int i = 0; i < rows.size(); i++) {
                ArrangingRow arrangingRow = rows.get(i);
                arrangingRow.removeAllViews();
            }

            int index = 0;

            for (int i = 0; i < views.size(); i++) {
                boolean hasRoom = rows.get(index).isPossibleAddView(views.get(i), (LayoutParams) views.get(i).getLayoutParams());
                if (hasRoom) {
                    rows.get(index).addView(views.get(i));

                } else {
                    index++;

                    if (index >= rows.size())
                        rows.add(new ArrangingRow(getContext()));

                    rows.get(index).addView(views.get(i));
                }
            }
        }
    }

    public boolean checkAnswer(String ans) {
        String userAns = "";
        ArrayList<String> strings = new ArrayList<>();

        for(int i = 0 ; i < rows.size() ; i++){
            strings.addAll( rows.get(i).getRowStrings() );
        }

        for (int i = 0 ; i < strings.size() ; i++){
            userAns = userAns + strings.get(i);

            if(i != strings.size() - 1 )
                userAns = userAns + " ";
        }

        if(ans.equals(userAns)){
            return true;
        }
        return false;
    }

    public int getViewsCount() {
        int count = 0 ;
        for (int i = 0 ; i < rows.size() ; i++){
            count = count + rows.get(i).getChildCount();
        }

        return count ;
    }
}
