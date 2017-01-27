package model;

import view.selectable_image_group.SelectableImage;

public interface ImageSelectListener {
    void onItemSelect(long word_id , SelectableImage selectableImage);
}