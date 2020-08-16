package com.plantscontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.plantscontrol.dao.PlantPestDatabase;
import com.plantscontrol.entity.Plant;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PlantPestListActivity extends AppCompatActivity {

    private static final int ACTIVITY_FORM_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_pest_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_plants, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                openFormNewPlantPest();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openFormNewPlantPest() {
        Intent intent = new Intent(PlantPestListActivity.this, PlantPestFormActivity.class);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }
}