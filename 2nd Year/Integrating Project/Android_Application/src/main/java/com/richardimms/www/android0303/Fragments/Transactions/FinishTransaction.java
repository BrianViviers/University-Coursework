package com.richardimms.www.android0303.Fragments.Transactions;

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
import android.widget.RatingBar;
import android.widget.Toast;


import com.richardimms.www.android0303.DataModel.Review;
import com.richardimms.www.android0303.Globals.Globals;
import com.richardimms.www.android0303.R;

/**
 * Created by Richard on 12/04/2015.
 */
public class FinishTransaction extends DialogFragment {
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater linf = LayoutInflater.from(getActivity());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final View inflator = linf.inflate(R.layout.dialog_transaction,null);

        builder.setTitle("Finalise Transaction");
        builder.setMessage("Enter a review:");
        builder.setView(inflator);

        final EditText et1 = (EditText) inflator.findViewById(R.id.makeTransactionComment);
        final RatingBar rb1 = (RatingBar) inflator.findViewById(R.id.transactionReviewValue);
        rb1.setRating(1);
        builder.setPositiveButton("Accept", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Review review = new Review();
                review.setTransactionID(Globals.selectedTransaction.getId());
                review.setReviewValue(rb1.getNumStars());
                review.setReviewText(et1.getText().toString());
                MyTaskParams myTaskParams = new MyTaskParams(review, getActivity());
                new  AcceptTransaction().execute(myTaskParams);
            }
        });
        builder.setNegativeButton("Reject", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        return builder.create();
    }

    private class MyTaskParams {
        Review review;
        Activity activity;
        MyTaskParams(Review review, Activity activity) {
            this.review = review;
            this.activity = activity;
        }
    }

    private class AcceptTransaction extends AsyncTask<MyTaskParams, Void, Boolean> {
        Activity activity;

        @Override
        protected Boolean doInBackground(MyTaskParams... params) {
            ConfirmTransaction confirm = new ConfirmTransaction();
            String confirmation = confirm.confirmTransaction(params[0].review);
            activity = params[0].activity;

            boolean result = false;

            if(confirmation.startsWith("Transaction updated"))
            {
                result = true;
            }
            else
            {
                result = false;
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {
            Log.i("On Post Ex", result.toString());
            if(result)
            {
                Toast.makeText(activity, "Successfully completed transaction!", Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(activity, "Transaction failed!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
