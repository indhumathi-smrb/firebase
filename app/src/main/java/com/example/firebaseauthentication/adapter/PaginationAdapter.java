package com.example.firebaseauthentication.adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.firebaseauthentication.datamodel.ItemListModel;
import com.example.firebaseauthentication.R;
import com.example.firebaseauthentication.databinding.ItemListBinding;

import java.util.ArrayList;
import java.util.List;



public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;

    private List<ItemListModel> itemsList;
    private Context context;

    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        itemsList = new ArrayList<>();
    }

    public List<ItemListModel> getMovies() {
        return itemsList;
    }

    public void setMovies(List<ItemListModel> movies) {
        this.itemsList = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_loading, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }


    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
       /* View v1 = inflater.inflate(R.layout.item_list, parent, false);
        viewHolder = new ItemVH(v1);
        return viewHolder;*/
        ItemListBinding itemListBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.item_list, parent, false);
        viewHolder=new ItemVH(itemListBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ItemListModel items = itemsList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                ItemVH itemVH = (ItemVH) holder;
                itemVH.itemListBinding.setItemsList(items);
               // holder.setViewModel(itemVH.binding);
                RequestOptions options = new RequestOptions()
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher_round)
                        .error(R.mipmap.ic_launcher_round);
                Glide.with(context).load(items.getImage()).apply(options).into(itemVH.itemListBinding.ivItems);
                break;
            case LOADING:
//                Do nothing
                break;
        }

    }

    @Override
    public int getItemCount() {
        return itemsList == null ? 0 : itemsList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == itemsList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }


    /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(ItemListModel mc) {
        itemsList.add(mc);
        notifyItemInserted(itemsList.size() - 1);
    }

    public void addAll(List<ItemListModel> mcList) {
        for (ItemListModel mc : mcList) {
            add(mc);
        }
    }

    public void remove(ItemListModel city) {
        int position = itemsList.indexOf(city);
        if (position > -1) {
            itemsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new ItemListModel());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = itemsList.size() - 1;
        ItemListModel item = getItem(position);

        if (item != null) {
            itemsList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public ItemListModel getItem(int position) {
        return itemsList.get(position);
    }


   /*
   View Holders
   _________________________________________________________________________________________________
    */

    /**
     * Main list's content ViewHolder
     */
    protected class ItemVH extends RecyclerView.ViewHolder {
       /* private TextView textView;
        private TextView tvDescription;
        private TextView tvPrice;
        private ImageView ivItems;*/
        ItemListBinding itemListBinding;
        public ItemVH(@NonNull ItemListBinding itemListBinding) {
            super(itemListBinding.getRoot());
            this.itemListBinding=itemListBinding;
           /* textView = (TextView) itemView.findViewById(R.id.textViewTitle);
            tvDescription = (TextView) itemView.findViewById(R.id.textViewDescription);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            ivItems = (ImageView) itemView.findViewById(R.id.ivItems);*/}
    }



    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }


}