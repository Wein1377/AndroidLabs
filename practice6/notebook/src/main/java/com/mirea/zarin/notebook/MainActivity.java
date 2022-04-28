package com.mirea.zarin.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
    private EditText editText;
    private EditText editName;

    private TextView noteName;
    private TextView noteText;

    private SharedPreferences preferences;

    private final String SAVED_NAME = "saved_name";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editName);
        editName = findViewById(R.id.editText);

        noteName = findViewById(R.id.noteName);
        noteText = findViewById(R.id.noteText);

        preferences = getPreferences(MODE_PRIVATE);

        load();
    }

    @Override
    protected void onStop()
    {
        save();

        super.onStop();
    }

    public String getTextFromFile(String fileName)
    {
        FileInputStream fin = null;
        try
        {
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);

            return new String(bytes);
        }
        catch (IOException ex)
        {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        finally
        {
            try
            {
                if (fin != null)
                {
                    fin.close();
                }
            }
            catch (IOException ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        return null;
    }

    public void save()
    {
        String name = editText.getText().toString() + ".txt";
        String text = editName.getText().toString();

        FileOutputStream outputStream;
        try
        {
            outputStream = openFileOutput(name, Context.MODE_PRIVATE);
            outputStream.write(text.getBytes());
            outputStream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SAVED_NAME, name);

        editor.apply();
    }

    public void load()
    {
        String name = preferences.getString(SAVED_NAME, "ERROR");
        String text = "ERROR";

        if (name.endsWith(".txt"))
        {
            text = getTextFromFile(name);
        }

        noteName.setText(name);
        noteText.setText(text);
    }
}