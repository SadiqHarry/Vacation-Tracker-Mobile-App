// Define the package where this class belongs
package com.mobile.VacationTracker.Database;

// Import necessary Android classes and libraries
import android.app.Application;

// Import DAOs and entities
import com.mobile.VacationTracker.DAO.ExcursionDao;
import com.mobile.VacationTracker.DAO.VacationDao;
import com.mobile.VacationTracker.Entities.Excursion;
import com.mobile.VacationTracker.Entities.Vacation;

// Import Java utilities
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Define a class called Repository responsible for interacting with the database
public class Repository {
    private final VacationDao mVacationDAO;
    private final ExcursionDao mExcursionDAO;
    private List<Vacation> mAllVacationsById;
    private List<Excursion> mAllExcursions;
    private List<Vacation> mAllVacations;

    // Define the number of database operation threads
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Constructor that initializes the Repository
    public Repository(Application application) {
        VacationDatabase db = VacationDatabase.getDatabase(application);
        mVacationDAO = db.VacationDao();
        mExcursionDAO = db.ExcursionDao();
    }

    // Method to asynchronously retrieve all vacations by ID from the database
    public List<Vacation> getAllVacationsById() {
        databaseExecutor.execute(() -> {
            mAllVacationsById = mVacationDAO.getAllVacationsById();
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacationsById;
    }

    // Method to asynchronously retrieve all vacations from the database
    public List<Vacation> getAllVacations() {
        databaseExecutor.execute(() -> {
            mAllVacations = mVacationDAO.getAllVacations();
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllVacations;
    }

    // Method to asynchronously insert a vacation into the database
    public void insert(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.insert(vacation);
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to asynchronously update a vacation in the database
    public void update(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.update(vacation);
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to asynchronously delete a vacation from the database
    public void delete(Vacation vacation) {
        databaseExecutor.execute(() -> {
            mVacationDAO.delete(vacation);
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to asynchronously retrieve all excursions from the database
    public List<Excursion> getAllExcursions() {
        databaseExecutor.execute(() -> {
            mAllExcursions = mExcursionDAO.getAllExcursions();
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mAllExcursions;
    }

    // Method to asynchronously insert an excursion into the database
    public void insert(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.insert(excursion);
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to asynchronously update an excursion in the database
    public void update(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.update(excursion);
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Method to asynchronously delete an excursion from the database
    public void delete(Excursion excursion) {
        databaseExecutor.execute(() -> {
            mExcursionDAO.delete(excursion);
        });
        try {
            Thread.sleep(1000); // Sleep to wait for the database operation to complete
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
