package com.epipasha.cleantaskmanager;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<TaskEntry> tasks;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_task, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvDescription.setText(tasks.get(position).getDescription());

        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("DD MM YYYY");

        holder.tvUpdateAt.setText(format.format(tasks.get(position).getUpdateAt()));
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<TaskEntry> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDescription;
        TextView tvUpdateAt;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tvDescroption);
            tvUpdateAt = itemView.findViewById(R.id.tvUpdateAt);
        }
    }

}
