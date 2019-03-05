package org.srijaniitism.android.srijanorganiser;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Main3Activity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    FloatingActionButton fab;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //prevent keyboard from automatically opening for edit text


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                check();
                //   Toast.makeText(getBaseContext(), "FAB Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        setPickTime();

        setPickDate();

        setTitle("New Event");

        MainActivity ma = new MainActivity();
        ma.i = 1;



    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(24)
    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        EditText edittext =  findViewById(R.id.editDate);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }

    @TargetApi(24)
    public void setPickDate() {
        myCalendar = Calendar.getInstance();

        EditText edittext = (EditText) findViewById(R.id.editDate);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @TargetApi(24)
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
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
                new DatePickerDialog(Main3Activity.this, date, myCalendar
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(Main3Activity.this, new TimePickerDialog.OnTimeSetListener() {

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


    public void check() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Add Event");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        write();
                        Toast.makeText(Main3Activity.this, "Event successfully added", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(Main3Activity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    public void write() {
        final TextView name = (TextView) findViewById(R.id.editName);
        final TextView place = (TextView) findViewById(R.id.editPlace);
        final TextView time = (TextView) findViewById(R.id.editTime);
        final TextView about = (TextView) findViewById(R.id.editAbout);
        final TextView rules = (TextView) findViewById(R.id.editRules);
        final TextView contact = (TextView) findViewById(R.id.editContact);
        final TextView date = (TextView) findViewById(R.id.editDate);

        Map<String, String> map = new HashMap<>();

        mDatabase = FirebaseDatabase.getInstance().getReference("Event/").child(name.getText().toString());
        map.put("place", place.getText().toString());
        map.put("date", date.getText().toString());
        map.put("time", time.getText().toString());
        map.put("about", about.getText().toString());
        map.put("rules", rules.getText().toString());
        map.put("contact", contact.getText().toString());


        mDatabase.setValue(map);
    }
}

