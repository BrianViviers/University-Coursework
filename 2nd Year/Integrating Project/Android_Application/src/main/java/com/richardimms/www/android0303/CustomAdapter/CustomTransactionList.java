package com.richardimms.www.android0303.CustomAdapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.richardimms.www.android0303.DataModel.Transaction;
import com.richardimms.www.android0303.R;

import java.util.AbstractList;
import java.util.ArrayList;

/**
 * Created by Richard on 07/04/2015.
 */
public class CustomTransactionList extends ArrayAdapter<String> {
    private final Context context;
    private final String[] web;
    private final AbstractList<Transaction> transactions;

    public CustomTransactionList(Context context,
                         String[] web, ArrayList<Transaction> transactions) {
        super(context, R.layout.bid_list_single, web);
        this.context = context;
        this.web = web;
        this.transactions = transactions;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView= inflater.inflate(R.layout.transaction_list_single, null, true);
        /*Assigns each advert in the list view with necessary information,
        advert title, the image of the advert and a name*/
        TextView name = (TextView) rowView.findViewById(R.id.transactionName);
        TextView description = (TextView) rowView.findViewById(R.id.transactionText);

        description.setText(transactions.get(position).getReviewText());
        name.setText(transactions.get(position).getTitle());

        /*******************************************************************/
        return rowView;
    }
}
