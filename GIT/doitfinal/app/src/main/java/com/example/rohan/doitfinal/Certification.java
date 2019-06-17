package com.example.rohan.doitfinal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;
import android.support.v7.widget.SearchView.*;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Certification extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener, SearchView.OnQueryTextListener {


    private List<String> newlistexam;
    private MyRecyclerViewAdapter adapter;
   private SearchView searchView;
   private int check=0;
   private ArrayList<String> animalNames,  animalName,alldatalistexam,alldatalistclient;
   private DatabaseReference listval,lisrallval;
  private RecyclerView recyclerView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_search,menu);

        MenuItem menuItem=menu.findItem(R.id.action_search);
        SearchView searchView= (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certification);
        listval=FirebaseDatabase.getInstance().getReference().child("Certification").child("CertListData");
        lisrallval=FirebaseDatabase.getInstance().getReference().child("AllDataTrain");



        animalNames = new ArrayList<>();

        alldatalistexam = new ArrayList<>();
        alldatalistclient = new ArrayList<>();
        animalName = new ArrayList<>();

       recyclerView = findViewById(R.id.recycleviewallcert);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listval.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren())
                {
                    animalNames.add(d.getValue().toString());
                    animalName.add(" ");

                }
                adapter = new MyRecyclerViewAdapter(Certification.this, animalNames,animalName);
                adapter.setClickListener(Certification.this);
                recyclerView.setAdapter(adapter);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

           lisrallval.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot d:dataSnapshot.getChildren())
                {
                    alldatalistexam.add(d.getValue().toString());
                    alldatalistclient.add(d.getKey().substring(0,d.getKey().indexOf("_")));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        // ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.listview,mobileArray);

       // ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.mylist, mobileArray);
        //ListView listView = (ListView) findViewById(R.id.listView);

       // listView.setAdapter(adapter);





    }

    @Override
    public void onItemClick(View view, int position) {


        if(check==0){

            Intent i=new Intent(this,SelectTestFromClient.class);
            i.putExtra("Client",animalNames.get(position));
            startActivity(i);
            Toast.makeText(this, animalNames.get(position), Toast.LENGTH_SHORT).show();

        }
        else{
            Toast.makeText(this, newlistexam.get(position), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        String usertext =newText.toLowerCase();

       newlistexam = new ArrayList<>();
        List<String> newlistclient = new ArrayList<>();

        if(TextUtils.isEmpty(usertext)) {
            check=0;
            for (String x : animalNames) {
                if (x.toLowerCase().contains(usertext)) {
                    newlistexam.add(x);
                    newlistclient.add(" ");

                }
            }
            adapter.UpdateItemsList(newlistexam, newlistclient);
        }
        else {
            check=1;
                for (String x : alldatalistexam) {
                    if (x.toLowerCase().contains(usertext)) {
                        newlistexam.add(x);
                        newlistclient.add(alldatalistclient.get(alldatalistexam.indexOf(x)));

                    }
                }
                adapter.UpdateItemsList(newlistexam, newlistclient);
            }



        return true;
    }

}