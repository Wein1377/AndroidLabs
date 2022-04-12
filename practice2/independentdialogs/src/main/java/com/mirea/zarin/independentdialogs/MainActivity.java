package com.mirea.zarin.independentdialogs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void showTimePickerDialog(View view)
    {
        MyTimeDialogFragment timeDialogFragment = new MyTimeDialogFragment();
        new TimePickerDialog(this, timeDialogFragment.timeSetListener, timeDialogFragment.time.get(Calendar.HOUR_OF_DAY), timeDialogFragment.time.get(Calendar.MINUTE), true).show();
    }

    public void showDatePickerDialog(View view)
    {
        MyDateDialogFragment dateDialogFragment = new MyDateDialogFragment();
        new DatePickerDialog(this, dateDialogFragment.dateSetListener, dateDialogFragment.time.get(Calendar.YEAR), dateDialogFragment.time.get(Calendar.MONTH), dateDialogFragment.time.get(Calendar.DAY_OF_MONTH) ).show();
    }

    public void showProgressDialog(View view)
    {
     MyProgressDialogFragment progressDialogFragment = new MyProgressDialogFragment();
     progressDialogFragment.show(getSupportFragmentManager(), "Progress");
    }
}