package com.mkurbanov.minecraftskins.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.mkurbanov.minecraftskins.data.database.models.Favorite;

import java.util.List;

@Dao
public interface FavDao {
    @Query("SELECT * FROM Favorite")
    List<Favorite> getAll();

    @Query("SELECT * FROM Favorite WHERE yo4f2 = :yo4f2")
    Favorite findByFileName(String yo4f2);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Favorite... favorites);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Favorite favorite);

    @Delete
    void delete(Favorite favorite);

    @Query("DELETE FROM Favorite WHERE yo4f2 = :yo4f2")
    void deleteByFileName(String yo4f2);

    @Query("DELETE FROM Favorite")
    void deleteAll();
}
