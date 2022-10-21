package com.example3t.banhangtienloi.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.ketnoi.client;
import com.example3t.banhangtienloi.ketnoi.cuahang;
import com.example3t.banhangtienloi.ketnoi.maychu;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private Button btndangnhap,btndangky;
    private EditText editTextsdt,editTextmk;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    cuahang cuahang;
    boolean islogin =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cuahang = client.getInstance(maychu.BASE_URL).create(cuahang.class);
        btndangnhap = (Button) findViewById(R.id.dangnhap);
        editTextsdt = (EditText) findViewById(R.id.sdtdn);
        editTextmk = (EditText) findViewById(R.id.mkdn);
        Paper.init(this);
        if (Paper.book().read("SDT") != null && Paper.book().read("matkhau") != null) {
             editTextsdt.setText(Paper.book().read("SDT"));
            editTextmk.setText(Paper.book().read("matkhau"));
            if(Paper.book().read("islogin")!=null){
                boolean flag = Paper.book().read("islogin");
                if(flag){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                        //  dangnhap((Paper.book().read("SDT")),Paper.book().read("matkhau"));

                        }


                    },1000);
                }

            }
        }
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String SDT = editTextsdt.getText().toString().trim();
                String matkhau = editTextmk.getText().toString().trim();
                if(TextUtils.isEmpty(SDT)||TextUtils.isEmpty(matkhau) ){
                    Toast.makeText(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin đăng nhập",Toast.LENGTH_SHORT).show();
                }
                else{
                    Paper.book().write("SDT", SDT);
                    Paper.book().write("matkhau", matkhau);
                    dangnhap(SDT,matkhau);
                }

            }
        });
        btndangky = (Button) findViewById(R.id.dangky1);
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DangKy.class);
                startActivity(intent);
            }
        });


        }

    private void dangnhap(String SDT, String matkhau) {
        compositeDisposable.add(cuahang.dangnhap(SDT,matkhau)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        usermd -> {
                            if(usermd.isSuccess())
                            {
                                islogin=true;
                                Paper.book().write("islogin", islogin);
                                maychu.userdangnhap=usermd.getResult().get(0);
                                Paper.book().write("user", usermd.getResult().get(0));
                                Intent intent = new Intent(MainActivity.this, Trangchu.class);
                                startActivity(intent);
                                finish();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"Sai thông tin đăng nhập",Toast.LENGTH_SHORT).show();
                            }

                        },throwable -> {
                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(maychu.userdangnhap.getSDT() != null && maychu.userdangnhap.getMatkhau() != null) {
            editTextsdt.setText(maychu.userdangnhap.getSDT());
            editTextmk.setText(maychu.userdangnhap.getMatkhau());
        }

        }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}


