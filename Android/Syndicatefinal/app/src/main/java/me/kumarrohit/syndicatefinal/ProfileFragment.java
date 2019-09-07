package me.kumarrohit.syndicatefinal;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

    ImageView photo ;
    TextView name,dob,email,phone,acc_num,acc_bal ;
    FirebaseDatabase firebaseDatabase ;


    public static final String FRAGMENT_TAG =
            BuildConfig.APPLICATION_ID + ".DEBUG_EXAMPLE_TWO_FRAGMENT_TAG";


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_profile, container, false);
         FirebaseAuth auth = FirebaseAuth.getInstance() ;
        final   String uid = auth.getUid() ;
        final DatabaseReference databaseReference = firebaseDatabase.getInstance().getReference().child("admin_app");
                DatabaseReference db = databaseReference.child("profiles");
        DatabaseReference reference = db.child(uid);
        if(databaseReference!=null)
        Log.d("check", "uid"+" "+uid);
        photo= view.findViewById(R.id.profile_image) ;
        name = view.findViewById(R.id.name)  ;
        dob = view.findViewById(R.id.dob) ;
        email = view.findViewById(R.id.email) ;
        phone = view.findViewById(R.id.phonenumber) ;
        acc_num = view.findViewById(R.id.accountnumber)  ;
        acc_bal = view.findViewById(R.id.accountbalance) ;

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("check", "uid"+" "+dataSnapshot);

                String name1 = dataSnapshot.child("Name").getValue().toString() ;
                String dob1 = dataSnapshot.child("DOB").getValue().toString();
                String email1 = dataSnapshot.child("Email Address").getValue().toString();
                String phone1  =dataSnapshot.child("Phone").getValue().toString() ;
                String Acno1  =dataSnapshot.child("Account Number").getValue().toString() ;
                String profile_image_link = dataSnapshot.child("profile_image").getValue().toString() ;
                String acc_balce = dataSnapshot.child("balance").getValue().toString() ;
                name.setText("Name: "+name1);
                dob.setText("Date of Birth: " +dob1);
                email.setText("Email: "+email1);
                phone.setText("Phone Number: "+phone1);
                acc_num.setText("Account Number: "+Acno1) ;
                acc_bal.setText("Account Balance: Rs. "+acc_balce);

                Picasso.get().load(profile_image_link).into(photo);



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("LogFragment", "loadLog:onCancelled", databaseError.toException());
            }
        };

        reference.addValueEventListener(valueEventListener);













        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle(" ");
    }
}