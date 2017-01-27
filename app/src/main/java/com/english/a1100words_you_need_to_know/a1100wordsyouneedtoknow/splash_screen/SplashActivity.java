package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.splash_screen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.MainActivity;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import su.levenetc.android.textsurface.Text;
import su.levenetc.android.textsurface.TextBuilder;
import su.levenetc.android.textsurface.TextSurface;
import su.levenetc.android.textsurface.animations.ChangeColor;
import su.levenetc.android.textsurface.animations.Delay;
import su.levenetc.android.textsurface.animations.Parallel;
import su.levenetc.android.textsurface.animations.Sequential;
import su.levenetc.android.textsurface.animations.ShapeReveal;
import su.levenetc.android.textsurface.animations.SideCut;
import su.levenetc.android.textsurface.animations.Slide;
import su.levenetc.android.textsurface.animations.TransSurface;
import su.levenetc.android.textsurface.contants.Align;
import su.levenetc.android.textsurface.contants.Pivot;
import su.levenetc.android.textsurface.contants.Side;

@RuntimePermissions
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Text text1 = TextBuilder
                .create("1100")
                .setSize(60)
                .setAlpha(0)
                .setColor(Color.YELLOW)
                .setPosition(Align.SURFACE_CENTER).build();


        Text text2 = TextBuilder
                .create("WORDS")
                .setSize(45)
                .setAlpha(0)
                .setColor(Color.RED)
                .setPosition(Align.BOTTOM_OF, text1).build();


        Text text3 = TextBuilder
                .create(" You Need")
                .setSize(45)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.BOTTOM_OF, text2).build();


        Text text4 = TextBuilder
                .create("to Know")
                .setSize(45)
                .setAlpha(0)
                .setColor(Color.WHITE)
                .setPosition(Align.BOTTOM_OF, text3).build();


        Text text5 = TextBuilder
                .create("by")
                .setSize(25)
                .setAlpha(0)
                .setColor(Color.YELLOW)
                .setPosition(Align.BOTTOM_OF | Align.RIGHT_OF, text4).build();


        Text text6 = TextBuilder
                .create("Seyyed ")
                .setSize(30)
                .setAlpha(0)
                .setColor(Color.parseColor("#53c3a1"))
                .setPosition(Align.BOTTOM_OF | Align.CENTER_OF, text5).build();


        Text text7 = TextBuilder
                .create(" Hossein")
                .setSize(30)
                .setAlpha(0)
                .setColor(Color.parseColor("#53c3a1"))
                .setPosition(Align.BOTTOM_OF, text6).build();


        Text text8 = TextBuilder
                .create("  Mosavi")
                .setSize(30)
                .setAlpha(0)
                .setColor(Color.parseColor("#53c3a1"))
                .setPosition(Align.BOTTOM_OF, text7).build();

        TextSurface textSurface = (TextSurface) findViewById(R.id.textSurface);

        textSurface.play(
                new Sequential(
                        ShapeReveal.create(text1, 500, SideCut.show(Side.LEFT), false),
                        new Parallel(ShapeReveal.create(text1, 500, SideCut.hide(Side.LEFT), false), new Sequential(Delay.duration(300), ShapeReveal.create(text1, 500, SideCut.show(Side.LEFT), false))),
                        new Parallel(new TransSurface(300, text2, Pivot.CENTER), ShapeReveal.create(text2, 500, SideCut.show(Side.LEFT), false)),
                        Delay.duration(200),
                        new Parallel(
                                new Parallel(new TransSurface(900, text3, Pivot.CENTER), Slide.showFrom(Side.LEFT, text3, 500), ChangeColor.to(text3, 10, Color.WHITE)),
                                Delay.duration(300),
                                new Parallel(new TransSurface(900, text4, Pivot.CENTER), Slide.showFrom(Side.LEFT, text4, 500), ChangeColor.to(text4, 10, Color.WHITE))
                                ),
                        Delay.duration(1000),
                        new Parallel(new TransSurface(500, text5, Pivot.LEFT), Slide.showFrom(Side.LEFT, text5, 300), ChangeColor.to(text5, 10, Color.YELLOW)),
                        new Parallel(Slide.showFrom(Side.LEFT, text6, 900), Delay.duration(400), Slide.showFrom(Side.LEFT, text7, 900), Delay.duration(400), Slide.showFrom(Side.LEFT, text8, 1000), Delay.duration(400)),
                        Delay.duration(3000)
                )
        );


//        Slide.showFrom(Side.LEFT, text6,300) ,
//                Slide.showFrom(Side.RIGHT, text7,300) ,
//                Slide.showFrom(Side.BOTTOM, text8,300),
//                Delay.duration(1000)

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SplashActivityPermissionsDispatcher.startActivityWithCheck(SplashActivity.this);
            }
        }, 5000);


//        textSurface.play(
//                new Sequential(
//                        Slide.showFrom(Side.TOP, textDaai, 500),
//                        Delay.duration(500),
//                        Alpha.hide(textDaai, 1500)
//                )
//        );


    }




    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void startActivity(){
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRationaleForStartActivity(final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setMessage("اپلیکیشن برای خواندن اطلاعات از روی حافظه، به مجوز دسترسی به کارت حافظه نیاز دارد.")
                .setPositiveButton("اجازه دادن", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton("رد کردن", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .show();
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDeniedForStartActivity() {
        Toast.makeText(this, " درخواست مجوز خواندن از کارت حافظه رد شد، اپلیکیشن قادر به خواندن اطلاعات از کارت حافظه نیست.", Toast.LENGTH_LONG).show();
        finish();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showNeverAskForStartActivity() {
        Toast.makeText(this, "لطفا از قسمت مجوزها، مجوز دسترسی به کارت حافظه را فعال کنید. ", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        SplashActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
