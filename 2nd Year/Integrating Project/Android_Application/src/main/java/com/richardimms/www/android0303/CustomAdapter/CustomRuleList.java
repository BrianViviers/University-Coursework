package com.richardimms.www.android0303.CustomAdapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.TextView;


import com.richardimms.www.android0303.DataModel.Rules;
import com.richardimms.www.android0303.R;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by Richard on 28/03/2015.
 */
public class CustomRuleList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;
    private final AbstractList<Rules> ruleArrayList;

    public CustomRuleList(Activity context,
                      String[] web, ArrayList<Rules> rule) {
        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.ruleArrayList = rule;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View rowView= inflater.inflate(R.layout.rule_list_single, null, true);
        /*Assigns each advert in the list view with necessary information,
        advert title, the image of the advert and a description*/
        TextView description = (TextView) rowView.findViewById(R.id.ruleName);

        description.setText(ruleArrayList.get(position).getRule());
        /*******************************************************************/
        return rowView;
    }
}
