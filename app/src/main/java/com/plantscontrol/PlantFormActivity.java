package com.plantscontrol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.plantscontrol.dao.PlantPestDatabase;
import com.plantscontrol.entity.Pest;
import com.plantscontrol.entity.Plant;

public class PlantFormActivity extends AppCompatActivity {

    private EditText editTextIdentificationPlantGarden, editTextPopularName, editTextScientificName;

    private Plant editPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_form);

        editTextIdentificationPlantGarden = findViewById(R.id.editTextIdentificationPlantGarden);
        editTextPopularName = findViewById(R.id.editTextPopularName);
        editTextScientificName = findViewById(R.id.editTextScientificName);

        checkExistsItemToEdit();
    }


    private void checkExistsItemToEdit() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Long pestId = null;
        if (bundle != null) {
            pestId = (Long) bundle.getSerializable(PlantListActivity.ITEM_PLANT_ID);

            if (pestId != null)
                findPestById(pestId);
        }
    }

    private void findPestById(final Long pestId) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PlantFormActivity.this);
                editPlant = plantPestDatabase.plantDao().findById(pestId);

                setValueFieldsForm();
            }
        });
    }

    private void setValueFieldsForm() {
        editTextIdentificationPlantGarden.setText(editPlant.getIdentificationPlantGarden());
        editTextPopularName.setText(editPlant.getPopularName());
        editTextScientificName.setText(editPlant.getScientificName());
    }

    public void clearForm() {
        editTextIdentificationPlantGarden.setText(null);
        editTextPopularName.setText(null);
        editTextScientificName.setText(null);

        editTextIdentificationPlantGarden.requestFocus();

        Toast.makeText(this, getString(R.string.text_form_fields_clean), Toast.LENGTH_SHORT).show();
    }

    public void saveForm() {
        if (editTextIdentificationPlantGarden.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.plant_form_identification_plant_garden)
            ));

            editTextIdentificationPlantGarden.requestFocus();
            return;
        }

        if (editTextPopularName.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.plant_form_popular_name)
            ));

            editTextPopularName.requestFocus();
            return;
        }

        if (editTextScientificName.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.plant_form_scientific_name)
            ));

            editTextScientificName.requestFocus();
            return;
        }

        Plant plant = new Plant();

        if (editPlant != null && editPlant.getId() != null)
            plant.setId(editPlant.getId());

        plant.setIdentificationPlantGarden(editTextIdentificationPlantGarden.getText().toString());
        plant.setPopularName(editTextPopularName.getText().toString());
        plant.setScientificName(editTextScientificName.getText().toString());

        saveFormAndReturnToList(plant);
    }

    private void saveFormAndReturnToList(final Plant plant) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Long id = null;

                PlantPestDatabase bla = PlantPestDatabase.getDatabase(PlantFormActivity.this);

                if (plant.getId() == null) {
                    id = bla.plantDao().insert(plant);
                } else {
                    bla.plantDao().update(plant);
                    id = plant.getId();
                }

                if (id != null) {
                    Intent returnIntent = new Intent();

                    setResult(RESULT_OK, returnIntent);
                    finish();
                } else {
                    setResult(RESULT_CANCELED);
                    finish();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuItemSaveForm:
                saveForm();
                return true;
            case R.id.menuItemCleanForm:
                clearForm();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_generic, menu);

        return true;
    }

    private void showToastFailSave(String msg) {
        if (!msg.equals(""))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}