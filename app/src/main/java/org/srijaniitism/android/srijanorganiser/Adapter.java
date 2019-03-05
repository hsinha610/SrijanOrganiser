package org.srijaniitism.android.srijanorganiser;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;


public class Adapter extends ArrayAdapter<events> implements Filterable {

    public Adapter(MainActivity context, ArrayList<events> names) {
        super(context,0,names);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;

        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listview, parent, false);
        }





        // Get the {@link AndroidFlavor} object located at this position in the list
        events currentAndroidFlavor = getItem(position);

        // Find the TextView in the listview.xml layout with the ID version_name
        TextView nameTextView = listItemView.findViewById(R.id.tv);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        nameTextView.setText(currentAndroidFlavor.getname());




        // Return the whole list item layout (containing 1 TextView)
        // so that it can be shown in the ListView
        return listItemView;
    }








}


