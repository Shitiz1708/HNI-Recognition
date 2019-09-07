package me.kumarrohit.syndicatefinal;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Feedback extends Fragment {

   EditText subject,message  ;
   Button submit ;
   FirebaseDatabase firebaseDatabase ;


    public Feedback() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);
        subject = view.findViewById(R.id.subject)  ;
         final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        message = view.findViewById(R.id.messaged)  ;
        submit = view.findViewById(R.id.submit)  ;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("feedback").child("subject").setValue(subject.getText().toString()) ;
                databaseReference.child("feedback").child("message").setValue(message.getText().toString()) ;
                Toast.makeText(getContext(),"Your feedback submitted successfully",Toast.LENGTH_SHORT).show();
                message.setText("");
                subject.setText("");

            }
        });
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(" ");
    }


}
