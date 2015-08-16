package com.example.pasichnyk.test_for_mobapply.model;

/**
 * Created by Pasichnyk on 14.08.2015.
 */
public class Order  {

    public OrderAddress departureAddress;
    public OrderAddress destinationAddress;


    public OrderAddress getDepartureAddress() {
        return departureAddress;
    }

    public void setDepartureAddress(OrderAddress departureAddress) {
        this.departureAddress = departureAddress;
    }

    public OrderAddress getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(OrderAddress destinationAddress) {
        this.destinationAddress = destinationAddress;
    }




}
