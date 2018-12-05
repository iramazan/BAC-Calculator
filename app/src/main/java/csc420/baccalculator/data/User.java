package csc420.baccalculator.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {
    @Ignore
    private final double femaleConst = 0.55;
    @Ignore
    private final double maleConst = 0.68;

    public enum Gender { MALE, FEMALE }

    public User(String name, Date birthday, Gender gender, int weight) {
        this.uid = 0;
        this.name = name;
        this.birthday = birthday;
        this.gender = gender;
        this.weight = weight;
    }

    @PrimaryKey(autoGenerate = true)
    public long uid;

    @ColumnInfo(name = "user_name")
    public String name;

    @ColumnInfo(name = "birthday")
    public Date birthday;

    @ColumnInfo(name = "weight")
    public int weight;

    @ColumnInfo(name = "gender")
    public Gender gender;

    public double getConstant() {
        if (gender.equals(Gender.MALE)) {
            return maleConst;
        } else {
            return femaleConst;
        }
    }
}
