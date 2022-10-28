package com.today.gamesdk.shabdamsdk.ui.activity;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.today.gamesdk.shabdamsdk.Constants;
import com.today.gamesdk.shabdamsdk.GameActivity;
import com.today.gamesdk.shabdamsdk.GameDataManager;
import com.today.gamesdk.shabdamsdk.GamePresenter;
import com.today.gamesdk.shabdamsdk.GameView;
import com.today.gamesdk.shabdamsdk.R;
import com.today.gamesdk.shabdamsdk.ToastUtils;
import com.today.gamesdk.shabdamsdk.Utils;
import com.today.gamesdk.shabdamsdk.event.CleverTapEvent;
import com.today.gamesdk.shabdamsdk.event.CleverTapEventConstants;
import com.today.gamesdk.shabdamsdk.model.SignupRequest;
import com.today.gamesdk.shabdamsdk.model.gamesubmit.SubmitGameRequest;
import com.today.gamesdk.shabdamsdk.model.leaderboard.LeaderboardListModel;
import com.today.gamesdk.shabdamsdk.pref.CommonPreference;
import com.today.gamesdk.shabdamsdk.ui.adapter.GetLeaderboardListAdapter;
import com.today.gamesdk.shabdamsdk.ui.englishShabdam.englishGameActivity.EnglishGameActivity;
import com.today.gamesdk.shabdamsdk.utils.ShabdamLanguagePreference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ShabdamLeaderBoardActivity extends ShabdamBaseActivity implements GameView, View.OnClickListener {

    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private String type;
    private GamePresenter gamePresenter;
    private GetLeaderboardListAdapter adapter;
    private RecyclerView recyclerView;
    private RelativeLayout rl_one, rl_two, rl_three, rl_share_btn,rlProgressBar,rlLeaderBoardMain;
    private TextView tv_name_one, tv_name_two, tv_name_three;
    private InterstitialAd mInterstitialAd;
    private LinearLayout ll_google_sign_in;
    private TextView tv_google_sign_in;
    private String gameStatus, gameTime;
    private int noOfattempt;
    private AdRequest adRequest;

    private ImageView iv_LangChange;

    String lan_Id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader_board_shabdam);
        adRequest = new AdRequest.Builder().build();
        gamePresenter = new GamePresenter(this, ShabdamLeaderBoardActivity.this);

        interstitialAdd();
        inIt();
        googleSignIn();
    }

    @SuppressLint("WrongViewCast")
    private void inIt() {
        findViewById(R.id.iv_back_btn).setOnClickListener(this);
        recyclerView = findViewById(R.id.rv_getLeaderboard_List);
        rlProgressBar = findViewById(R.id.rlProgressBar);
        rl_one = findViewById(R.id.rl_one);
        rl_two = findViewById(R.id.rl_two);
        rl_three = findViewById(R.id.rl_three);
        tv_name_one = findViewById(R.id.tv_name_one);
        tv_name_two = findViewById(R.id.tv_name_two);
        tv_name_three = findViewById(R.id.tv_name_three);
        rl_share_btn = findViewById(R.id.rl_share_btn);
        ll_google_sign_in = findViewById(R.id.ll_google_sign_in);
        ll_google_sign_in.setOnClickListener(this);
        tv_google_sign_in = findViewById(R.id.tv_google_sign_in);
        findViewById(R.id.rl_agla_shabd_btn).setOnClickListener(this);

        iv_LangChange = findViewById(R.id.iv_LangChange);
        iv_LangChange.setOnClickListener(this);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        if(intent != null && intent.getExtras() != null){
            if(!TextUtils.isEmpty(getIntent().getStringExtra(Constants.GAME_STATUS))){
                gameStatus = getIntent().getStringExtra(Constants.GAME_STATUS);
            }

            if(!TextUtils.isEmpty(getIntent().getStringExtra(Constants.TIME))){
                gameTime = getIntent().getStringExtra(Constants.TIME);
            }

            if(getIntent().getIntExtra(Constants.NUMBER_OF_ATTEMPT, 0) != 0){
                noOfattempt = getIntent().getIntExtra(Constants.NUMBER_OF_ATTEMPT, 1);
            }
        }

        rl_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleverTapEvent.getCleverTapEvents(ShabdamLeaderBoardActivity.this).createOnlyEvent(CleverTapEventConstants.LB_SHARE);
                takeScreenShot(getWindow().getDecorView());
            }
        });


        String gameId = CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID);
        if (!TextUtils.isEmpty(gameId)) {
            ll_google_sign_in.setVisibility(View.GONE);
            rlProgressBar.setVisibility(View.GONE);
            callGetLeaderBoardListAPI();

        } else {
            rlProgressBar.setVisibility(View.GONE);
            ll_google_sign_in.setVisibility(View.VISIBLE);
            tv_google_sign_in.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (ShabdamLeaderBoardActivity.this != null) {
                        if (ToastUtils.checkInternetConnection(ShabdamLeaderBoardActivity.this)) {
                            signIn();

                        } else {
                            Toast.makeText(ShabdamLeaderBoardActivity.this, getString(R.string.ensure_internet), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
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

                Utils.saveUserData(ShabdamLeaderBoardActivity.this,personName, personName, personEmail, String.valueOf(personPhoto));
                CleverTapEvent.getCleverTapEvents(ShabdamLeaderBoardActivity.this).createOnlyEvent(CleverTapEventConstants.LB_GOOGLE_LOGIN);

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

                if(!TextUtils.isEmpty(gameTime)){

                    if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")) {
                        SubmitGameRequest submitGameRequest = new SubmitGameRequest();
                        submitGameRequest.setGameUserId(CommonPreference.getInstance(ShabdamLeaderBoardActivity.this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
                        submitGameRequest.setGameStatus(gameStatus);
                        submitGameRequest.setNoOfAttempt(noOfattempt);
                        submitGameRequest.setTime(gameTime);
                        submitGameRequest.setLanguageId("1");
                        gamePresenter.submitGame(submitGameRequest);
                    }else{
                        SubmitGameRequest submitGameRequest = new SubmitGameRequest();
                        submitGameRequest.setGameUserId(CommonPreference.getInstance(ShabdamLeaderBoardActivity.this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
                        submitGameRequest.setGameStatus(gameStatus);
                        submitGameRequest.setNoOfAttempt(noOfattempt);
                        submitGameRequest.setTime(gameTime);
                        submitGameRequest.setLanguageId("2");
                        gamePresenter.submitGame(submitGameRequest);
                    }


                }
            }
        }
    }

    @Override
    public void onGameSubmit() {
        GameView.super.onGameSubmit();
        String game_id = CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID);



        //  compositeDisposable = interestPresenter.loadCategoryData();
        if(gamePresenter != null)
            gamePresenter.fetchLeaderBoardList(game_id, lan_Id);
    }

    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }



    private void callGetLeaderBoardListAPI() {
        String game_id = CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID);


        //  compositeDisposable = interestPresenter.loadCategoryData();
        if(gamePresenter != null)
            if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")) {
                lan_Id = "1";
            }else{
                lan_Id = "2";
            }
                gamePresenter.fetchLeaderBoardList(game_id, lan_Id);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back_btn) {
            loadAdd();
            if (!TextUtils.isEmpty(type) && type.equals("1")) {
                Intent intent = new Intent(this, ShabdamActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else if (!TextUtils.isEmpty(type) && type.equals("2")) {
                //ad added today
                loadAdd();

                if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")) {
                    Intent intent = new Intent(this, GameActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user_id", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
                    intent.putExtra("name", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.NAME));
                    intent.putExtra("uname", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.UNAME));
                    intent.putExtra("email", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.EMAIL));
                    intent.putExtra("profile_image", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.PROFILE_IMAGE));
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(this, EnglishGameActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("user_id", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
                    intent.putExtra("name", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.NAME));
                    intent.putExtra("uname", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.UNAME));
                    intent.putExtra("email", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.EMAIL));
                    intent.putExtra("profile_image", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.PROFILE_IMAGE));
                    startActivity(intent);
                    finish();
                }
            } else {
                onBackPressed();
            }
        } else if (view.getId() == R.id.rl_agla_shabd_btn) {
            findViewById(R.id.rl_agla_shabd_btn).setClickable(false);
            loadAdd();
            CleverTapEvent.getCleverTapEvents(ShabdamLeaderBoardActivity.this).createOnlyEvent(CleverTapEventConstants.LB_AGLA_SHABD);
        } else if(view.getId() == R.id.iv_LangChange){
           /* String activityToStart = "com.tvtoday.crosswordhindi.views.activity.languageSelection.LanguageSelectionActivity";
            try {
                Class<?> c = Class.forName(activityToStart);
                Intent intent = new Intent(this, c);
                intent.putExtra("called_game_id",1);
                startActivity(intent);

            } catch (ClassNotFoundException ignored) {
            }*/



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

                        ShabdamLanguagePreference.getInstance(ShabdamLeaderBoardActivity.this).setLanguage("hi");
                        CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI);
                        CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI);

                        Intent intent = new Intent(ShabdamLeaderBoardActivity.this, GameActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }else{
                        GameDataManager.getInstance().getDataList().clear();

                        ShabdamLanguagePreference.getInstance(ShabdamLeaderBoardActivity.this).setLanguage("");
                        CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH);
                        CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH);

                        Intent intent = new Intent(ShabdamLeaderBoardActivity.this, EnglishGameActivity.class);
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
        }
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
                this, CommonPreference.getInstance(ShabdamLeaderBoardActivity.this.getApplicationContext()).getPackageString("applicationId")+".LeaderBoardActivity.provider",
                imageFile);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_TEXT, Constants.LEADER_BOARD_SHARE_MSG);
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        try {
            this.startActivity(Intent.createChooser(intent, "Share With"));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onError(String errorMsg) {

    }

    @Override
    public void onGetLeaderBoardListFetched(List<LeaderboardListModel> list) {
        if (list.size() == 1) {
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.INVISIBLE);
            rl_three.setVisibility(View.INVISIBLE);
            tv_name_one.setText(list.get(0).getName());
        } else if (list.size() == 2) {
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.INVISIBLE);
            tv_name_one.setText(list.get(0).getName());
            tv_name_two.setText(list.get(1).getName());

        } else if (list.size() == 3) {
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            tv_name_one.setText(list.get(0).getName());
            tv_name_two.setText(list.get(1).getName());
            tv_name_three.setText(list.get(2).getName());
        } else if (list.size() == 4) {
            rl_one.setVisibility(View.VISIBLE);
            rl_two.setVisibility(View.VISIBLE);
            rl_three.setVisibility(View.VISIBLE);
            tv_name_one.setText(list.get(0).getName());
            tv_name_two.setText(list.get(1).getName());
            tv_name_three.setText(list.get(2).getName());
        }

        adapter = new GetLeaderboardListAdapter(this, list);
        RecyclerView.LayoutManager layoutManagerLive = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerLive);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (!TextUtils.isEmpty(type) && type.equals("2")) {

            if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")) {
                Intent intent = new Intent(this, GameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user_id", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
                intent.putExtra("name", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.NAME));
                intent.putExtra("uname", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.UNAME));
                intent.putExtra("email", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.EMAIL));
                intent.putExtra("profile_image", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.PROFILE_IMAGE));
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(this, EnglishGameActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("user_id", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
                intent.putExtra("name", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.NAME));
                intent.putExtra("uname", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.UNAME));
                intent.putExtra("email", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.EMAIL));
                intent.putExtra("profile_image", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.PROFILE_IMAGE));
                startActivity(intent);
                finish();
            }
        }
    }

    private void interstitialAdd() {

       /* MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });*/

        InterstitialAd.load(this, Constants.INTRESTITIAL_AD_ID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        rlProgressBar.setVisibility(View.GONE);
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
                    interstitialAdd();
                    startGame();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                    super.onAdFailedToShowFullScreenContent(adError);
                    interstitialAdd();
                    startGame();
                }
            });
        } else {
            findViewById(R.id.rl_agla_shabd_btn).setClickable(true);
            //interstitialAdd();
            //loadAdd();
           // startGame();
            // Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    private void startGame() {
        if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamLeaderBoardActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){
            Intent intent = new Intent(this, GameActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("user_id", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
            intent.putExtra("name", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.NAME));
            intent.putExtra("uname", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.UNAME));
            intent.putExtra("email", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.EMAIL));
            intent.putExtra("profile_image", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.PROFILE_IMAGE));
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, EnglishGameActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("user_id", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.GAME_USER_ID));
            intent.putExtra("name", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.NAME));
            intent.putExtra("uname", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.UNAME));
            intent.putExtra("email", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.EMAIL));
            intent.putExtra("profile_image", CommonPreference.getInstance(this.getApplicationContext()).getString(CommonPreference.Key.PROFILE_IMAGE));
            startActivity(intent);
            finish();
        }

    }


    @Override
    protected void onDestroy() {
        mGoogleSignInClient = null;
        adRequest = null;
        adapter = null;
        if (gamePresenter != null) {
            gamePresenter.onDestroy();
        }

        super.onDestroy();
    }
}