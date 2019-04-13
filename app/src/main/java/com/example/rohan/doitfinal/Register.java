package com.example.rohan.doitfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Register extends AppCompatActivity {
    EditText tName,tEmail,tPassword,tConfirmPassword;
    Button btnRegister;
    ListView ListUser;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
     //   result = (TextView) findViewById(R.id.textView2);
        tName = (EditText) findViewById(R.id.tName);
        tEmail = (EditText) findViewById(R.id.tEmail);
        tPassword = (EditText) findViewById(R.id.tPassword);
        tConfirmPassword = (EditText) findViewById(R.id.tConfirmPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        databaseReference = FirebaseDatabase.getInstance().getReference("Students");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addArrayList();



            }
        });
    }

    private void  addArrayList()
    {
        String email=tEmail.getText().toString().trim();
        String name =tName.getText().toString().trim();
        String password =tPassword.getText().toString().trim();
        String comfirmpassword =tConfirmPassword.getText().toString().trim();
       // String resulthash = result.getText().toString().trim();

        if (TextUtils.isEmpty(name)){
            tName.setError("Please enter your Name!");
        }else if(TextUtils.isEmpty(email)){
            tEmail.setError("Please enter your Email!");
        }else if(TextUtils.isEmpty(password)){
            tPassword.setError("Please enter your Password!");
        }else if(!password.equals(comfirmpassword)){
            tConfirmPassword.setError("Please put the same password");
        }else{

            //String id=  databaseReference.push().getKey();
          //  Students students= new Students(name,email);
            databaseReference.child((email.toString()).replace(".","_"));
            databaseReference.child((email.toString()).replace(".","_")).child("name").setValue(name.toString());
            try {
                databaseReference.child((email.toString()).replace(".","_")).child("password").setValue(Security.encrypt(password));
            } catch (Exception e) {
                e.printStackTrace();
            }
            databaseReference.child(email.replace(".","_")).child("Email").setValue(email);
            databaseReference.child(email.replace(".","_")).child("Name").setValue(name.toString());
            databaseReference.child(email.replace(".","_")).child("Phone").setValue("1234567890");
            databaseReference.child(email.replace(".","_")).child("Address").setValue("Address line");
            databaseReference.child(email.replace(".","_")).child("Dept").setValue("Dept");
            databaseReference.child(email.replace(".","_")).child("DOB").setValue("00/00/00");
            databaseReference.child(email.replace(".","_")).child("profilepic").setValue("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTcKBczScfOaO4p6LmN9qok-dAZSLpZ2PJdj1VZ71h2onzIoJt-");


            databaseReference.child((email.toString()).replace(".","_")).child("photo").setValue("anonymous");
            Toast.makeText(this,"Student is added",Toast.LENGTH_LONG).show();
            Cleartxt();

        }
        SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("mymail", email);
        editor.commit();


        finish();
        Intent intphto = new Intent(getApplicationContext(), otp.class);
        intphto.putExtra("mailid",email);
        startActivity(intphto);

    }

    private void Cleartxt(){
        tEmail.setText("");
        tName.setText("");
        tPassword.setText("");
        tConfirmPassword.setText("");
       // result.setText("");

    }
    public void computeMD5Hash(String password) {

        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(password.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuffer MD5Hash = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                MD5Hash.append(h);
            }

           // result.setText(MD5Hash);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}



