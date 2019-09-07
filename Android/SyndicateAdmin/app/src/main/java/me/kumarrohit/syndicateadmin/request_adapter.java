package me.kumarrohit.syndicateadmin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class request_adapter extends RecyclerView.Adapter<request_adapter.viewHolder> {
    Context context;


    private List<request> list;

    public request_adapter(List<request> list) {
        this.list = list;
    }

    public request_adapter(Context context, List<request> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public request_adapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_request_layout, viewGroup, false);
        context = viewGroup.getContext();

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull request_adapter.viewHolder viewHolder, int i) {
        String name = list.get(i).getName();
        String date = list.get(i).getDate() ;
        String time = list.get(i).getTime() ;
        String purpose  = list.get(i).getPurpose() ;
        String message = list.get(i).getMessage() ;

        viewHolder.setData(name,date,time,purpose,message);

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewHolder extends RecyclerView.ViewHolder  {

        TextView name, date,time,purpose,message;


        public viewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_in_request);
            date = itemView.findViewById(R.id.date_in_request) ;
            time =itemView.findViewById(R.id.time_in_request) ;
            purpose = itemView.findViewById(R.id.purpose_in_request) ;
            message  =itemView.findViewById(R.id.message_in_request) ;
         context = itemView.getContext();
        }

        public void setData(String mname, String mdate, String mtime, String mpurpose, String mmessage) {
            name.setText(mname);
           date.setText(mdate);
           time.setText(mtime);
           purpose.setText(mpurpose);
           message.setText(mmessage);
        }


    }

}

