package com.example.mymovie;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng currentlocation;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        currentlocation = new LatLng(-37.8771, 145.04493 );

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

        @Override
        public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.addMarker(new MarkerOptions().position(currentlocation).title("My location"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(currentlocation));
            /*Zoom levels
              1: World
                5: Landmass/continent
                   10: City
                      15: Streets
                             20: Buildings
                                    */
            float zoomLevel = (float) 10.0;
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentlocation, zoomLevel));
    }
    public class GeocodeAddress extends AsyncTask<Location, Void, String>
      {


          @Override
          protected String doInBackground(Location... params) {
              // TODO Auto-generated method stub
              if(params[0]!=null)
              {
                  Geocoder geocoder=new Geocoder(getApplicationContext());
                  try {
                      List<Address> geoaddress= geocoder.getFromLocationName(location, 1);
                      double lattitude=geoaddress.get(0).getLatitude();//获取纬度
                      double longtitude=geoaddress.get(0).getLongitude();//获取经度
                      currentlocation=new LatLng(lattitude, longtitude );
                  } catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          return null;
            }
            @Override
            protected void onPostExecute(String result) {
              // TODO Auto-generated method stub

            }

}
}