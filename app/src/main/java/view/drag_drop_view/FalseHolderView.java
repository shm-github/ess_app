package view.drag_drop_view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.Collections;

import model.OnDragAblesArrangeListener;
import util.Utils;

/**
 * Created by Hossein on 12/30/2016.
 */

public class FalseHolderView extends LinearLayout {
    private Utils.DayObject dayObject;
    private OnDragAblesArrangeListener onDragAblesArrangeListener;
    private Handler handler ;

    public FalseHolderView(Context context) {
        this(context, null);
    }

    public FalseHolderView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public FalseHolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        handler = new Handler();
    }

    private void initializeView() {

        this.setOnDragListener(myDragListener);
        this.setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        Collections.shuffle(dayObject.getWordForms());

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(Utils.convertDpToPx(getContext(), 2), Utils.convertDpToPx(getContext(), 2), Utils.convertDpToPx(getContext(), 2), Utils.convertDpToPx(getContext(), 2));

        DragItemView dragItemView;

        for (int i = 0; i < dayObject.getWordForms().size(); i++) {
            dragItemView = new DragItemView(getContext());
            dragItemView.setLayoutParams(params);
            dragItemView.setWordForm(dayObject.getWordForms().get(i));
            addView(dragItemView);
        }
    }


    OnDragListener myDragListener = new OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {
            int action = dragEvent.getAction();
            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    View view2 = (View) dragEvent.getLocalState();
                    ViewGroup owner = (ViewGroup) view2.getParent();
                    owner.removeView(view2);

                    LayoutParams params = (LayoutParams) view2.getLayoutParams();
                    params.gravity = Gravity.CENTER;
                    params.setMargins(Utils.convertDpToPx(getContext(), 2), Utils.convertDpToPx(getContext(), 2), Utils.convertDpToPx(getContext(), 2), Utils.convertDpToPx(getContext(), 2));

                    addView(view2, params);
                    view2.setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }

            return true;
        }
    };


    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkChildsCount();
            }
        }, 500);
    }


    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkChildsCount();
            }
        }, 500);
    }

    private void checkChildsCount() {
        if(onDragAblesArrangeListener == null)
            return;

        int childCount = getChildCount();

        if(childCount == 0)
            onDragAblesArrangeListener.onDragAblesArrange(true);
        else
            onDragAblesArrangeListener.onDragAblesArrange(false);
    }

    public void setData(Utils.DayObject data) {
        this.dayObject = data;

        initializeView();
    }

    public void setOnDragAblesArrangeListener(OnDragAblesArrangeListener onDragAblesArrangeListener) {
        this.onDragAblesArrangeListener = onDragAblesArrangeListener;
    }
}

