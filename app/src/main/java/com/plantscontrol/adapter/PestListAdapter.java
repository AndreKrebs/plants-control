package com.plantscontrol.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.plantscontrol.R;
import com.plantscontrol.entity.Pest;

import java.util.List;

public class PestListAdapter extends BaseAdapter {

    private final List<Pest> pests;
    private final Activity act;

    public PestListAdapter(List<Pest> pests, Activity act) {
        this.pests = pests;
        this.act = act;
    }

    @Override
    public int getCount() {
        return pests.size();
    }

    @Override
    public Object getItem(int position) {
        return pests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /*
    Implementação do método baseada no artigo https://www.alura.com.br/artigos/personalizando-uma-listview-no-android
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = act.getLayoutInflater().inflate(R.layout.custom_pest_list, parent, false);
        Pest pest = pests.get(position);

        TextView popularName = (TextView) view.findViewById(R.id.textViewCustomPopularName);
        TextView scientificName = (TextView) view.findViewById(R.id.textViewCustomScientificName);
        TextView type = (TextView) view.findViewById(R.id.textViewCustomType);
        TextView weather = (TextView) view.findViewById(R.id.textViewCustomWeather);

        popularName.setText(act.getText( R.string.popular_name_pest) + ": " + pest.getPopularName());
        scientificName.setText(act.getText( R.string.scientific_name_pest) + ": " + pest.getScientificName());
        type.setText(act.getText( R.string.pest_type) + ": " + pest.getType());
        weather.setText(act.getText( R.string.pest_ideal_weather_propagation) + ": " + pest.getWeather());

        return view;
    }
}
