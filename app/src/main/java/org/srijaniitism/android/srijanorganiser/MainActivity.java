package org.srijaniitism.android.srijanorganiser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.PreferenceActivity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;

import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;


public class MainActivity extends AppCompatActivity {
    static String s;
    private DatabaseReference mDatabase;
    FloatingActionButton fab1,fab2;

    static int i = 0;

    String eve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (i == 1) {
            i = 0;



            recreate();
        }


        final ArrayList<events> names = new ArrayList<>();
        final Adapter itemsAdapter = new Adapter(this, names);

        mDatabase = FirebaseDatabase.getInstance().getReference("Event/");

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();


                for (String l : td.keySet()) {
                    // Toast.makeText(MainActivity.this, l, Toast.LENGTH_SHORT).show();
                    names.add(new events(l));
                }
                itemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        final ListView listView = findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setTextFilterEnabled(true);


        // Set an item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                s = ((TextView) view).getText().toString().trim();
                Intent intnt = new Intent(view.getContext(), Main2Activity.class);
                startActivity(intnt);


            }


        });


        fab1 =  findViewById(R.id.fab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intnt = new Intent(MainActivity.this, Main3Activity.class);
                startActivity(intnt);

                //   Toast.makeText(getBaseContext(), "FAB Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        listView.setAdapter(itemsAdapter);


    }




}