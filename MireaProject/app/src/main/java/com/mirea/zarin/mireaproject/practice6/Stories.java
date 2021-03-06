package com.mirea.zarin.mireaproject.practice6;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mirea.zarin.mireaproject.MainActivity;
import com.mirea.zarin.mireaproject.R;
import com.mirea.zarin.mireaproject.practice6.db.Story;
import com.mirea.zarin.mireaproject.practice6.db.StoryDao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stories extends Fragment
{
        private static final int REQUEST_CODE = 1337;
        private FloatingActionButton addButton;

        private static RecyclerView recyclerView;
        private static StoryAdapter adapter;

        private static ArrayList<StoryItem> states = new ArrayList<>();

        public static StoryDao storyDao;

        private static Context context;

        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            context = requireContext();

            storyDao = MainActivity.db.storyDao();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            View view = inflater.inflate(R.layout.fragment_stories, container, false);

            recyclerView = view.findViewById(R.id.stories_view);

            this.addButton = view.findViewById(R.id.add_story_button);
            this.addButton.setOnClickListener(this::onButtonClick);

            loadData();

            return view;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
        {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK)
            {
                loadData();
            }
        }

        private void onButtonClick(View view)
        {
            StoryDialog dialogFragment = new StoryDialog();
            dialogFragment.show(requireActivity().getSupportFragmentManager(), "mirea");
        }

        private static void clearView()
        {
            states.clear();
            adapter = new StoryAdapter(context, states);
            recyclerView.setAdapter(adapter);
        }

        public static void loadData()
        {
            clearView();

            List<Story> stories = storyDao.getAll();
            Collections.reverse(stories);

            for (Story story : stories)
            {
                states.add(new StoryItem(story.subject, story.text));
            }

            adapter = new StoryAdapter(context, states);
            recyclerView.setAdapter(adapter);
        }
}
