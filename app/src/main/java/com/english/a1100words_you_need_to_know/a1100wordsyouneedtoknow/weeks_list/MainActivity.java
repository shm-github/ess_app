package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.splash_screen.SplashActivity;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.CardPagerAdapter;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.CustomViewPager;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.ShadowTransformer;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.WeekFragmentPagerAdapter;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import util.Utils;

public class MainActivity extends AppCompatActivity {

    private CustomViewPager mWeeksViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private WeekFragmentPagerAdapter mFragmentWeekAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private DrawerBuilder builder;
    private Toolbar toolbar;
    private Drawer result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWeeksViewPager = (CustomViewPager) findViewById(R.id.weeksViewPager);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

//        Utils.backUpDatabase();
        Utils.restoreDatabase();


        initializeDrawer(savedInstanceState);

        mCardAdapter = new CardPagerAdapter();

//        for (int i = 0; i < 46; i++)
//            mCardAdapter.addCardItem(new CardItem(R.string.title_1 , R.string.text_1));


        mFragmentWeekAdapter = new WeekFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(3, this));



//        mCardShadowTransformer = new ShadowTransformer(mWeeksViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mWeeksViewPager, mFragmentWeekAdapter);


        mWeeksViewPager.setAdapter(mFragmentWeekAdapter);


        mWeeksViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);


        //نمایش view  به جای fragment
//        mWeeksViewPager.setAdapter(mCardAdapter);
//        mWeeksViewPager.setPageTransformer(false, mCardShadowTransformer);

        mWeeksViewPager.setOffscreenPageLimit(3);

        //اسکرول کردن با دست را غیر فعال یا فعال میکند.
        mWeeksViewPager.setPagingEnabled(true);

        //نمایش انیمیشن بزرگنمایی scale
//        mCardShadowTransformer.enableScaling(true);
        mFragmentCardShadowTransformer.enableScaling(true);

        //ورق زدن پیجر به هفته جاری
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWeeksViewPager.setCurrentItem(3, true);

            }
        }, 500);


    }


    private void initializeDrawer(Bundle savedInstanceState) {
        // Create the AccountHeader
//        headerResult = new AccountHeaderBuilder()
//                .withActivity(this)
//                .withTranslucentStatusBar(true)
//                .withHeaderBackground(R.drawable.header)
//
//                .withSavedInstance(savedInstanceState)
//                .build();

        builder = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHasStableIds(true)
//                .withAccountHeader(headerResult) //set the AccountHeader we created earlier for the header
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("خانه").withIcon(FontAwesome.Icon.faw_home).withIdentifier(1).withSelectable(false).withTintSelectedIcon(false),
                        new PrimaryDrawerItem().withName("لایتنر").withIcon(R.drawable.icon).withIdentifier(2).withSelectable(false),
                        new PrimaryDrawerItem().withName("تنظیمات").withIcon(GoogleMaterial.Icon.gmd_settings).withIdentifier(3).withSelectable(false),
                        new PrimaryDrawerItem().withName("اشتراک گذاری").withDescription("1100 را به دوستان خود توصیه کنید!").withIcon(CommunityMaterial.Icon.cmd_share_variant).withIdentifier(4).withSelectable(false)
                )
                // add the items we want to use with our Drawer
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

                        long i = drawerItem.getIdentifier();

                        if (i == 5) {
//                            if (Utils.isInternetAvailable(getApplicationContext())) {
//                                purchase.startPayProcess();
//                            }else{
//                                Toast.makeText(MainActivity.this, "اتصال به اینترنت امکان پذیر نیست!", Toast.LENGTH_SHORT).show();
//                            }
                        }

                        return false;
                    }
                })
                .withSavedInstance(savedInstanceState)
                .withShowDrawerOnFirstLaunch(false)
                .withCloseOnClick(true);


//        if (!purchase.active) {
//            builder.addDrawerItems(
//                    new DividerDrawerItem(),
//                    new PrimaryDrawerItem().withName("نسخه طلایی").withDescription("برای حذف محدودیت ها نسخه طلایی را خریداری نمایید.").withIcon(CommunityMaterial.Icon.cmd_coin).withIdentifier(5).withSelectable(false)
//
//            );
//        }


        result = builder.build();
    }


    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }


    public void createShortCut() {
//        SharedPrefs.putBooleanValue(Const.FIRST_TIME_FLAG, false);

        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "Jane Eyre");
        Parcelable icon = Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.drawable.icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);
        shortcutintent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, new Intent(getApplicationContext(), SplashActivity.class));
        sendBroadcast(shortcutintent);
    }

}

