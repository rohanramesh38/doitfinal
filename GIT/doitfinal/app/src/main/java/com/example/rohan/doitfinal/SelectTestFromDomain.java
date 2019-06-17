package com.example.rohan.doitfinal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

public class SelectTestFromDomain extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {


    private TextView TvDomain;
    private RecyclerView RvSTFD;
    private List<String> Exams,summa;
    private MyRecyclerViewAdapter adapter1;
    private DatabaseReference dbref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_test_from_domain);


        Intent intent=getIntent();
        String Domain=intent.getStringExtra("Domain");




        Exams = new ArrayList<>();
        summa = new ArrayList<>();

        TvDomain=findViewById(R.id.domain);

        TvDomain.setText(Domain);
        RvSTFD=findViewById(R.id.recycleView_stfd);
        RvSTFD.setLayoutManager(new LinearLayoutManager(this));


        dbref= FirebaseDatabase.getInstance().getReference().child("DataByDomain").child(Domain);

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
                adapter1 = new MyRecyclerViewAdapter(SelectTestFromDomain.this, Exams,summa);
                adapter1.setClickListener(SelectTestFromDomain.this);
                RvSTFD.setAdapter(adapter1);
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
