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
    private ItemClickListener mItemClickListener;

    public ListAdapter(ItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

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
        format.applyPattern("dd MM yyyy");

        holder.tvUpdateAt.setText(format.format(tasks.get(position).getUpdateAt()));
    }

    @Override
    public int getItemCount() {
        return tasks == null ? 0 : tasks.size();
    }

    public void setTasks(List<TaskEntry> tasks){
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvDescription;
        TextView tvUpdateAt;

        public ViewHolder(View itemView) {
            super(itemView);

            tvDescription = itemView.findViewById(R.id.tvDescroption);
            tvUpdateAt = itemView.findViewById(R.id.tvUpdateAt);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int elementId = tasks.get(getAdapterPosition()).getId();
            mItemClickListener.onItemClickListener(elementId);
        }
    }

}
