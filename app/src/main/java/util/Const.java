package util;

import android.os.Environment;

/**
 * Created by Hossein on 11/21/2016.
 */
public class Const {
    public static final String APP_FOLDER = "/1100_words/";
    public static final String EXTERNAL_FOLDER = Environment.getExternalStorageDirectory().toString();
    public static final String DB_PATH = EXTERNAL_FOLDER + APP_FOLDER + "db/";
    public static final String IMAGE_PATH = EXTERNAL_FOLDER + APP_FOLDER + "pics/";
    public static final String SOUND_PATH = EXTERNAL_FOLDER + APP_FOLDER + "sounds/";

    public static final String FIRST_TIME = "first_Time";

}
