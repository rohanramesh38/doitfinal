package com.example.rohan.doitfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class Register extends AppCompatActivity {
    private EditText tName, tEmail, tPassword, tConfirmPassword;
    private Button btnRegister;
    private int lo=0;
    private ListView ListUser;
    private String Id = "", Name = "", Email = "", Pwd = "", ConfirmPwd = "";
    private DatabaseReference databaseReference;


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


                if (checkpassword()) {
                    addArrayList();
                }


            }
        });
    }

    private boolean checkpassword() {

        Name = tName.getText().toString().trim();
        if (TextUtils.isEmpty(Name)) {
            tName.setError("Enter a valid Name");
            return false;
        }

        Email = tEmail.getText().toString().trim();
        if (TextUtils.isEmpty(Email)) {
            tEmail.setError("Enter a valid Email");
            return false;
        }

        Pwd = tPassword.getText().toString().trim();
        if (TextUtils.isEmpty(Pwd) && Pwd.length() < 4) {
            tPassword.setError("Enter a valid Password");
            return false;
        }

        ConfirmPwd = tConfirmPassword.getText().toString().trim();
        if (TextUtils.isEmpty(ConfirmPwd) && ConfirmPwd.length() < 4) {
            tConfirmPassword.setError("Confirm your Password");
            return false;
        }

        if (!Pwd.equals(ConfirmPwd)) {
            tPassword.setError("Password didnt Match");
            tConfirmPassword.setError("Password didnt Match");
            return false;
        }


        Id = Email.replace(".", "_");
        return true;
    }

    private void addArrayList() {


        HashMap params=new HashMap();
        params.put("Email",Email);
        params.put("Name",Name);
        params.put("Phone","1234567890");
        params.put("Dept","Dept");
        params.put("DOB","Dob");
        try {
          params.put("password",Security.encrypt(Pwd));
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.put("Photo","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUdFBZbl_qSONKU9QF_c_hIIpEJON0YLUnbLWQy78kLfa_rwZs_g");
        params.put("Address","Address Not Found");
      databaseReference.child(Id).setValue(params);
        Toast.makeText(this, "Student is added", Toast.LENGTH_LONG).show();

        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doit.PRIVATEDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("id", Id);
        editor.putString("name", Name);
        editor.putString("address", "Address Not Found");
        editor.putString("phonenumber", "1234567890");
        editor.putString("email",Email);
        editor.putString("dob", "00-00-0000");
        editor.putString("dept", "Unknown");
        editor.putString("photo","https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTUdFBZbl_qSONKU9QF_c_hIIpEJON0YLUnbLWQy78kLfa_rwZs_g");
        editor.apply();

        Log.v("REG",""+Email+Name+Id);






            Cleartxt();

            Intent intphto = new Intent(Register.this, otp.class);
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



