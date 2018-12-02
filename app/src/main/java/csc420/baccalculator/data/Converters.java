package csc420.baccalculator.data;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class Converters {
    @TypeConverter
    public static Date fromTimeStamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public User.Gender fromGenderString(String value) {
        for (User.Gender gender : User.Gender.values()) {
            if (gender.name().equals(value)) {
                return gender;
            }
        }
        return null;
    }

    @TypeConverter
    public String toGenderString(User.Gender gender) {
        return gender == null ? null : gender.name();
    }

    @TypeConverter
    public Drink.DrinkType fromDrinkTypeString(String value) {
        for (Drink.DrinkType drinkType : Drink.DrinkType.values()) {
            if (drinkType.name().equals(value)) {
                return drinkType;
            }
        }
        return null;
    }

    @TypeConverter
    public String toDrinkTypeString(Drink.DrinkType drinkType) {
        return drinkType == null ? null : drinkType.name();
    }


}
