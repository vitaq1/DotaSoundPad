package com.incite.dotatisoundboard;


import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Arrays;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    final static private int TIMES_TO_NEXT_AD = 20;

    private int buttonWidth;
    private int buttonHeight;
    GridLayout grid;


    private SoundPool mSound;
    private int mPlay;
    MediaPlayer mp;

    int pixelsMarginSides;
    int pixelsMarginTop;


    Drawable heartOff = null;
    Drawable heartOn = null;
    float scale;


    private int soundDuration;

    boolean playing = false;
    private boolean favPicked = false;
    LottieAnimationView lottieAnimationView;
    LottieAnimationView favAnim;
    LottieAnimationView heartAnim;
    LottieAnimationView progress;

    Typeface font;

    private int adCounter = 1;


    private String[][] soundMap = {{"10억짜리 꿈의 고리! 15억짜리 꿈의 고리!", String.valueOf(R.raw.s10)},{"Absolutely Perfect", String.valueOf(R.raw.abs)},{"Brutal. Savage. Rekt.", String.valueOf(R.raw.brutal)},{"Ceeeb", String.valueOf(R.raw.ceeeb)},{"99%", String.valueOf(R.raw.s99)},{"Just disappointed", String.valueOf(R.raw.just_dissapointed)},{"Coming through with the wooooo", String.valueOf(R.raw.coming)},{"Ding Ding Ding Mother", String.valueOf(R.raw.ding)},{"Easiest money of my life!", String.valueOf(R.raw.easiest)},{"Echo Slamma Jamma!", String.valueOf(R.raw.echo)},{"Eughahaha Aah Ha Ha Ha Ha", String.valueOf(R.raw.eugh)},{"It's looking Spicy!", String.valueOf(R.raw.spicy)},{"Lakad Matataaag! Normalin, Normalin.", String.valueOf(R.raw.lakad)},{"Let's Play Some Dota", String.valueOf(R.raw.lets)},{"Mommy! I got me! Immortality!", String.valueOf(R.raw.mommy)},{"NAKUPUU", String.valueOf(R.raw.naku)},{"Oh my god. What Oh! Oh!", String.valueOf(R.raw.omg)},{"Oyoy oy oy, oy, oy, oy, oy, oy!", String.valueOf(R.raw.oy)},{"Roshan! Roshan! Roshan!", String.valueOf(R.raw.rosh)},{"See you later nerds", String.valueOf(R.raw.nerds)},{"Sir Chef. Goodness Gracious Palpable.", String.valueOf(R.raw.sir)},{"That's playing to win, baby!", String.valueOf(R.raw.playing)},{"The next level play!", String.valueOf(R.raw.next)},{"There is nothing that can stop this man", String.valueOf(R.raw.there)},{"They're all dead!", String.valueOf(R.raw.dead)},{"This guy has no chill", String.valueOf(R.raw.chill)},{"Waow", String.valueOf(R.raw.waoh)},{"What the * just happened", String.valueOf(R.raw.hwat)},{"You know what's cooking BOOM!", String.valueOf(R.raw.boom)},{"А нет, нет, ДА!", String.valueOf(R.raw.anet)},{"А ну-ка иди-ка сюда. А вот все. Все.", String.valueOf(R.raw.anuka)},{"Ай-ай-ай-ай-ай, что сейчас произошло!", String.valueOf(R.raw.ayay)},{"Бом", String.valueOf(R.raw.bom)},{"Вот Это Врыв!", String.valueOf(R.raw.vriv)},{"Вуп-вуп", String.valueOf(R.raw.vyp)},{"Гром оркестра", String.valueOf(R.raw.grom)},{"Да Да Да Нет.", String.valueOf(R.raw.dada)},{"Жил до конца, умер как герой", String.valueOf(R.raw.jil)},{"Иа!", String.valueOf(R.raw.ia)},{"Как же это сочно, ах!", String.valueOf(R.raw.sochno)},{"Красавчик!", String.valueOf(R.raw.kras)},{"Ой-ой-ой-ой-ой, бежать!", String.valueOf(R.raw.oioi)},{"Му", String.valueOf(R.raw.my)},{"Резать Резать Резать Резать", String.valueOf(R.raw.rezat)},{"Такая халява...Ха!", String.valueOf(R.raw.halyava)},{"Упс", String.valueOf(R.raw.ups)},{"Ух, это такая душка!", String.valueOf(R.raw.dushka)},{"Что это! Какая жесть!", String.valueOf(R.raw.jest)},{"Это. Просто. Нечто", String.valueOf(R.raw.nechto)},{"Это ГГ", String.valueOf(R.raw.gg)},{"Это ненормально, это нечестно!", String.valueOf(R.raw.nech)},{"Ё! А А Что!", String.valueOf(R.raw.ee)},{"你气不气", String.valueOf(R.raw.s7)},{"你行你行，你上你上", String.valueOf(R.raw.s8)},{"再见了宝贝儿！", String.valueOf(R.raw.s9)},{"加油!", String.valueOf(R.raw.s11)},{"唉唉唉！唉？唉 …", String.valueOf(R.raw.s12)},{"啊，队友呢？队友呢？队友呢？！队友呢？！？！", String.valueOf(R.raw.s13)},{"天火！", String.valueOf(R.raw.s14)},{"干嘛呢兄弟", String.valueOf(R.raw.s15)},{"我是一个没有感情的刷钱机器", String.valueOf(R.raw.s16)},{"来，你打我呀！", String.valueOf(R.raw.s17)},{"毁天灭地, NICE啊！", String.valueOf(R.raw.s18)},{"漂~ 亮！", String.valueOf(R.raw.s19)},{"玩不了啦!", String.valueOf(R.raw.s20)},{"脸都秀歪啦", String.valueOf(R.raw.s21)},{"走好，不送", String.valueOf(R.raw.s22)},{"跳走了", String.valueOf(R.raw.s23)},{"这波不亏, 666", String.valueOf(R.raw.s24)},{"골드 부활 못 해! YOLO! 골드 부활 못 해! YOLO!", String.valueOf(R.raw.s25)},{"네, 노, 네, 노 노 노!", String.valueOf(R.raw.s26)},{"스캔 돌려야 되요 따라란따란따라라라란 스캔 돌려야 된다고요!", String.valueOf(R.raw.s27)}, };
    private String[] temp = new String[soundMap.length];
    private String[][] sortedSoundMap = new String[soundMap.length][2];
    private Button[] buttons = new Button[soundMap.length];

    private String favInfo = "";

    private AdView mAdView;
    private InterstitialAd mInterstitialAd;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        grid = findViewById(R.id.grid);
        //pb = findViewById(R.id.progressBar);
        mSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        lottieAnimationView = findViewById(R.id.stopButton);
        favAnim = findViewById(R.id.favoritesAnim);
        heartAnim = findViewById(R.id.heartAnim);
        progress = findViewById(R.id.progress);

        //alf order
        for (int i = 0; i < soundMap.length; i++) {
            temp[i] = soundMap[i][0];
        }
        Arrays.sort(temp);
        for (int i = 0; i < soundMap.length; i++) {
            sortedSoundMap[i][0] = temp[i];
        }
        for (int i = 0; i < soundMap.length; i++) {
            for (String[] strings : soundMap) {
                if (sortedSoundMap[i][0].equals(strings[0])) {
                    sortedSoundMap[i][1] = strings[1];
                    break;
                }
            }
        }
        System.out.println(Arrays.deepToString(sortedSoundMap));
        //
        //ad 1
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("57EAAA7D731CEF0F24F49FCD11D83D7D")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .addTestDevice("9D7297F1609AE4A80DF55790DFEB6A0F")
                .build();
        mAdView.loadAd(adRequest);
        //
        //ad2
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4299246211131486/8763672680");
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("57EAAA7D731CEF0F24F49FCD11D83D7D")
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });
        //
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        scale = this.getResources().getDisplayMetrics().density;
        pixelsMarginSides = (int) (10 * scale);
        pixelsMarginTop = (int) (30 * scale);
        buttonWidth = (size.x  / 2) - 2*pixelsMarginSides;
        buttonHeight = buttonWidth/3;

        heartOff = this.getResources().getDrawable(R.drawable.heart_off);
        heartOn = this.getResources().getDrawable(R.drawable.heart_on);
        heartOff.setBounds(0, 0, 95, 95);
        heartOn.setBounds(0, 0, 95, 95);

        //
        font = ResourcesCompat.getFont(this, R.font.venus);
        for (int i = 0; i < sortedSoundMap.length; i++) {
            final Button b = new Button(this);
            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
            lp.width = buttonWidth;
            lp.height = buttonHeight;
            lp.rowSpec = GridLayout.spec(0, 1f);
            if ((i) % 3 == 0) {

                lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);

            }
            if ((i - 1) % 3 == 0) {

                lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);

            }
            if ((i - 2) % 3 == 0) {

                lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);

            }

            b.setLayoutParams(lp);
            b.setBackgroundResource(R.drawable.button_selector);
            b.setCompoundDrawables(heartOff, null, null, null);
            b.setText(sortedSoundMap[i][0]);
            b.setTextColor(getResources().getColor(R.color.colorWhite));
            b.setTypeface(font);
            b.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            b.setPadding(10, 10, 10, 10);
            b.setTextSize(10);
            b.setId(i + 1);
            b.setOnClickListener(this);
            b.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    final int DRAWABLE_LEFT = 0;
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        if (event.getX() < (b.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                            System.out.println("picked");
                            if (b.getContentDescription() == "off") {
                                b.setCompoundDrawables(heartOn, null, null, null);
                                b.setContentDescription("on");
                            } else {
                                b.setCompoundDrawables(heartOff, null, null, null);
                                b.setContentDescription("off");
                                if(grid.getChildCount()<sortedSoundMap.length-5){
                                    /////
                                    grid.removeAllViews();
                                    int k = 0;
                                    for (int i = 0; i < sortedSoundMap.length; i++) {
                                        if (buttons[i].getContentDescription() == "on") {
                                            final Button b = buttons[i];
                                            GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                                            lp.width = buttonWidth;
                                            lp.height = buttonHeight;
                                            lp.rowSpec = GridLayout.spec(0, 1f);

                                            if ((k) % 3 == 0) {
                                                lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                                            }
                                            if ((k - 1) % 3 == 0) {
                                                lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                                            }
                                            if ((k - 2) % 3 == 0) {
                                                lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                                            }

                                            b.setLayoutParams(lp);
                                            b.setBackgroundResource(R.drawable.button_selector);

                                            b.setCompoundDrawables(heartOff, null, null, null);


                                            b.setText(sortedSoundMap[i][0]);
                                            b.setTextColor(getResources().getColor(R.color.colorWhite));
                                            b.setTypeface(font);
                                            b.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                            b.setPadding(10, 10, 10, 10);
                                            b.setTextSize(10);
                                            b.setId(i + 1);

                                            b.setContentDescription(buttons[i].getContentDescription());
                                            if (b.getContentDescription() == "off") {
                                                b.setCompoundDrawables(heartOff, null, null, null);
                                            } else {
                                                b.setCompoundDrawables(heartOn, null, null, null);
                                            }
                                            grid.addView(b);
                                            k++;
                                        }
                                    }

                                    /////
                                }
                            }
                            return false;
                        }
                    }
                    return false;
                }
            });


            b.setContentDescription("off");
            buttons[i] = b;

            grid.addView(b);
        }
        loadFavs();
        checkFavsOnStart();
    }

    @Override
    public void onClick(View view) {
        if (adCounter % TIMES_TO_NEXT_AD == 0) {
            adCounter++;
            stopPlaying(findViewById(R.id.stopButton));
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            } else {
                Log.d("TAG", "The interstitial wasn't loaded yet.");
            }
        } else {
            adCounter++;
            for (int i = 1; i <= sortedSoundMap.length; i++) {
                if(view.getId()==i){
                    playSound(i);
                    break;
                }
            }
        }
    }

    public void startProgress(){
        playing = true;
        double k = 2560/(double)soundDuration;
        progress.setSpeed((float) k);
        progress.playAnimation();
    }

    public void playSound(final int i) {
        stopPlaying(findViewById(R.id.stopButton));
        mPlay = mSound.load(this, Integer.parseInt(sortedSoundMap[i - 1][1]), 1);
        mp = MediaPlayer.create(this, Integer.parseInt(sortedSoundMap[i - 1][1]));
        try {
            soundDuration = mp.getDuration();
        }
        catch (NullPointerException e){
            System.out.println(e.getMessage());
            soundDuration = 1500;
        }
        mSound.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                mSound.play(mPlay, 1, 1, 1, 0, 1);
                mSound.unload(Integer.parseInt(sortedSoundMap[i - 1][1]));
                mp.release();
                progress.playAnimation();
                startProgress();
            }
        });
    }

    public void stopPlaying(View view) {
        lottieAnimationView.setSpeed(1.5f);
        lottieAnimationView.playAnimation();
        if (playing) {
            playing = false;
            mSound.stop(mPlay);
            progress.setProgress(0f);
            progress.cancelAnimation();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void checkFavState(View view) {
        int k = 0;
        grid.removeAllViewsInLayout();
        heartAnim.playAnimation();
        if (favPicked) {
            favAnim.setMinAndMaxProgress(0.5f, 1f);
            favAnim.setSpeed(2f);
            favAnim.playAnimation();
            favPicked = !favPicked;
            //////

            for (int i = 0; i < sortedSoundMap.length; i++) {
                final Button b = buttons[i];
                GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                lp.width = buttonWidth;
                lp.height = buttonHeight;
                lp.rowSpec = GridLayout.spec(0, 1f);

                if ((i) % 3 == 0) {
                    lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                }
                if ((i - 1) % 3 == 0) {
                    lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                }
                if ((i - 2) % 3 == 0) {
                    lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                }

                b.setLayoutParams(lp);
                b.setBackgroundResource(R.drawable.button_selector);

                b.setCompoundDrawables(heartOff, null, null, null);


                b.setText(sortedSoundMap[i][0]);
                b.setTextColor(getResources().getColor(R.color.colorWhite));
                b.setTypeface(font);
                b.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                b.setPadding(10, 10, 10, 10);
                b.setTextSize(10);
                b.setId(i + 1);

                b.setOnClickListener(this);


                b.setContentDescription(buttons[i].getContentDescription());
                if (b.getContentDescription() == "off") {
                    b.setCompoundDrawables(heartOff, null, null, null);
                } else {
                    b.setCompoundDrawables(heartOn, null, null, null);
                }
                grid.addView(b);
            }
            //////
        } else {
            favAnim.setMinAndMaxProgress(0f, 0.5f);
            favAnim.setSpeed(2f);
            favAnim.playAnimation();
            favPicked = !favPicked;
            //////
            grid.setColumnCount(2);
            k = 0;
            grid.removeAllViews();

            for (int i = 0; i < sortedSoundMap.length; i++) {
                if (buttons[i].getContentDescription() == "on") {
                    final Button b = buttons[i];
                    GridLayout.LayoutParams lp = new GridLayout.LayoutParams();
                    lp.width = buttonWidth;
                    lp.height = buttonHeight;
                    lp.rowSpec = GridLayout.spec(0, 1f);

                    if ((k) % 3 == 0) {
                        lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                    }
                    if ((k - 1) % 3 == 0) {
                        lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                    }
                    if ((k - 2) % 3 == 0) {
                        lp.setMargins(pixelsMarginSides, pixelsMarginTop - 12, pixelsMarginSides, 0);
                    }

                    b.setLayoutParams(lp);
                    b.setBackgroundResource(R.drawable.button_selector);

                    b.setCompoundDrawables(heartOff, null, null, null);


                    b.setText(sortedSoundMap[i][0]);
                    b.setTextColor(getResources().getColor(R.color.colorWhite));
                    b.setTypeface(font);
                    b.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    b.setPadding(10, 10, 10, 10);
                    b.setTextSize(10);
                    b.setId(i + 1);

                    b.setOnClickListener(this);


                    b.setContentDescription(buttons[i].getContentDescription());
                    if (b.getContentDescription() == "off") {
                        b.setCompoundDrawables(heartOff, null, null, null);
                    } else {
                        b.setCompoundDrawables(heartOn, null, null, null);
                    }
                    grid.addView(b);
                    k++;
                }
            }


            //////////
        }
    }
    SharedPreferences sPref;

    final String SAVED_TEXT = "saved_text";
    void saveFavs() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();

        for (int i = 0; i < sortedSoundMap.length; i++) {
            if(buttons[i].getContentDescription()=="on"){
                favInfo+="1";
            }
            else favInfo+="0";
        }

        ed.putString(SAVED_TEXT, favInfo);
        ed.apply();
        System.out.println(favInfo);
    }

    void loadFavs() {
        String defaultString = "";
        for (int i = 0; i < sortedSoundMap.length; i++) {
            defaultString+="0";
        }
        sPref = getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, defaultString);

        while (savedText.length()<soundMap.length){
            savedText+="0";
        }

        for (int i = 0; i < sortedSoundMap.length; i++) {
            if(savedText.charAt(i)=='1'){
                buttons[i].setContentDescription("on");
            }
            else buttons[i].setContentDescription("off");
        }
        System.out.println(savedText);
    }

    void checkFavsOnStart(){
        for (int i = 0; i < sortedSoundMap.length; i++) {
            if (buttons[i].getContentDescription() == "off") {
                buttons[i].setCompoundDrawables(heartOff, null, null, null);
            } else {
                buttons[i].setCompoundDrawables(heartOn, null, null, null);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveFavs();
        stopPlaying(findViewById(R.id.stopButton));
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveFavs();
        stopPlaying(findViewById(R.id.stopButton));
    }
}
