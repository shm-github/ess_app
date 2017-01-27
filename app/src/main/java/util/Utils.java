package util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.Toast;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.AppController;
import com.ufreedom.uikit.FloatingText;
import com.ufreedom.uikit.effect.CurveFloatingPathEffect;
import com.ufreedom.uikit.effect.CurvePathFloatingAnimator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.List;

import database.Pic;
import database.PicDao;
import database.Pronunciation;
import database.PronunciationDao;
import database.Sentence;
import database.SentenceDao;
import database.Word;
import database.WordForm;
import database.WordFormDao;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.applyDimension;

/**
 * Created by Hossein on 12/21/2016.
 */

public class Utils {

    public int convertPxToDp(Context context , int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int convertDpToPx(Context context, float dp) {
        return (int) applyDimension(COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static void loadImageToIvFromPic(String file_name, ImageView img) {

        File file = new File(Const.IMAGE_PATH + file_name);
        if (!file.exists())
            return;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        img.setImageBitmap(BitmapFactory.decodeFile(Const.IMAGE_PATH + file_name, options));
    }

    public static FloatingText getFloatingText(Activity activity, int color, int textSize, String text, int offsetX, int offsetY) {
        return new FloatingText.FloatingTextBuilder(activity)
                .textColor(color) // floating  text color
                .textSize(textSize)   // floating  text size
                .textContent(text) // floating  text content
                .offsetX(offsetX) // the x offset  relate to the attached view
                .offsetY(offsetY) // the y offset  relate to the attached view
                .floatingAnimatorEffect(new CurvePathFloatingAnimator()) // floating animation
                .floatingPathEffect(new CurveFloatingPathEffect()) // floating path
                .build();
    }

    public static DayObject getDayObject(Word word) {

        List<Pic> p = AppController.getPicDao().queryBuilder().where(PicDao.Properties.Word_id.eq(word.getId())).list();
        List<Pronunciation> pr = AppController.getPronunciationDao().queryBuilder().where(PronunciationDao.Properties.Word_id.eq(word.getId())).list();
        List<Sentence> sentences = AppController.getSentenceDao().queryBuilder().where(SentenceDao.Properties.Word_id.eq(word.getId())).list();
        List<WordForm> wordForms = AppController.getWordFormDao().queryBuilder().where(WordFormDao.Properties.Word_id.eq(word.getId())).list();

        if (pr.isEmpty())
            return new Utils.DayObject(word, p.get(0), null , sentences , wordForms);
        else
            return new Utils.DayObject(word, p.get(0), pr.get(0) , sentences , wordForms);
    }


    public static class DayObject {

        private final List<WordForm> wordForms;
        private Word mWord;
        private Pic mPic;
        private Pronunciation pronunciation;
        private List<Sentence> sentences ;

        public DayObject(Word mWord, Pic mPic, Pronunciation pronunciation, List<Sentence> sentences, List<WordForm> wordForms) {
            this.mWord = mWord;
            this.mPic = mPic;
            this.pronunciation = pronunciation;
            this.sentences = sentences;
            this.wordForms = wordForms;
        }

        public List<WordForm> getWordForms() {
            return wordForms;
        }

        public Word getWord() {
            return mWord;
        }

        public void setWord(Word mWord) {
            this.mWord = mWord;
        }

        public Pic getPic() {
            return mPic;
        }

        public void setPic(Pic mPic) {
            this.mPic = mPic;
        }

        public Pronunciation getPronunciation() {
            return pronunciation;
        }

        public void setPronunciation(Pronunciation pronunciation) {
            this.pronunciation = pronunciation;
        }


        public List<Sentence> getSentences() {
            return sentences;
        }

        public void setSentences(List<Sentence> sentences) {
            this.sentences = sentences;
        }
    }


    public static void backUpDatabase() {
        try {
            File sd = Environment.getExternalStorageDirectory();


            if (sd.canWrite()) {
                File currentDB = AppController.getAppContext().getDatabasePath("database.db");
                File parentDBDirec = new File(Const.DB_PATH);

                if (!parentDBDirec.exists()) {
                    parentDBDirec.mkdirs();
                }

                File backupDB = new File(parentDBDirec, "database.db");

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB).getChannel();
                    FileChannel dst = new FileOutputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();

                    Toast.makeText(AppController.getAppContext(), "فایل پشتیبان در کارت حافظه ذخیره شد.", Toast.LENGTH_SHORT).show();

                }
            }
        } catch (Exception e) {
            Toast.makeText(AppController.getAppContext(), "فایل پشتیبان در کارت حافظه ذخیره نشد.", Toast.LENGTH_SHORT).show();
        }
    }


    public static void restoreDatabase() {
        try {
            File sd = Environment.getExternalStorageDirectory();


            if (sd.canWrite()) {
                File currentDB = AppController.getAppContext().getDatabasePath("database.db");
                File parentDBDirec = new File(Const.DB_PATH);

                if (!parentDBDirec.exists()) {
                    parentDBDirec.mkdirs();
                }

                File backupDB = new File(parentDBDirec, "database.db");

                if (currentDB.exists() && backupDB.exists()) {
                    FileChannel dst = new FileOutputStream(currentDB).getChannel();
                    FileChannel src = new FileInputStream(backupDB).getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();

                    Toast.makeText(AppController.getAppContext(), "اطلاعات بازگردانی شد.", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(AppController.getAppContext(), "اطلاعات بازگردانی نشد.", Toast.LENGTH_SHORT).show();
        }
    }

}
