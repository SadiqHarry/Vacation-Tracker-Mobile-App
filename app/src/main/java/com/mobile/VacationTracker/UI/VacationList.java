package com.mobile.VacationTracker.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mobile.VacationTracker.Database.Repository;
import com.mobile.VacationTracker.Entities.Vacation;
import com.mobile.VacationTracker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;

import java.util.List;

public class VacationList extends AppCompatActivity {

    private static final String TAG = "VacationListLog";
    private Repository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation_list);

        // Initialize RecyclerView and its adapter
        RecyclerView recyclerView = findViewById(R.id.vacation_recycler_view);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the repository for database operations
        mRepository = new Repository(getApplication());
        List<Vacation> allVacations = mRepository.getAllVacations();

        // Set the data for the adapter
        vacationAdapter.setVacations(allVacations);

        testData(); //test data

        // Initialize FloatingActionButton to add a new vacation
        FloatingActionButton fab = findViewById(R.id.vacationListAddButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start the VacationDetails activity to add a new vacation
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
    }

    // Create the options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    // Handle menu item selections
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.vacation_refresh) {
            // Refresh the list of vacations
            refreshVacationList();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Refresh the list of vacations
    private void refreshVacationList() {
        RecyclerView recyclerView = findViewById(R.id.vacation_recycler_view);
        final VacationAdapter adapter = new VacationAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setVacations(mRepository.getAllVacations());
    }

    // Update the vacation list when the activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        RecyclerView recyclerView = findViewById(R.id.vacation_recycler_view);
        final VacationAdapter vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        vacationAdapter.setVacations(mRepository.getAllVacations());
    }

         public void testData() {
        Repository repository1 = new Repository(getApplication());
        Vacation vacation1 = new Vacation(1, "test", "test",
                "9/4/23","9/5/23");
        mRepository.insert(vacation1);
        Log.e(TAG, "Test Passed");
    }


}
