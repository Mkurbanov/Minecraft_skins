package com.mkurbanov.minecraftskins.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.mkurbanov.minecraftskins.data.database.dao.FavDao;
import com.mkurbanov.minecraftskins.data.database.models.Favorite;

@Database(entities = {Favorite.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FavDao favDao();
}
