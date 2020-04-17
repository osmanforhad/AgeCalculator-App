package com.osmanforhad.agecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.Period;
import org.joda.time.PeriodType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    /*variable declaration**/
    private Button findDob_btn, calculate_btn;
    private TextView today_txt, dob_text, age_txt;
    String mbirthday, mtoday;

    /* DatePicker Declaration **/
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* initial the xml id **/
        today_txt = findViewById(R.id.dateText);
        dob_text = findViewById(R.id.dobText);
        age_txt = findViewById(R.id.ageText);

        findDob_btn = findViewById(R.id.dobBtn);
        calculate_btn = findViewById(R.id.calBtn);

        /* for getting current date
         * making & declare an calender instance **/
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        /* formatting the date **/
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        /* store the date into string variable **/
        mtoday = simpleDateFormat.format(Calendar.getInstance().getTime());
        /* set the date into xml UI **/
        today_txt.setText("Today :" + mtoday);

        /* for getting date of birth
         * use here datePicker through a button click **/
        findDob_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Making a DatePickerDialogue **/
                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), dateSetListener, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }//end of the onClick View

        });//end of the OnClickListener

        /* for displaying birth day **/
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;/* for avoid start count 0, now it will start count from 1 **/
                mbirthday = dayOfMonth + "/" + month + "/" + year;
                dob_text.setText("Birthday :" + mbirthday);
            }//end of the onDateSet

        };//end of the OnDateSetListener

        /* for Calculate the age through a button click **/
        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mbirthday == null) {
                    /* for displaying a message into UI **/
                    Toast.makeText(getApplicationContext(), "Please enter your date of birth", Toast.LENGTH_SHORT).show();
                }//end of the if condition
                else {
                    /* formatting the date **/
                    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        /* for birth date **/
                        Date date1 = simpleDateFormat1.parse(mbirthday);
                        /* for current date **/
                        Date date2 = simpleDateFormat1.parse(mtoday);

                        /* store both data which should calculate**/
                        long startDate = date1.getTime();
                        long endDate = date2.getTime();
                        /* calculate  Period
                         * through joda dependency
                         * which was adding in build.gradle fie **/
                        Period period = new Period(startDate, endDate, PeriodType.yearMonthDay());

                        /* get months, days and years **/
                        int years = period.getYears();
                        int months = period.getMonths();
                        int days = period.getDays();

                        /* set months, days and years into age_ext variable **/
                        age_txt.setText(years + "Years |" + months + "Months |" + days + "Days");

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }//end of the else statement

            }//end of the OnClick

        });//end of the setOnClickListener

    }//end of the onCreate method

}//end of the class
