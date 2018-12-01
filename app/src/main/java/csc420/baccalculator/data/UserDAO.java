package csc420.baccalculator.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM drink WHERE user_id IS :userId")
    List<Drink> getDriksForUser(int userId);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);
}
