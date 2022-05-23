package com.mirea.zarin.mireaproject.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mirea.zarin.mireaproject.practice6.db.Story;
import com.mirea.zarin.mireaproject.practice6.db.StoryDao;
import com.mirea.zarin.mireaproject.practice8.db.Photos;
import com.mirea.zarin.mireaproject.practice8.db.PhotosDao;

@Database(entities = {Story.class, Photos.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract StoryDao storyDao();
    public abstract PhotosDao photosDao();
}
