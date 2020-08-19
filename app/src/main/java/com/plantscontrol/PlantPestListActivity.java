package com.plantscontrol;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.plantscontrol.dao.PlantPestDatabase;
import com.plantscontrol.entity.PlantPest;
import com.plantscontrol.entity.dto.PlantPestDTO;

import java.util.ArrayList;
import java.util.List;

public class PlantPestListActivity extends AppCompatActivity {

    private static final int ACTIVITY_FORM_REQUEST = 1;
    public static final String ITEM_PLANT_PEST_ID = "ITEM-PLANT-PEST";

    private List<PlantPestDTO> plantPestList = new ArrayList<>();

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_pest_list);

        listView = findViewById(R.id.listViewPlantsPests);
        registerForContextMenu(listView);

        findAllPlantsPest();
    }

    private void findAllPlantsPest() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PlantPestListActivity.this);
                plantPestList = plantPestDatabase.plantPestDao().findAllPlantAndPest();

                PlantPestListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    setValueListView();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_FORM_REQUEST) {
            if (resultCode == RESULT_OK) {
                findAllPlantsPest();
                showToastLong(getString(R.string.pests_list_activityresult_successful_registration));
            }
        }
    }

    private void setValueListView() {
        List<String> list = new ArrayList<String>();

        for (PlantPestDTO item : plantPestList) {
            list.add(item.getPlantPopularName() + " / " + item.getPestPopularName());
        }

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_plants_pests, menu);

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
                editItem(info.position);
                return true;
            case R.id.menuItemDelete:
                deleteItem(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void editItem(int position) {
        openFormEditPlant(plantPestList.get(position).getId());
    }

    private void deleteItem(final int position) {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.text_delete))
                .setMessage(getString(
                        R.string.text_confirm_delete,
                        plantPestList.get(position).getPlantPopularName() + "/" + plantPestList.get(position).getPestPopularName()
                ))
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
                        PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PlantPestListActivity.this);

                        PlantPest plantPestToDelete = plantPestDatabase.plantPestDao().findById(plantPestList.get(position).getId());

                        plantPestDatabase.plantPestDao().delete(plantPestToDelete);
                        plantPestList = plantPestDatabase.plantPestDao().findAllPlantAndPest();

                        PlantPestListActivity.this.runOnUiThread(new Runnable() {
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

    private void openFormEditPlant(Long Id) {
        Intent intent = new Intent(PlantPestListActivity.this, PlantPestFormActivity.class);
        intent.putExtra(ITEM_PLANT_PEST_ID, Id);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }

    private void openFormNewPlantPest() {
        Intent intent = new Intent(PlantPestListActivity.this, PlantPestFormActivity.class);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }

    private void showToastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}