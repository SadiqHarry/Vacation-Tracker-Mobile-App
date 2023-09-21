package com.mobile.VacationTracker.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.mobile.VacationTracker.Database.Repository;
import com.mobile.VacationTracker.Entities.Excursion;
import com.mobile.VacationTracker.Entities.Vacation;
import com.mobile.VacationTracker.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {
    private EditText excursionTitleText;
    private EditText excursionDateText;
    private int excursionId;
    private int vacationId;
    private String title;
    private String date;
    private String vacStartDate;
    private String vacEndDate;
    private final Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener excursionDateCalendar;
    private Repository eRepository;
    private int numExc;
    private Excursion currentExc;
    private Vacation currentVac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_excursion_details);

        // Initialize UI elements and repository
        excursionTitleText = findViewById(R.id.excursion_title);
        excursionDateText = findViewById(R.id.excursion_date);
        eRepository = new Repository(getApplication());

        // Retrieve data from the intent
        vacationId = getIntent().getIntExtra("vacationId", -1);
        excursionId = getIntent().getIntExtra("excursionId", -1);
        title = getIntent().getStringExtra("excursionTitle");
        date = getIntent().getStringExtra("excursionStartDate");

        // Fetch vacation start and end dates
        List<Vacation> myVacations = eRepository.getAllVacations();
        for (Vacation v : myVacations) {
            if (v.getVacationId() == vacationId) {
                vacStartDate = v.getVacationStartDate();
                vacEndDate = v.getVacationEndDate();
                break;
            }
        }

        // Set title and date if available
        if (title != null) {
            excursionTitleText.setText(title);
            excursionDateText.setText(date);
        }

        // Initialize DatePickerDialog and its listener
        excursionDateText = findViewById(R.id.excursion_date);
        excursionDateCalendar = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                updateLabelDate();
            }
        };


        excursionDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        if (id == R.id.excursion_save) {
            saveExcursion();
            return true;
        }

        if (id == R.id.excursion_delete) {
            deleteExcursion();
            return true;
        }

        if (id == R.id.excursion_alert) {
            setExcursionAlert();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Check if the excursion date is within the vacation date range
    private boolean dateCheckExcursion() {
        try {
            Date excursionStartDate = new SimpleDateFormat("MM/dd/yy").parse(date);
            Date startDateVac = new SimpleDateFormat("MM/dd/yy").parse(vacStartDate);
            Date endDateVac = new SimpleDateFormat("MM/dd/yy").parse(vacEndDate);

            return !(startDateVac.after(excursionStartDate) || endDateVac.before(excursionStartDate));
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Validate the date format
    private boolean dateValidation(String date) {
        if (date.trim().isEmpty()) {
            return true;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");
            sdf.setLenient(false);
            try {
                Date validDate = sdf.parse(date);
                return true;
            } catch (ParseException e) {
                e.printStackTrace();
                Toast.makeText(ExcursionDetails.this, "Please enter a valid date.", Toast.LENGTH_LONG).show();
                return false;
            }
        }
    }

    // Update the label of the excursion date EditText
    private void updateLabelDate() {
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        excursionDateText.setText(sdf.format(myCalendar.getTime()));
    }

    // Show the DatePickerDialog
    private void showDatePickerDialog() {
        new DatePickerDialog(
                ExcursionDetails.this,
                excursionDateCalendar,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    // Save or update the excursion
    private void saveExcursion() {
        date = excursionDateText.getText().toString();

        if (dateCheckExcursion() && dateValidation(date)) {
            if (excursionId == -1) {
                String title = excursionTitleText.getText().toString();
                Excursion newExcursion = new Excursion(0, vacationId, title, date);
                eRepository.insert(newExcursion);
                Toast.makeText(ExcursionDetails.this, "Excursion Added", Toast.LENGTH_LONG).show();
                finish();
            } else if (excursionId >= 0) {
                String title = excursionTitleText.getText().toString();
                Excursion newExcursion = new Excursion(excursionId, vacationId, title, date);
                eRepository.update(newExcursion);
                Toast.makeText(ExcursionDetails.this, "Excursion Updated", Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            Toast.makeText(ExcursionDetails.this, "Please enter a date within the vacation date range", Toast.LENGTH_LONG).show();
        }
    }

    // Delete the excursion
    private void deleteExcursion() {
        for (Excursion exc : eRepository.getAllExcursions()) {
            if (exc.getExcursionId() == excursionId) {
                currentExc = exc;
            }
        }
        eRepository.delete(currentExc);
        Toast.makeText(ExcursionDetails.this, currentExc.getExcursionTitle() + " was deleted", Toast.LENGTH_LONG).show();
        finish();
    }

    // Set an alert for the excursion
    private void setExcursionAlert() {
        String title = excursionTitleText.getText().toString();
        String dateFromScreen = excursionDateText.getText().toString();
        String myFormat = "MM/dd/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        Date myDate = null;
        try {
            myDate = sdf.parse(dateFromScreen);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try {
            Long trigger = myDate.getTime();
            Intent intent = new Intent(ExcursionDetails.this, Receiver.class);
            intent.putExtra("key", title);
            PendingIntent sender = PendingIntent.getBroadcast(ExcursionDetails.this, ++MainActivity.numAlert, intent, PendingIntent.FLAG_IMMUTABLE);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, trigger, sender);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
