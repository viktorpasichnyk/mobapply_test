package com.example.pasichnyk.test_for_mobapply;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pasichnyk.test_for_mobapply.model.Order;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Order>>, OnMapReadyCallback {
    private static final int LOADER_JSON_PARSE_ID = 1;
    private List<Order> orderList;
    private Loader loader = null;
    private MapFragment mapFragment;
    private ProgressBar pb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getView().setVisibility(View.GONE);


        if(isNetworkConnected()){

            orderList = new ArrayList<>();
            loader = getLoaderManager().initLoader(LOADER_JSON_PARSE_ID, savedInstanceState, this);
            loader.forceLoad();
            pb = (ProgressBar) findViewById(R.id.progress_bar_main);
            pb.setVisibility(View.VISIBLE);
            Toast.makeText(this, R.string.start_data_loading, Toast.LENGTH_LONG).show();

        }



       }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Order>> onCreateLoader(int id, Bundle args) {

        if(id == LOADER_JSON_PARSE_ID){
           return new OrdersLoader(this);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Order>> loader, List<Order> data) {
        orderList = data;
        pb.setVisibility(View.GONE);
        mapFragment.getView().setVisibility(View.VISIBLE);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onLoaderReset(Loader<List<Order>> loader) {
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        double latGermany = 51.165691;
        double longGermany = 10.451526;

        LatLng germany = new LatLng(latGermany, longGermany);
        LatLng departLatLng;
        LatLng destLatLng;
        double longitude;
        double latitude;

        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(germany, 7));
        googleMap.addMarker(new MarkerOptions()
                .title(getString(R.string.germany_tytle))
                .snippet("")
                .position(germany)
                .visible(false)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        for(int i=0; i<orderList.size(); i++){
            latitude = orderList.get(i).getDepartureAddress().getLatitude();
            longitude = orderList.get(i).getDepartureAddress().getLongitude();
            departLatLng = new LatLng(latitude, longitude);

            latitude = orderList.get(i).getDestinationAddress().getLatitude();
            longitude = orderList.get(i).getDestinationAddress().getLongitude();
            destLatLng = new LatLng(latitude, longitude);

            googleMap.addMarker(new MarkerOptions()
                            .position(departLatLng)
                            .visible(true)
                            .title(orderList.get(i).getDepartureAddress().getCity() + "  " + orderList.get(i).getDepartureAddress().getStreet())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
            );

            googleMap.addMarker(new MarkerOptions()
                            .position(destLatLng)
                            .visible(true)
                            .title(orderList.get(i).getDestinationAddress().getCountry() + ", " + orderList.get(i).getDestinationAddress().getCity() + ", " + orderList.get(i).getDestinationAddress().getStreet())
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            );

            googleMap.addPolyline(new PolylineOptions().geodesic(true)
                    .add(departLatLng)
                .add(destLatLng))
            .setWidth(3);
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if (ni == null) {
            Toast.makeText(MainActivity.this, R.string.conntct_to_the_network, Toast.LENGTH_LONG).show();
            return false;
        } else
            return true;
    }
}

