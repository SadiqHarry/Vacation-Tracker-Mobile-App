package com.mobile.VacationTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.VacationTracker.Entities.Excursion;
import com.mobile.VacationTracker.R;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {
    private final LayoutInflater mInflater;
    private final Context context;
    private List<Excursion> mExcursions;

    // Constants for Intent extras keys
    public static final String EXTRA_VACATION_ID = "vacationId";
    public static final String EXTRA_EXCURSION_ID = "excursionId";
    public static final String EXTRA_EXCURSION_TITLE = "excursionTitle";
    public static final String EXTRA_EXCURSION_START_DATE = "excursionStartDate";

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionTitleTextView;

        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionTitleTextView = itemView.findViewById(R.id.excursion_list_item);

            // Set a click listener for the item view
            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    Excursion current = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);

                    // Pass data as Intent extras
                    intent.putExtra(EXTRA_VACATION_ID, current.getVacationId());
                    intent.putExtra(EXTRA_EXCURSION_ID, current.getExcursionId());
                    intent.putExtra(EXTRA_EXCURSION_TITLE, current.getExcursionTitle());
                    intent.putExtra(EXTRA_EXCURSION_START_DATE, current.getExcursionStartDate());

                    // Start the details activity
                    context.startActivity(intent);
                }
            });
        }
    }

    public ExcursionAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the list, or 0 if it's null
        return mExcursions == null ? 0 : mExcursions.size();
    }

    // Update the list of excursions
    public void setExcursions(List<Excursion> excursions) {
        mExcursions = excursions;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            // Bind data to the view holder
            Excursion current = mExcursions.get(position);
            String title = current.getExcursionTitle();
            holder.excursionTitleTextView.setText(title);
        } else {
            // If the list is null, display a default text
            holder.excursionTitleTextView.setText("No Title");
        }
    }
}
