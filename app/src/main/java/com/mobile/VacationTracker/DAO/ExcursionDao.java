// Define the package where this DAO interface belongs
package com.mobile.VacationTracker.DAO;

// Import necessary Room database annotations and classes
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

// Import the Excursion entity class
import com.mobile.VacationTracker.Entities.Excursion;

// Import the List class to use in the return type
import java.util.List;

// Declare this interface as a Room Database Access Object (DAO)
@Dao
public interface ExcursionDao {
    // Define an insert method for adding an Excursion to the database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Excursion excursion);

    // Define an update method for updating an existing Excursion in the database
    @Update
    void update(Excursion excursion);

    // Define a delete method for removing an Excursion from the database
    @Delete
    void delete(Excursion excursion);

    // Define a query method to retrieve all Excursions from the database
    @Query("SELECT * FROM EXCURSION ORDER BY EXCURSIONTITLE ASC")
    List<Excursion> getAllExcursions();
}
