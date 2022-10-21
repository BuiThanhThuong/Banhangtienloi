package com.example3t.banhangtienloi.adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SymbolTable;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example3t.banhangtienloi.Interface.ItemClickListener;
import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.model.mathang;
//import com.squareup.picasso.Picasso;
import com.example3t.banhangtienloi.Main.thongtinsanpham;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

public class mathangadapter extends RecyclerView.Adapter<mathangadapter.holderhv> {
    Context context;
    List<mathang> array;


    public mathangadapter(Context context, List<mathang> array) {
        this.context = context;
        this.array = array;
    }


    @NonNull
    @Override
    public holderhv onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongmathang,parent,false);
        return new holderhv(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holderhv holder, int position) {
        mathang mathangs=array.get(position);
        holder.txttensanpham.setText(mathangs.getTenmathang());
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        holder.txtgiasanpham.setText("Giá: "+(mathangs.getGia())+"đ");
        Glide.with(context).load(mathangs.getHinhanhmathang()).into(holder.imhinhanhsp);
        holder.setItemClickListener(new ItemClickListener(){
            @Override
            public void onClick(View view , int pos, boolean isLongClick)
            {
                if(!isLongClick)
                {
                    Intent intent = new Intent(context,thongtinsanpham.class);
                    intent.putExtra("thongtin",mathangs);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });
    }



    @Override
    public int getItemCount() {
        return array.size();
    }

    public class holderhv extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView txttensanpham,txtgiasanpham;
            ImageView imhinhanhsp;
            private ItemClickListener itemClickListener;

        public holderhv(@NonNull View itemView) {
            super(itemView);
            imhinhanhsp= (ImageView) itemView.findViewById(R.id.imhinhmathang);
            txtgiasanpham = (TextView) itemView.findViewById(R.id.txtgiasanpham);
            txttensanpham= (TextView)itemView.findViewById(R.id.txttensanpham);
            itemView.setOnClickListener(this);
        }


        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition(),false);
        }
    }

}
