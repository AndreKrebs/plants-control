package com.plantscontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.plantscontrol.entity.Plant;

import java.util.ArrayList;
import java.util.List;


public class PlantListActivity extends AppCompatActivity {

    private static final int ACTIVITY_FORM_REQUEST = 1;

    private List<Plant> plantList;
    private ListView listViewPlants;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_list);

        listViewPlants = findViewById(R.id.listViewPests);
        registerForContextMenu(listViewPlants);

        if (plantList == null)
            plantList = new ArrayList<>();

        plantList.add(new Plant(1l, "aaaa", "dddd", "hahahahaahah"));
        plantList.add(new Plant(2l, "bbbb", "cccc", "hahahahaahah"));
        plantList.add(new Plant(3l, "cccc", "bbbb", "hahahahaahah"));
        plantList.add(new Plant(4l, "dddd", "aaaa", "hahahahaahah"));

        setValueListView();
    }

    private void setValueListView() {
        List<String> list = new ArrayList<String>();

        for (Plant item : plantList) {
            list.add(item.getIdentificationPlantGarden() + " / " + item.getPopularName());
        }

        listViewPlants.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
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
                openFormNewPlant();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void openFormNewPlant() {
        Intent intent = new Intent(PlantListActivity.this, PlantFormActivity.class);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }


}