package com.plantscontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.plantscontrol.dao.PlantPestDatabase;
import com.plantscontrol.entity.Pest;
import com.plantscontrol.entity.Plant;
import com.plantscontrol.entity.PlantPest;
import com.plantscontrol.entity.enums.PestTypeEnum;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlantPestFormActivity extends AppCompatActivity {

    private Spinner spinnerPlants, spinnerPests;
    private EditText editTextDateDetectedPest, editTextDateFinalRegister, editTextFinalDescription;
    private RadioGroup radioGroupResolvedQuestion;

    private List<Plant> plantList = new ArrayList<>();
    private List<Pest> pestList = new ArrayList<>();
    private List<PlantPest> plantPestList = new ArrayList<>();
    private Boolean valueRadioButtonIsResolved;

    private Long selectedPlantId;
    private Long selectedPestId;
    private Calendar calendarDatePestDetected, calendarDateFinalRegister;

    private PlantPest editPlantPest = new PlantPest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_pest_form);

        spinnerPlants = findViewById(R.id.spinnerPlants);
        spinnerPests = findViewById(R.id.spinnerPests);
        editTextDateDetectedPest = findViewById(R.id.editTextDateDetectedPest);
        editTextDateFinalRegister = findViewById(R.id.editTextDateFinalRegister);
        radioGroupResolvedQuestion = findViewById(R.id.radioGroupResolvedQuestion);
        editTextFinalDescription = findViewById(R.id.editTextFinalDescription);

        configureSpinnerPests();
        configureSpinnerPlants();
        configureDateField();
    }

    private void configureDateField() {
        calendarDatePestDetected = Calendar.getInstance();
        calendarDateFinalRegister = Calendar.getInstance();

        editTextDateDetectedPest.setFocusable(false);
        editTextDateDetectedPest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(PlantPestFormActivity.this,
                        R.style.CustomDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendarDatePestDetected.set(year, month, dayOfMonth);
                                String textDate = formatDate(PlantPestFormActivity.this, calendarDatePestDetected.getTime());
                                editTextDateDetectedPest.setText(textDate);
                            }
                        },
                        calendarDatePestDetected.get(Calendar.YEAR),
                        calendarDatePestDetected.get(Calendar.MONTH),
                        calendarDatePestDetected.get(Calendar.DAY_OF_MONTH));

                picker.show();
            }
        });

        editTextDateFinalRegister.setFocusable(false);
        editTextDateFinalRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog picker = new DatePickerDialog(PlantPestFormActivity.this,
                        R.style.CustomDatePickerDialogTheme,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendarDateFinalRegister.set(year, month, dayOfMonth);
                                String textDate = formatDate(PlantPestFormActivity.this, calendarDateFinalRegister.getTime());
                                editTextDateFinalRegister.setText(textDate);
                            }
                        },
                        calendarDateFinalRegister.get(Calendar.YEAR),
                        calendarDateFinalRegister.get(Calendar.MONTH),
                        calendarDateFinalRegister.get(Calendar.DAY_OF_MONTH));

                picker.show();
            }
        });
    }

    private void configureSpinnerPests() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<String> optionsSpinnerPestsText = new ArrayList<>();
                final List<Long> optionsSpinnerPestsId = new ArrayList<>();

                optionsSpinnerPestsText.add("");
                optionsSpinnerPestsId.add(-1l);

                PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PlantPestFormActivity.this);
                pestList = plantPestDatabase.pestDao().findAll();


                for (Pest item : pestList) {
                    optionsSpinnerPestsId.add(item.getId());
                    optionsSpinnerPestsText.add(item.getPopularName()+"/"+item.getScientificName());
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        PlantPestFormActivity.this,
                        android.R.layout.simple_list_item_1,
                        optionsSpinnerPestsText);

                PlantPestFormActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        spinnerPests.setAdapter(adapter);

                        spinnerPests.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                                selectedPestId = optionsSpinnerPestsId.get(position);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parentView) {}
                        });
                    }
                });
            }
        });
    }

    private void configureSpinnerPlants() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final List<String> optionsSpinnerPlantsText = new ArrayList<>();
                final List<Long> optionsSpinnerPlantsId = new ArrayList<>();

                optionsSpinnerPlantsText.add("");
                optionsSpinnerPlantsId.add(-1l);

                PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PlantPestFormActivity.this);
                plantList = plantPestDatabase.plantDao().findAll();


                for (Plant item : plantList) {
                    optionsSpinnerPlantsId.add(item.getId());
                    optionsSpinnerPlantsText.add(item.getPopularName()+"/"+item.getIdentificationPlantGarden());
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        PlantPestFormActivity.this,
                        android.R.layout.simple_list_item_1,
                        optionsSpinnerPlantsText);

                PlantPestFormActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    spinnerPlants.setAdapter(adapter);

                    spinnerPlants.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                            selectedPlantId = optionsSpinnerPlantsId.get(position);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parentView) {}
                    });
                    }
                });
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

    private void clearForm() {

        // TODO: implementar ***************************

    }

    private void saveForm() {
        if (formIsValid()) {
            getValuesToSave();

            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    Long id = null;

                    PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PlantPestFormActivity.this);

                    if (editPlantPest.getId() == null) {
                        id = plantPestDatabase.plantPestDao().insert(editPlantPest);
                    } else {
                        plantPestDatabase.plantPestDao().update(editPlantPest);
                        id = editPlantPest.getId();
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
    }

    private void getValuesToSave() {
        editPlantPest.setPlantId(selectedPlantId);
        editPlantPest.setPestId(selectedPestId);
        editPlantPest.setDateDetectedPest(calendarDatePestDetected.getTime());

        if (editPlantPest != null && radioGroupResolvedQuestion.getCheckedRadioButtonId() != -1) {
            editPlantPest.setProblemResolved(valueRadioButtonIsResolved);
            editPlantPest.setDateProblemResolved(calendarDateFinalRegister.getTime());
            editPlantPest.setDescriptionResolved(editTextFinalDescription.getText().toString());
        }
    }


    private boolean formIsValid() {
        if (spinnerPlants.getSelectedItem().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.plant_pest_form_select_plant)
            ));
            spinnerPlants.requestFocus();
            return false;
        }

        if (spinnerPests.getSelectedItem().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.plant_pest_form_select_pest)
            ));
            spinnerPlants.requestFocus();
            return false;
        }

        if (editTextDateDetectedPest.getText().toString().equals("")) {
            showToastFailSave(getString(
                    R.string.text_form_fields_validation,
                    getString(R.string.plant_pest_form_date_identification)
            ));

            editTextDateDetectedPest.requestFocus();
            return false;
        }

        if (editPlantPest != null && radioGroupResolvedQuestion.getCheckedRadioButtonId() != -1) {
            if (editTextDateFinalRegister.getText().toString().equals("")) {
                showToastFailSave(getString(
                        R.string.text_form_fields_validation,
                        getString(R.string.plant_pest_form_date_final_registration)
                ));

                editTextDateDetectedPest.requestFocus();
                return false;
            }

            if (editTextFinalDescription.getText().toString().equals("")) {
                showToastFailSave(getString(
                        R.string.text_form_fields_validation,
                        getString(R.string.plant_pest_form_description_final_record)
                ));

                editTextDateDetectedPest.requestFocus();
                return false;
            }
        }


        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_form_generic, menu);

        return true;
    }

    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        calendarDatePestDetected.set(year, month, dayOfMonth);

        String textDate = formatDate(this, calendarDatePestDetected.getTime());

        editTextDateDetectedPest.setText(textDate);
    }

    private static String formatDate(Context context, Date date) {
        SimpleDateFormat dateFormat;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            String pattern = DateFormat.getBestDateTimePattern(Locale.getDefault(), "MM/dd/yyyy");
            dateFormat = new SimpleDateFormat(pattern);
        } else {
            dateFormat = (SimpleDateFormat) DateFormat.getMediumDateFormat(context);
        }

        return dateFormat.format(date);
    }

    private void showToastFailSave(String msg) {
        if (!msg.equals(""))
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void clickRadioButtonIsResolved(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonResolvedYes:
                if (checked)
                    valueRadioButtonIsResolved = true;
                break;
            case R.id.radioButtonResolvedNo:
                if (checked)
                    valueRadioButtonIsResolved = false;
                break;
            default:
                valueRadioButtonIsResolved = null;
                break;
        }
    }
}