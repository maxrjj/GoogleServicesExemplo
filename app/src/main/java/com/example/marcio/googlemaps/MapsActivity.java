package com.example.marcio.googlemaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import android.location.*;
import android.util.Log;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    //private Localizacao local;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email_id");
        System.out.println(email);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        callConnection();
    }

    private synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

    }

    //listener
    @Override
    public void onConnected(Bundle bundle) {
        //Log.i("LOG", "onConnected( " + bundle + ")");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location loc = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(loc != null) {

            //this.local = new Localizacao();


            Log.v("LOG", "Latitude : " + loc.getLatitude());
            Log.v("LOG", "Longitude : " + loc.getLongitude());

            LatLng local = new LatLng(loc.getLatitude(), loc.getLongitude());

            mMap.addMarker(new MarkerOptions().position(local).title("Minha Localização"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(local));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f));

/*            //Select -------------------


            local = new LatLng(loc.getLatitude(), loc.getLongitude());

            mMap.addMarker(new MarkerOptions().position(local).title("A Outra Localização"));*/

        }
    }

    public void custonAddMarker(LatLng latLong, String Title, String snippet ){

        MarkerOptions options = new MarkerOptions();
        options.position(latLong).title(Title).snippet(snippet).draggable(true);



    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i("LOG","onConnectionSuspend( "+i+")");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i("LOG","onConnectionSuspend( "+connectionResult+")");
    }
}

