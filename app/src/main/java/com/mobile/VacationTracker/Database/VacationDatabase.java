// Define the package where this class belongs
package com.mobile.VacationTracker.Database;

// Import necessary Android classes and libraries
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mobile.VacationTracker.DAO.ExcursionDao;
import com.mobile.VacationTracker.DAO.VacationDao;
import com.mobile.VacationTracker.Entities.Excursion;
import com.mobile.VacationTracker.Entities.Vacation;

// Define a Room Database class called VacationDatabase
@Database(entities = {Vacation.class, Excursion.class}, version = 1, exportSchema = false)
public abstract class VacationDatabase extends RoomDatabase {
    // Declare abstract methods to access DAOs
    public abstract VacationDao VacationDao();
    public abstract ExcursionDao ExcursionDao();

    // Declare a volatile INSTANCE variable to ensure thread safety
    private static volatile VacationDatabase INSTANCE;

    // Define a method to get a reference to the database instance
    static VacationDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabase.class) {
                if (INSTANCE == null) {
                    // Create a new instance of the database if it doesn't exist
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), VacationDatabase.class, "VacationDatabase.db")
                            .fallbackToDestructiveMigration() // Handle schema changes by dropping and recreating the database
                            .allowMainThreadQueries() // Allow database queries on the main thread (not recommended for production)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
