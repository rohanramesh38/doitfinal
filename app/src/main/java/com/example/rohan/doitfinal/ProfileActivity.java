package com.example.rohan.doitfinal;

import android.content.Intent;
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

public class ProfileActivity extends AppCompatActivity {
    private static final int PICK_PHOTO_FOR_AVATAR = 10;
    int lo = 0, m = 0;

    DatabaseReference databaseReference,dbrefim;
    EditText tname, tpass, temail, tdob, tadd, tdept;
    String sname, spass, smail, sdob, sadd, sdept, simg;
    Button bt;
    private Uri mImageUri;
    ImageView imgprofile;
    Uri imaguri;
    public static final int PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        smail=getIntent().getStringExtra("mailid");

        temail = findViewById(R.id.maill);
        tname = findViewById(R.id.ususr);
        tpass = findViewById(R.id.pswrdd);
        tdob = findViewById(R.id.dob);
        tadd = findViewById(R.id.home);
        tdept = findViewById(R.id.depts);
        imgprofile = findViewById(R.id.profilepic);

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



        bt = findViewById(R.id.butsave);
       // smail = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        temail.setText(smail);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(smail.replace(".", "_"));
        dbrefim=FirebaseDatabase.getInstance().getReference().child("Students").child(smail.replace(".", "_"));

        String s = smail.replace("@", "%40");
        s = s.replace(".", "_");
        simg = "https://firebasestorage.googleapis.com/v0/b/doitfinal.appspot.com/o/maggie.jpeg?alt=media&token=7ad2a292-7c75-4bc9-9e84-811b58e10b5e";
        simg = simg.replace("maggie", s).trim();

        // dbrefim.child("profilepic").setValue(" ");
        databaseReference.child("profilepic").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              //  Picasso.get().load(dataSnapshot.getValue().toString()).into(imgprofile);

                Picasso.get()
                        .load(dataSnapshot.getValue().toString().trim())
                        .placeholder(R.drawable.blankpo)
                        .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                        .into(imgprofile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setdetails();

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
      //  smail = FirebaseAuth.getInstance().getCurrentUser().getEmail();

      smail=getIntent().getStringExtra("mailid");
        smail=getIntent().getStringExtra("mailid");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(smail.replace(".", "_"));
        lo = 0;



//Picasso.get().load(simg).into(imgprofile);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    Log.v("val", dataSnapshot1.getValue().toString());


                    if (lo == 0) {
                        tadd.setText(dataSnapshot1.getValue().toString());
                    } else if (lo == 1) {
                        tdob.setText(dataSnapshot1.getValue().toString());
                    } else if (lo == 2) {
                        tdept.setText(dataSnapshot1.getValue().toString());
                    } else if (lo == 3) {
                        temail.setText(dataSnapshot1.getValue().toString());
                    } else if (lo == 4) {
                        tname.setText(dataSnapshot1.getValue().toString());
                    } else if (lo == 5) {
                        tpass.setText(dataSnapshot1.getValue().toString());
                    }


                    lo++;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null) {

            imaguri = data.getData();


            Picasso.get()
                    .load(imaguri)
                    .placeholder(R.drawable.blankpo)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(imgprofile);


            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imaguri);
                ImageView imageView = (ImageView) findViewById(R.id.profilepic);
                imageView.setImageBitmap(bitmap);

                StorageReference mountainsRef = FirebaseStorage.getInstance().getReference(smail.replace(".", "_") + ".jpg");
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] dataa = baos.toByteArray();

                String s = smail.replace("@", "%40");
                s = s.replace(".", "_");
                simg = "https://firebasestorage.googleapis.com/v0/b/doitfinal.appspot.com/o/maggie.jpg?alt=media&token=9b6a9e60-db88-42d5-a09e-32954bac2614";
                simg = simg.replace("maggie", s).trim();

                databaseReference.child("profilepic").setValue(" ");
                databaseReference.child("profilepic").setValue(simg);


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
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                        // ...
                    }
                });


//                UploadTask uploadTask = mountainsRef.putBytes(dataa);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void setdetails() {

        sname = tname.getText().toString().trim();
        smail = temail.getText().toString().trim();
        spass = tpass.getText().toString().trim();
        sdept = tdept.getText().toString().trim();
        sadd = tadd.getText().toString().trim();
        sdob = tdob.getText().toString().trim();
        Log.v("logd", sname + smail + sdob + sdept + sadd + spass);
        if ((!TextUtils.isEmpty(sname) && !TextUtils.isEmpty(smail) && !TextUtils.isEmpty(spass) && !TextUtils.isEmpty(sdept) && !TextUtils.isEmpty(sadd) && !TextUtils.isEmpty(sdob))) {
            Log.v("logd--", sname + smail + sdob + sdept + sadd + spass);
            databaseReference.child("Email").setValue(smail);
            databaseReference.child("Name").setValue(sname);
            databaseReference.child("Phone").setValue(spass);
            databaseReference.child("DOB").setValue(sdob);
            databaseReference.child("Dept").setValue(sdept);
            databaseReference.child("Address").setValue(sadd);


        }
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