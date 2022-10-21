package com.example3t.banhangtienloi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.model.Donhang;

import java.util.List;

public class Donhangad extends RecyclerView.Adapter<Donhangad.Hodler> {
    private RecyclerView.RecycledViewPool recycledViewPool =new RecyclerView.RecycledViewPool();
    Context context;
    List<Donhang> donhangList;

    public Donhangad(Context context, List<Donhang> donhangs) {
        this.context = context;
        this.donhangList = donhangs;
    }

    @NonNull
    @Override
    public Hodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donhang,parent,false);
        return new Hodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Hodler holder, int position) {
        Donhang donhangg = donhangList.get(position);
        holder.txtinhtrang.setText(donhangg.getTinhtrang());
        holder.txttongtien.setText(donhangg.getTongtien());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerViewdonhang.getContext(),
                LinearLayoutManager.VERTICAL,
                 false
        );
        layoutManager.setInitialPrefetchItemCount(donhangg.getItem().size());
        Chitietdonhangad chitietdonhangad = new Chitietdonhangad(context,donhangg.getItem());
        holder.recyclerViewdonhang.setLayoutManager(layoutManager);
        holder.recyclerViewdonhang.setAdapter(chitietdonhangad);
        holder.recyclerViewdonhang.setRecycledViewPool(recycledViewPool);
    }

    @Override
    public int getItemCount() {
        return donhangList.size();
    }

    public class Hodler extends RecyclerView.ViewHolder {
        TextView txtdonhang,txtinhtrang,txttongtien;
        RecyclerView recyclerViewdonhang;
        public Hodler(@NonNull View itemView) {
            super(itemView);
          //  txtdonhang=itemView.findViewById(R.id.iddonhang);
            txtinhtrang=itemView.findViewById(R.id.txttinhtrang);
            txttongtien=itemView.findViewById(R.id.tongtiendh);
            recyclerViewdonhang=itemView.findViewById(R.id.recyviewct);
        }
    }
}
