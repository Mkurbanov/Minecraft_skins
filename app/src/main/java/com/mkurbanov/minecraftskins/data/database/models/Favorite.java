package com.mkurbanov.minecraftskins.data.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(primaryKeys = {"yo4f2"})
public class Favorite  {
    @NotNull
    @ColumnInfo(name = "yo4f2")
    public String yo4f2;
    @ColumnInfo(name = "yo4t3")
    public String yo4t3;
    @ColumnInfo(name = "yo4i1")
    public String yo4i1;
    @ColumnInfo(name = "yo4_jo2x6")
    public String yo4_jo2x6;
    @ColumnInfo(name = "yo4_1apqg")
    public String yo4_1apqg;
    @ColumnInfo(name = "yo4d4")
    public String yo4d4;
    @ColumnInfo(name = "yo4_6g")
    public String yo4_6g;

    public Favorite(String yo4f2, String yo4t3, String yo4i1, String yo4_jo2x6, String yo4_1apqg, String yo4d4, String yo4_6g) {
        this.yo4f2 = yo4f2;
        this.yo4t3 = yo4t3;
        this.yo4i1 = yo4i1;
        this.yo4_jo2x6 = yo4_jo2x6;
        this.yo4_1apqg = yo4_1apqg;
        this.yo4d4 = yo4d4;
        this.yo4_6g = yo4_6g;
    }
}
