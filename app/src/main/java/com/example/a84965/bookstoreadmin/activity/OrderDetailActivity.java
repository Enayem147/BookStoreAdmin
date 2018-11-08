package com.example.a84965.bookstoreadmin.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.adapter.OrderReviewAdapter;
import com.example.a84965.bookstoreadmin.model.DonHang;
import com.example.a84965.bookstoreadmin.ultil.GetChildFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderDetailActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    Handler handler;
    TextView txtTotal, txtMaHD, txtTen, txtSDT, txtDiaChi;
    ListView listView;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    String sdt = "";
    String maDH = "";
    Toolbar toolbar;
    ArrayList<DonHang> listDonHang;
    OrderReviewAdapter orderReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        callControls();
        initToolBar();
        initBookList();
        initOrderDetail();
    }

    private void initOrderDetail() {

    }

    private void initBookList() {
        orderReviewAdapter = new OrderReviewAdapter(listDonHang,this);
        listView.setAdapter(orderReviewAdapter);
        mDatabase.child("DonHang").child(sdt).addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                DonHang donHang = dataSnapshot.getValue(DonHang.class);
                if(donHang.getDH_Ma().equals(maDH)){
                    listDonHang.add(donHang);
                    orderReviewAdapter.notifyDataSetChanged();
                }
                super.onChildAdded(dataSnapshot, s);
            }
        });
    }

    private void initToolBar() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        toolbar = findViewById(R.id.toolBar_OrderDetail);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Chi tiết đơn hàng");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void callControls() {
        Intent intent = getIntent();
        sdt = intent.getStringExtra("SDT");
        maDH = intent.getStringExtra("MaDH");
        listView = findViewById(R.id.listView_Order);
        listDonHang = new ArrayList<>();
    }
}
