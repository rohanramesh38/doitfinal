package com.example.rohan.doitfinal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PayDetails extends AppCompatActivity implements  PaymentResultListener {
    private static final String TAG = PayDetails.class.getSimpleName();

    String schoice,sdomain,sloc,smethod,snew;
    TextView tchoice,tdomain,tloc,tmethod,tnew,told;
Button bt;
    DatabaseReference databaseReference;

    @Override
    protected void onStart() {
        super.onStart();

    databaseReference= FirebaseDatabase.getInstance().getReference().child(schoice).child(sloc).child(sdomain).child(smethod);
databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
       String s[]= dataSnapshot.getValue().toString().split(",");

       told.setText(s[0]);
       tnew.setText(s[1]);
        snew=s[1];
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
});

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_details);



        tchoice=findViewById(R.id.choicep);
        tdomain=findViewById(R.id.domainp);
        tloc=findViewById(R.id.locationp);
        tmethod=findViewById(R.id.methodp);
        tnew=findViewById(R.id.newp);
        told=findViewById(R.id.oldp);
        bt=findViewById(R.id.butpay);


bt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        startPayment();
    }
});
        Intent i=getIntent();

        schoice=i.getStringExtra("choice");
        sdomain=i.getStringExtra("domain");
        sloc=i.getStringExtra("location");
        smethod=i.getStringExtra("course");


        tchoice.setText(schoice);
        tdomain.setText(sdomain);
        tloc.setText(sloc);
        tmethod.setText(smethod);
        Log.v("message",schoice+sdomain+sloc);

    }


    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

            final Activity activity = this;

            final Checkout co = new Checkout();

            try {
                JSONObject options = new JSONObject();
                options.put("name", "Doit Corp");
                options.put("description", schoice+" course "+sdomain);
                //You can omit the image option to fetch the image from dashboard
                options.put("image", "https://lh3.googleusercontent.com/CJNJ3_nBPQjsAUHUz-WJu-kXNcSrbkgN5kcR0Zb0FfECgfCDMGSs4gGhFYVNpmHbuA");
                options.put("currency", "INR");
                options.put("amount", snew+"00");

                JSONObject preFill = new JSONObject();
                preFill.put("email", "we.beyond.horizons@doit.com");
                preFill.put("contact", "1230984567");

                options.put("prefill", preFill);

                co.open(activity, options);
            } catch (Exception e) {
                Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                        .show();
                e.printStackTrace();
            }
        }

        /**
         * The name of the function has to be
         * onPaymentSuccess
         * Wrap your code in try catch, as shown, to ensure that this method runs correctly
         */
        @SuppressWarnings("unused")
        @Override
        public void onPaymentSuccess (String razorpayPaymentID){
            try {
                Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "Exception in onPaymentSuccess", e);
            }
        }

        /**
         * The name of the function has to be
         * onPaymentError
         * Wrap your code in try catch, as shown, to ensure that this method runs correctly
         */
        @SuppressWarnings("unused")
        @Override
        public void onPaymentError ( int code, String response){
            try {
                Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Log.e(TAG, "Exception in onPaymentError", e);
            }
        }

}
