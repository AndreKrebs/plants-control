package com.plantscontrol;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.plantscontrol.dao.PlantPestDatabase;
import com.plantscontrol.entity.Pest;
import com.plantscontrol.entity.enums.PestPropagationSpeedEnum;
import com.plantscontrol.entity.enums.PestTypeEnum;
import com.plantscontrol.entity.enums.PestWeatherEnum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PestFormActivity extends AppCompatActivity {

    private Spinner spinnerWeather;
    private RadioGroup radioGroupPestType;
    private CheckBox checkBoxSlow, checkBoxModerate, checkBoxFast;
    private EditText editTextPopularName, editTextScientificName, editTextPestDescription, editTexControlMethodsDescription;
    private Pest editPest;
    private String valueRadioButtonPestTypeSelected;
    private String valueCheckboxPropagationSpeedSelected;
    ArrayList<String> listWeatherOptions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_form);
        setTitle(R.string.pest_form_page_title);

        spinnerWeather = findViewById(R.id.spinnerWeather);

        checkBoxSlow = findViewById(R.id.checkBoxSlow);
        checkBoxModerate = findViewById(R.id.checkBoxModerate);
        checkBoxFast = findViewById(R.id.checkBoxFast);

        editTextPopularName = findViewById(R.id.editTextPopularName);
        editTextScientificName = findViewById(R.id.editTextScientificName);
        editTextPestDescription = findViewById(R.id.editTextPestDescription);
        editTexControlMethodsDescription = findViewById(R.id.editTexControlMethodsDescription);

        radioGroupPestType = findViewById(R.id.radioGroupPestType);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        optionsSpinnerWeather();

        checkExistsItemToEdit();
    }

    private void checkExistsItemToEdit() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            editPest = (Pest) bundle.getSerializable(PestListActivity.ITEM_PEST);

            populateFormFromObject(editPest);

        }
    }

    private void populateFormFromObject(Pest pest) {

        editTextPopularName.setText(pest.getPopularName(), TextView.BufferType.EDITABLE);
        editTextScientificName.setText(pest.getScientificName(), TextView.BufferType.EDITABLE);
        editTextPestDescription.setText(pest.getDescription());
        editTexControlMethodsDescription.setText(pest.getMethodsDescription());

        setRadioButtonPestType(pest.getType());
        setCheckboxPropagationSpeed(pest.getVelocity());
        setSpinnerWeather(pest.getWeather());
    }

    private void optionsSpinnerWeather() {
        listWeatherOptions.add("");
        List<PestWeatherEnum> weatherOptionsList = Arrays.asList(PestWeatherEnum.values());

        for (PestWeatherEnum item : weatherOptionsList) {
            listWeatherOptions.add(getString(getResources()
                                    .getIdentifier(
                                            "pest_form_" + item.getWeather(),
                                            "string",
                                            getPackageName()
                                    )));
        }

        ArrayAdapter<String> adapter = new
            ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listWeatherOptions);

        spinnerWeather.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    public void clearForm() {
        radioGroupPestType.clearCheck();
        spinnerWeather.setSelection(0);

        checkBoxSlow.setChecked(false);
        checkBoxModerate.setChecked(false);
        checkBoxFast.setChecked(false);

        editTextPopularName.setText(null);
        editTextScientificName.setText(null);
        editTextPestDescription.setText(null);
        editTexControlMethodsDescription.setText(null);

        editTextPopularName.requestFocus();

        Toast.makeText(this, getString(R.string.text_form_fields_clean), Toast.LENGTH_SHORT).show();
    }

    public void saveForm() {
        if (editTextPopularName.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.pest_form_popular_name)
                ));

            editTextPopularName.requestFocus();
            return;
        }
        if (editTextScientificName.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.pest_form_scientific_name)
            ));
            editTextScientificName.requestFocus();
            return;
        }
        if (radioGroupPestType.getCheckedRadioButtonId() == -1) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation_radiobutton,
                    getString(R.string.pest_form_type)
            ));
            radioGroupPestType.requestFocus();
            return;
        }
        if (editTextPestDescription.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.pest_form_description)
            ));
            editTextPestDescription.requestFocus();
            return;
        }
        if (editTextScientificName.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.pest_form_scientific_name)
            ));
            editTextScientificName.requestFocus();
            return;
        }
        if (!checkBoxSlow.isChecked() && !checkBoxModerate.isChecked() && !checkBoxFast.isChecked()) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation_checkbox,
                    getString(R.string.pest_form_propagation_speed)
            ));
            checkBoxSlow.requestFocus();
            return;
        }
        if (spinnerWeather.getSelectedItem().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.pest_form_ideal_weather_propagation)
            ));
            checkBoxSlow.requestFocus();
            return;
        }
        if (editTexControlMethodsDescription.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.pest_form_control_description)
            ));
            editTextPestDescription.requestFocus();
            return;
        }

        Pest pest = new Pest();

        if (editPest != null && editPest.getId() != null)
            pest.setId(editPest.getId());

        pest.setVelocity(valueCheckboxPropagationSpeedSelected);
        pest.setType(this.valueRadioButtonPestTypeSelected);

        pest.setMethodsDescription(editTexControlMethodsDescription.getText().toString());
        pest.setScientificName(editTextScientificName.getText().toString());
        pest.setWeather(spinnerWeather.getSelectedItem().toString());
        pest.setPopularName(editTextPopularName.getText().toString());
        pest.setDescription(editTextPestDescription.getText().toString());

        saveFormAndReturnToList(pest);
    }

    private void saveFormAndReturnToList(final Pest pest) {

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Long id = null;

                PlantPestDatabase bla = PlantPestDatabase.getDatabase(PestFormActivity.this);

                if (pest.getId() == null) {
                    id = bla.pestDao().insert(pest);
                } else {
                    bla.pestDao().update(pest);
                    id = pest.getId();
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

    private void showToastFailSave(String msg) {
        if (!msg.equals(""))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void clickRadioButtonPestType(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonPestTypeInsect:
                if (checked)
                    valueRadioButtonPestTypeSelected = PestTypeEnum.valueOf("INSECT").getType();
                break;
            case R.id.radioButtonPestTypeFungus:
                if (checked)
                    valueRadioButtonPestTypeSelected = PestTypeEnum.valueOf("FUNGUS").getType();
                break;
            case R.id.radioButtonPestTypeVirus:
                if (checked)
                    valueRadioButtonPestTypeSelected = PestTypeEnum.valueOf("VIRUS").getType();
                break;
            default:
                valueRadioButtonPestTypeSelected = "";
                break;
        }
    }

    private void setRadioButtonPestType(String value) {
        if (PestTypeEnum.valueOf("INSECT").getType().equals(value)) {
            ((RadioButton) findViewById(R.id.radioButtonPestTypeInsect)).setChecked(true);
            valueRadioButtonPestTypeSelected = PestTypeEnum.valueOf("INSECT").getType();
            return;
        }
        if (PestTypeEnum.valueOf("FUNGUS").getType().equals(value)) {
            ((RadioButton) findViewById(R.id.radioButtonPestTypeFungus)).setChecked(true);
            valueRadioButtonPestTypeSelected = PestTypeEnum.valueOf("FUNGUS").getType();
            return;
        }
        if (PestTypeEnum.valueOf("VIRUS").getType().equals(value)) {
            ((RadioButton) findViewById(R.id.radioButtonPestTypeVirus)).setChecked(true);
            valueRadioButtonPestTypeSelected = PestTypeEnum.valueOf("VIRUS").getType();
            return;
        }
    }

    public void clickCheckboxPropagationSpeed(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        String selectedValue = "";

        if (view.getId() == R.id.checkBoxFast) {
            if (checked)
                selectedValue = PestPropagationSpeedEnum.valueOf("FAST").getPropagationSpeed();
            else
                deselectCheckbox(view.getId());
        } else {
            deselectCheckbox(R.id.checkBoxFast);
        }

        if (view.getId() == R.id.checkBoxModerate) {
            if (checked)
                selectedValue = PestPropagationSpeedEnum.valueOf("MODERATE").getPropagationSpeed();
            else
                deselectCheckbox(view.getId());
        } else {
            deselectCheckbox(R.id.checkBoxModerate);
        }

        if (view.getId() == R.id.checkBoxSlow) {
            if (checked)
                selectedValue = PestPropagationSpeedEnum.valueOf("SLOW").getPropagationSpeed();
            else
                deselectCheckbox(view.getId());
        } else {
            deselectCheckbox(R.id.checkBoxSlow);
        }

        this.valueCheckboxPropagationSpeedSelected = selectedValue;
    }

    private void deselectCheckbox(int idField) {
        ((CheckBox) findViewById(idField)).setChecked(false);
    }

    private void setCheckboxPropagationSpeed(String value) {
        if (PestPropagationSpeedEnum.valueOf("FAST").getPropagationSpeed().equals(value)) {
            ((CheckBox) findViewById(R.id.checkBoxFast)).setChecked(true);
            valueCheckboxPropagationSpeedSelected = PestPropagationSpeedEnum.valueOf("FAST").getPropagationSpeed();
            return;
        }

        if (PestPropagationSpeedEnum.valueOf("MODERATE").getPropagationSpeed().equals(value)) {
            ((CheckBox) findViewById(R.id.checkBoxModerate)).setChecked(true);
            valueCheckboxPropagationSpeedSelected = PestPropagationSpeedEnum.valueOf("MODERATE").getPropagationSpeed();
            return;
        }

        if (PestPropagationSpeedEnum.valueOf("SLOW").getPropagationSpeed().equals(value)) {
            ((CheckBox) findViewById(R.id.checkBoxSlow)).setChecked(true);
            valueCheckboxPropagationSpeedSelected = PestPropagationSpeedEnum.valueOf("SLOW").getPropagationSpeed();
            return;
        }
    }

    private void setSpinnerWeather(String value) {
        spinnerWeather.setSelection(listWeatherOptions.indexOf(value));
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
            case android.R.id.home :
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
}
