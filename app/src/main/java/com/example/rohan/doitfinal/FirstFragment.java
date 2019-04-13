package com.example.rohan.doitfinal;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */public class FirstFragment extends Fragment {
Button bt;
    public static final String PAGE_TITLE = "Problems";
    public FirstFragment() {
        // Required empty public constructor
    }

    public static FirstFragment newInstance() {
        FirstFragment fragment = new FirstFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view =inflater.inflate(R.layout.fragment_first, container, false);

       bt=(Button)view.findViewById(R.id.train);

       bt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

              Intent i=new Intent(getContext(),SelectDomActivity.class);

              i.putExtra("choice","Training");
              startActivity(i);



           }
       });

    return view;
    }
}