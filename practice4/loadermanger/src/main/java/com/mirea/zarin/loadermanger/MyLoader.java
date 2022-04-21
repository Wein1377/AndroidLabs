package com.mirea.zarin.loadermanger;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.loader.content.AsyncTaskLoader;

import java.util.concurrent.ThreadLocalRandom;

public class MyLoader extends AsyncTaskLoader<String>
{
    private String editText;
    public static final String ARG_WORD = "word";
    public StringBuffer word;
    public String result;
    public int count;
    public int max;

    public MyLoader(@NonNull Context context, Bundle args)
    {
        super(context);
        if (args != null)
            editText = args.getString(ARG_WORD);
    }
    @Override
    protected void onStartLoading()
    {
        super.onStartLoading();
        forceLoad();
    }
    @Override
    public String loadInBackground()
    {
        result = "";

        word = new StringBuffer(editText);
        max = word.length();

        for (int i = 0; i < max; i++) {
            count = ThreadLocalRandom.current().nextInt(word.length());
            result += word.charAt(count);
            word.deleteCharAt(count);
        }

        return result;
    }
}
