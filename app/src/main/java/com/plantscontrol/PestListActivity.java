package com.plantscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

    private ListView listViewPests;
    private ViewGroup footer;
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("PEST-LIST", (Serializable) pestList);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    private void setItensList() {
        adapterList = new PestListAdapter(pestList, this);

        if (footer == null) {
            footer = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_pest_footer_list, listViewPests, false);
            listViewPests.addFooterView(footer);
        }

        listViewPests.setAdapter(adapterList);
    }

    public void openFormNewPest(View view) {
        Intent intent = new Intent(PestListActivity.this, PestFormActivity.class);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }

    public void openActivityAuthorship(View view) {
        Intent intent = new Intent(PestListActivity.this, AuthorshipActivity.class);
        intent.putExtra("LIST_PEST", (Serializable) pestList);
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
                    showToastLong("Cadastro realizado com sucesso!");
                }


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
}