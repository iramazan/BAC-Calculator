package csc420.baccalculator.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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
