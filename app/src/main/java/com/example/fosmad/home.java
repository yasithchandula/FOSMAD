package com.example.fosmad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.SearchView;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    itmadapter itmadapter;
    ArrayList<item> list;
    Button cat_tea,cat_coffee,cat_juice,cat_smoothie,homenav;
    private String selectedfilter;

    BottomNavigationView bottomNavigationView;
    SearchView searchView;
//    BottomNavigationView bmappbar;


//    @Override
////    protected void onResume() {
////        super.onResume();
////        BottomNavigationView bt=(BottomNavigationView) findViewById(R.id.bottomnavview);
////        bt.setSelectedItemId(R.id.deals);
////    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.itemlist);
        database = FirebaseDatabase.getInstance().getReference("item");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();


        bottomNavigationView = findViewById(R.id.bottomnavview);
        bottomNavigationView.setBackground(null);

        cat_tea= findViewById(R.id.btn_cat_tea);
        cat_coffee=findViewById(R.id.btn_cat_coffee);
        cat_juice=findViewById(R.id.btn_cat_juice);
        cat_smoothie=findViewById(R.id.btn_cat_smoothie);

//        bmappbar=findViewById(R.id.bottomnav);


//        bmappbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
//        {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                System.out.println("Home Come");
//                switch (item.getItemId()){
//                    case R.id.home:
//                        System.out.println("Home clicked");
//                        break;
//                }
//                return true;
//            }
//        });
//
//        bmappbar.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                System.out.println("Home Come");
//                switch (item.getItemId()) {
//                    case R.id.home:
//                        System.out.println("Home clicked");
//                        break;
//                }
//                return true;
//            }
//        });


//        homenav=findViewById(R.id.home);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    item item = dataSnapshot.getValue(item.class);
                    list.add(item);
                }
                itmadapter = new itmadapter(home.this, list);
                recyclerView.setAdapter(itmadapter);
                itmadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });

        searchView = (SearchView) findViewById(R.id.pt_searchbar);
        searchView.setQueryHint("Search Tea");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
               itmadapter.getFilter().filter(s);
               return false;
            }
        });


        //Listn to clicks of home category buttons

        cat_tea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterlist("tea");
            }
        });

        cat_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterlist("coffee");
            }
        });

        cat_juice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterlist("juice");
            }
        });

        cat_smoothie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterlist("smoothie");
            }
        });



//        homenav.findViewById(R.id.bottomnavview);
//        homenav.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("HIIIIIIIII");
//            }
//        });
////
////        bottomNavigationView.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                System.out.println("HELLOOOOOOOO");
////            }
////        });




















    }






    public void filterlist(String status){
        selectedfilter=status;
        ArrayList<item> list1=new ArrayList<item>();

        for (item item:list){
            if (item.getCategory().toLowerCase().contains(status)){
                list1.add(item);
            }
        }
        itmadapter adapter = new itmadapter(getApplicationContext(),list1);
        System.out.println(list1);
        recyclerView.setAdapter(adapter);


    }

    public void teatapped(View view){
        filterlist("tea");

    }

    public void homeui(View view) {
        Intent intent = new Intent(this,home.class  );
        startActivity(intent);
    }










}
