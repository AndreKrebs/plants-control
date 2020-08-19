package com.plantscontrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.plantscontrol.dao.PlantPestDatabase;
import com.plantscontrol.entity.Plant;

import java.util.ArrayList;
import java.util.List;


public class PlantListActivity extends AppCompatActivity {

    private static final int ACTIVITY_FORM_REQUEST = 1;
    public static final String ITEM_PLANT_ID = "ITEM-PLANT";

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

        findAllPlants();
    }

    private void findAllPlants() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PlantListActivity.this);
                plantList = plantPestDatabase.plantDao().findAll();

                PlantListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setValueListView();
                    }
                });
            }
        });
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_FORM_REQUEST) {
            if (resultCode == RESULT_OK) {
                findAllPlants();
                showToastLong(getString(R.string.pests_list_activityresult_successful_registration));
            }
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        getMenuInflater().inflate(R.menu.menu_generic_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.menuItemEdit:
                editItemPlant(info.position);
                return true;
            case R.id.menuItemDelete:
                deleteItemPlant(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void editItemPlant(int position) {
        openFormEditPlant(plantList.get(position).getId());
    }

    private void deleteItemPlant(final int position) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.text_delete))
                .setMessage(getString(R.string.text_confirm_delete, plantList.get(position).getPopularName()))
                .setPositiveButton(getString(R.string.text_yes), null)
                .setNegativeButton(getString(R.string.text_no), null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PlantListActivity.this);
                        plantPestDatabase.plantDao().delete(plantList.get(position));
                        plantList = plantPestDatabase.plantDao().findAll();

                        PlantListActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setValueListView();
                            }
                        });
                    }
                });
                dialog.dismiss();
            }
        });

    }

    private void openFormEditPlant(Long plantId) {
        Intent intent = new Intent(PlantListActivity.this, PlantFormActivity.class);
        intent.putExtra(ITEM_PLANT_ID, plantId);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }

    public void openFormNewPlant() {
        Intent intent = new Intent(PlantListActivity.this, PlantFormActivity.class);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }

    private void showToastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}