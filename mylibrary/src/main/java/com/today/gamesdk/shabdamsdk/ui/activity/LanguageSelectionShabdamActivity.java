package com.today.gamesdk.shabdamsdk.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.today.gamesdk.shabdamsdk.R;

public class LanguageSelectionShabdamActivity extends AppCompatActivity {

    private LinearLayout lLayHindiBtn;
    private LinearLayout lLayEnglishBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection_shabdam);

        initViews();
    }

    private void initViews() {
        lLayEnglishBtn = findViewById(R.id.lLayEnglishBtn);
        lLayEnglishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        lLayHindiBtn = findViewById(R.id.lLayHindiBtn);
        lLayHindiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}