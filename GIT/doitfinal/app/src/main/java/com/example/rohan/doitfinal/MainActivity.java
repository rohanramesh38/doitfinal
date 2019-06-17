package com.example.rohan.doitfinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99 ;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION_COARSE = 101;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 123;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE_READ =143 ;
  private EditText tEmail,tPassword;
   private TextView btnRegister;
   private Button btnLogin,btnOption;
    private DatabaseReference databaseReference,db1;
    private int lo=0;
    private String Id="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tEmail=(EditText) findViewById(R.id.username);
        tPassword=(EditText) findViewById(R.id.password);
        btnLogin=(Button) findViewById(R.id.loginbutton);
        btnRegister= findViewById(R.id.newUser);
        PermissionsCheck();


        // btnOption=(Button)findViewById(R.id.button);
        databaseReference= FirebaseDatabase.getInstance().getReference("Students");

        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
        {
            finish();
            startActivity(new Intent(MainActivity.this,WorkActivity.class));
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd= null;
                try {
                    pwd = tPassword.getText().toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                logIn(tEmail.getText().toString().trim(),pwd);
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intphto =new Intent(getApplicationContext(),Register.class);
                startActivity(intphto);
            }
        });

    }
    private void logIn(final String memail,final String mpassword) {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(memail.replace(".", "_")).exists()) {

                    try {
                        Log.v("myapp",Security.decrypt((dataSnapshot.child(memail.replace(".","_")).child("password")).getValue().toString()));
                        Log.v("myappin",mpassword);
                        if(Security.decrypt((dataSnapshot.child(memail.replace(".","_")).child("password")).getValue().toString()).equals(mpassword))
                        {


                            SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doit.PRIVATEDATA", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("id", memail.replace(".","_"));
                            editor.putString("email",memail);
                            editor.apply();


                                Toast.makeText(MainActivity.this,"Proceed Login",Toast.LENGTH_LONG).show();
                                Intent intphto =new Intent(getApplicationContext(),otp.class);
                                finish();

                                startActivity(intphto);



                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }



                }else {
                    Toast.makeText(MainActivity.this,"User is not register",Toast.LENGTH_LONG).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void PermissionsCheck() {


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            }
            else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
            }



            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                }
                else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
                }
            }


            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                }
                else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE_READ);
                }
            }





        }






    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    PermissionsCheck();
                }
                return;
            }


            case MY_PERMISSIONS_REQUEST_STORAGE: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    PermissionsCheck();
                }
                return;
            }

            case MY_PERMISSIONS_REQUEST_STORAGE_READ: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    PermissionsCheck();
                }
                return;
            }



        }
    }



}
