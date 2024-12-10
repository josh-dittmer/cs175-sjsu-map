package edu.sjsu.android.groupproject12.list;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.sjsu.android.groupproject12.databinding.FragmentLocationBinding;

import java.util.List;

public class LocationViewAdapter extends RecyclerView.Adapter<LocationViewAdapter.ViewHolder> {

    private final List<Location> mValues;

    public LocationViewAdapter(List<Location> items) {
        mValues = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentLocationBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Location loc = mValues.get(position);
        holder.nameView.setText(loc.getName());
        holder.visitedView.setText(loc.getVisited() ? "VISITED" : "NOT VISITED");
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView nameView;
        public final TextView visitedView;

        public ViewHolder(FragmentLocationBinding binding) {
            super(binding.getRoot());
            nameView = binding.name;
            visitedView = binding.visited;
        }
    }
}