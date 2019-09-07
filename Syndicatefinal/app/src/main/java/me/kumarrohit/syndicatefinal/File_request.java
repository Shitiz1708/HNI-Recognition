package me.kumarrohit.syndicatefinal;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class File_request extends Fragment {

    EditText name, date, time, message;
    EditText purpose;
    Button submitbutton;
    int i = 0;

    final Calendar myCalendar = Calendar.getInstance();

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String uid = mAuth.getUid();
    DatabaseReference userdatabaseReference = FirebaseDatabase.getInstance().getReference().child("admin_app");


    public File_request() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_file_request, container, false);
        // Inflate the layout for this fragment
        name = view.findViewById(R.id.request_name);

        message = view.findViewById(R.id.request_message);
        purpose = view.findViewById(R.id.request_purpose);
        submitbutton = view.findViewById(R.id.submit_button);

        date = view.findViewById(R.id.date);
        time = view.findViewById(R.id.time);
        date.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        /*      Your code   to get date and time    */
                        selectedmonth = selectedmonth + 1;
                                if(selectedmonth<10&&selectedday<10)
                                {
                                    date.setText("0" + selectedday + "/0" + selectedmonth + "/" + selectedyear);
                                }
                                else if(selectedday<10)
                                {
                                    date.setText("0" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                                }
                                else if(selectedmonth<10)
                                {
                                    date.setText("" + selectedday + "/0" + selectedmonth + "/" + selectedyear);
                                }
                                else

                        date.setText("" + selectedday + "/" + selectedmonth + "/" + selectedyear);
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });


        time.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        StringBuffer buffer = new StringBuffer();
                        buffer.append(selectedHour);
                        buffer.append(":");
                        buffer.append(selectedMinute);
                        time.setText(buffer);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString();
                String date1 = date.getText().toString();
                String time1 = time.getText().toString();
                String purpose1 = purpose.getText().toString();
                String message1 = message.getText().toString();
                Map<String, String> userData = new HashMap<String, String>();

                userData.put("name", name1);
                userData.put("date", date1);
                userData.put("time", time1);
                userData.put("purpose", purpose1);
                userData.put("message", message1);


                userdatabaseReference.child("hni_requests").child(uid + "").push().setValue(userData);
                Toast.makeText(getActivity().getApplicationContext(), "Your request filled succesfully", Toast.LENGTH_SHORT).show();
                name.setText("");
                date.setText("");
                time.setText("");
                purpose.setText("");
                message.setText("");


            }
        });



        return view;

    }


}




