package fr.esgi.moc.moviemeeting.data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DateFormatter {

    private Date mDate;


    public DateFormatter(){

    }
    public DateFormatter(Date mDate){
        this.mDate = mDate;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }


    public String getFormatDate(){

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if(mDate == null){
            return "Pas de date";
        }

        String date = df.format(mDate);

        return date;
    }



}
