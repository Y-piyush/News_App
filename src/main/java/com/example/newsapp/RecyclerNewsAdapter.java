package com.example.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerNewsAdapter extends RecyclerView.Adapter<View_Holder> {
     private ArrayList<News> list=new ArrayList<>();News current;
   private newsItemClicked listener;

 public RecyclerNewsAdapter( newsItemClicked listener)
  {
      this.listener=listener;
  }

    @NonNull
    @Override
    public View_Holder onCreateViewHolder( ViewGroup parent, int viewType) {
     final View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news,parent,false);
      final View_Holder holder=new View_Holder(v);
      v.setOnClickListener( new View.OnClickListener()
      {
          @Override
          public void onClick(View view) {
              listener.itemonclick(list.get(holder.getAbsoluteAdapterPosition()));
          }
      });
        return holder;
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public void onBindViewHolder( View_Holder holder, int position) {
     current= list.get(position);
      holder.title.setText(current.title);
        Glide.with(holder.itemView.getContext()).load(current.imgurl).into(holder.image);
    }
    public void newsupdate(ArrayList<News> un)
    {
        list.clear();
        list.addAll(un);
        notifyDataSetChanged();
    }

}

class View_Holder extends RecyclerView.ViewHolder {
    TextView title;
    ImageView image;
    CardView cv;

    View_Holder(View itemview) {
        super(itemview);
        cv=itemview.findViewById(R.id.cardView);
        title = itemview.findViewById(R.id.title);
        image= itemview.findViewById(R.id.imageView);
    }


}
interface newsItemClicked{

      void itemonclick(News pos);

}