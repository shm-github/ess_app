package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.screens;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.exercise_screen.adapter.MainPagerAdapter;
import com.gigamole.navigationtabstrip.NavigationTabStrip;

import org.antennapod.audio.MediaPlayer;
import org.antennapod.audio.SonicAudioPlayer;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.Locale;

import events.EventPlayComplete;
import events.EventPlaySound;
import events.EventPlaySoundEffect;
import events.EventTTSRequest;
import util.Const;

public class ExerciseActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private SonicAudioPlayer sonicAudioPlayer;
    private MediaPlayer mediaPlayer;
    private String currentPlayedFileName = "";
    private MediaPlayer mediaPlayerEffect;
    private SonicAudioPlayer sonicAudioPlayerEffect;

    Handler handler = new Handler();
    private TextToSpeech tts;
    private boolean ttsIsReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        tts = new TextToSpeech(getApplicationContext(), this);
        tts.setLanguage(Locale.US);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);


        final NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts);
        navigationTabStrip.setTitles("Vocabularies", "Exercise");
        navigationTabStrip.setViewPager(viewPager);
        viewPager.setCurrentItem(0);


        mediaPlayer = new MediaPlayer(getApplicationContext());
        sonicAudioPlayer = new SonicAudioPlayer(mediaPlayer, getApplicationContext());

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer arg0) {

                EventBus.getDefault().post(new EventPlayComplete());
            }
        });

        mediaPlayerEffect = new MediaPlayer(getApplicationContext());
        sonicAudioPlayerEffect = new SonicAudioPlayer(mediaPlayerEffect, getApplicationContext());
        sonicAudioPlayerEffect.setVolume(40, 40);

        File file = new File(Const.SOUND_PATH);
        file.mkdirs();
    }


    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    @Subscribe
    public void playSound(EventPlaySound event) {

        if (event.getFileName() == null)
            return;

        if (!currentPlayedFileName.equals(event.getFileName())) {
            File soundFile = new File(Const.SOUND_PATH + event.getFileName());
            if (soundFile.exists()) {
                sonicAudioPlayer.reset();
                sonicAudioPlayer.setDataSource(Const.SOUND_PATH + event.getFileName());
                sonicAudioPlayer.prepare();
            }
        }

        sonicAudioPlayer.start();

        currentPlayedFileName = event.getFileName();

    }


    @Subscribe
    public void playSoundEffect(final EventPlaySoundEffect event) {

        if (event.getFileName() == null)
            return;

        File soundFile = new File(Const.SOUND_PATH + event.getFileName());
        if (soundFile.exists()) {
            sonicAudioPlayerEffect.reset();
            sonicAudioPlayerEffect.setDataSource(Const.SOUND_PATH + event.getFileName());
            sonicAudioPlayerEffect.prepare();
        }

        sonicAudioPlayerEffect.start();

    }


    @Subscribe
    public void PlaySoundTTS(final EventTTSRequest request) {
        if (ttsIsReady)
            tts.speak(request.getText(), TextToSpeech.QUEUE_ADD, null);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (sonicAudioPlayer != null)
            sonicAudioPlayer.release();

        if (mediaPlayer != null)
            mediaPlayer.release();

        if (sonicAudioPlayerEffect != null)
            sonicAudioPlayerEffect.release();

        if (mediaPlayerEffect != null)
            mediaPlayerEffect.release();
    }


    @Override
    public void onInit(int status) {

        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("error", "This Language is not supported");
                ttsIsReady = false;
            } else {
                ttsIsReady = true;
            }
        } else
            Log.e("error", "Initilization Failed!");

    }
}
