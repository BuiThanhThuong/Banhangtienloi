package com.example3t.banhangtienloi.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example3t.banhangtienloi.adapter.GioHangad;
import com.example3t.banhangtienloi.ketnoi.client;
import com.example3t.banhangtienloi.ketnoi.cuahang;
import com.example3t.banhangtienloi.ketnoi.maychu;
import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.model.mathang;
import com.google.gson.Gson;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.internal.Util;

public class ThanhToan extends AppCompatActivity {
    TextView ten,sdt,mail,tongtien;
    EditText editTextdiachi;
    Button xacnhan;
    ImageView imthanhtoan;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    cuahang cuahang;
    long tongtienn;
    int solgtthem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        cuahang = client.getInstance(maychu.BASE_URL).create(cuahang.class);
        ten = findViewById(R.id.tenthanhtoan);
        sdt= findViewById(R.id.sdtthanhtoan);
        mail=findViewById(R.id.mailthanhoan);
        tongtien=findViewById(R.id.tongtienthanhtoan);
        editTextdiachi =findViewById(R.id.diachi);
        imthanhtoan=findViewById(R.id.imthanhtoan);
        imthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
            }
        });
        xacnhan=findViewById(R.id.btnxacnhanmua);
        demsl();
        xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String diachi = editTextdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(diachi)){
                    Toast.makeText(ThanhToan.this, "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    Log.d("test", new Gson().toJson(maychu.dshang));
                    String sdt = maychu.userdangnhap.getSDT();
                    String email=maychu.userdangnhap.getGmail();
                    int idsuer= maychu.userdangnhap.getIDuser();
                    String tenuser=maychu.userdangnhap.getTenuser();
                    compositeDisposable.add(cuahang.donhang(sdt,email,String.valueOf(tongtienn),idsuer,tenuser,diachi,solgtthem, new Gson().toJson(maychu.dshang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    usermd -> {

                                        Toast.makeText(getApplicationContext(),"Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(),Trangchu.class);
                                        maychu.dshang.clear();
                                        startActivity(intent);
                                        finish();

                                    },
                                    throwable -> {

                                        Toast.makeText(getApplicationContext(),throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });

        DecimalFormat decimalFormat = new DecimalFormat("###.###");
        tongtienn=getIntent().getLongExtra("tongtien",0);
        tongtien.setText(decimalFormat.format(tongtienn)+"đ");
        sdt.setText(maychu.userdangnhap.getSDT());
        mail.setText(maychu.userdangnhap.getGmail());
        ten.setText(maychu.userdangnhap.getTenuser());


    }

    private void demsl() {
        solgtthem = 0;
        for(int i =0; i<maychu.dshang.size();i++){
            solgtthem = solgtthem+maychu.dshang.get(i).getSoluong();
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}