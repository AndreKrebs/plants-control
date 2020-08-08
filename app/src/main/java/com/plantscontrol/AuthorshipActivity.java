package com.plantscontrol;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.renderscript.Allocation;
import android.view.MenuItem;
import android.widget.TextView;

import com.plantscontrol.entity.Pest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AuthorshipActivity extends AppCompatActivity {

    private List<Pest> pestList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorship);
        pestList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            pestList = (List<Pest>) bundle.getSerializable(PestListActivity.PEST_LIST);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onBackPressed() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(PestListActivity.PEST_LIST, (Serializable) pestList);
        setResult(RESULT_CANCELED, returnIntent);
        // finish() não é usado e 'super' é adicionado no final para não alterar o valor de setResult e perder os dados
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home :
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}