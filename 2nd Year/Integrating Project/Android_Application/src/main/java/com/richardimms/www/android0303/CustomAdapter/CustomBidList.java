package com.richardimms.www.android0303.CustomAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.richardimms.www.android0303.DataModel.Bid;
import com.richardimms.www.android0303.R;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by Richard on 02/04/2015.
 */
public class CustomBidList extends ArrayAdapter<String> {

    private final Context context;
    private final String[] web;
    private final AbstractList<Bid> bidList;

    public CustomBidList(Context context,
                          String[] web, ArrayList<Bid> bid) {
        super(context, R.layout.bid_list_single, web);
        this.context = context;
        this.web = web;
        this.bidList = bid;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater =  (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView= inflater.inflate(R.layout.bid_list_single, null, true);
        /*Assigns each advert in the list view with necessary information,
        advert title, the image of the advert and a name*/
        TextView name = (TextView) rowView.findViewById(R.id.bidName);
        TextView description = (TextView) rowView.findViewById(R.id.bidDescription);

        description.setText(bidList.get(position).getText());
        name.setText(bidList.get(position).getAdvertTitle());
        /*******************************************************************/
        return rowView;
    }
}
