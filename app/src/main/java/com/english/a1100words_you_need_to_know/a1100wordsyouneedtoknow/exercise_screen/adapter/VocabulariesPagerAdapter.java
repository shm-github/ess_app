package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.AppController;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import database.Pic;
import database.Word;
import database.WordDao;
import events.EventPlaySound;
import util.Utils;

/**
 * Created by GIGAMOLE on 7/27/16.
 */
public class VocabulariesPagerAdapter extends PagerAdapter implements View.OnClickListener {


    private final List<Word> words;
    private final List<Pic> pics;
    private final List<Utils.DayObject> dayObjects;

    private Context mContext;
    private LayoutInflater mLayoutInflater;


    public VocabulariesPagerAdapter(final Context context, final boolean isTwoWay) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

        pics = new ArrayList<>();
        dayObjects = new ArrayList<>();

        words = AppController.getWordDao().queryBuilder().where(WordDao.Properties.Date_id.eq(1)).list();

        for (int i = 0; i < words.size(); i++) {

            dayObjects.add(Utils.getDayObject(words.get(i)));


        }

    }

    @Override
    public int getCount() {
        return dayObjects.size();
    }


    @Override
    public int getItemPosition(final Object object) {
        return POSITION_NONE;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;

        view = mLayoutInflater.inflate(R.layout.item, container, false);
        setupItem(view, dayObjects.get(position));

        container.addView(view);
        return view;
    }


    private void setupItem(View view, Utils.DayObject dayObject) {

        final TextView txt = (TextView) view.findViewById(R.id.txt_item);
        txt.setTag(dayObject);
        txt.setText(dayObject.getWord().getWord());

        final TextView txt_mean = (TextView) view.findViewById(R.id.txt_mean);
        txt_mean.setTag(dayObject);
        txt_mean.setText(dayObject.getWord().getPer_def());

        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        img.setTag(dayObject);

        view.setTag(dayObject);

        Utils.loadImageToIvFromPic(dayObject.getPic().getFile_name(), img);


        img.setOnClickListener(this);
        txt.setOnClickListener(this);
        txt_mean.setOnClickListener(this);
        view.setOnClickListener(this);

    }


    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }


    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }



    @Override
    public void onClick(View view) {

        Utils.DayObject dayObject = (Utils.DayObject) view.getTag();
        EventBus.getDefault().post(new EventPlaySound(dayObject.getPronunciation().getFile_name()));

    }


}
