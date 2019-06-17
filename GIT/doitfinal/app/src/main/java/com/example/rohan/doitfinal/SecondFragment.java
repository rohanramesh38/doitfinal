package com.example.rohan.doitfinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SecondFragment extends Fragment {
Button bt;
    public static final String PAGE_TITLE = "Problems";
    public SecondFragment() {
        // Required empty public constructor
    }

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =inflater.inflate(R.layout.fragment_second, container, false);
   bt=view.findViewById(R.id.cert);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getContext(),SelectDomActivity.class);

                i.putExtra("choice","Certification");
                startActivity(i);



            }
        });


   return view;
    }
}