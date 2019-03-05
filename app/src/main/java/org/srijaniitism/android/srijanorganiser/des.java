package org.srijaniitism.android.srijanorganiser;

public class des {
    private static String place;
    private static String time;
    private static String about;
    private static String rules;
    private static String contact;
    private static String date;

    public des() {
        place = "";
        time = "";
        about = "";
        rules = "";
        contact = "";
        date="";
    }


    public des(String p, String t, String a, String r, String c,String d) {
        place = p;
        date= d;
        time = t;
        about = a;
        rules = r;
        contact = c;
    }

    public String getPlace() {
        return place;
    }

    public String getTime() {
        return time;
    }

    public String getAbout() {
        return about;
    }

    public String getRules() {
        return rules;
    }

    public String getContact() {
        return contact;
    }

    public  String getDate() { return date; }
}
