package com.richardimms.www.android0303.Fragments.Bids;

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


import com.richardimms.www.android0303.DataModel.Bid;
import com.richardimms.www.android0303.DataModel.Member;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.Methods.ConfirmBids;
import com.richardimms.www.android0303.R;


public class MakeBidFragment extends DialogFragment {

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater linf = LayoutInflater.from(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View inflator = linf.inflate(R.layout.dialog_accept_reject,null);

        builder.setTitle("Accept Bid");
        builder.setMessage("Message:");
        builder.setView(inflator);

        final EditText et1 = (EditText) inflator.findViewById(R.id.bidComment);
        builder.setPositiveButton("Accept", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //Bid ID, Ad ID, Offeree ID, offeere ID, AdType1, Return Message
                Bid acceptedObject = new Bid();
                Bid selectedBid = Globals.selectedBid;
                Member member = Globals.member;
                acceptedObject.setAdvertID(selectedBid.getAdvertID());
                acceptedObject.setOffererID(selectedBid.getOffererID());
                acceptedObject.setOffereeID(member.getId());
                acceptedObject.setAdvertTypeID(selectedBid.getAdvertTypeID());
                acceptedObject.setID(selectedBid.getID());
                acceptedObject.setReturnMessage(et1.getText().toString());
                Log.i("Advert ID Selected BID ", acceptedObject.getAdvertID().toString());
                MyTaskParams params = new MyTaskParams(acceptedObject);
                new AcceptBidTask().execute(params);
            }
        });
        builder.setNegativeButton("Reject", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //Rejected  - set BID OBJECT - RETURN MESSAGE - ID OF BID
                Bid selectedBid = Globals.selectedBid;
                Bid rejectedObject = new Bid();
                String reject = et1.getText().toString();

                rejectedObject.setReturnMessage(et1.getText().toString());
                rejectedObject.setID(selectedBid.getID());
                MyTaskParams params = new MyTaskParams(rejectedObject);
                new RejectBidTask().execute(params);
            }
        });

        return builder.create();
    }

    private class MyTaskParams {
        Bid bid;

        MyTaskParams(Bid bid) {
            this.bid = bid;
        }
    }

    private class AcceptBidTask extends AsyncTask<MyTaskParams, Void, Boolean> {

        @Override
        protected Boolean doInBackground(MyTaskParams... params) {
            ConfirmBids confirm = new ConfirmBids();
            confirm.acceptBids(params[0].bid);
            return true;
        }
    }

    private class RejectBidTask extends AsyncTask<MyTaskParams,Void,Boolean>
    {
        @Override
        protected  Boolean doInBackground(MyTaskParams...paramses)
        {
            ConfirmBids confirmBids = new ConfirmBids();
            confirmBids.rejectBids(paramses[0].bid);
            return true;
        }
    }
}
