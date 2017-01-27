package view.multi_choice_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import org.greenrobot.eventbus.EventBus;

import events.EventPlaySoundEffect;
import model.OnSelectListener;
import util.Utils;

/**
 * Created by Hossein on 12/26/2016.
 */

public class SingleChoiceAbleView extends FrameLayout implements View.OnClickListener {
    private TextView tv_single_choice;
    private Utils.DayObject dayObject;
    private boolean isTrue;
    private OnSelectListener onSelectListener;

    public SingleChoiceAbleView(Context context) {
        this(context, null);
    }

    public SingleChoiceAbleView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public SingleChoiceAbleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();
    }

    private void initializeView() {
        inflate(getContext(), R.layout.single_choice_abel_layout, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        tv_single_choice = (TextView) findViewById(R.id.single_choice_able);
        tv_single_choice.setOnClickListener(this);
    }


    public void setDayObject(Utils.DayObject dayObject, boolean isTrue, int showMode) {
        this.dayObject = dayObject;
        this.isTrue = isTrue;

        if (showMode == MultiChoiceView.SHOW_PERSIAN_DEFINITION)
            tv_single_choice.setText(dayObject.getWord().getPer_def());
        if (showMode == MultiChoiceView.SHOW_WORD)
            tv_single_choice.setText(dayObject.getWord().getWord());
        if (showMode == MultiChoiceView.SHOW_ENGLISH_DEFINITION)
            tv_single_choice.setText(dayObject.getWord().getEng_def());

    }

    public boolean isTrue() {
        return isTrue;
    }

    public long getWord_id() {
        return dayObject.getWord().getId();
    }

    public void selectView(boolean state) {
        tv_single_choice.setSelected(state);
    }

    public void showCorrectState() {
        tv_single_choice.setBackgroundResource(R.drawable.single_choice_selected_correct);
    }

    public void showWrongState() {
        tv_single_choice.setBackgroundResource(R.drawable.single_choice_selected_wrong);
    }


    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    @Override
    public void onClick(View view) {

        selectView(true);

        EventBus.getDefault().post(new EventPlaySoundEffect("pop.mp3"));

        if (onSelectListener != null)
            onSelectListener.onSelect(this, dayObject);
    }
}


