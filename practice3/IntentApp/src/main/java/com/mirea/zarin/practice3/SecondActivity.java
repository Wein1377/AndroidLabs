package com.mirea.zarin.practice3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity
{
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Bundle argument = getIntent().getExtras();
        String date = argument.get("date").toString();

        textView = (TextView) findViewById(R.id.textView2);
        textView.setText(date);
    }
}