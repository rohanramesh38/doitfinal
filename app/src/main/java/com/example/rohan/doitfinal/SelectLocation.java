package com.example.rohan.doitfinal;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class SelectLocation extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    @Override
    protected void onStart() {
        super.onStart();
        if (!mGoogleApiClient.isConnected())
            mGoogleApiClient.connect();

    }

    private TextView tf,tnf;
    private Button bt;
    int chkeck=0;
    private LocationRequest mLocationRequest;
    double lati=-77.03655,longi=38.89770;
    private GoogleApiClient mGoogleApiClient;
    String loc="Not found";
    List<String> listloc;

    private static final String LOGSERVICE = "#######";
    private final String mapbox_token="sk.eyJ1Ijoicm9oYW5yYW1lc2gzOCIsImEiOiJjanVlNXQ3c3QwMDRtNDlwcTdnY2ltY293In0.uO-ZKuwgDyTrAW-Dx5EpVg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        tf=findViewById(R.id.locfin);
        tnf=findViewById(R.id.notf);
        bt=findViewById(R.id.npage);

        buildGoogleApiClient();
        listloc = new ArrayList<String>();

        listloc.add("Anna Nagar");
        listloc.add("Ambattur");
        listloc.add("Avadi");
        listloc.add("Villivakkam:");
        listloc.add("Guindy");
        listloc.add("Kolathur");
        listloc.add("Madhavaram");
        listloc.add("Washermanpet");
        listloc.add("Perambur");
        listloc.add("T Nagar");

       /* MapboxGeocoder client = new MapboxGeocoder.Builder()
                .setAccessToken(MAPBOX_ACCESS_TOKEN)
                .setLocation("The White House")
                .build();
        */

       tnf.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {


               showRadioButtonDialog();

           }
       });
bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (!loc.equals("Not found"))
        {
            Intent i=new Intent(SelectLocation.this,SelectOFWRActivity.class);
            i.putExtra("choice",getIntent().getStringExtra("choice"));
            i.putExtra("domain",getIntent().getStringExtra("domain"));
            i.putExtra("location",loc);
            startActivity(i);

        }
        else
        {
            Toast.makeText(SelectLocation.this, "pls Select your location", Toast.LENGTH_SHORT).show();
        }



        // Log.v("result",reverseGeocode.toString());


    }
});








    }






    @Override
    public void onConnected(Bundle bundle) {
        Log.i(LOGSERVICE, "onConnected" + bundle);

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
        Location l = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (l != null) {
            Log.i(LOGSERVICE, "lat " + l.getLatitude());
            Log.i(LOGSERVICE, "lng " + l.getLongitude());
       lati=l.getLatitude();
               longi=l.getLongitude();


        }

        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(LOGSERVICE, "onConnectionSuspended " + i);

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v( "lat " , location.getLatitude()+"");
        Log.v("lng " , location.getLongitude()+"");


        lati=location.getLatitude();
        longi=location.getLongitude();

        Geocoder g=new Geocoder(this);

        try {

            String f = g.getFromLocation(lati,longi,1).get(0).getAddressLine(0);

        for (int i = 0; i < listloc.size(); i++) {
                if (f.indexOf(listloc.get(i)) > 0) {

                    loc = listloc.get(i);
                    tf.setText(loc);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }




        }




    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(LOGSERVICE, "onConnectionFailed ");

    }

    private void initLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    }

    private void startLocationUpdate() {
        initLocationRequest();

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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    private void stopLocationUpdate() {
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();
    }



    private void showRadioButtonDialog() {

        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.radiobutton_dialog);
        dialog.setTitle("SELECT LOCATION");
        List<String> stringList=new ArrayList<>();
        for(int i=0;i<listloc.size();i++) {
            stringList.add(listloc.get(i));
        }
        RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);

        for(int i=0;i<stringList.size();i++){
            RadioButton rb=new RadioButton(this);
            rb.setText(stringList.get(i));
            rb.setTextSize(15);
            rg.addView(rb);
        }

        dialog.show();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               loc=listloc.get((checkedId-1)%10);
                tf.setText(loc);
                Log.v("sert",loc);

dialog.dismiss();
            }
        });

    }



}
