package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow; /**
 * Created by Hossein on 7/13/2016.
 */

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.crashlytics.android.Crashlytics;

import database.DaoMaster;
import database.DaoSession;
import database.DateDao;
import database.PicDao;
import database.PronunciationDao;
import database.SentenceDao;
import database.WeekDao;
import database.WordDao;
import database.WordFormDao;
import io.fabric.sdk.android.Fabric;


public class AppController extends Application{

    private static AppController mInstance;
//    private static VocabularyDao leitnerDao;

    private static Activity activity ;
    private static PicDao picDao;
    private static WordDao wordDao;
    private static WeekDao weekDao;
    private static DateDao dateDao;
    private static PronunciationDao pronunciationDao;
    private static SentenceDao sentenceDao ;
    private static WordFormDao wordFormDao ;




    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Fabric.with(this, new Crashlytics());

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this,"database.db",null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();

        picDao = daoSession.getPicDao();
        wordDao = daoSession.getWordDao();
        weekDao = daoSession.getWeekDao();
        dateDao = daoSession.getDateDao();
        pronunciationDao = daoSession.getPronunciationDao();
        sentenceDao = daoSession.getSentenceDao();
        wordFormDao = daoSession.getWordFormDao();


    }

    public static WordFormDao getWordFormDao() {
        return wordFormDao;
    }

    public static SentenceDao getSentenceDao() {
        return sentenceDao;
    }

    public static PicDao getPicDao() {
        return picDao;
    }

    public static WordDao getWordDao() {
        return wordDao;
    }

    public static WeekDao getWeekDao() {
        return weekDao;
    }

    public static DateDao getDateDao() {
        return dateDao;
    }

    public static PronunciationDao getPronunciationDao() {
        return pronunciationDao;
    }
    public static Activity getActivity() {
        return activity;
    }

    public static void setActivity(Activity activity) {
        AppController.activity = activity;
    }


    public static Context getAppContext() {
        return getInstance().getApplicationContext();
    }


    public static synchronized AppController getInstance() {
        return mInstance;
    }


}
