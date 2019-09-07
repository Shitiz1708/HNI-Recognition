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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class hni_request extends Fragment {


    RecyclerView recyclerView ;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("admin_app")
            .child("hni_requests") ;
    final List<request> list = new ArrayList<>() ;



    public hni_request() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_hni_request, container, false);
        recyclerView = view.findViewById(R.id.recycler_view_hni_request) ;
        final FragmentActivity c = getActivity() ;
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(c) ;
        recyclerView.setLayoutManager(linearLayoutManager);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot item_snapshot: dataSnapshot.getChildren())
                {
                    for(DataSnapshot sub_item_snapshot:item_snapshot.getChildren())
                    {       boolean   your_date_is_outdated = true;
                        String name = sub_item_snapshot.child("name").getValue().toString() ;
                        String date = sub_item_snapshot.child("date").getValue().toString() ;
                        String time = sub_item_snapshot.child("time").getValue().toString() ;
                        String purpose = sub_item_snapshot.child("purpose").getValue().toString() ;
                        String message = sub_item_snapshot.child("message").getValue().toString() ;
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date strDate = null;
                        try {
                            strDate = sdf.parse(date);
                            if (new Date().after(strDate)) {
                                your_date_is_outdated = true;
                            }
                            else{
                                your_date_is_outdated = false;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if(!your_date_is_outdated)
                        {
                             list.add(new request(name,date,time,purpose,message))  ;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("check", "run: list sent" +""+list.size());
                                final request_adapter profileAdapter1 = new request_adapter(c,list) ;
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

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        return view ;
    }

}
