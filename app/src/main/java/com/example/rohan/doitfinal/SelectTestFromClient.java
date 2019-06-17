package com.example.rohan.doitfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectTestFromClient extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {



    private TextView TvClient;
    private RecyclerView    RvSTFC;
    private List<String> Exams,summa;
    private MyRecyclerViewAdapter adapter1;
    private DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test_from_client);



        Intent intent=getIntent();
        String Client=intent.getStringExtra("Client");


        Exams = new ArrayList<>();
        summa = new ArrayList<>();

        TvClient=findViewById(R.id.client);

        TvClient.setText(Client);
        RvSTFC=findViewById(R.id.recycleView_stfc);
        RvSTFC.setLayoutManager(new LinearLayoutManager(this));


        dbref= FirebaseDatabase.getInstance().getReference().child("Data").child(Client);

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
Exams.clear();
                for (DataSnapshot d:dataSnapshot.getChildren())
                {
                  Exams.add(d.getValue().toString());
                  summa.add(" ");
                }

                Log.v("doit",Exams.toString());
                adapter1 = new MyRecyclerViewAdapter(SelectTestFromClient.this, Exams,summa);
                adapter1.setClickListener(SelectTestFromClient.this);
                RvSTFC.setAdapter(adapter1);
                adapter1.UpdateItemsList(Exams,summa);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    @Override
    public void onItemClick(View view, int position) {

        Toast.makeText(this, Exams.get(position), Toast.LENGTH_SHORT).show();

    }

}
