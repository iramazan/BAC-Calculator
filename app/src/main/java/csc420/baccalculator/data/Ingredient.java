package csc420.baccalculator.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = Drink.class,
        parentColumns = "uid",
        childColumns = "drink_id"))
public class Ingredient {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "ingredient_name")
    public String name;

    @ColumnInfo(name = "drink_id")
    public int drinkId;
}
