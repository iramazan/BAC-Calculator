package csc420.baccalculator.data;

import android.arch.persistence.room.Room;
import android.content.Context;

public class DatabaseManager {

    private static AppDatabase INSTANCE;

    public static AppDatabase getIntance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "users").build();
        }
        return INSTANCE;
    }
}
