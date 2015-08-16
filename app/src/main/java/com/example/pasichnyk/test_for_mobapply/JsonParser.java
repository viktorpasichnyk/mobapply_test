package com.example.pasichnyk.test_for_mobapply;

import com.example.pasichnyk.test_for_mobapply.model.Order;
import com.example.pasichnyk.test_for_mobapply.model.OrderAddress;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pasichnyk on 14.08.2015.
 */
public class JsonParser {
    private final static String JSON_DEPARTURE_ADDRESS_LABEL = "departureAddress";
    private final static String JSON_DESTINATION_ADDRESS_LABEL = "destinationAddress";
    private final static String JSON_COUNTRY_LABEL = "country";
    private final static String JSON_ZIP_CODE_LABEL = "zipCode";
    private final static String JSON_CITY_LABEL = "city";
    private final static String JSON_COUNTRY_CODE_LABEL = "countryCode";
    private final static String JSON_STREET_LABEL = "street";
    private final static String JSON_HOUSE_NUMBER_LABEL = "houseNumber";

    private final static String JSON_ARRAY_RESULT_LABEL = "results";
    private final static String JSON_GEOMETRY_LABEL = "geometry";
    private final static String JSON_LOCATION_LABEL = "location";
    private final static String JSON_LATITUDE_LABEL = "lat";
    private final static String JSON_LONGITUDE_LABEL = "lng";


    public static List<Order> getOrdersListFromJson(String jsonString) throws JSONException {

        List<Order> orderList = new ArrayList<>();
        JSONArray ordersJsonArray = new JSONArray(jsonString);

        for (int i = 0; i < ordersJsonArray.length(); i++) {
            Order order = new Order();
            OrderAddress departureAddress = new OrderAddress();
            OrderAddress destinationAddress = new OrderAddress();

            JSONObject orderItemObject = ordersJsonArray.getJSONObject(i);

            JSONObject departureAddressObject = orderItemObject.getJSONObject(JSON_DEPARTURE_ADDRESS_LABEL);
            departureAddress.setCountry(departureAddressObject.getString(JSON_COUNTRY_LABEL));
            departureAddress.setZipcode(departureAddressObject.getString(JSON_ZIP_CODE_LABEL));
            departureAddress.setCity(departureAddressObject.getString(JSON_CITY_LABEL));
            departureAddress.setCountryCode(departureAddressObject.getString(JSON_COUNTRY_CODE_LABEL));
            departureAddress.setStreet(departureAddressObject.getString(JSON_STREET_LABEL));
            departureAddress.setHouseNumber(departureAddressObject.getString(JSON_HOUSE_NUMBER_LABEL));

            JSONObject destinationAddressObject = orderItemObject.getJSONObject(JSON_DESTINATION_ADDRESS_LABEL);
            destinationAddress.setCountry(destinationAddressObject.getString(JSON_COUNTRY_LABEL));
            destinationAddress.setZipcode(destinationAddressObject.getString(JSON_ZIP_CODE_LABEL));
            destinationAddress.setCity(destinationAddressObject.getString(JSON_CITY_LABEL));
            destinationAddress.setCountryCode(destinationAddressObject.getString(JSON_COUNTRY_CODE_LABEL));
            destinationAddress.setStreet(destinationAddressObject.getString(JSON_STREET_LABEL));
            destinationAddress.setHouseNumber(destinationAddressObject.getString(JSON_HOUSE_NUMBER_LABEL));

            order.setDepartureAddress(departureAddress);
            order.setDestinationAddress(destinationAddress);

            orderList.add(order);
        }
        return orderList;
    }


    public static LatLng getLatLongFromJson(String jsonStr) {
        LatLng resultLatLong = new LatLng(0, 0);

        try {
            JSONObject mainJsonObject = new JSONObject(jsonStr);
            JSONArray resultsJsonArray = mainJsonObject.getJSONArray(JSON_ARRAY_RESULT_LABEL);
            JSONObject resultsItemObject = resultsJsonArray.getJSONObject(0);
            JSONObject geometryObject = resultsItemObject.getJSONObject(JSON_GEOMETRY_LABEL);
            JSONObject locationObject = geometryObject.getJSONObject(JSON_LOCATION_LABEL);
            double latitude = locationObject.getDouble(JSON_LATITUDE_LABEL);
            double longitude = locationObject.getDouble(JSON_LONGITUDE_LABEL);
            resultLatLong = new LatLng(latitude, longitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return resultLatLong;
    }
}
