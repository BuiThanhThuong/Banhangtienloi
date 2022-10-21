package com.example3t.banhangtienloi.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.adapter.GioHangad;
    import com.example3t.banhangtienloi.ketnoi.maychu;
import com.example3t.banhangtienloi.model.eventbus.tong;
import com.example3t.banhangtienloi.model.mathang;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

public class Giohang extends AppCompatActivity {
    TextView txtronggh, txttongtien,txttongtxt,txtkhuyenmai,txtkhuyenmai1,txtkhuyenmai2,txtkhuyenmai3;
    Toolbar toolbar;
    ImageView imgback1;
    RecyclerView recyclerView;
    Button btnthanhtoan;
    GioHangad gioHangad;
    long tongtien =0;
    mathang mathangg;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        imgback1=findViewById(R.id.back1);
        txtkhuyenmai=findViewById(R.id.khuyenmaitxt);
        txtkhuyenmai1=findViewById(R.id.khuyenmaitxt1);
        txtkhuyenmai2=findViewById(R.id.khuyenmaitxt2);
        txtkhuyenmai3=findViewById(R.id.khuyenmaitxt3);
        txttongtxt=findViewById(R.id.tongtientxt);
        txtronggh= (TextView) findViewById(R.id.txttrong);
        txttongtien= (TextView) findViewById(R.id.tongtien);
        recyclerView=(RecyclerView) findViewById(R.id.recyview);
        btnthanhtoan=(Button) findViewById(R.id.thanhtoan);
        toolbar=(Toolbar)findViewById(R.id.tbgiohang);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        gioHangad =new GioHangad(getApplicationContext(),maychu.dshang);
        recyclerView.setAdapter(gioHangad);
       if(maychu.dshang.size()==0){
           btnthanhtoan.setVisibility(View.INVISIBLE);
           txttongtien.setVisibility(View.INVISIBLE);
           txttongtxt.setVisibility(View.INVISIBLE);
           txtronggh.setVisibility(View.VISIBLE);
           txtkhuyenmai2.setVisibility(View.INVISIBLE);
           txtkhuyenmai.setVisibility(View.INVISIBLE);
           txtkhuyenmai1.setVisibility(View.INVISIBLE);
           txtkhuyenmai3.setVisibility(View.INVISIBLE);

       }else
       {
           txtronggh.setVisibility(View.INVISIBLE);
           txttongtxt.setVisibility(View.VISIBLE);
           txttongtien.setVisibility(View.VISIBLE);
           btnthanhtoan.setVisibility(View.VISIBLE);
       }
       tongtien();
       btnthanhtoan.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               Intent intent =new Intent(getApplicationContext(),ThanhToan.class);
               intent.putExtra("tongtien",tongtien);
               startActivity(intent);
           }
       });
       imgback1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent= new Intent(getApplicationContext(),Trangchu.class);
               startActivity(intent);
           }
       });

    }

    private void tongtien() {
        tongtien =0;
        for (int i=0; i<maychu.dshang.size();i++) {
                tongtien = tongtien + (maychu.dshang.get(i).getGiasp() * maychu.dshang.get(i).getSoluong());
        }
        if(tongtien>=200000&&tongtien<500000){
            tongtien=tongtien-20000;
            txtkhuyenmai.setVisibility(View.VISIBLE);
            txtkhuyenmai2.setVisibility(View.INVISIBLE);
            txtkhuyenmai1.setVisibility(View.INVISIBLE);
            txtkhuyenmai3.setVisibility(View.INVISIBLE);

        }
        else if(tongtien<200000&&tongtien>=150000) {
            tongtien=tongtien+0;
            txtkhuyenmai.setVisibility(View.INVISIBLE);
            txtkhuyenmai1.setVisibility(View.VISIBLE);
            txtkhuyenmai2.setVisibility(View.INVISIBLE);
            txtkhuyenmai3.setVisibility(View.INVISIBLE);
        }else if(tongtien<150000&&tongtien>0){
            tongtien= tongtien+15000;
            txtkhuyenmai2.setVisibility(View.VISIBLE);
            txtkhuyenmai.setVisibility(View.INVISIBLE);
            txtkhuyenmai1.setVisibility(View.INVISIBLE);
            txtkhuyenmai3.setVisibility(View.INVISIBLE);
        } else if(tongtien==0){

            txtkhuyenmai2.setVisibility(View.INVISIBLE);
            txtkhuyenmai.setVisibility(View.INVISIBLE);
            txtkhuyenmai1.setVisibility(View.INVISIBLE);
            txtkhuyenmai3.setVisibility(View.INVISIBLE);


        }
        else {
            tongtien=tongtien-50000;
            txtkhuyenmai2.setVisibility(View.INVISIBLE);
            txtkhuyenmai.setVisibility(View.INVISIBLE);
            txtkhuyenmai1.setVisibility(View.INVISIBLE);
            txtkhuyenmai3.setVisibility(View.VISIBLE);

        }
        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        txttongtien.setText(decimalFormat.format(tongtien)+"đ");
        }
    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void tinhtong(tong event){
        if(event!=null){
            tongtien();
        }
    }
}

