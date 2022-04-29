package com.mirea.zarin.mireaproject.practice6;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import androidx.fragment.app.Fragment;

import com.mirea.zarin.mireaproject.MainActivity;
import com.mirea.zarin.mireaproject.R;
import com.mirea.zarin.mireaproject.practice6.db.Story;
import com.mirea.zarin.mireaproject.practice6.db.StoryDao;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stories extends Fragment
{
    StoryDao storyDao;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        storyDao = MainActivity.db.storyDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_stories, container, false);
        return view;
    }

    private void addToDb(Story story)
    {
        storyDao.insert(story);
    }

    private ArrayList<Map.Entry<String, String>> getAll()
    {
        ArrayList<Map.Entry<String, String>> arrayList = new ArrayList<>();
        List<Story> stories = storyDao.getAll();

        for (Story story : stories)
        {
            Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>(story.subject, story.text);
            arrayList.add(entry);
        }

        return arrayList;
    }

    private SimpleAdapter getAdapter(ArrayList<Map.Entry<String, String>> arrayList)
    {

    }
}
