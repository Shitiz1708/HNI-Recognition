package me.kumarrohit.syndicateadmin;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile_fragment extends Fragment {
    RecyclerView recyclerView ;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance()
    .getReference().child("admin_app").child("profiles") ;
    final List<Profile> list1 = new ArrayList<>() ;




    public Profile_fragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_profile) ;
        final FragmentActivity c = getActivity();

        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);



         databaseReference.addValueEventListener(new ValueEventListener() {


             @Override
             public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 list1.clear();
                 for(DataSnapshot itemsnapshot : dataSnapshot.getChildren())
                 {

                     String name = itemsnapshot.child("Name").getValue().toString();
                     String dob = itemsnapshot.child("DOB").getValue().toString();
                     String phone = itemsnapshot.child("Phone").getValue().toString();
                     String acc_num = itemsnapshot.child("Account Number").getValue().toString();
                     String balance = itemsnapshot.child("balance").getValue().toString();
                     String profile = itemsnapshot.child("profile_image").getValue().toString();
                     String is_hni = itemsnapshot.child("is_hni").getValue().toString() ;
                     if(is_hni.equals("true"))
                     list1.add(new Profile(name,dob,phone,acc_num,"Rs. "+balance,profile)) ;



                     new Thread(new Runnable() {
                         @Override
                         public void run() {
                             Log.d("check", "run: list sent" +""+list1.size());
                             final ProfileAdapter profileAdapter1 = new ProfileAdapter(c,list1) ;
                             c.runOnUiThread(new Runnable() {
                                 @Override
                                 public void run() {
                                     recyclerView.setAdapter(profileAdapter1);
                                 }
                             });
                         }
                     }).start();


                 }

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         }) ;




        return view;
    }

}
