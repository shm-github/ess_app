package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.AppController;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.ArrangingSentenceFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.FillIncompleteSentenceFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.ImageDefinitionMultiChoiceFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.SoundImageGroupFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.SoundMultiChoiceFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.SoundTypeWordFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.WordDefinitionMultiChoiceFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.WordFormsDragAndDropFragment;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens.exercise_screens.WordImageGroupFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import database.Sentence;
import database.SentenceDao;
import database.Word;
import database.WordDao;
import util.Utils;

/**
 * Created by Hossein on 12/23/2016.
 */

public class ExercisePagerAdapter extends FragmentPagerAdapter {


    private final List<Word> words;
    private final int count;
    private final List<Sentence> sentences;
    private int currentPosition;
    private int questionType = 7;
    private final ArrayList<Integer> wordForms;
    private ArrayList<Word> wordsDuplicates;
    private int sentencePosition = 0;

    public ExercisePagerAdapter(FragmentManager fm) {
        super(fm);

        wordsDuplicates = new ArrayList<>();
        wordForms = new ArrayList<>();
        sentences = new ArrayList<>();

        words = AppController.getWordDao().queryBuilder().where(WordDao.Properties.Date_id.eq(1)).list();
        Collections.shuffle(words);
        wordsDuplicates.addAll(words);
        count = words.size();


        for (int i = 0; i < words.size(); i++) {
            sentences.addAll(AppController.getSentenceDao().queryBuilder().where(SentenceDao.Properties.Word_id.eq(words.get(i).getId())).list());

        }

        for (int i = 0; i < words.size(); i++) {
            if (Utils.getDayObject(words.get(i)).getWordForms().size() > 1)
                //store id of words that have 2 or more word forms object
                wordForms.add(i);
        }
    }

    @Override
    public int getCount() {
        return ((count * questionType) + wordForms.size() + sentences.size());
    }


    @Override
    public Fragment getItem(int position) {
        currentPosition = position;

        if (position < count)
            return WordImageGroupFragment.newInstance(Utils.getDayObject(words.get(position)), getFalseWords(position, 3));

        else if (position >= count && position < (count * 2))
            return WordDefinitionMultiChoiceFragment.newInstance(Utils.getDayObject(words.get(position % count)), getFalseWords(position % count, 2), WordDefinitionMultiChoiceFragment.SHOW_PERSIAN_DEF);

        else if (position >= (count * 2) && position < (count * 3)) {
            return ImageDefinitionMultiChoiceFragment.newInstance(Utils.getDayObject(words.get(position % count)), getFalseWords(position % count, 2));

        } else if (position >= (count * 3) && position < (count * 4)) {
            return SoundImageGroupFragment.newInstance(Utils.getDayObject(words.get(position % count)), getFalseWords(position % count, 3));

        } else if (position >= (count * 4) && position < (count * 5)) {
            return SoundMultiChoiceFragment.newInstance(Utils.getDayObject(words.get(position % count)), getFalseWords(position % count, 2));

        } else if (position >= (count * 5) && position < (count * 6)) {
            return SoundTypeWordFragment.newInstance(Utils.getDayObject(words.get(position % count)));

        } else if (position >= (count * 6) && position < (count * 7)) {
            return FillIncompleteSentenceFragment.newInstance(Utils.getDayObject(words.get(position % count)));

        } else if (position >= (count * 7) && position < ((count * 7) + wordForms.size())) {
            return WordFormsDragAndDropFragment.newInstance(Utils.getDayObject(words.get(wordForms.get(position % count))));

        } else  {
            return ArrangingSentenceFragment.newInstance(sentences.get(sentencePosition++));

        }
//        else if(position >= ((count * 8) + wordForms.size()) && position < ((count * 8) + wordForms.size() + sentences.size())) {
//            return ArrangingSentenceFragment.newInstance(sentences.get(sentencePosition++));
//
//        }


    }


    private ArrayList<Utils.DayObject> getFalseWords(int position, int falseAnswerCount) {
        ArrayList<Utils.DayObject> words = new ArrayList<>();

        Collections.shuffle(wordsDuplicates);

        for (int i = 0; i < this.wordsDuplicates.size(); i++) {

            if (words.size() == falseAnswerCount)
                break;

            if (this.words.get(position).getId() != this.wordsDuplicates.get(i).getId()) {
                Utils.DayObject currentDayObject = util.Utils.getDayObject(this.wordsDuplicates.get(i));
                words.add(currentDayObject);
            }
        }
        return words;
    }


}
