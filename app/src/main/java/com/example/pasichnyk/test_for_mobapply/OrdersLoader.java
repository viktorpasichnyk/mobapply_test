package com.example.pasichnyk.test_for_mobapply;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.example.pasichnyk.test_for_mobapply.model.Order;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasichnyk on 14.08.2015.
 */
public class OrdersLoader extends AsyncTaskLoader<List<Order>> {


    private List<Order> orderList = new ArrayList<>();
    private String ordersJsonString;
    private List<Order> orderListWithLatLong = new ArrayList<>();

    public OrdersLoader(Context context) {
        super(context);
    }

    @Override
    public List<Order> loadInBackground() {

        final String URL_STRING = "http://mobapply.com/tests/orders";
        ordersJsonString = JsonFromUrlUploader.getJsonFromUrl(URL_STRING);

        try {
            orderList = JsonParser.getOrdersListFromJson(ordersJsonString);
            orderListWithLatLong = GeocoderHelper.addLatLongToOrderList(orderList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return orderListWithLatLong;
    }



}
