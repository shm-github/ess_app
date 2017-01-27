package view.selectable_image_group;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.Pic;
import database.Word;
import model.ImageSelectListener;

/**
 * Created by Hossein on 12/23/2016.
 */

public class CheckableImageGroup extends GridLayout implements ImageSelectListener {

    ArrayList<SelectableImage> selectableImages;
    private Word word;
    private List<Pic> pic;
    private long selected_pic_word_id;
    private SelectableImage selected_selectableImage;
    private ViewGroup viewGroup;
    private View view;
    private ImageSelectListener listener;

    public CheckableImageGroup(Context context) {
        this(context, null);
    }

    public CheckableImageGroup(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public CheckableImageGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        instantiateView();
    }

    public void instantiateView() {
        selectableImages = new ArrayList<>();

        view = LayoutInflater.from(getContext()).inflate(R.layout.checkable_image_group_layout, this, true);

    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                addListeners();
            }
        }, 1000);

    }

    private void addListeners() {

        SelectableImage selectableImage1 = (SelectableImage) findViewById(R.id.selectable_img1);
        selectableImages.add(selectableImage1);
        SelectableImage selectableImage2 = (SelectableImage) findViewById(R.id.selectable_img2);
        selectableImages.add(selectableImage2);
        SelectableImage selectableImage3 = (SelectableImage) findViewById(R.id.selectable_img3);
        selectableImages.add(selectableImage3);
        SelectableImage selectableImage4 = (SelectableImage) findViewById(R.id.selectable_img4);
        selectableImages.add(selectableImage4);


        for (int i = 0; i < selectableImages.size(); i++)
            selectableImages.get(i).setOnStateChangeListener(this);

        showImagesFromFile();
    }

    private void showImagesFromFile() {
        if (pic == null || pic.isEmpty())
            return;

        Collections.shuffle(pic);
        for (int i = 0; i < selectableImages.size(); i++) {
            selectableImages.get(i).setImageFile(pic.get(i).getFile_name());
            selectableImages.get(i).setWordId(pic.get(i).getWord_id());
        }
    }


    public void setImagesList(List<Pic> pic, Word word) {
        this.pic = pic;
        this.word = word;

        Collections.shuffle(this.pic);

    }

    public boolean checkAnswer() {
        boolean answer = selected_pic_word_id == word.getId();
        if (answer)
            selected_selectableImage.showCorrectState();
        else {
            selected_selectableImage.showWrongState();
            showCorrectAnswer();
        }

        for(int i = 0 ; i < selectableImages.size() ; i++){
            selectableImages.get(i).setOnClickListener(null);
        }

        return answer;
    }

    private void showCorrectAnswer() {
        for (int i = 0; i < selectableImages.size(); i++) {
            if (selectableImages.get(i).getWordId() == word.getId())
                selectableImages.get(i).showCorrectState();
        }
    }

    public void setImageStateListener(ImageSelectListener listener) {
        this.listener = listener;
    }


    @Override
    public void onItemSelect(long word_id, SelectableImage selectableImage) {
        selected_pic_word_id = word_id;
        selected_selectableImage = selectableImage;

        if (listener != null)
            listener.onItemSelect(word_id, selectableImage);

        for (int i = 0; i < selectableImages.size(); i++) {
            if (selectableImages.get(i).getWordId() == word_id)
                continue;
            selectableImages.get(i).resetImageState();
        }

    }
}







