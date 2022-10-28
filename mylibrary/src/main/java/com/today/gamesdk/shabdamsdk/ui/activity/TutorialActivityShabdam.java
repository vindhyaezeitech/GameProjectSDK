package com.today.gamesdk.shabdamsdk.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.viewpager2.widget.ViewPager2;

import com.today.gamesdk.shabdamsdk.R;
import com.today.gamesdk.shabdamsdk.event.CleverTapEvent;
import com.today.gamesdk.shabdamsdk.event.CleverTapEventConstants;
import com.today.gamesdk.shabdamsdk.pref.CommonPreference;
import com.today.gamesdk.shabdamsdk.ui.adapter.ImageSlideAdapterShabdam;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivityShabdam extends ShabdamBaseActivity {
    int currentPage;
    private TextView next_btn, skip_btn, start_btn;
    private ImageSlideAdapterShabdam imageSlideAdapter;
    private ViewPager2 viewPager2;
    private String type;



   private Context context;
   private Resources resources;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_shabdam);

        viewPager2 = findViewById(R.id.vp_skip_viewpager1);

        imageSlideAdapter = new ImageSlideAdapterShabdam(this, getImages());
        viewPager2.setAdapter(imageSlideAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPage = position;
                invalidateButton();
            }
        });

        next_btn = findViewById(R.id.tv_next_btn);
        skip_btn = findViewById(R.id.tv_skip_btn);
        start_btn = findViewById(R.id.tv_start_btn);


        currentPage = 0;
        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentPage == 0) {
                    CleverTapEvent.getCleverTapEvents(TutorialActivityShabdam.this).createOnlyEvent(CleverTapEventConstants.SKIP_1);
                } else if (currentPage == 1) {
                    CleverTapEvent.getCleverTapEvents(TutorialActivityShabdam.this).createOnlyEvent(CleverTapEventConstants.SKIP_2);
                } else if (currentPage == 2) {
                    CleverTapEvent.getCleverTapEvents(TutorialActivityShabdam.this).createOnlyEvent(CleverTapEventConstants.SKIP_3);
                }
                CommonPreference.getInstance(TutorialActivityShabdam.this.getApplicationContext()).put(CommonPreference.Key.IS_TUTORIAL_SHOWN, true);
                startActivity(new Intent(TutorialActivityShabdam.this, ShabdamPaheliActivity.class));
                finish();
            }
        });


        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CleverTapEvent.getCleverTapEvents(TutorialActivityShabdam.this).createOnlyEvent(CleverTapEventConstants.START_GAME);
                CommonPreference.getInstance(TutorialActivityShabdam.this.getApplicationContext()).put(CommonPreference.Key.IS_TUTORIAL_SHOWN, true);
                startActivity(new Intent(TutorialActivityShabdam.this, ShabdamPaheliActivity.class));
                finish();
            }
        });

    }

    private void invalidateButton() {
        if (currentPage == imageSlideAdapter.getItemCount() - 1) {
            skip_btn.setVisibility(View.GONE);
            next_btn.setVisibility(View.GONE);
            start_btn.setVisibility(View.VISIBLE);
        } else {
            skip_btn.setVisibility(View.VISIBLE);
            next_btn.setVisibility(View.VISIBLE);
            start_btn.setVisibility(View.GONE);
        }
    }

    private void next() {
        if (currentPage == 0) {
            CleverTapEvent.getCleverTapEvents(TutorialActivityShabdam.this).createOnlyEvent(CleverTapEventConstants.NEXT_1);
        } else if (currentPage == 1) {
            CleverTapEvent.getCleverTapEvents(TutorialActivityShabdam.this).createOnlyEvent(CleverTapEventConstants.NEXT_2);
        } else if (currentPage == 2) {
            CleverTapEvent.getCleverTapEvents(TutorialActivityShabdam.this).createOnlyEvent(CleverTapEventConstants.NEXT_3);
        }

        currentPage++;
        viewPager2.setCurrentItem(currentPage);

    }

    private List<Integer> getImages() {

        if(!TextUtils.isEmpty(CommonPreference.getInstance(TutorialActivityShabdam.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE)) && CommonPreference.getInstance(TutorialActivityShabdam.this).getAppLanguageString(CommonPreference.Key.SHABDAM_APP_LANGUAGE).equals("hindi")){

            List<Integer> list = new ArrayList<>();
            list.add(R.drawable.first_img_shabdam);
            list.add(R.drawable.second_img_shabdam);
            list.add(R.drawable.third_img_shabdam);
            list.add(R.drawable.fourth_img);

            return list;
        }
        else{
            List<Integer> list = new ArrayList<>();
            list.add(R.drawable.skip1);
            list.add(R.drawable.skip2);
            list.add(R.drawable.skip3);
            list.add(R.drawable.skip4);

            return list;
        }

    }
}