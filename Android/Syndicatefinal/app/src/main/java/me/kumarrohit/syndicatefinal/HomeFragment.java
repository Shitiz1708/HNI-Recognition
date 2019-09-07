package me.kumarrohit.syndicatefinal;


import android.graphics.Typeface;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    CircleImageView profileinage ;
    TextView name,acc_no,acc_balance ;
    ImageView imageView ;
    FirebaseAuth mAuth = FirebaseAuth.getInstance() ;
    String uid = mAuth.getUid()  ;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("admin_app").child("profiles")
            .child(uid);



    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //from here

        if(uid!=null)
        {
            FirebaseDatabase.getInstance().getReference().child("admin_app").child("is_hni_online").setValue("true") ;
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String name1 = dataSnapshot.child("Name").getValue().toString() ;
                    String email1 = dataSnapshot.child("Email Address").getValue().toString() ;
                    String acc_num = dataSnapshot.child("Account Number").getValue().toString()  ;
                    String mode = "By Phone" ;
                    Map<String, String> userData = new HashMap<String, String>();

                    userData.put("Name",name1) ;
                    userData.put("Email",email1) ;
                    userData.put("Account_Number",acc_num) ;
                    userData.put("UID",uid) ;
                    userData.put("mode",mode) ;

                    FirebaseDatabase.getInstance().getReference().child("admin_app").child("hni_online").child(uid)
                            .setValue(userData) ;


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        profileinage = view.findViewById(R.id.home_image) ;
        name = view.findViewById(R.id.home_name) ;
        acc_no = view.findViewById(R.id.operativeno) ;
        acc_balance = view.findViewById(R.id.balance_remaining) ;
        imageView = view.findViewById(R.id.tpt) ;


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Picasso.get().load(dataSnapshot.child("profile_image").getValue().toString()).into(profileinage) ;
                name.setText(dataSnapshot.child("Name").getValue().toString());
                acc_no.setText(dataSnapshot.child("Account Number").getValue().toString());


                acc_balance.setText("Rs."+ dataSnapshot.child("balance").getValue().toString());




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }) ;


   imageView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(getActivity().getApplicationContext(), "This is  just to demonstrate a Real Application", Toast.LENGTH_SHORT).show();

                                    }
                                }
   );

        return view;
    }

}
