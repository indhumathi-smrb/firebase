package com.example.firebaseauthentication.datamodel;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class RatingCountModel {

    @SerializedName("rate")
    @Expose
    private Double rate;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

}
