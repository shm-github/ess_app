package view.drag_drop_view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import database.WordForm;

/**
 * Created by Hossein on 12/30/2016.
 */

public class HolderViewWrapper extends LinearLayout {
    private WordForm wordForm;
    private TextView holder_label;
    private HolderView holder;

    public HolderViewWrapper(Context context) {
        this(context, null);
    }

    public HolderViewWrapper(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HolderViewWrapper(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();
    }

    private void initializeView() {
        View view = inflate(getContext(), R.layout.holder_view_wraper_layout, this);

        holder_label = (TextView) view.findViewById(R.id.holder_label);
        holder = (HolderView) view.findViewById(R.id.holder);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        if (wordForm != null) {
            updateLabel();
        }
    }

    public DragItemView getDragItemView() {
        return holder.getDragItemView();
    }

    public boolean checkAnswer() {
        boolean ans = check();
        if (ans) {
            holder.setBackgroundResource(R.drawable.correct);

        } else {
            holder.setBackgroundResource(R.drawable.wrong);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.getDragItemView().setText(wordForm.getWord());
                }
            }, 2000);
        }

        getDragItemView().disableDragListener();

        return ans;
    }

    public void setWordForm(WordForm wordForm) {
        this.wordForm = wordForm;

        if (holder_label != null) {
            updateLabel();
        }
    }

    private void updateLabel() {
        if (wordForm.getIs_adj() != null && wordForm.getIs_adj())
            holder_label.setText("Adj");

        if (wordForm.getIs_verb() != null && wordForm.getIs_verb())
            holder_label.setText("Verb");

        if (wordForm.getIs_noun() != null && wordForm.getIs_noun())
            holder_label.setText("Noun");

        if (wordForm.getIs_adv() != null && wordForm.getIs_adv())
            holder_label.setText("Adv");
    }


    private boolean check() {
        if (wordForm.getIs_adj() != null && wordForm.getIs_adj()) {
            if (holder.getDragItemView().getWordForm().getIs_adj() != null)
                return true;
            else
                return false;

        } else if (wordForm.getIs_verb() != null && wordForm.getIs_verb())
            if (holder.getDragItemView().getWordForm() != null)
                return true;
            else
                return false;

        else if (wordForm.getIs_noun() != null && wordForm.getIs_noun())
            if (holder.getDragItemView().getWordForm().getIs_noun() != null)
                return true;
            else
                return false;


        else if (wordForm.getIs_adv() != null && wordForm.getIs_adv())
            if (holder.getDragItemView().getWordForm().getIs_adv() != null)
                return true;
            else
                return false;

        return false;
    }
}
