package com.example.fosmad;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BottomNavFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomNavFragment extends Fragment {

    Button homenav;
    BottomAppBar bappbar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BottomNavFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BottomNavFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BottomNavFragment newInstance(String param1, String param2) {
        BottomNavFragment fragment = new BottomNavFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }







    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bottom_nav, container, false);
        BottomNavigationView btab=(BottomNavigationView) view.findViewById(R.id.bottomnavview);
        btab.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case R.id.home:
                        Intent i = new Intent(getContext(), home.class);
                        startActivity(i);
                        break;

                    case R.id.deals:
                        Intent i1 = new Intent(getContext(),OffersActivity.class);
                        startActivity(i1);
                        break;
                }
                return true;

            }
        });


//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch(item.getItemId()){
//                    case R.id.home:
//                        System.out.println("Helllo");
//                        break;
//
//                    case R.id.deals:
//                        System.out.println("HIDFFD");
//                        break;
//                }
//                return true;
//
//
//            }
//
//        });

//        btab.setNavigationOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                switch (view.getId()){
//                    case R.id.home:
//                        System.out.println("SDfsfd");
//                        break;
//                }
//            }
//        });

        return view;
    }




}