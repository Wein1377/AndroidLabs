package com.mirea.zarin.practice4;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
{
    int counter = 0;
    TextView resultView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultView = findViewById(R.id.textView);
        Thread mainThread = Thread.currentThread();
        resultView.append("\n\n Текущий поток: " + mainThread.getName());
        // Меняем имя и выводим в текстовом поле
        mainThread.setName("MireaThread");
        resultView.append("\n Новое имя потока: " + mainThread.getName());
    }
    public void onClick(View view)
    {
        Runnable runnable = new Runnable()
        {
            public void run()
            {
                int numberThread = counter++;
                Log.i("ThreadProject", "Запущен поток № " + numberThread);
                long endTime = System.currentTimeMillis() + 20 * 1000;

                String classes = ((EditText)findViewById(R.id.editTextClasses)).getText().toString();
                String days = ((EditText)findViewById(R.id.editTextDays)).getText().toString();

                if (!classes.equals("") || !days.equals(""))
                {
                    resultView.setText("Среднее количество пар в день: "+Integer.parseInt(classes)/Integer.parseInt(days));
                }

                while (System.currentTimeMillis() < endTime)
                {
                    synchronized (this)
                    {
                        try
                        {
                            wait(endTime - System.currentTimeMillis());
                        } catch (Exception e)
                        {
                        }
                    }
                }
                Log.i("ThreadProject", "Выполнен поток № " + numberThread);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}