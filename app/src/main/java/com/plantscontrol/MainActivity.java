package com.plantscontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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

    public void openActivityAuthorship(View view) {
        Intent intent = new Intent(this, AuthorshipActivity.class);
        startActivity(intent);
    }

}
