package com.today.gamesdk.shabdamsdk.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.today.gamesdk.shabdamsdk.GameActivity;
import com.today.gamesdk.shabdamsdk.GamePresenter;
import com.today.gamesdk.shabdamsdk.GameView;
import com.today.gamesdk.shabdamsdk.R;
import com.today.gamesdk.shabdamsdk.ShabdamSplashActivity;
import com.today.gamesdk.shabdamsdk.ToastUtils;
import com.today.gamesdk.shabdamsdk.Utils;
import com.today.gamesdk.shabdamsdk.event.CleverTapEvent;
import com.today.gamesdk.shabdamsdk.event.CleverTapEventConstants;
import com.today.gamesdk.shabdamsdk.model.SignupRequest;
import com.today.gamesdk.shabdamsdk.pref.CommonPreference;

public class UserDetailActivity extends ShabdamBaseActivity implements GameView {
    private static final int RC_SIGN_IN = 1;
    GoogleSignInClient mGoogleSignInClient;
    private TextView tv_google_sign_in;
    private Button play_guest_btn;
    private LinearLayout ll_welcome;
    private FrameLayout flLoading;
    private USER_TYPE user_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_detail);
        ll_welcome = findViewById(R.id.ll_welcome_layout);
        flLoading = findViewById(R.id.fl_loading);
        Animation bottomDown = AnimationUtils.loadAnimation(this,
                R.anim.bottom_to_up_slide);
        ll_welcome.startAnimation(bottomDown);

        String email = CommonPreference.getInstance(UserDetailActivity.this.getApplicationContext()).getString(CommonPreference.Key.EMAIL);
        if (!TextUtils.isEmpty(email)) {
            Intent intent = new Intent(UserDetailActivity.this, ShabdamSplashActivity.class);
            startActivity(intent);
            finish();
        } else {
            inIt();
            googleSignIn();
        }
       /* Intent intent = new Intent(UserDetailActivity.this, ShabdamSplashActivity.class);
        intent.putExtra("user_id", "1123444");

        startActivity(intent);
        finish();*/

    }

    private void googleSignIn() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void inIt() {
        play_guest_btn = findViewById(R.id.play_guest_btn);

        play_guest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleverTapEvent.getCleverTapEvents(UserDetailActivity.this).createOnlyEvent(CleverTapEventConstants.PLAY_GUEST);
                user_type = USER_TYPE.GUEST;
                CommonPreference.getInstance(UserDetailActivity.this.getApplicationContext()).put(CommonPreference.Key.USER_ID, "");
                CommonPreference.getInstance(UserDetailActivity.this.getApplicationContext()).put(CommonPreference.Key.GAME_USER_ID, "");
                startFlow();
            }
        });

        tv_google_sign_in = findViewById(R.id.tv_google_sign_in);

        tv_google_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (UserDetailActivity.this != null) {
                    if (ToastUtils.checkInternetConnection(UserDetailActivity.this)) {
                        signIn();

                    } else {
                        Toast.makeText(UserDetailActivity.this, getString(R.string.ensure_internet), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void startFlow() {
        if (!CommonPreference.getInstance(UserDetailActivity.this.getApplicationContext()).getBoolean(CommonPreference.Key.IS_TUTORIAL_SHOWN, false)) {
            startActivity(new Intent(UserDetailActivity.this, TutorialActivityShabdam.class));
            finish();
        } else if (!CommonPreference.getInstance(UserDetailActivity.this.getApplicationContext()).getBoolean(CommonPreference.Key.IS_RULE_SHOWN, false)) {
            startActivity(new Intent(UserDetailActivity.this, ShabdamPaheliActivity.class));
            finish();
        } else {
            startActivity(new Intent(UserDetailActivity.this, GameActivity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
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

                Utils.saveUserData(UserDetailActivity.this, personName, personName, personEmail, String.valueOf(personPhoto));
                CleverTapEvent.getCleverTapEvents(UserDetailActivity.this).createOnlyEvent(CleverTapEventConstants.SIGN_UP);

                GamePresenter gamePresenter = new GamePresenter(this, UserDetailActivity.this);
                gamePresenter.signUpUser(signupRequest);

                //Toast.makeText(this, ""+personEmail, Toast.LENGTH_SHORT).show();
            }
            //startActivity(new Intent(this, ShabdamSplashActivity.class));

        } catch (ApiException e) {
            Log.d("Message", e.toString());
        }
    }

    @Override
    public void showProgress() {
        if (flLoading != null) {
            flLoading.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void hideProgress() {
        if (flLoading != null) {
            flLoading.setVisibility(View.GONE);
        }

    }

    @Override
    public void onError(String errorMsg) {
        if (flLoading != null) {
            flLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAddUser(com.today.gamesdk.shabdamsdk.model.adduser.Data data) {
        if (data != null) {
            if (data.getId() != "0") {
                CommonPreference.getInstance(this.getApplicationContext()).put(CommonPreference.Key.GAME_USER_ID, String.valueOf(data.getId()));
                startFlow();
            }
        }
    }

    private enum USER_TYPE {GUEST, GOOGLE}
}