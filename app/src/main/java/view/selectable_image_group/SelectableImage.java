package view.selectable_image_group;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import org.greenrobot.eventbus.EventBus;

import events.EventPlaySoundEffect;
import model.ImageSelectListener;
import util.Utils;

/**
 * Created by Hossein on 12/25/2016.
 */

public class SelectableImage extends FrameLayout implements View.OnClickListener {
    int curtain_selected = R.drawable.state_select;
    int curtain_wrong = R.drawable.state_wrong;
    int curtain_correct = R.drawable.state_correct;

    int icon_done = R.drawable.ic_done_all_black_24dp;
    int icon_wrong = R.drawable.ic_wrong_black_24dp;

    private ImageView curtain;
    private ImageView icon;
    private ImageView img;
    private long word_id;
    private ImageSelectListener listener;
    private View viewWrapper;

    public SelectableImage(Context context) {
        this(context , null);
    }

    public SelectableImage(Context context, AttributeSet attrs) {
        this(context , attrs , -1) ;
    }

    public SelectableImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        instantiateView();
    }

    public void instantiateView(){
         viewWrapper = inflate(getContext() , R.layout.selectable_image_layout , this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        img = (ImageView) findViewById(R.id.img);
        curtain = (ImageView) findViewById(R.id.img_curtain);
        icon = (ImageView) findViewById(R.id.img_icon);

        viewWrapper.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        curtain.setVisibility(VISIBLE);
        curtain.setImageResource(curtain_selected);

        EventBus.getDefault().post(new EventPlaySoundEffect("select.mp3"));

        if(listener != null)
            listener.onItemSelect(word_id , this);
    }

    public void setImageFile(String file_name){

        Utils.loadImageToIvFromPic(file_name , img);
    }

    public void resetImageState(){
        curtain.setVisibility(View.INVISIBLE);
    }


    public void setWordId(long wordId){
        this.word_id = wordId ;
    }


    public long getWordId(){
        return word_id;
    }

    public void showCorrectState(){
        curtain.setVisibility(VISIBLE);
        curtain.setImageResource(curtain_correct);

        icon.setImageResource(icon_done);
    }

    public void showWrongState(){
        curtain.setVisibility(VISIBLE);
        curtain.setImageResource(curtain_wrong);

        icon.setImageResource(icon_wrong);
    }

    public void setOnStateChangeListener(ImageSelectListener listener){
        this.listener = listener ;
    }



}


