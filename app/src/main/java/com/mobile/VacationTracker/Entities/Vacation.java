// Define the package where this class belongs
package com.mobile.VacationTracker.Entities;

// Import necessary Room database annotations
import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Define this class as an Entity in the Room database with a table name "Vacation"
@Entity(tableName = "Vacation")
public class Vacation {
    // Define the primary key for the Vacation entity and set it to auto-generate
    @PrimaryKey(autoGenerate = true)
    private int vacationId;
    private String vacationTitle;
    private String vacationHotel;
    private String vacationStartDate;
    private String vacationEndDate;

    // Constructor to initialize a Vacation object
    public Vacation(int vacationId, String vacationTitle, String vacationHotel, String vacationStartDate, String vacationEndDate) {
        this.vacationId = vacationId;
        this.vacationTitle = vacationTitle;
        this.vacationHotel = vacationHotel;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }

    // Getter method for getting the Vacation ID
    public int getVacationId() {
        return vacationId;
    }

    // Setter method for setting the Vacation ID
    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    // Getter method for getting the title of the Vacation
    public String getVacationTitle() {
        return vacationTitle;
    }

    // Setter method for setting the title of the Vacation
    public void setVacationTitle(String vacationTitle) {
        this.vacationTitle = vacationTitle;
    }

    // Getter method for getting the hotel information for the Vacation
    public String getVacationHotel() {
        return vacationHotel;
    }

    // Setter method for setting the hotel information for the Vacation
    public void setVacationHotel(String vacationHotel) {
        this.vacationHotel = vacationHotel;
    }

    // Getter method for getting the start date of the Vacation
    public String getVacationStartDate() {
        return vacationStartDate;
    }

    // Setter method for setting the start date of the Vacation
    public void setVacationStartDate(String vacationStartDate) {
        this.vacationStartDate = vacationStartDate;
    }

    // Getter method for getting the end date of the Vacation
    public String getVacationEndDate() {
        return vacationEndDate;
    }

    // Setter method for setting the end date of the Vacation
    public void setVacationEndDate(String vacationEndDate) {
        this.vacationEndDate = vacationEndDate;
    }
}
