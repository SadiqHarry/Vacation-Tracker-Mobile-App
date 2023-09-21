// Define the package where this class belongs
package com.mobile.VacationTracker.Entities;

// Import necessary Room database annotations
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Define this class as an Entity in the Room database with a table name "Excursion"
@Entity(tableName = "Excursion")
public class Excursion {
    // Define the primary key for the Excursion entity and set it to auto-generate
    @PrimaryKey(autoGenerate = true)
    private int excursionId;
    private int vacationId;
    private String excursionTitle;
    private String excursionStartDate;

    // Constructor to initialize an Excursion object
    public Excursion(int excursionId, int vacationId, String excursionTitle, String excursionStartDate) {
        this.excursionId = excursionId;
        this.vacationId = vacationId;
        this.excursionTitle = excursionTitle;
        this.excursionStartDate = excursionStartDate;
    }

    // Getter method for getting the Excursion ID
    public int getExcursionId() {
        return excursionId;
    }

    // Setter method for setting the Excursion ID
    public void setExcursionId(int excursionId) {
        this.excursionId = excursionId;
    }

    // Getter method for getting the Vacation ID associated with this Excursion
    public int getVacationId() {
        return vacationId;
    }

    // Setter method for setting the Vacation ID associated with this Excursion
    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    // Getter method for getting the title of the Excursion
    public String getExcursionTitle() {
        return excursionTitle;
    }

    // Setter method for setting the title of the Excursion
    public void setExcursionTitle(String excursionTitle) {
        this.excursionTitle = excursionTitle;
    }

    // Getter method for getting the start date of the Excursion
    public String getExcursionStartDate() {
        return excursionStartDate;
    }

    // Setter method for setting the start date of the Excursion
    public void setExcursionStartDate(String excursionStartDate) {
        this.excursionStartDate = excursionStartDate;
    }
}
