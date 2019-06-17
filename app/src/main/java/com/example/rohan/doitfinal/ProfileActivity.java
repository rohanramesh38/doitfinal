package com.example.rohan.doitfinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_PHOTO_FOR_AVATAR = 10;
    int lo = 0, m = 0;

   private DatabaseReference databaseReference,dbrefim;
    private EditText Etname, Etphone, Etdob, Etadd, Etdept;
    private TextView Temail;
    private String Name="",Phone="",Dept="",DOB="",Email="",Photo="",Id="",Address="",simg="";
   private Button btsave;
    private Uri mImageUri;
    CircleImageView imgprofile;
    Uri imaguri;
    public static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Temail = findViewById(R.id.mail);
        Etname = findViewById(R.id.name);
        Etphone = findViewById(R.id.phone);
        Etdob = findViewById(R.id.dob);
        Etadd = findViewById(R.id.home);
        Etdept = findViewById(R.id.depts);
        imgprofile = findViewById(R.id.profilepic);


        if(fetchdata())
        {
            Temail.setText(Email);
            Etphone.setText(Phone);
            Etadd.setText(Address);
            Etdob.setText(DOB);
            Etdept.setText(Dept);
            Etname.setText(Name);
            Log.v("firbserdoit",Photo);
            Picasso.get()
                    .load(Photo)
                    .placeholder(R.drawable.imageforpicasso)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imgprofile);


        }


        imgprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  dispatchTakePictureIntent();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });



        btsave = findViewById(R.id.butsave);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(Id);


        btsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(detailsnotempty())
                setdetails( Name,Phone,Address,DOB,Dept,Photo);

            }
        });

    }

    private boolean detailsnotempty() {



       Phone=Etphone.getText().toString().trim();
       if(TextUtils.isEmpty(Phone) && Phone.length()==10){
           Etphone.setError("enter your mobile number");
           return false;
       }

       Address=  Etadd.getText().toString().trim();
        if(TextUtils.isEmpty(Address)){
            Etadd.setError("enter your Address");
            return false;
        }

       DOB= Etdob.getText().toString().trim();
        if(TextUtils.isEmpty(DOB)){
            Etdob.setError("enter your mobile Date of birth");
            return false;
        }

       Dept= Etdept.getText().toString().trim();
        if(TextUtils.isEmpty(Dept)){
            Etdept.setError("enter your Dept");
            return false;
        }

       Name= Etname.getText().toString().trim();
        if(TextUtils.isEmpty(Name)){
            Etname.setError("enter your Name");
            return false;
        }




        return true;
    }

    private boolean fetchdata() {


        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doit.PRIVATEDATA", Context.MODE_PRIVATE);
        if(sp.contains("id")) {


            Id=sp.getString("id","");
            Name=sp.getString("name","");
            Phone=sp.getString("phonenumber","");
            Email=sp.getString("email","");
            Address=sp.getString("address","");

            String s = Email.replace("@", "%40");
            s = s.replace(".", "_");
            Photo = "https://firebasestorage.googleapis.com/v0/b/doitfinal.appspot.com/o/maggie.jpeg?alt=media&token=7ad2a292-7c75-4bc9-9e84-811b58e10b5e";
            Photo = Photo.replace("maggie", s).trim();
            Photo=sp.getString("photo",Photo);
            DOB=sp.getString("dob","");
            Dept=sp.getString("dept","");


        }







        return true;}


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imaguri = data.getData();


            Picasso.get()
                    .load(imaguri)
                    .placeholder(R.drawable.imageforpicasso)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imgprofile);


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imaguri);
                ImageView imageView = (ImageView) findViewById(R.id.profilepic);
                imageView.setImageBitmap(bitmap);

                StorageReference mountainsRef = FirebaseStorage.getInstance().getReference(Id+ ".jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataa = baos.toByteArray();

                String s = Email.replace("@", "%40");
                s = s.replace(".", "_");
                Photo = "https://firebasestorage.googleapis.com/v0/b/doitfinal.appspot.com/o/maggie.jpg?alt=media&token=9b6a9e60-db88-42d5-a09e-32954bac2614";
                Photo = Photo.replace("maggie", s).trim();


                /******************/
                UploadTask uploadTask = mountainsRef.putBytes(dataa);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        databaseReference.child("Photo").setValue(Photo);
                        Picasso.get()
                                .load(Photo)
                                .placeholder(R.drawable.imageforpicasso)
                                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                                .into(imgprofile);
                        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doit.PRIVATEDATA", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("photo",Photo);
                        editor.apply();

                    }
                });



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void
    setdetails( String name, String phone, String address, String dob,String dept, String photo){


        databaseReference.child("Photo").setValue(photo);
        databaseReference.child("Address").setValue(address);
        databaseReference.child("Phone").setValue(phone);
        databaseReference.child("DOB").setValue(dob);
        databaseReference.child("Dept").setValue(dept);
        databaseReference.child("Name").setValue(name);

        SharedPreferences sp = getApplicationContext().getSharedPreferences("com.doit.PRIVATEDATA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name", name);
        editor.putString("address", address);
        editor.putString("phonenumber", phone);
        editor.putString("dob", dob);
        editor.putString("dept", dept);
        editor.putString("photo",photo);
        editor.apply();

        finish();
        startActivity(new Intent(ProfileActivity.this,WorkActivity.class));


        Toast.makeText(this, "you data is saved", Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onBackPressed() {
        finish();
     startActivity(new Intent(this,WorkActivity.class));
    }





}