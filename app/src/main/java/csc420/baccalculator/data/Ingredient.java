package csc420.baccalculator.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
