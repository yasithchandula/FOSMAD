package com.example.fosmad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class itemlist extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    itmadapter itmadapter;
    ArrayList<item> list;

}
