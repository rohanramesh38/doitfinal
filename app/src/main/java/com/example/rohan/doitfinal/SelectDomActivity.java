package com.example.rohan.doitfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectDomActivity extends AppCompatActivity {
    DatabaseReference databaseReference;

    ArrayList<profile> list1;
    RecyclerView recyclerView;
    @Override
    protected void onStart() {
        super.onStart();

    databaseReference= FirebaseDatabase.getInstance().getReference().child("listdata");

 databaseReference.addValueEventListener(new ValueEventListener() {
     @Override
     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
list1.clear();

         for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
             profile p = new profile( dataSnapshot1.getValue().toString().trim(),dataSnapshot1.getKey());
             list1.add(p);
         }


     }

     @Override
     public void onCancelled(@NonNull DatabaseError databaseError) {

     }
 });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dom);

        recyclerView = (RecyclerView) findViewById(R.id.recycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        list1 = new ArrayList<profile>();


        recyclerView.addOnItemTouchListener(new MyAdapter(SelectDomActivity.this, recyclerView, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Toast.makeText(SelectActivity.this,"item "+(position+1),Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SelectDomActivity.this, SelectLocation.class);
                intent.putExtra("domain", list1.get(position).getDomain());
                intent.putExtra("choice",getIntent().getStringExtra("choice"));
                startActivity(intent);


            }

            @Override
            public void onItemLongClick(View view, int position) {
                // ...
            }
        }));




        databaseReference= FirebaseDatabase.getInstance().getReference().child("listdata");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            list1.clear();
            for(DataSnapshot dataSnapshot1:dataSnapshot.getChildren()) {
                profile p = new profile( dataSnapshot1.getValue().toString().trim(),dataSnapshot1.getKey());
                list1.add(p);
            }


               MyAdapter adapter = new MyAdapter(SelectDomActivity.this, list1);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
