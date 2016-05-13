package com.richardimms.www.android0303.CustomAdapter;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.richardimms.www.android0303.DataModel.Advert;
import com.richardimms.www.android0303.R;

import java.util.AbstractList;
import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String>{
    private final Activity context;
    private final String[] web;
    private final AbstractList<Advert> adverts;

    public CustomList(Activity context,
                      String[] web, ArrayList<Advert> advert) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.adverts = advert;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.list_single, null, true);
        /*Assigns each advert in the list view with necessary information,
        advert title, the image of the advert and a description*/
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView description = (TextView) rowView.findViewById(R.id.description);
        description.setText(adverts.get(position).getDescription());
        txtTitle.setText(adverts.get(position).getTitle());
        imageView.setImageBitmap(adverts.get(position).getAdvertImage());
        /*******************************************************************/
        return rowView;
    }
}