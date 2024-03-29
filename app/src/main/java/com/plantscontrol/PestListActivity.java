package com.plantscontrol;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.plantscontrol.adapter.PestListAdapter;
import com.plantscontrol.dao.PlantPestDatabase;
import com.plantscontrol.entity.Pest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PestListActivity extends AppCompatActivity {

    private static final int ACTIVITY_FORM_REQUEST = 1;
    private static final int ACTIVITY_AUTHORSHIP_REQUEST = 2;
    public static final String PEST_LIST = "LIST-PEST";
    public static final String ITEM_PEST = "ITEM-PEST";

    public static final String FIELD_POPULAR_NAME = "POPULAR_NAME";
    public static final String FIELD_SCIENTIFIC_NAME = "SCIENTIFIC_NAME";

    private static final String FILE_PREFERENCES = "com.plantscontrol.sharedpreferences.PREF_LIST_PESTS";
    private static final String ORDER_LIST = "ORDER_LIST";

    private ListView listViewPests;
    private ViewGroup orderListOptions;
    private PestListAdapter adapterList;
    private String preferenceValueOrderList = "";

    private List<Pest> pestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_list);

        if (pestList == null)
            pestList = new ArrayList<>();

        listViewPests = findViewById(R.id.listViewPests);

        listViewPests.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Pest pest = (Pest) listViewPests.getItemAtPosition(position);
                Toast.makeText(
                        PestListActivity.this,
                        pest.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        findAllPests();

        registerForContextMenu(listViewPests);
    }

    private void findAllPests() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PestListActivity.this);
                pestList = plantPestDatabase.pestDao().findAll();

                loadPreferences();
                orderLystPestsByField(preferenceValueOrderList);

                PestListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setAdapterList();
                        orderLystPestsByField(preferenceValueOrderList);
                        setRadioButonListOrder();
                    }
                });
            }
        });
    }

    private void deleteSelectedPest(final Pest pest) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                PlantPestDatabase plantPestDatabase = PlantPestDatabase.getDatabase(PestListActivity.this);
                plantPestDatabase.pestDao().delete(pest);
                pestList = plantPestDatabase.pestDao().findAll();

                PestListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    setAdapterList();
                    orderLystPestsByField(preferenceValueOrderList);
                    }
                });
            }
        });
    }

    private void loadPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);

        preferenceValueOrderList = sharedPreferences.getString(ORDER_LIST, FIELD_POPULAR_NAME);
    }

    public void saveOrderListPreference(String option) {
        SharedPreferences sharedPreferences = getSharedPreferences(FILE_PREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ORDER_LIST, option);
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(
                PEST_LIST,
                (Serializable) pestList);

        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void setAdapterList() {
        adapterList = new PestListAdapter(pestList, this);

        if (orderListOptions == null) {
            orderListOptions = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_pest_order_list, listViewPests, false);
            listViewPests.addHeaderView(orderListOptions);
        }

        listViewPests.setAdapter(adapterList);
    }

    public void openFormNewPest() {
        Intent intent = new Intent(PestListActivity.this, PestFormActivity.class);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }


    private void openFormEditPest(Pest pest) {
        Intent intent = new Intent(PestListActivity.this, PestFormActivity.class);
        intent.putExtra(ITEM_PEST, pest);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);

    }

    public void openAuthorship() {
        Intent intent = new Intent(PestListActivity.this, AuthorshipActivity.class);
        startActivityForResult(intent, ACTIVITY_AUTHORSHIP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_FORM_REQUEST) {
            if (resultCode == RESULT_OK) {
                    findAllPests();
                    showToastLong(getString(R.string.pests_list_activityresult_successful_registration));
            }
        } else if(requestCode == ACTIVITY_AUTHORSHIP_REQUEST) {
            if (resultCode == RESULT_CANCELED)
                findAllPests();
        }
    }

    private void showToastLong(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_pests, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemAdd:
                openFormNewPest();
                return true;
            case R.id.menuItemAbout:
                openAuthorship();
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
                editItemPest(info.position - 1);
                return true;
            case R.id.menuItemDelete:
                deleteItemPest(info.position - 1);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void editItemPest(int position) {
        Pest pest = pestList.get(position);
        openFormEditPest(pest);
    }

    private void deleteItemPest(final int position) {

        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.text_delete))
                .setMessage(getString(R.string.text_confirm_delete, pestList.get(position).getPopularName()))
                .setPositiveButton(getString(R.string.text_yes), null)
                .setNegativeButton(getString(R.string.text_no), null)
                .show();

        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedPest(pestList.get(position));
                dialog.dismiss();
            }
        });
    }

    private void orderLystPestsByField(final String field) {
        Collections.sort(pestList, new Comparator<Pest>() {
            @Override
            public int compare(Pest p1, Pest p2) {
                if (FIELD_POPULAR_NAME.equals(field))
                    return p1.getPopularName().compareTo(p2.getPopularName());

                if (FIELD_SCIENTIFIC_NAME.equals(field))
                    return p1.getScientificName().compareTo(p2.getScientificName());

                return 0;
            }
        });
    }

    public void clickRadioButtonOrderListByField(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.radioButtonOrderListPopularName:
                if (checked) {
                    preferenceValueOrderList = FIELD_POPULAR_NAME;
                    orderLystPestsByField(FIELD_POPULAR_NAME);
                    saveOrderListPreference(FIELD_POPULAR_NAME);
                }
                break;
            case R.id.radioButtonOrderListScientificName:
                if (checked) {
                    preferenceValueOrderList = FIELD_SCIENTIFIC_NAME;
                    orderLystPestsByField(FIELD_SCIENTIFIC_NAME);
                    saveOrderListPreference(FIELD_SCIENTIFIC_NAME);
                }
                break;
        }
        adapterListRefresh();
    }

    private void setRadioButonListOrder() {
        switch (preferenceValueOrderList) {
            case FIELD_POPULAR_NAME:
                ((RadioButton) findViewById(R.id.radioButtonOrderListPopularName)).setChecked(true);
                break;
            case FIELD_SCIENTIFIC_NAME:
                ((RadioButton) findViewById(R.id.radioButtonOrderListScientificName)).setChecked(true);
                break;
        }
    }

    private void adapterListRefresh() {
        listViewPests.invalidateViews();
        adapterList.notifyDataSetChanged();
    }
}