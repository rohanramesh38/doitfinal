package com.example.rohan.doitfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class WorkActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    String link="oiuy";
    int lo=0;
    DatabaseReference databaseReference;

    String sname, spass, smail, sdob, sadd, sdept,sademail;
    CircularImageView nav_img;
    TextView navser,navdept;

    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
       smail = settings.getString("mymail", "defaultvalue");
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Students");
        FirebaseDatabase.getInstance().getReference().child("Email").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                smail=dataSnapshot.getValue().toString().replace(".","_").trim();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        onstart();
        onstart();

    }
    FragmentPagerAdapter adapterViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SharedPreferences settings = getSharedPreferences("mysettings", Context.MODE_PRIVATE);
        smail = settings.getString("mymail", "defaultvalue");
onstart();
onstart();

        viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {

            @Override
            public void transformPage(View view, float position) {
                if (position <= -1.0F) {
                    view.setAlpha(0);
                } else if (position < 0F) {
                    view.setAlpha(1);
                    view.setTranslationX((int) ((float) (view.getWidth()) * -position));
                } else if (position >= 0F) {
                    view.setAlpha(1);
                } else if (position > 1.0F) {
                    view.setAlpha(0);
                }
            }
        });
        viewPager.setAdapter(viewPagerAdapter);




        //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        nav_img=hView.findViewById(R.id.profchange);
        navser = (TextView)hView.findViewById(R.id.nav_name);
        navdept   = (TextView)hView.findViewById(R.id.navdept);

        Log.v("imagelink",link+"");
             //




        databaseReference.child(smail.replace(".","_")).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Log.v("val", dataSnapshot1.getValue().toString());


                    if (lo == 0) {
                        sadd=(dataSnapshot1.getValue().toString());
                    } else if (lo == 1) {
                        sdob=(dataSnapshot1.getValue().toString());
                    } else if (lo == 2) {
                        sdept=(dataSnapshot1.getValue().toString());
                    } else if (lo == 3) {
                        smail=(dataSnapshot1.getValue().toString().replace(".","_"));
                    } else if (lo == 4) {
                        sname=(dataSnapshot1.getValue().toString());
                    } else if (lo == 5) {
                        spass=(dataSnapshot1.getValue().toString());
                    }


                    lo++;
                }
                navser.setText(sname);
                navdept.setText(sdept);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





    }

    private void onstart() {



        databaseReference=FirebaseDatabase.getInstance().getReference().child("Students");
        FirebaseDatabase.getInstance().getReference().child("Email").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                smail=dataSnapshot.getValue().toString().replace(".","_").trim();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



            databaseReference.child(smail.replace(".","_")).child("profilepic").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if(link!=null) {
                        link = dataSnapshot.getValue().toString().trim();
                       // Picasso.get().load(link).into(nav_img);

                        Picasso.get()
                                .load(link)
                                .placeholder(R.drawable.blankpo)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .into(nav_img);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });





    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

            Intent i=new Intent(this,ProfileActivity.class);
            i.putExtra("mailid",smail);
            finish();
            startActivity(i);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this,MainActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    public static class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private static final int NUM_ITEMS = 2;

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return FirstFragment.newInstance();
            }
            else {
                return SecondFragment.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {

            if(position == 0){
                return FirstFragment.PAGE_TITLE;
            }
            else {
                return SecondFragment.PAGE_TITLE;
            }
        }



    }


}



