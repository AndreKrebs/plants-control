package com.plantscontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PestFormActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_form);
        setTitle(R.string.pest_page_title_form);
    }
}
