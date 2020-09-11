package com.example.invinity_scroll.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.invinity_scroll.R;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<String> dataContacts = new ArrayList<>();
    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOADING = 2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM){
            return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent,false));
        }
        return new ProgressViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_ITEM){
            ((ItemViewHolder) holder).bind(dataContacts.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataContacts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (dataContacts.get(position) != null) ? TYPE_ITEM : TYPE_LOADING;
    }

    public void addDataContact(List<String> contacts){
        if(contacts != null){
            dataContacts.addAll(contacts);
            notifyDataSetChanged();
        }
    }

    public void addLoading(){
        dataContacts.add(null);
        notifyDataSetChanged();
    }

    public void removeDataLoading(){
        dataContacts.remove((dataContacts.size() - 1));
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView tvName;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
        }

        public void bind(String name) {
           tvName.setText(name);
        }
    }

    class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
