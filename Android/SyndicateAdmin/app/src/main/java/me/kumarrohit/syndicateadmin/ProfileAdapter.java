package me.kumarrohit.syndicateadmin;



import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.List;


public class ProfileAdapter extends RecyclerView.Adapter<ProfileAdapter.viewHolder> {
    Context context;


    private List<Profile> list;

    public ProfileAdapter(List<Profile> list) {
        this.list = list;
    }

    public ProfileAdapter(Context context, List<Profile> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ProfileAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.profile_row, viewGroup, false);
        context = viewGroup.getContext();

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileAdapter.viewHolder viewHolder, int i) {
        String name = list.get(i).getName();
        String dob = list.get(i).getDob();
        String phone = list.get(i).getPhone() ;
        String acc_num = list.get(i).getAccount_number();
        String balance = list.get(i).getBalance() ;
        String profile_link = list.get(i).getProfile_image();


        viewHolder.setData(name, dob, phone,acc_num,balance,profile_link);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder  {
        ImageView imageView;
        TextView name, dob, phone, acc_num, balance;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
           dob = itemView.findViewById(R.id.dob);
            phone = itemView.findViewById(R.id.phone);
            acc_num = itemView.findViewById(R.id.acc_num);
            balance = itemView.findViewById(R.id.balance);
            imageView = itemView.findViewById(R.id.profile_image);
            context = itemView.getContext();
        }

        public void setData(String mname, String mdob, String mphone, String macc_num, String mbalance, String mImage) {
            name.setText(mname);
            dob.setText(mdob);
            phone.setText(mphone);
            acc_num.setText(macc_num);
            balance.setText(mbalance);
            Picasso.get().load(mImage).resize(10000,16000).onlyScaleDown().centerCrop().into(imageView);
        }


    }

}
