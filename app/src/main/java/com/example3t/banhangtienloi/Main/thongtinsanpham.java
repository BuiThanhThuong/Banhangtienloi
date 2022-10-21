package com.example3t.banhangtienloi.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.text.DecimalFormat;
import com.bumptech.glide.Glide;
import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.ketnoi.maychu;
import com.example3t.banhangtienloi.model.giohang;
import com.example3t.banhangtienloi.model.mathang;
import com.nex3z.notificationbadge.NotificationBadge;

public class thongtinsanpham extends AppCompatActivity {
    TextView txttenmathang,txtgia,txtmota,txtnhacungcap;
    Button btnthem;
    Spinner spinner;
    ImageView imhinhmathang,imhvetrangchu;
    mathang mathangg;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtinsanpham);
        imhvetrangchu=findViewById(R.id.ímvetrangchu);
        imhvetrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Trangchu.class);
                startActivity(intent);
            }
        });
        txtgia = (TextView) findViewById(R.id.gia);
        txtmota = (TextView) findViewById(R.id.mota);
        txttenmathang = (TextView) findViewById(R.id.txttenmathang);
        txtnhacungcap = (TextView) findViewById(R.id.tennhacungcap);
        btnthem = (Button) findViewById(R.id.themgiohang);
        spinner = (Spinner) findViewById(R.id.spinner);
        imhinhmathang = (ImageView) findViewById(R.id.imthongtin);
        badge = (NotificationBadge) findViewById(R.id.solg);
        FrameLayout frameLayout=findViewById(R.id.giohangfr);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
            }
        });
        them();
        themgiohang();
        if (maychu.dshang != null) {
            int solgtthem = 0;
            for(int i =0; i<maychu.dshang.size();i++){
                solgtthem = solgtthem+maychu.dshang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(solgtthem));
        }

    }


    private void themgiohang() {
            btnthem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    themgh();
                }

                private void themgh() {


                   if(maychu.dshang.size() > 0)
                    {
                        boolean flag =false;
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        for(int i =0; i < maychu.dshang.size(); i++){
                            if(maychu.dshang.get(i).getMamathang() == mathangg.getMamathang()){
                                maychu.dshang.get(i).setSoluong(soluong + maychu.dshang.get(i).getSoluong()) ;
                                long gia =(mathangg.getGia())/* * maychu.dshang.get(i).getSoluong()**/;
                                maychu.dshang.get(i).setGiasp(gia);
                                flag=true;
                            }
                        }
                        if(flag==false){
                            giohang giohang = new giohang();
                            long gia=(mathangg.getGia());//*soluong;
                            giohang.setGiasp(gia);
                            giohang.setSoluong(soluong);
                            giohang.setMamathang(mathangg.getMamathang());
                            giohang.setTenmathang(mathangg.getTenmathang());
                            giohang.setHinhanhmathang(mathangg.getHinhanhmathang());
                            maychu.dshang.add(giohang);

                        }

                    }else {
                        giohang giohang = new giohang();
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long gia=( mathangg.getGia());//* soluong;
                        giohang.setGiasp(gia);;
                        giohang.setSoluong(soluong);
                        giohang.setMamathang(mathangg.getMamathang());
                        giohang.setTenmathang(mathangg.getTenmathang());
                        giohang.setHinhanhmathang(mathangg.getHinhanhmathang());
                        maychu.dshang.add(giohang);
                    }
                    int solgtthem = 0;
                    for(int i =0; i<maychu.dshang.size();i++) {
                        solgtthem = solgtthem + maychu.dshang.get(i).getSoluong();
                    }
                    badge.setText(String.valueOf(solgtthem));

                }
            });


        }



    private void them() {
                mathangg =mathangg= (mathang) getIntent().getSerializableExtra("thongtin");
                txttenmathang.setText(mathangg.getTenmathang());
                txtmota.setText(mathangg.getMota());
                DecimalFormat decimalFormat = new DecimalFormat("###.###");
                Glide.with(getApplicationContext()).load(mathangg.getHinhanhmathang()).into(imhinhmathang);
                txtgia.setText("Gía: "+(mathangg.getGia())+"đ");
                txtnhacungcap.setText(mathangg.getNhacungcap());
                Integer[] SO = new Integer[]{1};
                ArrayAdapter<Integer> adins = new ArrayAdapter<>(this, io.paperdb.R.layout.support_simple_spinner_dropdown_item, SO);
                spinner.setAdapter(adins);
            }

    @Override
    protected void onResume() {
        super.onResume();
        themgiohang();
        if (maychu.dshang != null) {
            int solgtthem = 0;
            for(int i =0; i<maychu.dshang.size();i++){
                solgtthem = solgtthem+maychu.dshang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(solgtthem));
        }

    }
}
