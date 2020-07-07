package com.plantscontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;

public class PestFormActivity extends AppCompatActivity {

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
    }
}
