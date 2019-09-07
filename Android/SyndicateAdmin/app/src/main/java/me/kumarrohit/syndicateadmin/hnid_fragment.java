package me.kumarrohit.syndicateadmin;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
public class hnid_fragment extends Fragment {

    RecyclerView recyclerView ;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("admin_app") ;



    public hnid_fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hnid_fragment, container, false);

        recyclerView=v.findViewById(R.id.detected_recycler_view) ;

        final FragmentActivity c = getActivity();

        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        final List<Profile> list = new ArrayList<>();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String str = dataSnapshot.child("is_hni_present").getValue().toString();
                if (str.equals("true")) {
                    databaseReference.child("hni_detected").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            list.clear();
                            for (DataSnapshot item_snapshot : dataSnapshot.getChildren()) {
                                String uid = item_snapshot.child("UID").getValue().toString();
                                Log.d("check", "hehehe");
                                databaseReference.child("profiles").child(uid).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Log.d("check", "hehehe");

                                        String name = dataSnapshot.child("Name").getValue().toString();
                                        String dob = dataSnapshot.child("DOB").getValue().toString();
                                        String phone = dataSnapshot.child("Phone").getValue().toString();
                                        String acc_num = dataSnapshot.child("Account Number").getValue().toString();
                                        String balance = dataSnapshot.child("balance").getValue().toString();
                                        String profile = dataSnapshot.child("profile_image").getValue().toString();
                                        list.add(new Profile(name, dob, phone, acc_num, balance, profile));

                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.d("check", "run: list sent" + "" + list.size());
                                                final ProfileAdapter profileAdapter = new ProfileAdapter(c, list);

                                                c.runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        recyclerView.setAdapter(profileAdapter);
                                                    }
                                                });
                                            }
                                        }).start();


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });



        return v ;
        }
    }
