package com.example.firebaseauthentication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;

public class ItemListAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    ArrayList<ItemListModel> userArrayList;

    public ItemListAdapter1() {
        this.userArrayList = new ArrayList<ItemListModel>();
    }
    public ItemListAdapter1(ArrayList<ItemListModel> itemList) {

        this.userArrayList = itemList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new RecyclerViewViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ItemListModel user = userArrayList.get(position);
        RecyclerViewViewHolder viewHolder= (RecyclerViewViewHolder) holder;

        viewHolder.txtView_title.setText(user.getTitle());
        viewHolder.txtView_description.setText(user.getDescription());
    }

    @Override
    public int getItemCount() {
        return userArrayList == null ? 0 : userArrayList.size();
    }

    public void addItems(final List<ItemListModel> userArrayList) {
       // this.userArrayList.clear();
        this.userArrayList = (ArrayList<ItemListModel>) userArrayList;
        notifyDataSetChanged();
    }

    class RecyclerViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_icon;
        TextView txtView_title;
        TextView txtView_description;

        public RecyclerViewViewHolder(@NonNull View itemView) {
            super(itemView);
           // imgView_icon = itemView.findViewById(R.id);
            txtView_title = itemView.findViewById(R.id.textViewTitle);
            txtView_description = itemView.findViewById(R.id.textViewDescription);


        }

    }
    public void clear() {
        userArrayList.clear();
        notifyDataSetChanged();
    }

}
