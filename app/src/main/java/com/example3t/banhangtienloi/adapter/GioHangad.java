package com.example3t.banhangtienloi.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example3t.banhangtienloi.Interface.Imclick;
import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.ketnoi.maychu;
import com.example3t.banhangtienloi.model.eventbus.tong;
import com.example3t.banhangtienloi.model.giohang;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

public class GioHangad extends RecyclerView.Adapter<GioHangad.Myholder> {
    Context context;
    List<giohang> listgiohang;


    public GioHangad(Context context, List<giohang> listgiohang) {
        this.context = context;
        this.listgiohang = listgiohang;
    }

    @NonNull
    @Override
    public Myholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.giohang,parent,false);
        return new Myholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myholder holder, int position) {
        giohang giohang= listgiohang.get(position);
        holder.tenmathanggiohang.setText(giohang.getTenmathang());
        holder.soluong.setText(giohang.getSoluong() + " ");
        Glide.with(context).load(giohang.getHinhanhmathang()).into(holder.hinhmathanggiohang);
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        holder.giamat1.setText("Giá: "+(giohang.getGiasp())+"đ");
        long gia = giohang.getSoluong()*giohang.getGiasp();
        holder.tonggia.setText(decimalFormat.format(gia)+"đ");
        holder.setImclick(new Imclick() {
            @Override
            public void onImageClick(View view, int pos, int congtru) {
                if(congtru ==1) {
                    if (listgiohang.get(pos).getSoluong() > 1) {
                        int slmoi = listgiohang.get(pos).getSoluong() - 1;
                        listgiohang.get(pos).setSoluong(slmoi);
                        holder.soluong.setText(listgiohang.get(pos).getSoluong() + "");
                        long gia = listgiohang.get(pos).getSoluong() * listgiohang.get(pos).getGiasp();
                        holder.tonggia.setText(decimalFormat.format(gia) + "đ");
                        EventBus.getDefault().postSticky(new tong());


                    } else if (listgiohang.get(pos).getSoluong() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo");
                        builder.setMessage("Xóa khỏi giỏ hàng ?");
                        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                maychu.dshang.remove(pos);
                                notifyDataSetChanged();
                                EventBus.getDefault().postSticky(new tong());
                            }
                        });
                        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                            }
                        });
                        builder.show();
                    }
                }

                    else if(congtru ==2) {

                        int slmoi=listgiohang.get(pos).getSoluong() + 1;
                        listgiohang.get(pos).setSoluong(slmoi);

                    holder.soluong.setText(listgiohang.get(pos).getSoluong()+"");
                    long gia = listgiohang.get(pos).getSoluong()*listgiohang.get(pos).getGiasp();
                    holder.tonggia.setText(decimalFormat.format(gia)+"đ");
                    EventBus.getDefault().postSticky(new tong());
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return listgiohang.size();
    }


    public class Myholder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView hinhmathanggiohang,tru,cong;
        TextView tenmathanggiohang,giamat1,soluong,tonggia;
        Imclick imclick;
        public Myholder(@NonNull View itemView) {
            super(itemView);
            hinhmathanggiohang = itemView.findViewById(R.id.hinhmathanggiohang);
            tenmathanggiohang = itemView.findViewById(R.id.tenmathanggiohang);
            soluong = itemView.findViewById(R.id.soluong);
            giamat1 = itemView.findViewById(R.id.giamat1);
            tonggia = itemView.findViewById(R.id.tonggia);
            tru=itemView.findViewById(R.id.tru);
            cong = itemView.findViewById(R.id.cong);
            tru.setOnClickListener(this);
            cong.setOnClickListener(this);


        }

        public void setImclick(Imclick imclick) {
            this.imclick = imclick;
        }

        @Override
        public void onClick(View view) {
            if(view ==tru){
                imclick.onImageClick(view, getAdapterPosition(),1);
            }else {
                imclick.onImageClick(view, getAdapterPosition(),2);
            }
        }
    }
}
