package com.plantscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.plantscontrol.adapter.PestListAdapter;
import com.plantscontrol.entity.Pest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PestListActivity extends AppCompatActivity {

    private static final int ACTIVITY_FORM_REQUEST = 1;
    private static final int ACTIVITY_AUTHORSHIP_REQUEST = 2;
    public static final String PEST_LIST = "LIST-PEST";
    public static final String ITEM_PEST = "ITEM-PEST";

    private ListView listViewPests;
    private PestListAdapter adapterList;

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
                Toast.makeText(PestListActivity.this, pest.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        setItensList();

        registerForContextMenu(listViewPests);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra(PEST_LIST, (Serializable) pestList);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void setItensList() {
        adapterList = new PestListAdapter(pestList, this);

        listViewPests.setAdapter(adapterList);
    }

    public void openFormNewPest() {
        Intent intent = new Intent(PestListActivity.this, PestFormActivity.class);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }


    private void openFormEditPest(Pest pest) {
        Intent intent = new Intent(PestListActivity.this, PestFormActivity.class);
//        intent.putExtra(PEST_LIST, )
        intent.putExtra(ITEM_PEST, (Serializable) pest);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);

    }

    public void openAuthorship() {
        Intent intent = new Intent(PestListActivity.this, AuthorshipActivity.class);
        intent.putExtra(PEST_LIST, (Serializable) pestList);
        startActivityForResult(intent, ACTIVITY_AUTHORSHIP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_FORM_REQUEST) {
            if (resultCode == RESULT_OK) {
                Pest pest = (Pest) data.getSerializableExtra(PestFormActivity.NEW_PEST);

                if (pest != null) {
                    pestList.add(pest);
                    adapterList.notifyDataSetChanged();
                    showToastLong(getString(R.string.pests_list_activityresult_successful_registration));
                } else {
                    pest = (Pest) data.getSerializableExtra(PestFormActivity.EDIT_PEST);
                    pestList.set(pest.getId().intValue(), pest);
                    adapterList.notifyDataSetChanged();
                    showToastLong(getString(R.string.pests_list_activityresult_successful_update));
                }

                if(pest == null)
                    showToastLong(getString(R.string.pests_list_activityresult_error_update_list));

            }
        } else if(requestCode == ACTIVITY_AUTHORSHIP_REQUEST) {
            if (resultCode == RESULT_CANCELED) {
                pestList = (List<Pest>) data.getSerializableExtra(PEST_LIST);
                // necessário recriar adapterList pois parece que no retorno ele perde o adapter e o notifyDataSetChanged() não funciona mais
                setItensList();
            }

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

        getMenuInflater().inflate(R.menu.menu_pests_list_context, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {

        AdapterView.AdapterContextMenuInfo info;

        info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch(item.getItemId()) {
            case R.id.menuItemEdit:
                editItemPest(info.position);
                return true;
            case R.id.menuItemDelete:
                deleteItemPest(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }

    }

    private void editItemPest(int position) {
        Pest pest = pestList.get(position);
        pest.setId((long) position);
        openFormEditPest(pest);
    }

    private void deleteItemPest(int position) {
        pestList.remove(position);
        adapterList.notifyDataSetChanged();
    }
}