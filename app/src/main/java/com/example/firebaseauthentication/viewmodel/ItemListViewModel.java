package com.example.firebaseauthentication.viewmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.firebaseauthentication.BR;
import com.example.firebaseauthentication.datamodel.ItemListModel;
import com.example.firebaseauthentication.adapter.PaginationAdapter;

import java.util.List;


public class ItemListViewModel extends BaseObservable {
    private static final String TAG = "ItemListViewModel";
    private PaginationAdapter adapter;
    private List<ItemListModel> data;

    /*public ItemListViewModel() {
        data = new ArrayList<>();
        adapter = new PaginationAdapter();
    }*/

    public void setUp() {
        // perform set up tasks, such as adding listeners, data population, etc.
        populateData();
    }

    public void tearDown() {
        // perform tear down tasks, such as removing listeners
    }

    @Bindable
    public List<ItemListModel> getData() {
        return this.data;
    }

    @Bindable
    public PaginationAdapter getAdapter() {
        return this.adapter;
    }

    private void populateData() {
        // populate the data from the source, such as the database.
        /*for (int i = 0; i < 50; i++) {
            DataModel dataModel = new DataModel();
            dataModel.setTitle(String.valueOf(i));
            data.add(dataModel);
        }*/
        notifyPropertyChanged(BR.data);
    }
}
