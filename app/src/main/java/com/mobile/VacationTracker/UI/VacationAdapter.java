package com.mobile.VacationTracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobile.VacationTracker.Entities.Vacation;
import com.mobile.VacationTracker.R;

import java.util.List;

public class VacationAdapter extends RecyclerView.Adapter<VacationAdapter.VacationViewHolder> {
    private final LayoutInflater mInflater;
    private final Context context;
    private List<Vacation> mVacation;

    class VacationViewHolder extends RecyclerView.ViewHolder {
        private final TextView vacationListItem;

        private VacationViewHolder(View itemView) {
            super(itemView);
            vacationListItem = itemView.findViewById(R.id.vacation_list_text_view);

            // Set a click listener to handle item clicks
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Vacation current = mVacation.get(position);

                    // Create an intent to open VacationDetails activity
                    Intent intent = new Intent(context, VacationDetails.class);
                    intent.putExtra("vacationId", current.getVacationId());
                    intent.putExtra("vacationTitle", current.getVacationTitle());
                    intent.putExtra("vacationHotel", current.getVacationHotel());
                    intent.putExtra("vacationStartDate", current.getVacationStartDate());
                    intent.putExtra("vacationEndDate", current.getVacationEndDate());
                    intent.putExtra("position", position);
                    context.startActivity(intent);
                }
            });
        }
    }

    // Set the list of vacations and notify the adapter of changes
    public void setVacations(List<Vacation> vacations) {
        mVacation = vacations;
        notifyDataSetChanged();
    }

    public VacationAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public VacationAdapter.VacationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.activity_vacation_list_item, parent, false);
        return new VacationViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        if (mVacation != null)
            return mVacation.size();
        else
            return 0;
    }

    // Set the vacation titles and notify the adapter of changes
    public void setVacationTitle(List<Vacation> title) {
        mVacation = title;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull VacationAdapter.VacationViewHolder holder, int position) {
        if (mVacation != null) {
            Vacation current = mVacation.get(position);
            String title = current.getVacationTitle();
            holder.vacationListItem.setText(title);
        } else {
            holder.vacationListItem.setText("No Title");
        }
    }


}
