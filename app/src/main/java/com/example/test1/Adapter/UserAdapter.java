package com.example.test1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test1.EntityClass.PersonModel;
import com.example.test1.R;
import com.example.test1.UpdateActivity;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<PersonModel> list;
    DeleteItemClickListener deleteItemClickListener;

    public UserAdapter(Context context, List<PersonModel> list, DeleteItemClickListener deleteItemClickListener) {
        this.context = context;
        this.list = list;
        this.deleteItemClickListener = deleteItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.age.setText(String.valueOf(list.get(position).getAge()));
        holder.fee.setText(String.valueOf(list.get(position).getFee()));
        holder.termDate.setText(list.get(position).getTermDate());

        // Edit/Update Button Function
        holder.editDataBtn.setOnClickListener((view -> {
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("nameSent", list.get(position).getName());
            intent.putExtra("ageSent", list.get(position).getAge());
            intent.putExtra("feeSent", list.get(position).getFee());
            intent.putExtra("dateSent", list.get(position).getTermDate());
            Log.d("****Key*****", String.valueOf(list.get(position).getKey()));
            intent.putExtra("idSent", String.valueOf(list.get(position).getKey()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);

        }));

        // Delete Button Function
        holder.deleteDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemClickListener.onItemDelete(position, list.get(position).getKey());
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // View Holder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, age, fee, termDate;
        Button editDataBtn, deleteDataBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            editDataBtn = itemView.findViewById(R.id.btnEdit);
            deleteDataBtn = itemView.findViewById(R.id.btnDelete);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            fee = itemView.findViewById(R.id.fee);
            termDate = itemView.findViewById(R.id.termDate);
        }
    }


    public interface DeleteItemClickListener {
        void onItemDelete(int position, int id);
    }
}
