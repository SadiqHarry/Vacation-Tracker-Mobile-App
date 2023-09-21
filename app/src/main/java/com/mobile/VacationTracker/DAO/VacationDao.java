// Define the package where this DAO interface belongs
package com.mobile.VacationTracker.DAO;

// Import necessary Room database annotations and classes
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

// Import the Vacation entity class
import com.mobile.VacationTracker.Entities.Vacation;

// Import the List class to use in the return type
import java.util.List;

// Declare this interface as a Room Database Access Object (DAO)
@Dao
public interface VacationDao {
    // Define an insert method for adding a Vacation to the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    // Define an update method for updating an existing Vacation in the database
    @Update
    void update(Vacation vacation);

    // Define a delete method for removing a Vacation from the database
    @Delete
    void delete(Vacation vacation);

    // Define a query method to retrieve all Vacations from the database and order them by Vacation ID
    @Query("SELECT * FROM VACATION ORDER BY VACATIONID ASC")
    List<Vacation> getAllVacations();

    // Define a query method to retrieve all Vacations by Vacation ID and order them by Vacation ID
    @Query("SELECT * FROM VACATION WHERE VACATIONID = VACATIONID ORDER BY VACATIONID ASC")
    List<Vacation> getAllVacationsById();
}
