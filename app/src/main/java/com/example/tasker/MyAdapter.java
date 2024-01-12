package com.example.tasker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recTask.setText(dataList.get(position).getTaskName());
        holder.recDesc.setText(dataList.get(position).getTaskDescription());
        holder.recDueDate.setText(dataList.get(position).getDueDate());

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Task", dataList.get(holder.getAdapterPosition()).getTaskName());
                intent.putExtra("Task Description", dataList.get(holder.getAdapterPosition()).getTaskDescription());
                intent.putExtra("Due Date", dataList.get(holder.getAdapterPosition()).getDueDate());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() : 0;
    }

    public void searchDataList(ArrayList<DataClass> searchList) {
        if (searchList != null) {
            dataList = searchList;
            notifyDataSetChanged();
        }
    }
}


class MyViewHolder extends RecyclerView.ViewHolder{
    TextView recDesc, recTask, recDueDate;
    CardView recCard;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recCard = itemView.findViewById(R.id.recCard);

        recTask = itemView.findViewById(R.id.recTaskName);
        recDesc = itemView.findViewById(R.id.recTaskDesc);
        recDueDate = itemView.findViewById(R.id.recDueDate);

    }
}