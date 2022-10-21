package com.example3t.banhangtienloi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.model.Item;

import java.util.List;

public class Chitietdonhangad extends RecyclerView.Adapter<Chitietdonhangad.ctdhholder> {
    Context context;
    List<Item> itemList;

    public Chitietdonhangad(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ctdhholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chitiet,parent,false);
        return new ctdhholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ctdhholder holder, int position) {
        Item item=itemList.get(position);
        holder.txtten.setText(item.getTenmathang()+"");
        holder.txtsoluong.setText("Số lượng: "+item.getSoluong());
        Glide.with(context).load(item.getHinhanhmathang()).into(holder.imghinhanhct);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ctdhholder extends  RecyclerView.ViewHolder{
        ImageView imghinhanhct;
        TextView txtten,txtsoluong;
        public ctdhholder(@NonNull View itemView) {
            super(itemView);
            imghinhanhct=itemView.findViewById(R.id.hinhanhct);
            txtten=itemView.findViewById(R.id.tenspct);
            txtsoluong=itemView.findViewById(R.id.soluongspct);
        }
    }
}
