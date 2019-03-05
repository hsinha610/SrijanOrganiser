package org.srijaniitism.android.srijanorganiser;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Main2Activity extends AppCompatActivity {
    final MainActivity ma = new MainActivity();
    Calendar myCalendar;
    private DatabaseReference mDatabase;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        fab =  findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
                //   Toast.makeText(getBaseContext(), "FAB Clicked", Toast.LENGTH_SHORT).show();
            }
        });


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //prevent keyboard from automatically opening for edit text


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        setPickTime();

        setPickDate();


        final MainActivity ma = new MainActivity();
        setTitle(ma.s);

        read();


    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.read:
                read();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @TargetApi(24)
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());
        EditText edittext =  findViewById(R.id.editDate);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @TargetApi(24)
    public void setPickDate() {
        myCalendar = Calendar.getInstance();

        EditText edittext =  findViewById(R.id.editDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @TargetApi(24)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {
            @TargetApi(24)
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Main2Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void setPickTime() {
        final EditText chooseTime = findViewById(R.id.editTime);
        chooseTime.setShowSoftInputOnFocus(false);
        chooseTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                TimePickerDialog timePickerDialog = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(minutes==0)
                        {if(hourOfDay<12)
                        {chooseTime.setText(hourOfDay + ":" + "00"+" a.m");}
                        else if(hourOfDay>12)
                        {chooseTime.setText(hourOfDay-12 + ":" + "00"+" p.m");}}
                        else{
                        if(hourOfDay<12)
                        {chooseTime.setText(hourOfDay + ":" + minutes+" a.m");}
                        else if(hourOfDay>12)
                        {chooseTime.setText(hourOfDay-12 + ":" + minutes+" p.m");}}
                    }
                }, 0, 0, true);
                timePickerDialog.show();

            }
        });
    }


    public void write() {

        final TextView place =  findViewById(R.id.editPlace);
        final TextView time = findViewById(R.id.editTime);
        final TextView about =  findViewById(R.id.editAbout);
        final TextView rules =  findViewById(R.id.editRules);
        final TextView contact =  findViewById(R.id.editContact);
        final TextView date =  findViewById(R.id.editDate);

        Map<String, String> map = new HashMap<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("Event/").child(ma.s);

        map.put("place", place.getText().toString());
        map.put("date", date.getText().toString());
        map.put("time", time.getText().toString());
        map.put("about", about.getText().toString());
        map.put("rules", rules.getText().toString());
        map.put("contact", contact.getText().toString());

        mDatabase.setValue(map);

    }

    public void read() {
        final TextView place =  findViewById(R.id.editPlace);
        final TextView time =  findViewById(R.id.editTime);
        final TextView about =  findViewById(R.id.editAbout);
        final TextView rules =  findViewById(R.id.editRules);
        final TextView contact =  findViewById(R.id.editContact);
        final TextView date =  findViewById(R.id.editDate);


        mDatabase = FirebaseDatabase.getInstance().getReference("Event/");


        mDatabase.child(ma.s).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                des user = dataSnapshot.getValue(des.class);
                // ...
                place.setText(user.getPlace());
                about.setText(user.getAbout());
                time.setText(user.getTime());
                rules.setText(user.getRules());
                contact.setText(user.getContact());
                date.setText(user.getDate());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }


    public void check() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Upload changes");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        write();
                        Toast.makeText(Main2Activity.this, "Changes uploaded", Toast.LENGTH_LONG).show();
                        read();
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                read();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
