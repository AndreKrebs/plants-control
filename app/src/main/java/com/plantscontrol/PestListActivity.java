package com.plantscontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.plantscontrol.adapter.PestListAdapter;
import com.plantscontrol.entity.Pest;

import java.util.ArrayList;
import java.util.List;

public class PestListActivity extends AppCompatActivity {

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

        listViewPests.setAdapter(adapter);

    }


}