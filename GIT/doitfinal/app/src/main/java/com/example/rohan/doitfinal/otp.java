package com.example.rohan.doitfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class otp extends AppCompatActivity {
    private Spinner spinner;
    private EditText editText;

    private String Id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        spinner = findViewById(R.id.spinnerCountries);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, CountryData.countryNames));

        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doit.PRIVATEDATA", Context.MODE_PRIVATE);
        if(sp.contains("id")) {
            Id=sp.getString("id","");
        }
        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
        int spinnerPosition = myAdap.getPosition("India");
        spinner.setSelection(spinnerPosition);

        editText = findViewById(R.id.editTextPhone);


        findViewById(R.id.buttonContinue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = CountryData.countryAreaCodes[spinner.getSelectedItemPosition()];

                String number = editText.getText().toString().trim();

                if (number.isEmpty() || number.length() < 10) {
                    editText.setError("Valid number is required");
                    editText.requestFocus();
                    return;
                }

                String phoneNumber = "+" + code + number;

               Intent intent = new Intent(otp.this, verification.class);
                intent.putExtra("phonenumber", phoneNumber);
                FirebaseDatabase.getInstance().getReference("Students").child(Id).child("Phone").setValue(number);
              //  FirebaseDatabase.getInstance().getReference().child(Id)
                SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doit.PRIVATEDATA", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("phonenumber",number);
                editor.apply();
                FirebaseDatabase.getInstance().getReference("Students").child(Id).child("Phone").setValue(number);
                startActivity(intent);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
           Intent intent = new Intent(this, WorkActivity.class);
            finish();
           startActivity(intent);
        }
    }
}


