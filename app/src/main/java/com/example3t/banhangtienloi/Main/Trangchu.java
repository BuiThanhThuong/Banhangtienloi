package com.example3t.banhangtienloi.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DownloadManager;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;


import com.example3t.banhangtienloi.ketnoi.cuahang;

import com.example3t.banhangtienloi.R;
import com.example3t.banhangtienloi.ketnoi.client;
import com.example3t.banhangtienloi.model.mathang;
import com.example3t.banhangtienloi.model.user;
import com.google.android.material.navigation.NavigationView;
import com.example3t.banhangtienloi.ketnoi.maychu;
import com.example3t.banhangtienloi.adapter.mathangadapter;
import com.example3t.banhangtienloi.model.mathangmd;
import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class Trangchu extends AppCompatActivity {

    NavigationView navigationView;
    ListView listView;
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    ArrayList<mathang> mathangs;
    mathangadapter mathangadapterr;
    CompositeDisposable compositeDisposable= new CompositeDisposable();
    cuahang cuahang;
    NotificationBadge badge;
    List<mathang> mathangg;
    FrameLayout frameLayout,frameLayout2,frameLayout3,frameLayout4,frameLayout5;
    EditText searchView;
    ImageView timiemic;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        cuahang = client.getInstance(maychu.BASE_URL).create(cuahang.class);
        Paper.init(this);
        if(Paper.book().read("user")!=null){
            user userr = Paper.book().read("user");
            maychu.userdangnhap=userr;
        }
        Anhxa();
        dsmathang();
        acctionToolBar();
        menuu();

    }

    private void menuu() {
        frameLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Trangchu.class);
                startActivity(intent);
            }
        });
        frameLayout4.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Xemdonhang.class);
                startActivity(intent);
            }
        }));
        frameLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paper.book().delete("user");
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void acctionToolBar() {

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_menu_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void dsmathang() {

        compositeDisposable.add(cuahang.getThemsanpham()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        mathangmd -> {
                            if(mathangmd.isSuccess()) {

                                    mathangg = mathangmd.getResult();
                                    mathangadapterr = new mathangadapter(getApplicationContext(), mathangg);
                                    recyclerView.setAdapter(mathangadapterr);
                                }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(),"Không kết nối được " + throwable.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                ));

    }




    private void Anhxa() {
        toolbar=findViewById(R.id.tbtc);
        navigationView = findViewById(R.id.naviga);
        listView = findViewById(R.id.listview);
        recyclerView = findViewById(R.id.recyview);
        drawerLayout = findViewById(R.id.iddrawblelayout);
        mathangs = new ArrayList<>();
        mathangadapterr = new mathangadapter(getApplicationContext(), mathangs);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        frameLayout=findViewById(R.id.giohangfr);
        frameLayout2=findViewById(R.id.frane2);
        frameLayout3=findViewById(R.id.frmmenu);
        frameLayout4=findViewById(R.id.frmmen4);
        frameLayout5=findViewById(R.id.frmmen5);
        recyclerView.setAdapter(mathangadapterr);
        mathangg =new ArrayList<>();
        badge = findViewById(R.id.solg);
        if(maychu.dshang==null){
            maychu.dshang= new ArrayList<>();
        }else{
            int solgtthem = 0;
            for(int i =0; i<maychu.dshang.size();i++) {
                solgtthem = solgtthem + maychu.dshang.get(i).getSoluong();
            }
            badge.setText(String.valueOf(solgtthem));
        }
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Giohang.class);
                startActivity(intent);
            }
        });
        frameLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(View.VISIBLE);


            }
        });
        timiemic=findViewById(R.id.timkiemic);
        timiemic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                searchView.setVisibility(View.VISIBLE);

            }
        });
        searchView =findViewById(R.id.timkiem);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                timkiem();

            }

            private void timkiem() {
                String timkiem = searchView.getText().toString().trim();
                    compositeDisposable.add(cuahang.seach(timkiem)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    mathangmd -> {
                                        if (mathangmd.isSuccess()) {
                                            mathangadapterr = new mathangadapter(getApplicationContext(), mathangmd.getResult());
                                            recyclerView.setAdapter(mathangadapterr);

                                        }


                                    }, throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }

                            ));
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        int solgtthem = 0;
        for(int i =0; i<maychu.dshang.size();i++) {
            solgtthem = solgtthem + maychu.dshang.get(i).getSoluong();
        }
        badge.setText(String.valueOf(solgtthem));
    }
}
