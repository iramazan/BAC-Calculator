package csc420.baccalculator.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {
    public enum Gender { MALE, FEMALE }

    public User(String name, Date birthday, Gender gender) {
        this.uid = 0;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "user_name")
    public String name;

    @ColumnInfo(name = "birthday")
    public Date birthday;

    @ColumnInfo(name = "gender")
    public Gender gender;
}
