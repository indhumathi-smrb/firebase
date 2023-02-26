package com.example.firebaseauthentication.viewmodel;

import android.text.TextUtils;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.firebaseauthentication.datamodel.ItemListModel;

public class DataItemViewModel extends BaseObservable {
    private ItemListModel dataModel;

    public DataItemViewModel(ItemListModel dataModel) {
        this.dataModel = dataModel;
    }

    public void setUp() {
        // perform set up tasks, such as adding listeners
    }

    public void tearDown() {
        // perform tear down tasks, such as removing listeners
    }

    @Bindable
    public String getTitle() {
        return !TextUtils.isEmpty(dataModel.getTitle()) ? dataModel.getTitle() : "";
    }

    @Bindable
    public String getDescription() {
        return !TextUtils.isEmpty(dataModel.getDescription()) ? dataModel.getDescription() : "";
    }

    @Bindable
    public String getPrice() {
        return !TextUtils.isEmpty(String.valueOf(dataModel.getPrice())) ? String.valueOf(dataModel.getPrice()) : "";
    }
}
