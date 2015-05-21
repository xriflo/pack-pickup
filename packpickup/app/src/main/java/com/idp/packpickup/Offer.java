package com.idp.packpickup;

import android.graphics.Bitmap;

/**
 * Created by happyfeet on 5/18/2015.
 */
public class Offer {
    private Bitmap image;
    private String name_user, phone, arrival, departure;

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public Offer() {
    }

    public Offer(Bitmap image, String name_user, String phone, String arrival, String departure) {
        this.image = image;
        this.name_user = name_user;
        this.phone = phone;
        this.arrival = arrival;
        this.departure = departure;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public void setName_user(String name_user) {
        this.name_user = name_user;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }



    public Bitmap getImage() {
        return image;
    }

    public String getName_user() {
        return name_user;
    }

    public String getPhone() {
        return phone;
    }

    public String getArrival() {
        return arrival;
    }

    public String getDeparture() {
        return departure;
    }

}
