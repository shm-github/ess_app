package view.drag_drop_view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Hossein on 12/30/2016.
 */

public class HolderView extends LinearLayout {

    private boolean filled;
    private DragItemView currentView;

    public HolderView(Context context) {
        this(context, null);

    }

    public HolderView(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public HolderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeView();
    }

    private void initializeView() {

        this.setOnDragListener(myDragListener);
        this.setGravity(Gravity.CENTER);
    }

    public DragItemView getDragItemView(){
        return currentView;
    }


    OnDragListener myDragListener = new OnDragListener() {
        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            switch (dragEvent.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    filled = false;
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    if (!(getChildCount() > 0)) {
                        filled = true;
                        DragItemView view2 = (DragItemView) dragEvent.getLocalState();
                        ViewGroup owner = (ViewGroup) view2.getParent();
                        owner.removeView(view2);

                        LayoutParams params = (LayoutParams) view2.getLayoutParams();
                        params.gravity = Gravity.CENTER;
                        params.setMargins(0, 0, 0, 0);
                        addView(view2, params);
                        view2.setVisibility(View.VISIBLE);
                        view2.setHolderView(HolderView.this);
                        currentView =(DragItemView) view2;
                    }
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                default:
                    break;
            }

            return true;
        }
    };
}
