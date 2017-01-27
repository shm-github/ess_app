package model;

import util.Utils;
import view.multi_choice_view.SingleChoiceAbleView;

public interface OnSelectListener{
    public void onSelect(SingleChoiceAbleView singleChoiceAbleView , Utils.DayObject dayObject);
}