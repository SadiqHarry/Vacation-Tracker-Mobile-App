package com.mobile.VacationTracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile.VacationTracker.Database.Repository;
import com.mobile.VacationTracker.Entities.Excursion;
import com.mobile.VacationTracker.Entities.Vacation;
import com.mobile.VacationTracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class VacationDetails extends AppCompatActivity {
    EditText titleText;
    EditText hotelText;
    EditText startDateText;
    EditText EndDateText;


    int vacationId;
    String title;
    String hotel;
    String start_date;
    String end_date;

    Vacation currentVac;
    int num;

    final Calendar myCalendarStart = Calendar.getInstance();
    final Calendar myCalendarEnd = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener startDate;
    DatePickerDialog.OnDateSetListener endDate;


    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the activity's content view to the specified layout
        setContentView(R.layout.activity_vacation_details);

        // Initialize a repository instance for data management
        repository = new Repository(getApplication());

        // Find and assign views to variables for later use
        titleText = findViewById(R.id.title);
        hotelText = findViewById(R.id.hotel);
        startDateText = findViewById(R.id.startDate);
        EndDateText = findViewById(R.id.endDate);

        // Retrieve data passed from the previous activity via Intent extras
        vacationId = getIntent().getIntExtra("vacationId", -1);
        title = getIntent().getStringExtra("vacationTitle");
        hotel = getIntent().getStringExtra("vacationHotel");
        start_date = getIntent().getStringExtra("vacationStartDate");
        end_date = getIntent().getStringExtra("vacationEndDate");

        // Check if title is not null (i.e., editing an existing vacation)
        if (title != null) {
            // Reassign vacationId and update text fields
            vacationId = getIntent().getIntExtra("vacationId", -1);
            titleText.setText(title);
            hotelText.setText(hotel);
            startDateText.setText(start_date);
            EndDateText.setText(end_date);
        }

        // Set up a listener for the start date text field to open a DatePickerDialog
        startDateText = findViewById(R.id.startDate);
        startDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String formatDate = "MM/dd/yy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.US);

            updateLabelStart(); // Call a method to update the start date label
        };

        // Attach the DatePickerDialog to the start date text field
        startDateText.setOnClickListener(v -> new DatePickerDialog(VacationDetails.this, startDate, myCalendarStart
                .get(Calendar.YEAR), myCalendarStart.get(Calendar.MONTH),
                myCalendarStart.get(Calendar.DAY_OF_MONTH)).show());

        // Similar setup for the end date text field
        EndDateText = findViewById(R.id.endDate);
        endDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarEnd.set(Calendar.YEAR, year);
            myCalendarEnd.set(Calendar.MONTH, monthOfYear);
            myCalendarEnd.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            String formatDate = "MM/dd/yy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.US);

            updateLabelEnd(); // Call a method to update the end date label
        };

        // Attach the DatePickerDialog to the end date text field
        EndDateText.setOnClickListener(v -> new DatePickerDialog(VacationDetails.this, endDate, myCalendarEnd
                .get(Calendar.YEAR), myCalendarEnd.get(Calendar.MONTH),
                myCalendarEnd.get(Calendar.DAY_OF_MONTH)).show());

        // Find the FloatingActionButton and set a click listener
        FloatingActionButton fab = findViewById(R.id.start_excursion_details);
        fab.setOnClickListener(view -> {
            // Create an intent to start the ExcursionDetails activity and pass data
            Intent intent = new Intent(VacationDetails.this, ExcursionDetails.class);
            intent.putExtra("vacationId", vacationId);
            startActivity(intent);
        });

        // Set up a listener for the start date again (duplicate code)
        startDate = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendarStart.set(Calendar.YEAR, year);
            myCalendarStart.set(Calendar.MONTH, monthOfYear);
            myCalendarStart.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            updateLabelStart(); // Call a method to update the start date label
        };

        // Find the RecyclerView and configure it with excursion data
        RecyclerView recyclerView = findViewById(R.id.excursion_view);
        repository = new Repository(getApplication());
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
        recyclerView.setAdapter(excursionAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Filter and display excursions based on the current vacationId
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) filteredExcursions.add(e);
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

    /**
     * Updates the start date text field with a formatted date.
     */
    private void updateLabelStart() {
        // Define the desired date format
        String formatDate = "MM/dd/yy";

        // Create a SimpleDateFormat object with the specified format and locale
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.US);

        // Set the text of the startDateText view to the formatted date from myCalendarStart
        startDateText.setText(dateFormat.format(myCalendarStart.getTime()));
    }


    /**
     * Updates the end date text field with a formatted date.
     */
    private void updateLabelEnd() {
        // Define the desired date format
        String formatDate = "MM/dd/yy";

        // Create a SimpleDateFormat object with the specified format and locale
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.US);

        // Set the text of the EndDateText view to the formatted date from myCalendarEnd
        EndDateText.setText(dateFormat.format(myCalendarEnd.getTime()));
    }


    /**
     * Checks if the start date of a vacation is before the end date.
     *
     * @return True if the start date is before the end date; otherwise, false.
     */
    private boolean checkDate() {
        // Initialize Date objects for the start and end dates
        Date startDateVac = new Date();
        Date endDateVac = new Date();

        // Parse the start date string into a Date object
        try {
            startDateVac = new SimpleDateFormat("MM/dd/yy").parse(start_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Parse the end date string into a Date object
        try {
            endDateVac = new SimpleDateFormat("MM/dd/yy").parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Check if the start date is before the end date
        return startDateVac.before(endDateVac);
    }


    /**
     * Initializes the options menu for the VacationDetails activity.
     *
     * @param menu The menu to be initialized.
     * @return True if the menu is successfully inflated; otherwise, false.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu layout specified in R.menu.menu_vacation_details
        getMenuInflater().inflate(R.menu.menu_vacation_details, menu);

        // Return true to indicate that the menu was successfully created
        return true;
    }

    /**
     * Called when the activity is resumed, typically after being paused or created.
     * Refreshes the excursion list when the activity becomes visible again.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Find the RecyclerView for displaying excursions
        RecyclerView recyclerView = findViewById(R.id.excursion_view);

        // Initialize an ExcursionAdapter for managing excursion data
        final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);

        // Set the RecyclerView's adapter to the newly created ExcursionAdapter
        recyclerView.setAdapter(excursionAdapter);

        // Configure the RecyclerView with a LinearLayoutManager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create a filtered list of excursions based on the current vacationId
        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) {
                filteredExcursions.add(e);
            }
        }

        // Update the ExcursionAdapter with the filtered list of excursions
        excursionAdapter.setExcursions(filteredExcursions);
    }


    /**
     * Handles menu item selection in the VacationDetails activity.
     *
     * @param item The selected menu item.
     * @return True if the menu item action is handled; otherwise, false.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save) {
            // Get the start and end date from the respective text fields
            start_date = startDateText.getText().toString();
            end_date = EndDateText.getText().toString();

            // Check if the dates are valid (start date is before end date, and both are valid)
            if (checkDate() && validStartDate(start_date) && validEndDate(end_date)) {
                if (vacationId == -1) {
                    // Create a new Vacation object and insert it into the repository
                    String title = titleText.getText().toString();
                    String lodging = hotelText.getText().toString();
                    Vacation newVacation = new Vacation(0, title, lodging, start_date, end_date);
                    repository.insert(newVacation);
                    Toast.makeText(VacationDetails.this, "Successful!", Toast.LENGTH_LONG).show();
                    this.finish();
                } else {
                    // Update an existing Vacation object in the repository
                    String title = titleText.getText().toString();
                    String lodging = hotelText.getText().toString();
                    String start = startDateText.getText().toString();
                    String end = EndDateText.getText().toString();
                    Vacation newVacation = new Vacation(vacationId, title, lodging, start, end);
                    repository.update(newVacation);
                    Toast.makeText(VacationDetails.this, "Successful!", Toast.LENGTH_LONG).show();
                    this.finish();
                }
                return true;
            } else {
                Toast.makeText(VacationDetails.this, "End date must be after start date", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (id == R.id.delete) {
            // Find the current Vacation based on vacationId
            for (Vacation vac : repository.getAllVacations()) {
                if (vac.getVacationId() == Integer.parseInt(String.valueOf(vacationId)))
                    currentVac = vac;
            }

            // Count the number of excursions associated with this vacation
            num = 0;
            for (Excursion exc : repository.getAllExcursions()) {
                if (exc.getVacationId() == Integer.parseInt(String.valueOf(vacationId)))
                    ++num;
            }

            if (num == 0) {
                // Delete the vacation if it has no associated excursions
                repository.delete(currentVac);
                Toast.makeText(VacationDetails.this, currentVac.getVacationTitle() + " Deleted", Toast.LENGTH_LONG).show();
                this.finish();
            } else {
                Toast.makeText(VacationDetails.this, "Cannot delete a vacation assigned to excursions", Toast.LENGTH_LONG).show();
            }
            return true;
        }

        if (id == R.id.share) {
            // Create an intent for sharing vacation details
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, titleText.getText().toString() + " " +
                    hotelText.getText().toString() + " " +
                    startDateText.getText().toString() + " " + EndDateText.getText().toString());
            sendIntent.putExtra(Intent.EXTRA_TITLE, "Great choice");
            sendIntent.setType("text/plain");

            // Create a share intent and start the sharing chooser
            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
            return true;
        }

        if (id == R.id.start_alert) {
            // Create an alert for the start date
            String displayDate = startDateText.getText().toString();
            String formatDate = "MM/dd/yy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.US);
            Date myDate = null;
            try {
                myDate = dateFormat.parse(displayDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(VacationDetails.this, Receiver.class);
                intent.putExtra("key", title + " vacation is starting");
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
            }
            return true;
        }

        if (id == R.id.end_alert) {
            // Create an alert for the end date
            String displayDate = startDateText.getText().toString();
            String formatDate = "MM/dd/yy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(formatDate, Locale.US);
            Date myDate = null;
            try {
                myDate = dateFormat.parse(displayDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            try {
                Long trigger = myDate.getTime();
                Intent intent = new Intent(VacationDetails.this, Receiver.class);
                intent.putExtra("key", title + " vacation is ending");
                PendingIntent sender = PendingIntent.getBroadcast(VacationDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
            } catch (Exception e) {
            }
            return true;
        }

        if (id == R.id.refresh) {
            // Refresh the excursion list in the RecyclerView
            RecyclerView recyclerView = findViewById(R.id.excursion_view);
            repository = new Repository(getApplication());
            final ExcursionAdapter excursionAdapter = new ExcursionAdapter(this);
            recyclerView.setAdapter(excursionAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<Excursion> filteredExcursions = new ArrayList<>();
            for (Excursion e : repository.getAllExcursions()) {
                if (e.getVacationId() == vacationId) filteredExcursions.add(e);
            }
            excursionAdapter.setExcursions(filteredExcursions);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Validates a start date string to ensure it is in the correct format.
     *
     * @param start_date The start date string to validate.
     * @return True if the start date is valid or empty; otherwise, false.
     */
    public boolean validStartDate(String start_date) {
        // Check if the start date string is empty or contains only whitespace
        if (start_date.trim().equals("")) {
            return true; // Empty start date is considered valid
        } else {
            // Create a SimpleDateFormat object for parsing and set leniency to false
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            dateFormat.setLenient(false);

            try {
                // Attempt to parse the start_date string into a Date object
                Date validDate = dateFormat.parse(start_date);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(VacationDetails.this, "Enter valid date", Toast.LENGTH_LONG).show();
                return false; // Parsing failed, indicating an invalid date
            }

            return true; // Start date is valid according to the specified format
        }
    }

    /**
     * Validates an end date string to ensure it is in the correct format.
     *
     * @param end_date The end date string to validate.
     * @return True if the end date is valid or empty; otherwise, false.
     */
    public boolean validEndDate(String end_date) {
        // Check if the end date string is empty or contains only whitespace
        if (end_date.trim().equals("")) {
            return true; // Empty end date is considered valid
        } else {
            // Create a SimpleDateFormat object for parsing and set leniency to false
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
            dateFormat.setLenient(false);

            try {
                // Attempt to parse the end_date string into a Date object
                Date validDate = dateFormat.parse(end_date);
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(VacationDetails.this, "Enter valid date.", Toast.LENGTH_LONG).show();
                return false; // Parsing failed, indicating an invalid date
            }

            return true; // End date is valid according to the specified format
        }
    }
}
