package com.example.newsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;

public class OuterRecycler extends RecyclerView.Adapter<Heading_View_holder> {
    ArrayList<String> data=new ArrayList<>();
    headingclicked listn;
    OuterRecycler( ArrayList<String> data,headingclicked listn)
    {
        this.data=data;
        this.listn=listn;
    }

    @NonNull
    @Override
    public Heading_View_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.headinglayout,parent,false);
        Heading_View_holder hold=new Heading_View_holder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listn.headingisclicked(hold.getAbsoluteAdapterPosition());

            }
        });
        return hold;
    }
  /* void updatedata(ArrayList<String> dat)
   { data.clear();
       data.addAll(dat);
       notifyDataSetChanged();
   }*/
    @Override
    public void onBindViewHolder(@NonNull Heading_View_holder holder, int position) {
    String curr=data.get(position);
    holder.txt.setText(curr);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

}
class Heading_View_holder extends RecyclerView.ViewHolder {
    TextView txt;
    Heading_View_holder(View iv)
    {
        super(iv);
        txt=(TextView)iv.findViewById(R.id.Txtview);
    }
}
interface headingclicked
{
    void headingisclicked(int pos);
}
