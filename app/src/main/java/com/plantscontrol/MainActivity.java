package com.plantscontrol;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.plantscontrol.entity.Pest;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.main_page_title_form);
    }

    public void openListPest(View view) {
        Intent intent = new Intent(this, PestListActivity.class);
        startActivity(intent);
    }

    public void openListPlant(View view) {
        Intent intent = new Intent(this, PlantListActivity.class);
        startActivity(intent);
    }
}
