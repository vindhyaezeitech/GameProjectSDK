package com.today.gamesdk.shabdamsdk.ui.englishShabdam.englishGameActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.material.navigation.NavigationView;
import com.today.gamesdk.shabdamsdk.Constants;
import com.today.gamesdk.shabdamsdk.GameActivity;
import com.today.gamesdk.shabdamsdk.GameDataManager;
import com.today.gamesdk.shabdamsdk.GamePresenter;
import com.today.gamesdk.shabdamsdk.GameView;
import com.today.gamesdk.shabdamsdk.R;
import com.today.gamesdk.shabdamsdk.ToastUtils;
import com.today.gamesdk.shabdamsdk.Utils;
import com.today.gamesdk.shabdamsdk.db.Task;
import com.today.gamesdk.shabdamsdk.event.CleverTapEvent;
import com.today.gamesdk.shabdamsdk.event.CleverTapEventConstants;
import com.today.gamesdk.shabdamsdk.model.GetWordRequest;
import com.today.gamesdk.shabdamsdk.model.SignupRequest;
import com.today.gamesdk.shabdamsdk.model.dictionary.CheckWordDicRequest;
import com.today.gamesdk.shabdamsdk.model.gamesubmit.SubmitGameRequest;
import com.today.gamesdk.shabdamsdk.model.getwordresp.Datum;
import com.today.gamesdk.shabdamsdk.model.statistics.Data;
import com.today.gamesdk.shabdamsdk.pref.CommonPreference;
import com.today.gamesdk.shabdamsdk.ui.activity.ShabdamActivity;
import com.today.gamesdk.shabdamsdk.ui.activity.ShabdamLeaderBoardActivity;
import com.today.gamesdk.shabdamsdk.ui.activity.ShabdamSettingsActivity;
import com.today.gamesdk.shabdamsdk.ui.activity.UserDetailActivity;
import com.today.gamesdk.shabdamsdk.utils.ShabdamLanguagePreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Main Activity
 */
public class EnglishGameActivity extends AppCompatActivity implements View.OnClickListener, GameView,  Animator.AnimatorListener {

   private MediaPlayer mediaPlayer;
   private MediaPlayer mediaPlayerGameComplete;
   private MediaPlayer mediaPlayerWrongAnswer;

    public static final int MAX_CHAR_LENGTH = 5;
    public static final int MAX_ATTEMPT = 6;
    private static final int RC_SIGN_IN = 1;
    private final int MAX_INDEX = 16;
    private final int UPDATE_REQUEST_CODE = 1612;
    char[] word_array = new char[5];
    char[] entered_word_array = new char[5];
    //StringBuilder[] matra = new StringBuilder[5];
    char[] charArray;
    Animation animBlink;
    private Boolean hintToastCounter = false;
    int blinkCount;
    Animator.AnimatorListener animatorListener;
    List<String> list = new ArrayList<>();
    GoogleSignInClient mGoogleSignInClient;
    private LinearLayout lLayMain;
    private ArrayList<Integer> btnIdList = new ArrayList<>();
    private String gameResult;
    private Boolean isHintTaken = false;
    private DrawerLayout drawerLayout;
    private ImageView ivHamburger;
    private ImageView iv_LangChange;

    private CLICK_ITEM click_item;
    private int[] keyIdArray = {R.id.tv_Q, R.id.tv_W, R.id.tv_E, R.id.tv_R, R.id.tv_T,
            R.id.tv_Y, R.id.tv_U, R.id.tv_I, R.id.tv_O, R.id.tv_P,
            R.id.tv_A, R.id.tv_S, R.id.tv_D, R.id.tv_F, R.id.tv_G,
            R.id.tv_H, R.id.tv_J, R.id.tv_K, R.id.tv_L, R.id.tv_Z,
            R.id.tv_X, R.id.tv_C, R.id.tv_V, R.id.tv_B, R.id.tv_N,R.id.tv_M,};

    private int index = 0;
    private int currentAttempt = 1;
    private String correctWord = "ENGLI";
    private int hintCount = 0;

    private TextView tvCross;
    private TextView tvEnter;
    private RelativeLayout rl_uttar_dekho_btn, continue_btn, agla_shabd_btn, rl_share_btn,rl_hint,rLayUttarDekho;
    private GamePresenter gamePresenter;
    private FrameLayout flLoading;
    private boolean mTimingRunning;
    private TextView tv_played, tv_win, tv_current_streak, tv_max_streak, tv_timer_counter_text;
    private Chronometer tv_timer_text;
    private long pauseOffset, minutes, seconds;
    private String minute, second;
    private Datum datumCorrectWord;
    private InterstitialAd mInterstitialAd;
    private Animation shakeAnimation;
    private boolean isUttarDekheClicked;
    private RewardedAd mRewardedAd;
    private Handler handler = new Handler();
    private boolean isHintPressed;
    private LinearLayout ll_google_sign_in, lLayEnglishAnswer, lLayHindiAnswer;
    private TextView tv_google_sign_in;
    private ImageView ivHome;
    private AlertDialog alertDialog;
    private AdRequest adRequest;

    private LinearLayout lLayTvToday;
    private LinearLayout lLayTutorial;
    private LinearLayout lLayShare;
    private LinearLayout lLaySetting;
    private LinearLayout lLayPrivacy;
    private LinearLayout lLayTerms;
    private TextView nav_name;

    private AnimatorSet flipOutAnimatorSet;

    private AnimatorSet flipInAnimatorSet;

    //visibleView.visible()
    private AnimatorSet flipOutAnimatorSet2;

    private AnimatorSet flipInAnimatorSet2;
    //visibleView.visible()
    private AnimatorSet flipOutAnimatorSet3;

    private AnimatorSet flipInAnimatorSet3;

    private AnimatorSet flipOutAnimatorSet4;

    private AnimatorSet flipInAnimatorSet4;

    private AnimatorSet flipOutAnimatorSet5;

    private AnimatorSet flipInAnimatorSet5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_english_game);
        initAnimation();
        adRequest = new AdRequest.Builder().build();
        rLayUttarDekho = findViewById(R.id.rLayUttarDekho);

        ivHamburger = findViewById(R.id.ivHamburger);
        ivHamburger.setOnClickListener(this);

        iv_LangChange = findViewById(R.id.iv_LangChange);
        iv_LangChange.setOnClickListener(this);


        NavigationView navigationView = (NavigationView) findViewById(R.id.navView);
        View header = navigationView.getHeaderView(0);
        drawerLayout = findViewById(R.id.drawerLayout);

        lLayTerms = header.findViewById(R.id.lLayTerms);
        lLayPrivacy = header.findViewById(R.id.lLayPrivacy);
        lLaySetting = header.findViewById(R.id.lLaySetting);
        lLayShare = header.findViewById(R.id.lLayShare);
        lLayTutorial = header.findViewById(R.id.lLayTutorial);
        lLayTvToday = header.findViewById(R.id.lLayTvToday);

        nav_name = header.findViewById(R.id.nav_name);

        if(CommonPreference.getInstance(this).getString(CommonPreference.Key.NAME) !=null){
            nav_name.setText(CommonPreference.getInstance(this).getString(CommonPreference.Key.NAME));
        }
        lLayTvToday.setOnClickListener(this);

        lLayTutorial.setOnClickListener(this);
        lLayShare.setOnClickListener(this);
        lLaySetting.setOnClickListener(this);
        lLayPrivacy.setOnClickListener(this);
        lLayTerms.setOnClickListener(this);

        lLayMain = findViewById(R.id.lLayMain);
        mediaPlayer = MediaPlayer.create(this, R.raw.peaceful_garden_healing);
        mediaPlayerGameComplete = MediaPlayer.create(this, R.raw.game_complete);
        mediaPlayerWrongAnswer = MediaPlayer.create(this, R.raw.for_error_music);

        if(CommonPreference.getInstance(EnglishGameActivity.this).getSoundState()){
            mediaPlayer.start();
        }else {
            mediaPlayer.stop();
        }

        interstitialAdd();
        if(hintCount < 2){
            initRewardAdd();
        }
        googleSignIn();

        if (!TextUtils.isEmpty(CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.NAME))) {
            ((TextView) findViewById(R.id.tv_uname)).setText(CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.NAME));
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        mAdView.loadAd(adRequest);

        animBlink = AnimationUtils.loadAnimation(this,
                R.anim.blink);
        initViewClick();

        gamePresenter = new GamePresenter(this, EnglishGameActivity.this);

       /* if (!TextUtils.isEmpty(userId)) {
            AddUserRequest request = new AddUserRequest();
            request.setUserId(userId);
            request.setName(name);
            request.setUname(u_name);
            request.setEmail(email);
            request.setProfileimage(profile_image);
            gamePresenter.addUser(request);
        }*/

        if (gamePresenter != null) {
            GetWordRequest getWordRequest = new GetWordRequest();
            getWordRequest.setUserId(CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
            getWordRequest.setLanguageId("2");
           gamePresenter.fetchNewWord(EnglishGameActivity.this, getWordRequest);
          //  gamePresenter.fetchEnglish();
        }
      /*  if (!CommonPreference.getInstance(GameActivity.this).getBoolean(CommonPreference.Key.IS_FIRST_TIME)) {
            CommonPreference.getInstance(GameActivity.this).put(CommonPreference.Key.IS_FIRST_TIME, true);
            kaiseKhelePopup();
        }*/
    }

    private void  initAnimation(){

        flipOutAnimatorSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_out
                );

        flipInAnimatorSet =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_in
                );

        flipOutAnimatorSet2 =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_out
                );

        flipInAnimatorSet2 =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_in
                );

        flipOutAnimatorSet3 =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_out
                );

        flipInAnimatorSet3 =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_in
                );

        flipOutAnimatorSet4 =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_out
                );

        flipInAnimatorSet4 =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_in
                );

        flipOutAnimatorSet5 =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_out
                );

        flipInAnimatorSet5 =
                (AnimatorSet) AnimatorInflater.loadAnimator(
                        EnglishGameActivity.this,
                        R.animator.flip_in
                );
    }


    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }*/
   private void openDrawer(){
       drawerLayout.openDrawer(GravityCompat.START);
   }

    private void closeDrawer(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == UPDATE_REQUEST_CODE){
            if(requestCode != RESULT_OK){
                finish();
            }
        }
    }

    private void handleSignInResult(com.google.android.gms.tasks.Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();
                // CommonPreference.getInstance(UserDetailActivity.this).put(CommonPreference.Key.EMAIL, personEmail);

                SignupRequest signupRequest = new SignupRequest();
                signupRequest.setEmail(personEmail);
                signupRequest.setProfileimage(String.valueOf(personPhoto));
                signupRequest.setName(personName);
                signupRequest.setUname(personName);
                signupRequest.setUserId("");

                Utils.saveUserData(EnglishGameActivity.this, personName, personName, personEmail, String.valueOf(personPhoto));
                CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.SP_GOOGLE_LOGIN);

                if (gamePresenter != null) {
                    gamePresenter.signUpUser(signupRequest);
                }

                //Toast.makeText(this, ""+personEmail, Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(this, ShabdamSplashActivity.class));

        } catch (ApiException e) {
            Log.d("Message", e.toString());
        }
    }



    @Override
    public void onAddUser(com.today.gamesdk.shabdamsdk.model.adduser.Data data) {
        if (data != null) {
            if (data.getId() != "0") {
                CommonPreference.getInstance(this.getApplicationContext()).put(CommonPreference.Key.GAME_USER_ID, String.valueOf(data.getId()));
                ll_google_sign_in.setVisibility(View.GONE);
                if (!TextUtils.isEmpty(CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.NAME))) {
                    ((TextView) findViewById(R.id.tv_uname)).setText(CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.NAME));
                }
                callgetStreakAPI();
            }
        }
    }


    private void interstitialAdd() {

       /* MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });*/

        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, Constants.INTRESTITIAL_AD_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        //Log.i(TAG, "onAdLoaded");
                        // loadAdd();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        //Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });
    }

    private void loadAdd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(this);
            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdDismissedFullScreenContent() {
                    super.onAdDismissedFullScreenContent();
                    if (click_item == CLICK_ITEM.HINT) {
                        showHint();
                    } else if (click_item == CLICK_ITEM.AGLA_SHABD) {
                        openAglaShabd();
                    } else if (click_item == CLICK_ITEM.UTTAR_DEKHO) {
                        openUttarDekho();
                    }
                    interstitialAdd();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    if (click_item == CLICK_ITEM.HINT) {
                        showHint();
                    } else if (click_item == CLICK_ITEM.AGLA_SHABD) {
                        openAglaShabd();
                    } else if (click_item == CLICK_ITEM.UTTAR_DEKHO) {
                        openUttarDekho();
                    }
                    interstitialAdd();
                }
            });
        } else {
            if (click_item == CLICK_ITEM.HINT) {
                showHint();
            } else if (click_item == CLICK_ITEM.AGLA_SHABD) {
                openAglaShabd();
            } else if (click_item == CLICK_ITEM.UTTAR_DEKHO) {
                openUttarDekho();
            }
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
           if(rl_uttar_dekho_btn != null){
               rl_uttar_dekho_btn.setEnabled(true);
           }

        }
    }

    private void openUttarDekho() {
        if (mTimingRunning) {
            tv_timer_text.stop();
            pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text.getBase();
            mTimingRunning = false;
            minutes = TimeUnit.MILLISECONDS.toMinutes(pauseOffset);
            seconds = TimeUnit.MILLISECONDS.toSeconds(pauseOffset) % 60;
            minute = String.format("%02d", minutes);
            second = String.format("%02d", seconds);
        }

        Intent intent = new Intent(this, ShabdamActivity.class);
        intent.putExtra("word", correctWord);
        intent.putExtra("minute", minute);
        intent.putExtra("second", second);
        intent.putExtra("currentAttempt", String.valueOf(currentAttempt));
        startActivity(intent);
        finish();
    }

    private void openAglaShabd() {
        Intent intent = new Intent(EnglishGameActivity.this, EnglishGameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("user_id", CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
        intent.putExtra("name", CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.NAME));
        intent.putExtra("uname", CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.UNAME));
        intent.putExtra("email", CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.EMAIL));
        intent.putExtra("profile_image", CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.PROFILE_IMAGE));
        startActivity(intent);
        finish();
    }

    private void initRewardAdd() {
        AdRequest adRequest = new AdRequest.Builder().build();

        RewardedAd.load(this, Constants.REWARD_AD_ID,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        // Log.d(TAG, loadAdError.getMessage());
                       // Toast.makeText(this,)
                        mRewardedAd = null;
                        //initRewardAdd();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        rl_hint.setVisibility(View.VISIBLE);
                        if(currentAttempt < 4){
                            if(hintCount < 1){
                                rl_hint.setBackgroundResource(R.drawable.bg_hint);
                                rl_hint.setClickable(true);
                            }else {
                                rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                                rl_hint.setClickable(false);
                            }
                        }else {
                            if(hintCount < 2){
                                rl_hint.setBackgroundResource(R.drawable.bg_hint);
                                rl_hint.setClickable(true);
                            }else {
                                rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                                rl_hint.setClickable(false);
                            }
                        }


                    }
                });
    }

    private void showRewardAdd() {
        if (mRewardedAd != null) {
            mRewardedAd.show(this, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    //  Log.d(TAG, "The user earned the reward.");
                    int rewardAmount = rewardItem.getAmount();
                    String rewardTpype = rewardItem.getType();
                    showHint();
                    if(hintCount < 2){
                        initRewardAdd();
                    }
                }
            });
            mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    // Log.d(TAG, "Ad was shown.");
                }

                @Override
                public void onAdFailedToShowFullScreenContent(AdError adError) {
                    // Called when ad fails to show.
                    // Log.d(TAG, "Ad failed to show.");
                    showHint();
                    if(hintCount < 2){
                        initRewardAdd();
                    }
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    // Set the ad reference to null so you don't show the ad a second time.
                    // Log.d(TAG, "Ad was dismissed.");
                    mRewardedAd = null;
                    if(hintCount < 2){
                        initRewardAdd();
                    }
                }
            });
        } else {
            // Log.d(TAG, "The rewarded ad wasn't ready yet.");
           // showHint();
            if(hintCount < 2){
                initRewardAdd();
            }
        }
    }


    private void gameTimer() {
        tv_timer_text.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                if ((SystemClock.elapsedRealtime() - tv_timer_text.getBase() >= 3600000)) {
                    tv_timer_text.setBase(SystemClock.elapsedRealtime());

                    endGame(Constants.LOSS, String.valueOf(3600000 / 1000), currentAttempt,"2");

                    Toast.makeText(EnglishGameActivity.this, "Game Finished!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        startTimer();
    }

    private void endGame(String gameStatus, String time, int attempt, String langId) {

        gameResult = gameStatus;
        if (!TextUtils.isEmpty(CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID))) {
            SubmitGameRequest submitGameRequest = new SubmitGameRequest();
            submitGameRequest.setGameUserId(CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
            submitGameRequest.setGameStatus(gameStatus);
            submitGameRequest.setNoOfAttempt(attempt);
            submitGameRequest.setTime(time);
            submitGameRequest.setLanguageId(langId);
            gamePresenter.submitGame(submitGameRequest);
        } else {
            if(CommonPreference.getInstance(EnglishGameActivity.this).getSoundState()){
                mediaPlayerGameComplete.start();
            }else {
                mediaPlayerGameComplete.pause();
            }
            GameDataManager.getInstance().removeData();
            Intent intent = new Intent(EnglishGameActivity.this, ShabdamLeaderBoardActivity.class);
            intent.putExtra(Constants.NUMBER_OF_ATTEMPT, attempt);
            intent.putExtra(Constants.TIME, time);
            intent.putExtra(Constants.GAME_STATUS, gameStatus);
            intent.putExtra("type", "2");
            startActivity(intent);
            finish();
        }
    }

    private void startTimer() {
        if (!mTimingRunning) {
            tv_timer_text.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            tv_timer_text.start();
            mTimingRunning = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!CommonPreference.getInstance(EnglishGameActivity.this).getSoundState()){
            mediaPlayer.pause();
            mediaPlayerGameComplete.pause();
            mediaPlayerWrongAnswer.pause();
        }
        if(CommonPreference.getInstance(EnglishGameActivity.this).getSoundState()){
            mediaPlayer.start();
        }else {
            mediaPlayer.pause();
        }

        if (click_item != CLICK_ITEM.HINT) {
            gameTimer();
        } else {
            click_item = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.pause();
        if (click_item != CLICK_ITEM.HINT) {
            if (mTimingRunning) {
                tv_timer_text.stop();
                pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text.getBase();
                mTimingRunning = false;
                minutes = TimeUnit.MILLISECONDS.toMinutes(pauseOffset);
                seconds = TimeUnit.MILLISECONDS.toSeconds(pauseOffset) % 60;
                minute = String.format("%02d", minutes);
                second = String.format("%02d", seconds);
            }
        }
    }

    private void initViewClick() {
        tvCross = findViewById(R.id.tv_cross);
        tvEnter = findViewById(R.id.tv_enter);
        flLoading = findViewById(R.id.fl_loading);
        agla_shabd_btn = findViewById(R.id.rl_agla_shabd_btn);
        tv_timer_text = findViewById(R.id.tv_timer_text);
        rl_hint = findViewById(R.id.rl_hint);
        ivHome = findViewById(R.id.ivHome);
        ivHome.setOnClickListener(this);
        flLoading.setOnClickListener(this);


        tvCross.setOnClickListener(this);
        tvEnter.setOnClickListener(this);
        findViewById(R.id.rl_hint).setOnClickListener(this);

        findViewById(R.id.tv_Q).setOnClickListener(this);
        findViewById(R.id.tv_W).setOnClickListener(this);
        findViewById(R.id.tv_E).setOnClickListener(this);
        findViewById(R.id.tv_R).setOnClickListener(this);
        findViewById(R.id.tv_T).setOnClickListener(this);
        findViewById(R.id.tv_Y).setOnClickListener(this);
        findViewById(R.id.tv_U).setOnClickListener(this);
        findViewById(R.id.tv_I).setOnClickListener(this);
        findViewById(R.id.tv_O).setOnClickListener(this);
        findViewById(R.id.tv_P).setOnClickListener(this);
        findViewById(R.id.tv_A).setOnClickListener(this);
        findViewById(R.id.tv_S).setOnClickListener(this);
        findViewById(R.id.tv_D).setOnClickListener(this);
        findViewById(R.id.tv_F).setOnClickListener(this);
        findViewById(R.id.tv_G).setOnClickListener(this);
        findViewById(R.id.tv_H).setOnClickListener(this);
        findViewById(R.id.tv_J).setOnClickListener(this);
        findViewById(R.id.tv_K).setOnClickListener(this);
        findViewById(R.id.tv_L).setOnClickListener(this);
        findViewById(R.id.tv_Z).setOnClickListener(this);
        findViewById(R.id.tv_X).setOnClickListener(this);
        findViewById(R.id.tv_C).setOnClickListener(this);
        findViewById(R.id.tv_V).setOnClickListener(this);
        findViewById(R.id.tv_B).setOnClickListener(this);
        findViewById(R.id.tv_N).setOnClickListener(this);
        findViewById(R.id.tv_M).setOnClickListener(this);


        findViewById(R.id.rl_uttar_dekho_btn).setOnClickListener(this);
        findViewById(R.id.iv_question_mark_btn).setOnClickListener(this);
        findViewById(R.id.iv_trophy_btn).setOnClickListener(this);
        findViewById(R.id.iv_statistics_btn).setOnClickListener(this);
        findViewById(R.id.iv_settings_btn).setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.tv_Q || id == R.id.tv_W || id == R.id.tv_E || id == R.id.tv_R || id == R.id.tv_T || id == R.id.tv_Y || id == R.id.tv_U || id == R.id.tv_I || id == R.id.tv_O || id == R.id.tv_P || id == R.id.tv_A || id == R.id.tv_S || id == R.id.tv_D || id == R.id.tv_F || id == R.id.tv_G || id == R.id.tv_H || id == R.id.tv_J || id == R.id.tv_K || id == R.id.tv_L || id == R.id.tv_Z || id == R.id.tv_X || id == R.id.tv_C || id == R.id.tv_V || id == R.id.tv_B || id == R.id.tv_N || id == R.id.tv_M ) {
            if (index < currentAttempt * 5) {
                btnIdList.add(view.getId());
            }
            setText(((TextView) findViewById(view.getId())).getText().toString());
        } else if (id == R.id.tv_cross) {
            removeText();
        } else if (id == R.id.tv_enter) {
            if (index != currentAttempt * MAX_CHAR_LENGTH) {
                ToastUtils.show(EnglishGameActivity.this, "Text is too short");
                return;
            }
            if (gamePresenter != null) {
                CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.ENTER);
                CheckWordDicRequest request = new CheckWordDicRequest();
                request.setWord(getEnteredText());
                gamePresenter.checkDictionary(request);
                // onWordCheckDic(true);
            }
            //submitText();
        } else if (id == R.id.rl_uttar_dekho_btn) {
            CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.UTTAR_DEKHO);
            click_item = CLICK_ITEM.UTTAR_DEKHO;
            if(rl_uttar_dekho_btn != null){
                rl_uttar_dekho_btn.setEnabled(false);
            }
            if (!TextUtils.isEmpty(correctWord)) {
                //  endGame(Constants.LOSS,String.valueOf(pauseOffset / 1000),currentAttempt);
                if (TextUtils.isEmpty(CommonPreference.getInstance(EnglishGameActivity.this).getString(CommonPreference.Key.GAME_USER_ID))) {
                    gameResult = Constants.LOSS;
                    GameDataManager.getInstance().removeData();
                    statisticsPopup(null);
                } else {
                    isUttarDekheClicked = true;
                    endGame(Constants.LOSS, String.valueOf(pauseOffset / 1000), currentAttempt,"2");
                }
            }
        } else if (id == R.id.iv_question_mark_btn) {
            CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.QUESTION_MARK);
            kaiseKhelePopup();
        }
        else if(id == R.id.ivHome){
            String activityToStart = "com.tvtoday.crosswordhindi.views.activity.homePage.HomeActivity";
            try {
                Class<?> c = Class.forName(activityToStart);
                Intent intent = new Intent(this, c);
                intent.putExtra("called_game_id",1);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            } catch (ClassNotFoundException ignored) {
            }

        }else if (id == R.id.iv_trophy_btn) {
            CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.LEADERBOARD_ICON);
            startActivity(new Intent(this, ShabdamLeaderBoardActivity.class));
            //finish();
            //onBackPressed();
        }else if(id == R.id.iv_LangChange) {
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.language_selection_filter, null);

            ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;

            final PopupWindow popupWindow = new PopupWindow(popupView, width,
                    height, true);

            popupWindow.showAtLocation(iv_LangChange, 0, 160, 150);

            RadioGroup radioGroup = popupView.findViewById(R.id.radioGroup);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if(checkedId== R.id.radioBtnHindi){
                        GameDataManager.getInstance().getDataList().clear();

                        ShabdamLanguagePreference.getInstance(EnglishGameActivity.this).setLanguage("hi");
                        CommonPreference.getInstance(EnglishGameActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI);
                        CommonPreference.getInstance(EnglishGameActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI);

                        Intent intent = new Intent(EnglishGameActivity.this, GameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        GameDataManager.getInstance().getDataList().clear();

                        ShabdamLanguagePreference.getInstance(EnglishGameActivity.this).setLanguage("");
                        CommonPreference.getInstance(EnglishGameActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH);
                        CommonPreference.getInstance(EnglishGameActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH);

                        Intent intent = new Intent(EnglishGameActivity.this, EnglishGameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();                    }
                }
            });

            ImageView ivCancel = popupView.findViewById(R.id.ivCancel);
            ivCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
          /*  String activityToStart = "com.tvtoday.crosswordhindi.views.activity.languageSelection.LanguageSelectionActivity";
            try {
                Class<?> c = Class.forName(activityToStart);
                Intent intent = new Intent(this, c);
                intent.putExtra("called_game_id",1);
                startActivity(intent);

            } catch (ClassNotFoundException ignored) {
            }*/
        }
        else if (id == R.id.iv_statistics_btn) {
            CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.STATISTICS_ICON);
            if (!TextUtils.isEmpty(CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID))) {
                callgetStreakAPI();
            } else {
                statisticsPopup(null);
            }
        } else if (id == R.id.iv_settings_btn) {
         //   startActivityForResult(new Intent(this, ShabdamSettingsActivity.class), 200);
        Intent intent = new Intent(this, ShabdamSettingsActivity.class);
        someActivityResultLauncher.launch(intent);
        } else if (id == R.id.rl_hint) {
            CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.HINT_BUTTON);
            // showHint();

           /* hintToastCounter++;
            if(hintToastCounter==2){
                ToastUtils.show(GameActivity.this, "Your hint button has activated");
            }*/



            if(currentAttempt < 4){
                if(hintCount < 1){
                    rl_hint.setBackgroundResource(R.drawable.bg_hint);
                    rl_hint.setClickable(true);
                }else {
                    rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                    rl_hint.setClickable(false);
                }
            }else {
                if(hintCount < 1){
                    rl_hint.setBackgroundResource(R.drawable.bg_hint);
                    rl_hint.setClickable(true);
                }else {
                    rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                    rl_hint.setClickable(false);
                }
            }

            if(hintCount < 2){
                showRewardAdd();
                // loadAdd();
                click_item = CLICK_ITEM.HINT;
            }

            //----- ashwini>

           /* if(currentAttempt<4){
                if(!isHintTaken){
                    rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                    rl_hint.setClickable(false);

                    if (hintCount < 2) {
                        showRewardAdd();
                        // loadAdd();
                        // isHintPressed = true;
                        click_item = CLICK_ITEM.HINT;
                        //loadAdd();
                    }
                    isHintTaken = true;
                }
            }

            if(currentAttempt>3){
                if(!isHintTaken){
                    rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                    rl_hint.setClickable(false);

                    if (hintCount < 2) {
                        showRewardAdd();
                        // loadAdd();
                        isHintPressed = false;
                        click_item = CLICK_ITEM.HINT;
                        //loadAdd();
                    }
                }else{
                    rl_hint.setBackgroundResource(R.drawable.bg_hint);
                    rl_hint.setClickable(true);
                }

            }*/
        }else if(id == R.id.ivHamburger) {
            openDrawer();
        }else if(id == R.id.lLayTerms){
            closeDrawer();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1efJN1iZrt9r_hd4hK8_Kct0tUsnVNptSXSm_26DcE6Q/edit?usp=sharing"));
            startActivity(browserIntent);
        }else if(id == R.id.lLayPrivacy){
            closeDrawer();
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://docs.google.com/document/d/1efJN1iZrt9r_hd4hK8_Kct0tUsnVNptSXSm_26DcE6Q/edit?usp=sharing"));
            startActivity(browserIntent);
        }else if(id == R.id.lLaySetting){
            closeDrawer();
            Intent intent = new Intent(EnglishGameActivity.this, ShabdamSettingsActivity.class);
            startActivity(intent);

        }else if(id == R.id.lLayShare){
            closeDrawer();
            Intent intent2 = new Intent(); intent2.setAction(Intent.ACTION_SEND);
            intent2.setType("text/plain");
            intent2.putExtra(Intent.EXTRA_TEXT, "Love playing crossword, check out the new Hindi crossword on Android https://play.google.com/store/apps/details?id=com.tvtoday.crosswordhindi" +  "\n" +
                    "An iPhone version is also available: https://apps.apple.com/us/app/vargpaheli/id1622360590");
            startActivity(Intent.createChooser(intent2, "Share via"));

        }else if(id == R.id.lLayTutorial){

        }else if(id == R.id.lLayTvToday){
            closeDrawer();

            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=TV+Today+Network"));
            startActivity(browserIntent);
        }
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        openLogin();
                    }
                }
            });

    private void openLogin(){
        Intent intent = new Intent(EnglishGameActivity.this, UserDetailActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setComponent(new ComponentName(EnglishGameActivity.this, UserDetailActivity.class));
        startActivity(intent);
        Toast.makeText(EnglishGameActivity.this, "Logout successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getEnteredText() {
        String str = "";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < entered_word_array.length; i++) {
            builder.append(entered_word_array[i]).toString();
        }

        return builder.toString();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //startActivityForResult(signInIntent, RC_SIGN_IN);
        someActivityResultLauncherLogin.launch(signInIntent);
    }

    ActivityResultLauncher<Intent> someActivityResultLauncherLogin = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        com.google.android.gms.tasks.Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        handleSignInResult(task);
                    }
                }
            });

    
    private void statisticsPopup(Data data) {
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
        if (mTimingRunning) {
            tv_timer_text.stop();
            pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text.getBase();
            mTimingRunning = false;
            minutes = TimeUnit.MILLISECONDS.toMinutes(pauseOffset);
            seconds = TimeUnit.MILLISECONDS.toSeconds(pauseOffset) % 60;
            minute = String.format("%02d", minutes);
            second = String.format("%02d", seconds);
        }
        final AlertDialog.Builder builder = new AlertDialog.Builder(EnglishGameActivity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.statistics_popup_layout, viewGroup, false);
        ImageView cancel_btn = dialogView.findViewById(R.id.iv_cancel_btn);

        ImageView iv_LangChange = dialogView.findViewById(R.id.iv_LangChange);
        iv_LangChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater)
                        getSystemService(LAYOUT_INFLATER_SERVICE);
                @SuppressLint("InflateParams") View popupView = inflater.inflate(R.layout.language_selection_filter, null);

                ViewGroup root = (ViewGroup) getWindow().getDecorView().getRootView();

                // create the popup window
                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;

                final PopupWindow popupWindow = new PopupWindow(popupView, width,
                        height, true);

                popupWindow.showAtLocation(iv_LangChange, 0, 160, 150);

                RadioGroup radioGroup = popupView.findViewById(R.id.radioGroup);
                radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if(checkedId== R.id.radioBtnHindi){
                            ShabdamLanguagePreference.getInstance(EnglishGameActivity.this).setLanguage("hi");
                            CommonPreference.getInstance(EnglishGameActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI);
                            CommonPreference.getInstance(EnglishGameActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI);

                            Intent intent = new Intent(EnglishGameActivity.this, GameActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            ShabdamLanguagePreference.getInstance(EnglishGameActivity.this).setLanguage("");
                            CommonPreference.getInstance(EnglishGameActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH);
                            CommonPreference.getInstance(EnglishGameActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH);

                            Intent intent = new Intent(EnglishGameActivity.this, EnglishGameActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

                ImageView ivCancel = popupView.findViewById(R.id.ivCancel);
                ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

               /* // languageSwitch();
                String activityToStart = "com.tvtoday.crosswordhindi.views.activity.languageSelection.LanguageSelectionActivity";
                try {
                    Class<?> c = Class.forName(activityToStart);
                    Intent intent = new Intent(EnglishGameActivity.this, c);
                    intent.putExtra("called_game_id",1);
                    startActivity(intent);

                } catch (ClassNotFoundException ignored) {
                }
                // Toast.makeText(GameActivity.this, "hello testing", Toast.LENGTH_SHORT).show();
*/
            }
        });

        tv_played = dialogView.findViewById(R.id.tv_played);
        lLayEnglishAnswer = dialogView.findViewById(R.id.lLayEnglishAnswer);
        lLayHindiAnswer = dialogView.findViewById(R.id.lLayHindiAnswer);
        tv_win = dialogView.findViewById(R.id.tv_win);
        tv_current_streak = dialogView.findViewById(R.id.tv_current_streak);
        tv_max_streak = dialogView.findViewById(R.id.tv_max_streak);
        agla_shabd_btn = dialogView.findViewById(R.id.rl_agla_shabd_btn);
        rl_share_btn = dialogView.findViewById(R.id.rl_share_btn);
        ll_google_sign_in = dialogView.findViewById(R.id.ll_google_sign_in);
        ll_google_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tv_google_sign_in = dialogView.findViewById(R.id.tv_google_sign_in);


        if (TextUtils.isEmpty(CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID))) {
            ll_google_sign_in.setVisibility(View.VISIBLE);
            tv_google_sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (EnglishGameActivity.this != null) {
                        if (ToastUtils.checkInternetConnection(EnglishGameActivity.this)) {
                            signIn();
                        } else {
                            Toast.makeText(EnglishGameActivity.this, getString(R.string.ensure_internet), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        builder.setView(dialogView);
        alertDialog = builder.create();

        String email = CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID);
        if (!TextUtils.isEmpty(email)) {
            ll_google_sign_in.setVisibility(View.GONE);
        } else {
            ll_google_sign_in.setVisibility(View.VISIBLE);
        }

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_timer_text.setBase(SystemClock.elapsedRealtime() - pauseOffset);
                tv_timer_text.start();
                mTimingRunning = true;
                alertDialog.dismiss();
            }
        });
        rl_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.SP_SHARE);
                takeScreenShot(dialogView);
            }
        });
        agla_shabd_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.SP_AGLA_SHABD);
                click_item = CLICK_ITEM.AGLA_SHABD;
                loadAdd();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);

        if (data != null) {
            tv_played.setText(data.getPlayed());
            tv_current_streak.setText(data.getCurrentStreak());
            tv_max_streak.setText(data.getMaxStreak());

            if (data.getWin().equals("0") || data.getPlayed().equals("0")) {
                tv_win.setText("0");
            } else {
                float win = Integer.parseInt(data.getWin());
                float total_played = Integer.parseInt(data.getPlayed());
                int percent = (int) ((win / total_played) * 100f);
                tv_win.setText(String.valueOf(percent));
            }

            if (data != null && data.getNoOfAttempts() != null) {

                int progressOne = 0, progressTwo = 0, progressThree = 0, progressFour = 0, progressFive = 0, progressSix = 0;
                try {
                    progressOne = !TextUtils.isEmpty(data.getPlayed()) && !TextUtils.isEmpty(data.getNoOfAttempts().get1()) ? (Integer.parseInt(data.getNoOfAttempts().get1()) * 100) / Integer.parseInt(data.getPlayed()) : 0;
                    progressTwo = !TextUtils.isEmpty(data.getPlayed()) && !TextUtils.isEmpty(data.getNoOfAttempts().get2()) ? (Integer.parseInt(data.getNoOfAttempts().get2()) * 100) / Integer.parseInt(data.getPlayed()) : 0;
                    progressThree = !TextUtils.isEmpty(data.getPlayed()) && !TextUtils.isEmpty(data.getNoOfAttempts().get3()) ? (Integer.parseInt(data.getNoOfAttempts().get3()) * 100) / Integer.parseInt(data.getPlayed()) : 0;
                    progressFour = !TextUtils.isEmpty(data.getPlayed()) && !TextUtils.isEmpty(data.getNoOfAttempts().get4()) ? (Integer.parseInt(data.getNoOfAttempts().get4()) * 100) / Integer.parseInt(data.getPlayed()) : 0;
                    progressFive = !TextUtils.isEmpty(data.getPlayed()) && !TextUtils.isEmpty(data.getNoOfAttempts().get5()) ? (Integer.parseInt(data.getNoOfAttempts().get5()) * 100) / Integer.parseInt(data.getPlayed()) : 0;
                    progressSix = !TextUtils.isEmpty(data.getPlayed()) && !TextUtils.isEmpty(data.getNoOfAttempts().get6()) ? (Integer.parseInt(data.getNoOfAttempts().get6()) * 100) / Integer.parseInt(data.getPlayed()) : 0;

                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (!TextUtils.isEmpty(data.getNoOfAttempts().get1())
                        && !data.getNoOfAttempts().get1().equalsIgnoreCase("0")) {
                    dialogView.findViewById(R.id.rl_layout1).setVisibility(View.GONE);
                    dialogView.findViewById(R.id.rl_layout_one).setVisibility(View.VISIBLE);
                    ((TextView) dialogView.findViewById(R.id.tv_pb_1)).setText(data.getNoOfAttempts().get1());
                    ((ProgressBar) dialogView.findViewById(R.id.p_bar_1)).setProgress(progressOne);
                } else {
                    dialogView.findViewById(R.id.rl_layout1).setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(data.getNoOfAttempts().get2())
                        && !data.getNoOfAttempts().get2().equalsIgnoreCase("0")) {
                    dialogView.findViewById(R.id.rl_layout2).setVisibility(View.GONE);
                    dialogView.findViewById(R.id.rl_layout_two).setVisibility(View.VISIBLE);
                    ((TextView) dialogView.findViewById(R.id.tv_pb_2)).setText(data.getNoOfAttempts().get2());
                    ((ProgressBar) dialogView.findViewById(R.id.p_bar_2)).setProgress(progressTwo);

                } else {
                    dialogView.findViewById(R.id.rl_layout2).setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(data.getNoOfAttempts().get3())
                        && !data.getNoOfAttempts().get3().equalsIgnoreCase("0")) {
                    dialogView.findViewById(R.id.rl_layout3).setVisibility(View.GONE);
                    dialogView.findViewById(R.id.rl_layout_three).setVisibility(View.VISIBLE);
                    ((TextView) dialogView.findViewById(R.id.tv_pb_3)).setText(data.getNoOfAttempts().get3());
                    ((ProgressBar) dialogView.findViewById(R.id.p_bar_3)).setProgress(progressThree);

                } else {
                    dialogView.findViewById(R.id.rl_layout3).setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(data.getNoOfAttempts().get4())
                        && !data.getNoOfAttempts().get4().equalsIgnoreCase("0")) {
                    dialogView.findViewById(R.id.rl_layout4).setVisibility(View.GONE);
                    dialogView.findViewById(R.id.rl_layout_four).setVisibility(View.VISIBLE);
                    ((TextView) dialogView.findViewById(R.id.tv_pb_4)).setText(data.getNoOfAttempts().get4());
                    ((ProgressBar) dialogView.findViewById(R.id.p_bar_4)).setProgress(progressFour);

                } else {
                    dialogView.findViewById(R.id.rl_layout4).setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(data.getNoOfAttempts().get5())
                        && !data.getNoOfAttempts().get5().equalsIgnoreCase("0")) {
                    dialogView.findViewById(R.id.rl_layout5).setVisibility(View.GONE);
                    dialogView.findViewById(R.id.rl_layout_five).setVisibility(View.VISIBLE);
                    ((TextView) dialogView.findViewById(R.id.tv_pb_5)).setText(data.getNoOfAttempts().get5());
                    ((ProgressBar) dialogView.findViewById(R.id.p_bar_5)).setProgress(progressFive);

                } else {
                    dialogView.findViewById(R.id.rl_layout5).setVisibility(View.VISIBLE);
                }

                if (!TextUtils.isEmpty(data.getNoOfAttempts().get6())
                        && !data.getNoOfAttempts().get6().equalsIgnoreCase("0")) {
                    dialogView.findViewById(R.id.rl_layout6).setVisibility(View.GONE);
                    dialogView.findViewById(R.id.rl_layout_six).setVisibility(View.VISIBLE);
                    ((TextView) dialogView.findViewById(R.id.tv_pb_6)).setText(data.getNoOfAttempts().get6());
                    ((ProgressBar) dialogView.findViewById(R.id.p_bar_6)).setProgress(progressSix);
                } else {

                }
            }
        }
        if (gameResult != null && gameResult.equalsIgnoreCase(Constants.LOSS)) {
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.setCancelable(false);
            cancel_btn.setVisibility(View.GONE);

            //for -----hindi answer ----------------------------------
            TextView tvOne = dialogView.findViewById(R.id.tv_one);
            TextView tvTwo = dialogView.findViewById(R.id.tv_two);
            TextView tvThree = dialogView.findViewById(R.id.tv_three);

            //for -----english answer ----------------------------------------
            TextView tv_one_eng = dialogView.findViewById(R.id.tv_one_eng);
            TextView tv_two_eng = dialogView.findViewById(R.id.tv_two_eng);
            TextView tv_three_eng = dialogView.findViewById(R.id.tv_three_eng);
            TextView tv_forth_eng = dialogView.findViewById(R.id.tv_forth_eng);
            TextView tv_fifth_eng = dialogView.findViewById(R.id.tv_fifth_eng);

            try {
                if(!TextUtils.isEmpty(CommonPreference.getInstance(EnglishGameActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(EnglishGameActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){
                    lLayEnglishAnswer.setVisibility(View.GONE);
                    lLayHindiAnswer.setVisibility(View.VISIBLE);
                    tvOne.setText(new StringBuilder().append(word_array[0]));
                    tvTwo.setText(new StringBuilder().append(word_array[1]));
                    tvThree.setText(new StringBuilder().append(word_array[2]));
                }else{
                    lLayEnglishAnswer.setVisibility(View.VISIBLE);
                    lLayHindiAnswer.setVisibility(View.GONE);
                    //for -----set english answer data -------------------------------
                    tv_one_eng.setText(String.valueOf(word_array[0]));
                    tv_two_eng.setText(String.valueOf(word_array[1]));
                    tv_three_eng.setText(String.valueOf(word_array[2]));
                    tv_forth_eng.setText(String.valueOf(word_array[3]));
                    tv_fifth_eng.setText(String.valueOf(word_array[4]));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            dialogView.findViewById(R.id.ll_sahi_jawab).setVisibility(View.GONE);

        }
        alertDialog.show();
    }

    private void takeScreenShot(View view) {
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }
            String path = mainDir + "/" + "TrendOceans" + "-" + format + ".jpeg";
            view.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
            view.setDrawingCacheEnabled(false);

            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            shareScreenShot(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shareScreenShot(File imageFile) {
        Uri uri = FileProvider.getUriForFile(
                this,
                CommonPreference.getInstance(EnglishGameActivity.this.getApplicationContext()).getPackageString("applicationId") + ".GameActivity.provider",
                imageFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, Constants.STATISTICS_SHARE_MSG_ENG);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    private void callgetStreakAPI() {
        String game_id = CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID);
        gamePresenter = new GamePresenter(this, EnglishGameActivity.this);
        gamePresenter.fetchStatisticsData(game_id,"2");
    }

    private void kaiseKhelePopup() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EnglishGameActivity.this, R.style.CustomAlertDialog);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.kaise_khele_popup_layout, viewGroup, false);
        RelativeLayout continue_btn = dialogView.findViewById(R.id.rl_continue_btn);
        builder.setView(dialogView);
        final AlertDialog alertDialog = builder.create();
        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    /**
     * check which cell to set text
     * If already have text accept MATRA_TEXT then remove text
     * and add new text
     *
     * @param s
     */
    private void setText(String s) {
        try{
            if (index < currentAttempt * 5) {
                index = index + 1;
                if (getId(index) != 0) {
                    updateWordCharArray(s);
                    ((TextView) findViewById(getId(index))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.black));
                    ((TextView) findViewById(getId(index))).setText(new StringBuilder().append(s).append(getTextIndex(index)));
                }
            }
        }catch (Exception e){

        }
    }

    private void updateWordCharArray(String s) {
        entered_word_array[index % MAX_CHAR_LENGTH == 0 ? MAX_CHAR_LENGTH - 1 : (index % MAX_CHAR_LENGTH) - 1] = s.toCharArray()[0];
    }

    private String getTextIndex(int index) {
        return ((TextView) findViewById(getId(index))).getText().toString();
    }

    private void removeText() {
        if (index > (currentAttempt - 1) * 5) {
            ((TextView) findViewById(getId(index))).setText("");
            if (btnIdList != null && btnIdList.size() > 0) {
                btnIdList.remove(btnIdList.size() - 1);
                CleverTapEvent.getCleverTapEvents(EnglishGameActivity.this).createOnlyEvent(CleverTapEventConstants.BACK_SPACE);
            }
            updateWordCharArray("x");
            findViewById(getId(index)).setBackgroundResource(R.drawable.bg_answer);
            ((TextView) findViewById(getId(index))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.black));
            ((TextView) findViewById(getId(index))).setText("");
            // ((TextView) findViewById(getId(index))).setText(matra[index % MAX_CHAR_LENGTH == 0 ? MAX_CHAR_LENGTH - 1 : (index % MAX_CHAR_LENGTH) - 1]);

            index = index - 1;

        }
    }

    private void submitText() {
       try{
           animate();

           handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                   if (index % MAX_CHAR_LENGTH == 0) {

                       //verifyText--> call API --> increment count
                       if (!verifyText()) {
                           //Increment current Attempt Count at Last
                           if (currentAttempt < MAX_ATTEMPT) {
                               currentAttempt = currentAttempt + 1;
                               updateCurrentAttempt();
                           }
                           //  btnIdList.clear();
                       }


                       if (index == MAX_ATTEMPT * MAX_CHAR_LENGTH) {
                           if (!Arrays.equals(word_array, entered_word_array)) {

                               endGame(Constants.LOSS, String.valueOf(pauseOffset / 1000), currentAttempt,"2");
                               //openLeaderBoardOnGameEnd();
                           }
                       }
                   }
               }
           }, 1600);
       }catch (Exception e){

       }
    }

    /**
     * hit API to check word in dictionary
     */
    private boolean verifyText() {
        return mapWord();
    }

    private void updateCurrentAttempt() {
        try{
            //Update Matra
            if (btnIdList != null) {
                btnIdList.clear();
            }

            for (int i = 1; i < MAX_CHAR_LENGTH + 1; i++) {
                ((TextView) findViewById(getId((currentAttempt - 1) * 5 + i))).setText("");
            }

            if (hintCount == 1) {
                hintOne();
            } else if (hintCount == 2) {
                hintTwo();
            }




            if(currentAttempt < 4){
                if(hintCount < 1){
                    rl_hint.setBackgroundResource(R.drawable.bg_hint);
                    rl_hint.setClickable(true);
                }else {
                    rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                    rl_hint.setClickable(false);
                }
            }else {
                if(hintCount < 2){
                    rl_hint.setBackgroundResource(R.drawable.bg_hint);
                    rl_hint.setClickable(true);
                }else {
                    rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                    rl_hint.setClickable(false);
                }
            }
            //ashwini------>
           /* if(currentAttempt > 3){

                if(!isHintTaken && hintCount> 0){
                    rl_hint.setBackgroundResource(R.drawable.bg_hint_gray);
                    rl_hint.setClickable(false);
                    isHintTaken=false;
                }else{
                    rl_hint.setBackgroundResource(R.drawable.bg_hint);
                    rl_hint.setClickable(true);
                    //ToastUtils.show(EnglishGameActivity.this, "Your hint button has activated");
                    isHintTaken=false;
                }
            }*/
        }catch (Exception e){

        }
    }

    private int getId(int pos) {
        switch (pos) {
            case 1:
                return R.id.et_1;

            case 2:
                return R.id.et_2;

            case 3:
                return R.id.et_3;

            case 4:
                return R.id.et_4;

            case 5:
                return R.id.et_5;

            case 6:
                return R.id.et_6;

            case 7:
                return R.id.et_7;

            case 8:
                return R.id.et_8;

            case 9:
                return R.id.et_9;

            case 10:
                return R.id.et_10;

            case 11:
                return R.id.et_11;

            case 12:
                return R.id.et_12;

            case 13:
                return R.id.et_13;

            case 14:
                return R.id.et_14;

            case 15:
                return R.id.et_15;
            case 16:
                return R.id.et_16;

            case 17:
                return R.id.et_17;

            case 18:
                return R.id.et_18;

            case 19:
                return R.id.et_19;

            case 20:
                return R.id.et_20;

            case 21:
                return R.id.et_21;

            case 22:
                return R.id.et_22;

            case 23:
                return R.id.et_23;

            case 24:
                return R.id.et_24;

            case 25:
                return R.id.et_25;

            case 26:
                return R.id.et_26;

            case 27:
                return R.id.et_27;

            case 28:
                return R.id.et_28;

            case 29:
                return R.id.et_29;

            case 30:
                return R.id.et_30;
        }

        return 0;
    }


    private boolean checkLetter(char c) {
        return ((int) c >= 2309 && (int) c <= 2324)
                || ((int) c >= 2400 && (int) c <= 2401)|| ((int) c >= 2325 && (int) c <= 2361)
                || ((int) c >= 2392 && (int) c <= 2399)
                || (int) c == 2319 || (int) c == 2320 || (int) c == 2323 || (int) c == 2324;
    }

    /**
     * if word array same and entered_word_array
     * Correct Word
     * hit api, stop timer, disable keyboard, color all the answer boxes with green
     * Else check word in dictionary
     * yes then check is letter are in word_array
     * yes check if it is right place
     * yes color answer box of that letter with green
     * color keyboard box of that letter with green
     * no color answer box of that letter with yellow
     * color keyboard box of that letter with yellow
     * no color answer box with yellow
     * color keyborad box of that letter with yellow
     * no re-attempt or color it with grey
     *
     * @return
     */
    private boolean mapWord() {
        try {
            if (Arrays.equals(word_array, entered_word_array)) {
                updateGreenBoxes();
                if (gamePresenter != null) {
                    if (tv_timer_text != null) {
                        tv_timer_text.stop();
                        pauseOffset = SystemClock.elapsedRealtime() - tv_timer_text.getBase();
                        mTimingRunning = false;
                    }
                    if (!TextUtils.isEmpty(datumCorrectWord.getId())) {
                        saveID(datumCorrectWord.getId());
                    }

                    endGame(Constants.WIN, String.valueOf(pauseOffset / 1000), currentAttempt,"2");
                    // save correct word id
                    //datumCorrectWord.getId()
                }
                return true;
            }// dictionary check is pending
            else {//check is letter are in word_array
                for (int i = 0; i < entered_word_array.length; i++) {
                    boolean isExist = false;
                    for (int j = 0; j < word_array.length; j++) {
                        if (entered_word_array[i] == word_array[j]) {
                            isExist = true;
                            if (i == j) {//same postion green
                                findViewById(getId((currentAttempt - 1) * 5 + 1 + i)).setBackgroundResource(R.drawable.bg_green_box);
                                findViewById(btnIdList.get(i)).setBackgroundResource(R.drawable.bg_green_box);
                                ((TextView) findViewById(getId((currentAttempt - 1) * 5 + 1 + i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
                                ((TextView) findViewById(btnIdList.get(i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
                            } else {// yellow
                                findViewById(getId((currentAttempt - 1) * 5 + 1 + i)).setBackgroundResource(R.drawable.bg_yellow);
                                findViewById(btnIdList.get(i)).setBackgroundResource(R.drawable.bg_yellow);
                                ((TextView) findViewById(getId((currentAttempt - 1) * 5 + 1 + i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
                                ((TextView) findViewById(btnIdList.get(i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));

                            }
                        }
                    }
                    if (!isExist) {//Not in word
                        findViewById(getId((currentAttempt - 1) * 5 + 1 + i)).setBackgroundResource(R.drawable.bg_grey);
                        findViewById(btnIdList.get(i)).setBackgroundResource(R.drawable.bg_grey);
                        ((TextView) findViewById(getId((currentAttempt - 1) * 5 + 1 + i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
                        ((TextView) findViewById(btnIdList.get(i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveID(String id) {
        Task task = new Task();
        task.setWordId(id);

        gamePresenter.saveIDLocalDB(EnglishGameActivity.this, task);

    }

    private void openLeaderBoardOnGameEnd() {
        if(CommonPreference.getInstance(EnglishGameActivity.this).getSoundState()){
            mediaPlayerGameComplete.start();
        }else {
            mediaPlayerGameComplete.pause();
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(EnglishGameActivity.this, ShabdamLeaderBoardActivity.class);
                intent.putExtra("type", "2");
                startActivity(intent);
                finish();
                //finish();
            }
        }, 500);
    }

    private void updateGreenBoxes() {
        for (int i = (currentAttempt - 1) * 5 + 1; i < currentAttempt * 5 + 1; i++) {
            findViewById(getId(i)).setBackgroundResource(R.drawable.bg_green_box);
            ((TextView) findViewById(getId(i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
        }

        //update Key Board
        for (int j = 0; j < btnIdList.size(); j++) {
            findViewById(btnIdList.get(j)).setBackgroundResource(R.drawable.bg_green_box);
            ((TextView) findViewById(btnIdList.get(j))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));

        }
    }

    private void animate() {
        try {
            //visibleView.visible()



            flipOutAnimatorSet.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 1)));
            flipOutAnimatorSet2.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 2)));
            flipOutAnimatorSet3.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 3)));
            flipOutAnimatorSet4.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 4)));
            flipOutAnimatorSet5.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 5)));


            flipInAnimatorSet.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 1)));
            flipInAnimatorSet2.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 2)));
            flipInAnimatorSet3.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 3)));
            flipInAnimatorSet4.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 4)));
            flipInAnimatorSet5.setTarget(findViewById(getId((currentAttempt - 1) * 5 + 5)));


            animatorListener = new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if (animator == flipOutAnimatorSet) {
                        flipInAnimatorSet.start();
                        // flipOutAnimatorSet2.start();
                    } else if (animator == flipOutAnimatorSet2) {
                        flipInAnimatorSet2.start();
                        // flipOutAnimatorSet3.start();
                    } else  if (animator == flipOutAnimatorSet3){
                        flipInAnimatorSet3.start();
                    }else  if (animator == flipOutAnimatorSet4){
                        flipInAnimatorSet4.start();
                    }else  {
                        flipInAnimatorSet5.start();
                    }
                    /*flipInAnimatorSet.start();
                    flipInAnimatorSet2.start();
                    flipInAnimatorSet3.start();*/
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            };


            flipOutAnimatorSet.start();
            flipOutAnimatorSet.addListener(animatorListener);

            flipOutAnimatorSet2.start();
            flipOutAnimatorSet2.addListener(animatorListener);

            flipOutAnimatorSet3.start();
            flipOutAnimatorSet3.addListener(animatorListener);

            flipOutAnimatorSet4.start();
            flipOutAnimatorSet4.addListener(animatorListener);

            flipOutAnimatorSet5.start();
            flipOutAnimatorSet5.addListener(animatorListener);











        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showProgress() {
        if (!isFinishing()) {
            flLoading.setVisibility(View.VISIBLE);
        }
    }

    /*private void flipCardAnimation(){
        final ObjectAnimator oa1 = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 0f);
        final ObjectAnimator oa2 = ObjectAnimator.ofFloat(imageView, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView.setImageResource(R.drawable.frontSide);
                oa2.start();
            }
        });
        oa1.start();
    }*/

    @Override
    public void hideProgress() {
        if (!isFinishing()) {
            flLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onError(String errorMsg) {
        if (!isFinishing()) {
            flLoading.setVisibility(View.GONE);
            ToastUtils.show(EnglishGameActivity.this, errorMsg);
        }
    }

    @Override
    public void onWordFetched(Datum datumCorrectWord) {
        try{
            this.datumCorrectWord = datumCorrectWord;
            this.correctWord = datumCorrectWord.getWords().toString();
            word_array = datumCorrectWord.getWords().toCharArray();
            ToastUtils.show(EnglishGameActivity.this, correctWord);
            // showMatraText();
            updateCurrentAttempt();
        }catch (Exception e){

        }
    }

    @Override
    public void onStatisticsDataFetched(Data data) {
        GameView.super.onStatisticsDataFetched(data);
        statisticsPopup(data);
    }

    /**
     * Hard coded log-- can make it dynamic but not in mood
     */
    void showHint() {
        if (hintCount < 2) {
            btnIdList.clear();
            if (hintCount == 0) {
                hintCount++;
                hintOne();

            } else {
                //First Text
                hintCount++;
                hintTwo();
            }
        }


      /*  if (hintCount < 2) {
            btnIdList.clear();
            entered_word_array[hintCount] = word_array[hintCount];
            hintCount++;
            int pos = ((currentAttempt - 1) * 3) + hintCount;

            if (hintCount == 2) {
                btnIdList.add(getKeyId(String.valueOf(word_array[0])));
                ((TextView) findViewById(getId(pos - 1))).setText(new StringBuilder().append(word_array[0]).append(matra[0]));
                findViewById(getId(pos-1)).setBackgroundResource(R.color.green);
                entered_word_array[0] = word_array[0];
                updateWordCharArray(String.valueOf(word_array[hintCount - 1]));

            }

            ((TextView) findViewById(getId(pos))).setText(new StringBuilder().append(word_array[hintCount - 1]).append(matra[hintCount - 1]));
            findViewById(getId(hintCount)).setBackgroundResource(R.color.green);
            updateWordCharArray(String.valueOf(word_array[hintCount - 1]));
            int id = getKeyId(String.valueOf(word_array[hintCount - 1]));
            if (id != -1) {
                btnIdList.add(id);
                findViewById(id).setBackgroundResource(R.drawable.bg_green_box);
            }
            index = pos;


            clearNextBoxesAfterHint();
        }*/
    }

    private void hintTwo() {
        // hintCount ++;
        entered_word_array[0] = word_array[0];
        int pos = ((currentAttempt - 1) * 5) + 1;
        ((TextView) findViewById(getId(pos))).setText(new StringBuilder().append(word_array[0]));
        findViewById(getId(pos)).setBackgroundResource(R.drawable.bg_green_box);
        ((TextView) findViewById(getId(pos))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));

        int id = getKeyId(String.valueOf(word_array[0]));
        if (id != -1) {
            btnIdList.add(id);
            findViewById(id).setBackgroundResource(R.drawable.bg_green_box);
            ((TextView) findViewById(id)).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
        }

       // ((TextView) findViewById(getId(pos + 1))).setText(matra[1]);
       // ((TextView) findViewById(getId(pos + 2))).setText(matra[2]);

        // Second Text
        entered_word_array[1] = word_array[1];
        int pos2 = ((currentAttempt - 1) * 5) + 2;
        ((TextView) findViewById(getId(pos2))).setText(new StringBuilder().append(word_array[1]));
        findViewById(getId(pos2)).setBackgroundResource(R.drawable.bg_green_box);
        ((TextView) findViewById(getId(pos2))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));

        int id2 = getKeyId(String.valueOf(word_array[1]));
        if (id2 != -1) {
            btnIdList.add(id2);
            findViewById(id2).setBackgroundResource(R.drawable.bg_green_box);
            ((TextView) findViewById(id2)).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
        }

        ((TextView) findViewById(getId(pos + 2))).setText("");
        index = pos2;

    }

    private void hintOne() {
        // hintCount ++;
        entered_word_array[0] = word_array[0];
        int pos = ((currentAttempt - 1) * 5) + 1;
        ((TextView) findViewById(getId(pos))).setText(new StringBuilder().append(word_array[0]));
        findViewById(getId(pos)).setBackgroundResource(R.drawable.bg_green_box);
        ((TextView) findViewById(getId(pos))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));

        int id = getKeyId(String.valueOf(word_array[0]));
        if (id != -1) {
            btnIdList.add(id);
            findViewById(id).setBackgroundResource(R.drawable.bg_green_box);
            ((TextView) findViewById(id)).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
        }

        ((TextView) findViewById(getId(pos + 1))).setText("");
        ((TextView) findViewById(getId(pos + 2))).setText("");

        index = pos;
    }

    private void clearNextBoxesAfterHint() {
        for (int i = 0; i < 5 - hintCount; i++) {
            ((TextView) findViewById(getId(index + i + 1))).setText("");
        }
    }

    public int getKeyId(String str) {
        int id = -1;

        for (int i = 0; i < keyIdArray.length; i++) {
            if (((TextView) findViewById(keyIdArray[i])).getText().toString().equalsIgnoreCase(str)) {
                id = keyIdArray[i];
            }
        }
        return id;
    }

    @Override
    public void onWordCheckDic(boolean isMatched) {
        if (isMatched) {
            // shake attempted layout
            submitText();
        } else {
            mediaPlayerWrongAnswer.start();
            if(CommonPreference.getInstance(EnglishGameActivity.this).getSoundState()){
                mediaPlayerWrongAnswer.start();

            }else {
                mediaPlayerWrongAnswer.pause();
            }

            shakeAnimation();
            for (int i = 1; i < MAX_CHAR_LENGTH + 1; i++) {
                findViewById(getId((currentAttempt - 1) * 5 + i)).setBackgroundResource(R.drawable.bg_red);
                ((TextView) findViewById(getId((currentAttempt - 1) * 5 + i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.white));
            }
            findViewById(R.id.fl_dic_error).setVisibility(View.VISIBLE);

            shakeAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    for (int i = 1; i < MAX_CHAR_LENGTH + 1; i++) {
                        findViewById(getId((currentAttempt - 1) * 5 + i)).setBackgroundResource(R.drawable.bg_answer);
                        ((TextView) findViewById(getId((currentAttempt - 1) * 5 + i))).setText("");
                        ((TextView) findViewById(getId((currentAttempt - 1) * 5 + i))).setTextColor(ContextCompat.getColor(EnglishGameActivity.this, R.color.black));
                    }
                    index = (currentAttempt - 1) * 5;
                    updateCurrentAttempt();
                    findViewById(R.id.fl_dic_error).setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

            // ToastUtils.show(GameActivity.this, "शब्द शब्दकोश में नहीं है। ");
        }
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        mediaPlayerWrongAnswer.stop();
        mediaPlayerGameComplete.stop();
        if (gamePresenter != null) {
            gamePresenter.onDestroy();
        }
        tv_timer_text = null;
        handler.removeCallbacksAndMessages(null);
        handler = null;
        alertDialog = null;
        keyIdArray = null;
     //   matra = null;
        btnIdList = null;
        entered_word_array = null;
        datumCorrectWord = null;
        word_array = null;
        charArray = null;
        gamePresenter = null;
        mGoogleSignInClient = null;
        adRequest = null;
        mRewardedAd = null;
        shakeAnimation = null;
        super.onDestroy();
    }

    @Override
    public void onGameSubmit() {
        if (isUttarDekheClicked) {
            isUttarDekheClicked = false;
            if(rl_uttar_dekho_btn != null){
                rl_uttar_dekho_btn.setEnabled(false);
            }

            loadAdd();

        } else {
            if (gameResult != null && gameResult.equalsIgnoreCase(Constants.LOSS)) {
                callgetStreakAPI();
            } else {
                openLeaderBoardOnGameEnd();
            }
        }
    }

    private void shakeAnimation() {
        shakeAnimation = AnimationUtils.loadAnimation(EnglishGameActivity.this, R.anim.shake);
        getGrid().setAnimation(shakeAnimation);
    }

    public View getGrid() {
        if (currentAttempt == 1) {
            return findViewById(R.id.ll_grid_one);
        } else if (currentAttempt == 2) {

            return findViewById(R.id.ll_grid_two);
        } else if (currentAttempt == 3) {

            return findViewById(R.id.ll_grid_three);
        } else if (currentAttempt == 4) {

            return findViewById(R.id.ll_grid_four);
        } else if (currentAttempt == 5) {

            return findViewById(R.id.ll_grid_five);
        }

        return null;
    }

    @Override
    public void onAnimationStart(Animator animation) {

    }

    @Override
    public void onAnimationEnd(Animator animation) {

    }

    @Override
    public void onAnimationCancel(Animator animation) {

    }

    @Override
    public void onAnimationRepeat(Animator animation) {

    }

    private enum CLICK_ITEM {HINT, UTTAR_DEKHO, AGLA_SHABD}

}