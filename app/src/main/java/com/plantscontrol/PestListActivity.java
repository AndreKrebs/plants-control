package com.plantscontrol;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.plantscontrol.adapter.PestListAdapter;
import com.plantscontrol.entity.Pest;

import java.util.ArrayList;
import java.util.List;

public class PestListActivity extends AppCompatActivity {

    static final int ACTIVITY_FORM_REQUEST = 1;

    private ListView listViewPests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pest_list);

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

    private void setItensList() {
        String popularNames[] = getResources().getStringArray(R.array.popular_name);
        String scientificNames[] = getResources().getStringArray(R.array.scientific_name);
        String pestTypes[] = getResources().getStringArray(R.array.pest_type);
        String weathers[] = getResources().getStringArray(R.array.weather);

        List<Pest> pests = new ArrayList<>();

        for (int cont = 0; cont < popularNames.length; cont++) {
            pests.add(new Pest(
                    popularNames[cont],
                    scientificNames[cont],
                    pestTypes[cont],
                    weathers[cont])
            );
        }

        PestListAdapter adapter = new PestListAdapter(pests, this);

        ViewGroup footer = (ViewGroup) getLayoutInflater().inflate(R.layout.custom_pest_footer_list, listViewPests, false);

        listViewPests.addFooterView(footer);
        listViewPests.setAdapter(adapter);

    }

    public void openFormNewPest(View view) {
        Intent intent = new Intent(PestListActivity.this, PestFormActivity.class);
        startActivityForResult(intent, ACTIVITY_FORM_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTIVITY_FORM_REQUEST) {
            if (resultCode == RESULT_OK) {
                Pest pest = (Pest) data.getSerializableExtra("newPest");
                Toast.makeText(this, pest.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
}