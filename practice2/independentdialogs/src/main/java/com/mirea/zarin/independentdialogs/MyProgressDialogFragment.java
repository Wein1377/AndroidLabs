package com.mirea.zarin.independentdialogs;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class MyProgressDialogFragment extends DialogFragment
{
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Dialog progressDialog = new ProgressDialog(getContext());
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.holo_green_light);
        return progressDialog;
    }
}
