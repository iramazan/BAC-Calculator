package csc420.baccalculator.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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

    @ColumnInfo(name = "drink_name")
    public String drinkName;

    @ColumnInfo(name = "user_id")
    public int userId;
}
