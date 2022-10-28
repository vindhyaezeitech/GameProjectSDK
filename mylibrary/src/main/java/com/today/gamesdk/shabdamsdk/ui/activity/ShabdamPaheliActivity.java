package com.today.gamesdk.shabdamsdk.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.today.gamesdk.shabdamsdk.GameActivity;
import com.today.gamesdk.shabdamsdk.GameDataManager;
import com.today.gamesdk.shabdamsdk.R;
import com.today.gamesdk.shabdamsdk.event.CleverTapEvent;
import com.today.gamesdk.shabdamsdk.event.CleverTapEventConstants;
import com.today.gamesdk.shabdamsdk.pref.CommonPreference;
import com.today.gamesdk.shabdamsdk.ui.englishShabdam.englishGameActivity.EnglishGameActivity;
import com.today.gamesdk.shabdamsdk.utils.ShabdamLanguagePreference;

public class ShabdamPaheliActivity extends ShabdamBaseActivity {

    private Button aage_bade_btn;
    private CheckBox checkBox;
    private ImageView ivRuleHome,iv_LangChange;
    private RelativeLayout rLayBgImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shabdam_paheli);
        aage_bade_btn = findViewById(R.id.aage_bade_btn);
        checkBox = findViewById(R.id.check_box_btn);

        rLayBgImage = findViewById(R.id.rLayBgImage);
        ivRuleHome = findViewById(R.id.ivRuleHome);

        iv_LangChange = findViewById(R.id.iv_LangChange);
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

                            GameDataManager.getInstance().getDataList().clear();

                            ShabdamLanguagePreference.getInstance(ShabdamPaheliActivity.this).setLanguage("hi");
                            CommonPreference.getInstance(ShabdamPaheliActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.HINDI);
                            CommonPreference.getInstance(ShabdamPaheliActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.HINDI);

                            Intent intent = new Intent(ShabdamPaheliActivity.this, GameActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }else{
                            GameDataManager.getInstance().getDataList().clear();

                            ShabdamLanguagePreference.getInstance(ShabdamPaheliActivity.this).setLanguage("");
                            CommonPreference.getInstance(ShabdamPaheliActivity.this).put(CommonPreference.Key.SHABDAM_LANGUAGE,CommonPreference.Key.ENGLISH);
                            CommonPreference.getInstance(ShabdamPaheliActivity.this).put(CommonPreference.Key.SHABDAM_APP_LANGUAGE,CommonPreference.Key.ENGLISH);

                            Intent intent = new Intent(ShabdamPaheliActivity.this, EnglishGameActivity.class);
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
            }
        });
        ivRuleHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamPaheliActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamPaheliActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){

        }else{
            final int sdk = android.os.Build.VERSION.SDK_INT;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                rLayBgImage.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.englis_rule) );
            } else {
                rLayBgImage.setBackground(ContextCompat.getDrawable(this, R.drawable.englis_rule));
            }
        }


        aage_bade_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CommonPreference.getInstance(ShabdamPaheliActivity.this.getApplicationContext()).getBoolean(CommonPreference.Key.IS_RULE_SHOWN) == true) {
                    CleverTapEvent.getCleverTapEvents(ShabdamPaheliActivity.this).createOnlyEvent(CleverTapEventConstants.GO_FORWARD);
                    CleverTapEvent.getCleverTapEvents(ShabdamPaheliActivity.this).createOnlyEvent(CleverTapEventConstants.DONOTSHOW);
                } else {
                    CleverTapEvent.getCleverTapEvents(ShabdamPaheliActivity.this).createOnlyEvent(CleverTapEventConstants.GO_FORWARD);
                }

                if(!TextUtils.isEmpty(CommonPreference.getInstance(ShabdamPaheliActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(ShabdamPaheliActivity.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){
                    startActivity(new Intent(ShabdamPaheliActivity.this, GameActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(ShabdamPaheliActivity.this, EnglishGameActivity.class));
                    finish();
                }
            }
        });

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                CommonPreference.getInstance(ShabdamPaheliActivity.this.getApplicationContext()).put(CommonPreference.Key.IS_RULE_SHOWN, buttonView.isChecked());
            }
        });
    }
}