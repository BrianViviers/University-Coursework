package com.richardimms.www.android0303.Fragments.Bids;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.richardimms.www.android0303.DataModel.Advert;
import com.richardimms.www.android0303.DataModel.Bid;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;

import com.richardimms.www.android0303.Methods.RespondToAdvert;
import com.richardimms.www.android0303.R;

/**
 * Created by philip on 10/04/2015.
 */
public class RespondToAdvertDialog extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {

    LayoutInflater linf = LayoutInflater.from(getActivity());
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    final View inflator = linf.inflate(R.layout.dialog_make_bid,null);

    builder.setTitle("Make a bid");
    builder.setMessage("Enter a message:");
    builder.setView(inflator);

    final EditText et1 = (EditText) inflator.findViewById(R.id.makeBidComment);
    builder.setPositiveButton("Make Bid", new Dialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which)
        {
            //Bid ID, Ad ID, Offeree ID, offeere ID, AdType1, Return Message

            Globals globals = new Globals();
            Member member = globals.member;
            Advert advert = globals.selectedAdvert;
            Bid bid = new Bid();

            bid.setOffereeID(advert.getMember_id());

            bid.setOffererID(member.getId());

            bid.setAdvertID(advert.getId());
                /*Make advert type ID the actual advert type*/
            bid.setAdvertTypeID(1);
            /********************************************/
            bid.setText(et1.getText().toString());
            MyTaskParams params = new MyTaskParams(bid,getActivity());

            new AcceptBidTask().execute(params);
        }
    });
    builder.setNegativeButton("Cancel", new Dialog.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    });

    return builder.create();
}

    private class MyTaskParams {
        Bid bid;
        Activity activity;
        MyTaskParams(Bid bid, Activity activity) {
            this.bid = bid;
            this.activity = activity;
        }

    }

    private class AcceptBidTask extends AsyncTask<MyTaskParams, Void, Boolean> {
        Activity activity;

        @Override
        protected Boolean doInBackground(MyTaskParams... params) {
            Boolean result = false;
            Bid bid = params[0].bid;
            activity = params[0].activity;
            RespondToAdvert respondToAdvert = new RespondToAdvert();
            result = respondToAdvert.respondToAdvert(bid);
            return result;
        }
        protected void onPostExecute(Boolean result) {
            Log.i("On Post Ex", result.toString());
            if (!result) {
                Toast.makeText(activity, "There was an error making an offer", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(activity, "Success, offer has been made", Toast.LENGTH_LONG).show();
            }
        }
    }
}
