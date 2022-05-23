package com.mirea.zarin.mireaproject.practice8.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Photos
{
    public Photos(byte[] photo)
    {
        this.photo = photo;
    }

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    public byte[] photo;
}
