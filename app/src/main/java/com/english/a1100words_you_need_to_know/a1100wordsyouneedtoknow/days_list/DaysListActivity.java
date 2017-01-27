package com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.days_list;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.R;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.days_list.days_pager.DayFragmentPagerAdapter;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.days_list.idiom_pager.IdiomFragmentPagerAdapter;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.CardPagerAdapter;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.CustomViewPager;
import com.english.a1100words_you_need_to_know.a1100wordsyouneedtoknow.weeks_list.weeks_pager.ShadowTransformer;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class DaysListActivity extends AppCompatActivity {

    private CustomViewPager mDayViewPager;
    private CustomViewPager mIdiomViewPager;
    private CardPagerAdapter mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private DayFragmentPagerAdapter mFragmentDayAdapter;
    private IdiomFragmentPagerAdapter mFragmentIdiomAdapter;
    private ShadowTransformer mFragmentCardShadowTransformer;
    private DrawerBuilder builder;
    private Toolbar toolbar;
    private Drawer result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_list);

        mDayViewPager = (CustomViewPager) findViewById(R.id.dayViewPager);
        mIdiomViewPager = (CustomViewPager) findViewById(R.id.idiomViewPager);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        initializeDrawer(savedInstanceState);

        mCardAdapter = new CardPagerAdapter();

//        for (int i = 0; i < 46; i++)
//            mCardAdapter.addCardItem(new CardItem(R.string.title_1 , R.string.text_1));


        mFragmentDayAdapter = new DayFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(3, this));

        mFragmentIdiomAdapter = new IdiomFragmentPagerAdapter(getSupportFragmentManager(),
                dpToPixels(3, this));


//        mCardShadowTransformer = new ShadowTransformer(mWeeksViewPager, mCardAdapter);
        mFragmentCardShadowTransformer = new ShadowTransformer(mDayViewPager, mFragmentDayAdapter);


        mDayViewPager.setAdapter(mFragmentDayAdapter);

        mIdiomViewPager.setAdapter(mFragmentIdiomAdapter);


        mDayViewPager.setPageTransformer(false, mFragmentCardShadowTransformer);


        //نمایش view  به جای fragment
//        mWeeksViewPager.setAdapter(mCardAdapter);
//        mWeeksViewPager.setPageTransformer(false, mCardShadowTransformer);

        mDayViewPager.setOffscreenPageLimit(3);

        //اسکرول کردن با دست را غیر فعال یا فعال میکند.
        mDayViewPager.setPagingEnabled(true);
        mIdiomViewPager.setPagingEnabled(false);

        //نمایش انیمیشن بزرگنمایی scale
//        mCardShadowTransformer.enableScaling(true);
        mFragmentCardShadowTransformer.enableScaling(true);

        //ورق زدن پیجر به هفته جاری
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDayViewPager.setCurrentItem(3, true);

            }
        }, 500);

        mDayViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mIdiomViewPager.setCurrentItem(position , true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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


}
