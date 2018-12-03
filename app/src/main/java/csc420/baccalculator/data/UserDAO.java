package csc420.baccalculator.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM ingredient where drink_id IS :drinkId")
    List<Ingredient> getIngredientsForDrink(long drinkId);

    @Query("SELECT * FROM drink WHERE user_id IS :userId")
    List<Drink> getDrinksForUser(long userId);

    @Query("SELECT * FROM user WHERE uid is :userId")
    User getUserById(long userId);

    @Insert
    long insert(User user);

    @Insert
    long insert(Drink drink);

    @Insert
    long insert(Ingredient ingredient);

    @Delete
    void delete(User user);
}
