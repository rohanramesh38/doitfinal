package com.example.rohan.doitfinal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectOFWRActivity extends AppCompatActivity {
Button bt1,bt2,bt3,bt4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_ofwr);

        bt1=findViewById(R.id.online);
        bt2=findViewById(R.id.reglar);
        bt3=findViewById(R.id.week);
        bt4=findViewById(R.id.fast);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(SelectOFWRActivity.this,PayDetails.class);
                i.putExtra("choice",getIntent().getStringExtra("choice"));
                i.putExtra("domain",getIntent().getStringExtra("domain"));
                i.putExtra("location",getIntent().getStringExtra("location"));
                i.putExtra("course","Online");

                startActivity(i);

            }
        });


        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(SelectOFWRActivity.this,PayDetails.class);
                i.putExtra("choice",getIntent().getStringExtra("choice"));
                i.putExtra("domain",getIntent().getStringExtra("domain"));
                i.putExtra("location",getIntent().getStringExtra("location"));
                i.putExtra("course","Regular");

                startActivity(i);

            }
        });


        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(SelectOFWRActivity.this,PayDetails.class);
                i.putExtra("choice",getIntent().getStringExtra("choice"));
                i.putExtra("domain",getIntent().getStringExtra("domain"));
                i.putExtra("location",getIntent().getStringExtra("location"));
                i.putExtra("course","Week End");
                startActivity(i);

            }
        });

        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(SelectOFWRActivity.this,PayDetails.class);
                i.putExtra("choice",getIntent().getStringExtra("choice"));
                i.putExtra("domain",getIntent().getStringExtra("domain"));
                i.putExtra("location",getIntent().getStringExtra("location"));
                i.putExtra("course","Fast Track");

                startActivity(i);

            }
        });
    }
}
