package com.example.pasichnyk.test_for_mobapply;

import android.text.TextUtils;

import com.example.pasichnyk.test_for_mobapply.model.Order;
import com.example.pasichnyk.test_for_mobapply.model.OrderAddress;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasichnyk on 15.08.2015.
 */
public class GeocoderHelper {

    public static List<Order> addLatLongToOrderList(List<Order> ordersListFromJson) {
        List<Order> resultOrderList = new ArrayList<>();
        String paramStrDepartureAdd;
        String paramStrDestinationAdd;
        Order order;
        OrderAddress departureAddress;
        OrderAddress destinationAddress;

        for (int i = 0; i < ordersListFromJson.size(); i++) {
            order = ordersListFromJson.get(i);
            departureAddress = order.getDepartureAddress();
            destinationAddress = order.getDestinationAddress();
            String urlDepartureLatLong;
            String urlDestinationLatLong;
            String jsonDepartureLatLong;
            String jsonDestinationLatLong;
            LatLng latLongDeparture;
            LatLng latLongDestination;


            paramStrDepartureAdd = departureAddress.getCity() + "," + getParamsWithoutCityCountrycode(departureAddress) + "," + departureAddress.getCountryCode();
            urlDepartureLatLong = getUrlWithParams(paramStrDepartureAdd);
            jsonDepartureLatLong = JsonFromUrlUploader.getJsonFromUrl(urlDepartureLatLong);
            latLongDeparture = JsonParser.getLatLongFromJson(jsonDepartureLatLong);

            if (latLongDeparture.latitude == 0 && latLongDeparture.longitude == 0) {
                paramStrDepartureAdd = getParamsWithoutCityCountrycode(departureAddress) + "," + departureAddress.getCountryCode();
                urlDepartureLatLong = getUrlWithParams(paramStrDepartureAdd);
                jsonDepartureLatLong = JsonFromUrlUploader.getJsonFromUrl(urlDepartureLatLong);
                latLongDeparture = JsonParser.getLatLongFromJson(jsonDepartureLatLong);

                if (latLongDeparture.latitude == 0 && latLongDeparture.longitude == 0) {
                    paramStrDepartureAdd = getParamsWithoutCityCountrycode(departureAddress);
                    urlDepartureLatLong = getUrlWithParams(paramStrDepartureAdd);
                    jsonDepartureLatLong = JsonFromUrlUploader.getJsonFromUrl(urlDepartureLatLong);
                    latLongDeparture = JsonParser.getLatLongFromJson(jsonDepartureLatLong);
                }
            }

            paramStrDestinationAdd = destinationAddress.getCity() + "," + getParamsWithoutCityCountrycode(destinationAddress) + "," + destinationAddress.getCountryCode();
            urlDestinationLatLong = getUrlWithParams(paramStrDestinationAdd);
            jsonDestinationLatLong = JsonFromUrlUploader.getJsonFromUrl(urlDestinationLatLong);
            latLongDestination = JsonParser.getLatLongFromJson(jsonDestinationLatLong);

            if (latLongDestination.latitude == 0 && latLongDestination.longitude == 0) {
                paramStrDestinationAdd = getParamsWithoutCityCountrycode(destinationAddress) + "," + destinationAddress.getCountryCode();
                urlDestinationLatLong = getUrlWithParams(paramStrDestinationAdd);
                jsonDestinationLatLong = JsonFromUrlUploader.getJsonFromUrl(urlDestinationLatLong);
                latLongDestination = JsonParser.getLatLongFromJson(jsonDestinationLatLong);

                if (latLongDestination.latitude == 0 && latLongDestination.longitude == 0) {
                    paramStrDestinationAdd = getParamsWithoutCityCountrycode(destinationAddress);
                    urlDestinationLatLong = getUrlWithParams(paramStrDestinationAdd);
                    jsonDestinationLatLong = JsonFromUrlUploader.getJsonFromUrl(urlDestinationLatLong);
                    latLongDestination = JsonParser.getLatLongFromJson(jsonDestinationLatLong);
                }

            }

            order.getDepartureAddress().setLatitude(latLongDeparture.latitude);
            order.getDepartureAddress().setLongitude(latLongDeparture.longitude);
            order.getDestinationAddress().setLatitude(latLongDestination.latitude);
            order.getDestinationAddress().setLongitude(latLongDestination.longitude);

            resultOrderList.add(order);
        }
        return ordersListFromJson;
    }

    public static String getParamsWithoutCityCountrycode(OrderAddress orderAddress) {
        String params = "";

        if (!TextUtils.isEmpty(orderAddress.getStreet())) {
            params = params + orderAddress.getStreet() + ",";
        }
        if (!TextUtils.isEmpty(orderAddress.getHouseNumber())) {
            params = params + orderAddress.getHouseNumber() + ",";
        }
        params = params + orderAddress.getZipcode();

        return params;
    }

    public static String getUrlWithParams(String params) {
        String urlWithParams;
        urlWithParams = "https://maps.google.com/maps/api/geocode/json?address=" + params + "&sensor=false";
        return urlWithParams;
    }
}
