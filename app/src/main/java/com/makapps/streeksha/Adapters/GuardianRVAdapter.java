package com.makapps.streeksha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makapps.streeksha.Models.Guardian;
import com.makapps.streeksha.R;

import java.util.ArrayList;

public class GuardianRVAdapter extends RecyclerView.Adapter<GuardianRVAdapter.ViewHolder> {

    private ArrayList<Guardian> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public GuardianRVAdapter(Context context, ArrayList<Guardian> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.guardian_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Guardian g = mData.get(position);
        holder.id.setText(g.getId());
        holder.name.setText(g.getName());
        holder.email.setText(g.getEmail());
        holder.phone.setText(g.getPhone());
        holder.relationship.setText(g.getRelationship());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView id,name,email,phone,relationship;

        ViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.rv_item_id);
            name = itemView.findViewById(R.id.rv_item_name);
            email = itemView.findViewById(R.id.rv_item_email);
            phone = itemView.findViewById(R.id.rv_item_phone);
            relationship = itemView.findViewById(R.id.rv_item_relationship);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public void updateDataset(ArrayList<Guardian> mData){
        this.mData = mData;
    }
    // convenience method for getting data at click position
     public Guardian getItem(int id) {
        return mData.get(id);
    }
    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
