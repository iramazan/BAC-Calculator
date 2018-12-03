package csc420.baccalculator.data;

import android.arch.persistence.room.*;

import java.util.List;

@Entity(foreignKeys = @ForeignKey(
        entity = User.class,
        parentColumns = "uid",
        childColumns = "user_id"
))
public class Drink {
    // TODO: What are drink types?
    public enum DrinkType { BEER, WINE, LIQUOR, MIXED }

    public Drink(DrinkType drinkType, String drinkPath, String drinkName) {
        this.drinkType = drinkType;
        this.drinkPath = drinkPath;
        this.drinkName = drinkName;
    }

    public Drink(List<Ingredient> ingredients, DrinkType drinkType, String drinkPath, String drinkName) {
        this.ingredients = ingredients;
        this.userId = uid;
        this.drinkType = drinkType;
        this.drinkPath = drinkPath;
        this.drinkName = drinkName;
    }

    @PrimaryKey(autoGenerate = true)
    public long uid;

    @ColumnInfo(name = "drink_type")
    public DrinkType drinkType;

    @ColumnInfo(name = "drink_path")
    public String drinkPath;

    @ColumnInfo(name = "drink_name")
    public String drinkName;

    @ColumnInfo(name = "user_id")
    public long userId;

    @Ignore
    public List<Ingredient> ingredients;
}
