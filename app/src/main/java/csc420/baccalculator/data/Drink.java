package csc420.baccalculator.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "uid",
        childColumns = "user_id"
))
public class Drink {
    // TODO: What are drink types?
    public enum DrinkType { BEER, WINE, LIQUOR, MIXED }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "drink_type")
    public DrinkType drinkType;

    @ColumnInfo(name = "drink_path")
    public String drinkPath;

    @ColumnInfo(name = "drink_name")
    public String drinkName;

    @ColumnInfo(name = "user_id")
    public int userId;
}
