package goodtogo.com.myquotes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.ClipboardManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import goodtogo.com.myquotes.fr.ScreenSlidePageFragment;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import goodtogo.com.myquotes.fr.ScreenSlidePageFragment;
import goodtogo.com.myquotes.model.LoveModel;

public class ShowActivity extends FragmentActivity {
    public static int WHICH_PAGE= 0;
    public static int get_current_page = 0;

    private int NUM_PAGE = 25;
    private PagerAdapter mPagerAdapter;
    private ViewPager mPager;
    private Button share_btn, next_btn, pre_btn, random_btn, copy_btn;
    TextView tv_show_index, label_app;
    RelativeLayout my_screen_bg;
    private InterstitialAd mInterstitialAd;
    private List<LoveModel> listLoveModel;
    private String FROM_SEARCH = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_show);

        mInterstitialAd = new InterstitialAd(this);
        //mInterstitialAd.setAdUnitId("ca-app-pub-3982140158032455/4391864522");
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        init();
        listLoveModel = new ArrayList<LoveModel>();
        getTitleAndPageFromMainClass();
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        WhichPageShouldShow();
        final Random rand = new Random();
        randomBackground() ;

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);

                showAds();
            }
        });
        pre_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPager.setCurrentItem(mPager.getCurrentItem() - 1 , true);
                showAds();
            }
        });
        share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                label_app.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "Share", Toast.LENGTH_SHORT).show();
                invisibleAll();
                Bitmap bitmap = takeScreenshot();
                saveBitmap(bitmap);
                shareIt();
                visibleAll();
                showNewAds();
            }
        });
        random_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int r = rand.nextInt(NUM_PAGE) + 1;
                mPager.setCurrentItem(r, true);
                randomBackground();
                showAds();
            }
        });
        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAds();
                copyToClipBoard();
            }
        });

        showNewAds();
    }


    private void init(){
        mPager = (ViewPager) findViewById(R.id.pager);
        share_btn = (Button) findViewById(R.id.btn_share);
        next_btn = (Button) findViewById(R.id.btn_next);
        pre_btn = (Button) findViewById(R.id.btn_previous);
        tv_show_index = (TextView) findViewById(R.id.tv_index);
        random_btn = (Button) findViewById(R.id.btn_random);
        my_screen_bg = (RelativeLayout) findViewById(R.id.main_layout);
        copy_btn = (Button) findViewById(R.id.btn_copy);
        label_app = (TextView) findViewById(R.id.labelApp);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            get_current_page = position;

            tv_show_index.setText(mPager.getCurrentItem() + "/" + NUM_PAGE);
            ScreenSlidePageFragment screenSlidePageFragment = new ScreenSlidePageFragment();
            Bundle b = new Bundle();
           // b.putString("KEY_STRING", "This is Page " + position);
            if ("".equals(FROM_SEARCH) || "ERROR".equals(FROM_SEARCH)){
                FROM_SEARCH =  listLoveModel.get(position).getItemText();
            }
            b.putString("KEY_STRING", FROM_SEARCH);
            FROM_SEARCH = "";
            showAds();
            //b.putString("KEY_STRING", listLoveModel.get(position).getItemText());
            screenSlidePageFragment.setArguments(b);
            return screenSlidePageFragment;

        }

        @Override
        public int getCount() {
            return NUM_PAGE;
        }
    }

    private void getTitleAndPageFromMainClass(){
        Bundle extras = getIntent().getExtras();
        int value= 0;
        String title = null;
        if (extras != null) {
             value = extras.getInt("pos",0);
             title = extras.getString("KEY_NAME", "text.json");
            //The key argument here must match that used in the other activity
        }
        mPager.setCurrentItem(value);
        //return "ERROR";
        ReadJson readJson = new ReadJson(getApplication(), title);
        listLoveModel = readJson.passJson();
        NUM_PAGE = listLoveModel.size();
    }
    private void WhichPageShouldShow(){
        Bundle extras = getIntent().getExtras();
        int value= 0;
        String isSearch = "";

        if (extras != null) {
            value = extras.getInt("pos",0);
            isSearch = extras.getString("isSearch", "ERROR");
            FROM_SEARCH = isSearch;
        }



        //Toast.makeText(getApplicationContext(), isSearch, Toast.LENGTH_SHORT).show();
        mPager.setCurrentItem(value);
    }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }
    File imagePath;
    public void saveBitmap(Bitmap bitmap) {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }

        private void shareIt() {
            Uri uri = Uri.fromFile(imagePath);
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("image/*");
            String shareBody = "screen shot";
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "From My App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);

            startActivity(Intent.createChooser(sharingIntent, getString(R.string.share_to)));
        }

    private void invisibleAll(){
        //share_btn, next_btn, pre_btn, random_btn;
        share_btn.setVisibility(View.INVISIBLE);
        next_btn.setVisibility(View.INVISIBLE);
        pre_btn.setVisibility(View.INVISIBLE);
        random_btn.setVisibility(View.INVISIBLE);
        share_btn.setVisibility(View.INVISIBLE);
        copy_btn.setVisibility(View.INVISIBLE);
        tv_show_index.setVisibility(View.INVISIBLE);

        label_app.setVisibility(View.VISIBLE);
    }

    private void visibleAll(){
        //share_btn, next_btn, pre_btn, random_btn;
        share_btn.setVisibility(View.VISIBLE);
        next_btn.setVisibility(View.VISIBLE);
        pre_btn.setVisibility(View.VISIBLE);
        random_btn.setVisibility(View.VISIBLE);
        share_btn.setVisibility(View.VISIBLE);
        copy_btn.setVisibility(View.VISIBLE);
        tv_show_index.setVisibility(View.VISIBLE);

        label_app.setVisibility(View.INVISIBLE);
    }


    // Copy EditCopy text to the ClipBoard
    private void copyToClipBoard() {
        ClipboardManager clipMan = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipMan.setText(listLoveModel.get(mPager.getCurrentItem()).getItemText());
        Toast.makeText(this,"Copied to Clipboard, You could pass it anywhere", Toast.LENGTH_SHORT ).show();
    }

    private void randomBackground() {
        final Random rand = new Random();

        int  n = rand.nextInt(50) + 1;
        if (n%2 == 0) {
            if (n > 42 && n < 50) {
                my_screen_bg.setBackgroundResource(R.drawable.bg_one);
            }else if(n > 28 && n < 42){
                my_screen_bg.setBackgroundResource(R.drawable.bg_sad_one);
            }else if(n > 11 && n < 28){
                my_screen_bg.setBackgroundResource(R.drawable.bg_love);
            }
            else{
                my_screen_bg.setBackgroundResource(R.drawable.bg_sad);
            }
        }
        else{
            if (n > 42 && n < 50) {
                my_screen_bg.setBackgroundResource(R.drawable.bg_flower);
            }else if(n > 28 && n < 42){
                my_screen_bg.setBackgroundResource(R.drawable.bg_angry);
            }else if (n> 18 && n < 28){
                my_screen_bg.setBackgroundResource(R.drawable.bg_second);
            }
            else if(n > 11 && n < 18){
                my_screen_bg.setBackgroundResource(R.drawable.bg_love);
            }
            else{
                my_screen_bg.setBackgroundResource(R.drawable.bg_two);
            }
        }
    }

    private void showAds(){
        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();
    }
    private void showNewAds(){
        mInterstitialAd = new InterstitialAd(getApplicationContext());
        //mInterstitialAd.setAdUnitId("ca-app-pub-3982140158032455/4391864522");
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();
    }

    private int sss(int a, int b){
        return a>b? a:b ;
    }
}
