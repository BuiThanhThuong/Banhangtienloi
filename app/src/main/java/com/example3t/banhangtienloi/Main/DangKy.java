package com.example3t.banhangtienloi.Main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example3t.banhangtienloi.ketnoi.client;
import com.example3t.banhangtienloi.ketnoi.cuahang;
import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.ketnoi.maychu;
import com.example3t.banhangtienloi.model.user;
import com.example3t.banhangtienloi.model.usermd;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class    DangKy extends AppCompatActivity {
    private Button btndangky;
    private EditText editTextsdtdk,editTextmkdk,editTextmkdk2,editTexttenuser,editTextgmail;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    cuahang cuahang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

       // dangky();
        btndangky = (Button) findViewById(R.id.DangKy);
        editTextsdtdk=(EditText) findViewById((R.id.sdtdk));
        editTextmkdk=(EditText) findViewById((R.id.mkdk));
        editTextmkdk2=(EditText) findViewById((R.id.mkdk2));
        editTexttenuser=(EditText) findViewById((R.id.tenuser));
        editTextgmail=(EditText) findViewById((R.id.gmail));
        cuahang = client.getInstance(maychu.BASE_URL).create(cuahang.class);

        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    rundangky();
                String SDT = editTextsdtdk.getText().toString().trim();
                String matkhau = editTextmkdk.getText().toString().trim();
                String matkhau2 = editTextmkdk2.getText().toString().trim();
                String tenuser = editTexttenuser.getText().toString().trim();
                String gmail = editTextgmail.getText().toString().trim();
                if(TextUtils.isEmpty(SDT)||TextUtils.isEmpty(matkhau) || TextUtils.isEmpty(matkhau2) || TextUtils.isEmpty(tenuser) ||TextUtils.isEmpty(gmail)){
                    Toast.makeText(getApplicationContext(),"Vui l??ng nh???p ?????y ????? th??ng tin ????ng k??",Toast.LENGTH_SHORT).show();
                }
                else if (editTextsdtdk.getText().length() !=10||editTextmkdk.getText().length() >16||editTextmkdk.getText().length() < 6){
                    Toast.makeText(getApplicationContext(),"Vui l??ng nh???p ????ng th??ng tin ????ng k??: S??? ??i???n tho???i g???m 10 ch??? s???, m???t kh???u c?? ????? d??i t???i ??a l?? 16 k?? t??? v?? t???i thi???u l??? 6 k?? t???",Toast.LENGTH_SHORT).show();
                }
                    else {
                    if(matkhau.equals(matkhau2)){
                        compositeDisposable.add(cuahang.user(SDT,matkhau,tenuser,gmail)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        usermd -> {
                                            if(usermd.isSuccess()){
                                                maychu.userdangnhap.setSDT(SDT);
                                                maychu.userdangnhap.setMatkhau(matkhau);
                                                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                                                startActivity(intent);
                                                Toast.makeText(getApplicationContext(),"????ng k?? th??nh c??ng",Toast.LENGTH_SHORT).show();
                                                xoa();
                                                finish();

                                            }else{
                                                Toast.makeText(getApplicationContext(),usermd.getMessage(),Toast.LENGTH_SHORT).show();
                                            }

                                        },throwable -> {
                                            Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                                        }
                                ));

                    } else {
                        Toast.makeText(getApplicationContext(),"M???t kh???u kh??ng tr??ng kh???p",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void xoa() {
        editTextsdtdk.getText().clear();
        editTextmkdk.getText().clear();
        editTextmkdk2.getText().clear();
        editTexttenuser.getText().clear();
        editTextgmail.getText().clear();
    }
    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}