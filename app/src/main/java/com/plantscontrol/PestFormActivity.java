package com.plantscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.plantscontrol.entity.Pest;

import java.util.ArrayList;
import java.util.List;

public class PestFormActivity extends AppCompatActivity {

    public static final String NEW_PEST = "NEW-PEST";

    private Spinner spinnerWeather;
    private RadioGroup radioGroupPestType;
    private CheckBox checkBoxSlow, checkBoxModerate, checkBoxFast;
    private EditText editTextPopularName, editTextScientificName, editTextPestDescription, editTexControlMethodsDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_form);
        setTitle(R.string.pest_page_title_form);

        spinnerWeather = findViewById(R.id.spinnerWeather);

        checkBoxSlow = findViewById(R.id.checkBoxSlow);
        checkBoxModerate = findViewById(R.id.checkBoxModerate);
        checkBoxFast = findViewById(R.id.checkBoxFast);

        editTextPopularName = findViewById(R.id.editTextPopularName);
        editTextScientificName = findViewById(R.id.editTextScientificName);
        editTextPestDescription = findViewById(R.id.editTextPestDescription);
        editTexControlMethodsDescription = findViewById(R.id.editTexControlMethodsDescription);

        radioGroupPestType = findViewById(R.id.radioGroupPestType);


        optionsSpinnerWeather();
    }

    private void optionsSpinnerWeather() {
        ArrayList<String> list = new ArrayList<>();

        list.add("");
        list.add(getString(R.string.equatorial));
        list.add(getString(R.string.tropical));
        list.add(getString(R.string.subtropical));
        list.add(getString(R.string.desert));
        list.add(getString(R.string.temperate));
        list.add(getString(R.string.mediterranean));
        list.add(getString(R.string.semi_arid));
        list.add(getString(R.string.continental_arid));
        list.add(getString(R.string.mountain_cold));
        list.add(getString(R.string.polar));

        ArrayAdapter<String> adapter = new
            ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list);

        spinnerWeather.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED);
    }

    public void clearForm(View view) {
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

        Toast.makeText(this, "Formulário limpo!", Toast.LENGTH_SHORT).show();
    }

    public void saveForm(View view) {

        if (editTextPopularName.getText().toString().equals("")) {
            showToastFailSave("Campo `"+ getString(R.string.popular_name_pest) +"` é obrigatório");
            editTextPopularName.requestFocus();
            return;
        }
        if (editTextScientificName.getText().toString().equals("")) {
            showToastFailSave("Campo `"+ getString(R.string.scientific_name_pest) +"` é obrigatório");
            editTextScientificName.requestFocus();
            return;
        }
        if (radioGroupPestType.getCheckedRadioButtonId() == -1) {
            showToastFailSave("Selecione uma opção em `"+ getString(R.string.pest_type) +"`");
            radioGroupPestType.requestFocus();
            return;
        }
        if (editTextPestDescription.getText().toString().equals("")) {
            showToastFailSave("Campo `"+ getString(R.string.pest_description) +"` é obrigatório");
            editTextPestDescription.requestFocus();
            return;
        }
        if (editTextScientificName.getText().toString().equals("")) {
            showToastFailSave("Campo `"+ getString(R.string.scientific_name_pest) +"` é obrigatório");
            editTextScientificName.requestFocus();
            return;
        }
        if (!checkBoxSlow.isChecked() && !checkBoxModerate.isChecked() && !checkBoxFast.isChecked()) {
            showToastFailSave("Selecione ao menos uma opção em `"+ getString(R.string.pest_propagation_speed) +"` é obrigatório");
            checkBoxSlow.requestFocus();
            return;
        }
        if (spinnerWeather.getSelectedItem().toString().equals("")) {
            showToastFailSave("Campo `"+ getString(R.string.pest_ideal_weather_propagation) +"` é obrigatório");
            checkBoxSlow.requestFocus();
            return;
        }
        if (editTexControlMethodsDescription.getText().toString().equals("")) {
            showToastFailSave("Campo `"+ getString(R.string.pest_control_description) +"` é obrigatório");
            editTextPestDescription.requestFocus();
            return;
        }

        Pest pest = new Pest();

        String velocity = "";

        if (checkBoxSlow.isChecked())
            velocity = checkBoxSlow.getText().toString();
        else if (checkBoxModerate.isChecked())
            velocity = checkBoxModerate.getText().toString();
        else if (checkBoxFast.isChecked())
            velocity = checkBoxFast.getText().toString();
        pest.setVelocity(velocity);

        int radioButtonID = radioGroupPestType.getCheckedRadioButtonId();
        RadioButton radioButton = radioGroupPestType.findViewById(radioButtonID);
        pest.setType(radioButton.getText().toString());

        pest.setMethodsDescription(editTexControlMethodsDescription.getText().toString());
        pest.setScientificName(editTextScientificName.getText().toString());
        pest.setWeather(spinnerWeather.getSelectedItem().toString());
        pest.setPopularName(editTextPopularName.getText().toString());
        pest.setDescription(editTextPestDescription.getText().toString());

        saveSuccessForm(pest);
    }

    private void saveSuccessForm(Pest pest) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(NEW_PEST, pest);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void showToastFailSave(String msg) {
        if (!msg.equals(""))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
