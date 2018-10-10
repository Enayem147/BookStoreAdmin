package com.example.a84965.bookstoreadmin.activity;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.adapter.OrderAdapter;
import com.example.a84965.bookstoreadmin.model.ChiTietDonHang;
import com.example.a84965.bookstoreadmin.model.DonHang;
import com.example.a84965.bookstoreadmin.ultil.GetChildFirebase;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashSet;

public class OrderActivity extends AppCompatActivity {
    private static DatabaseReference mDatabase;
    ImageView imgRefresh;
    Handler handler;
    private ListView listView;
    private OrderAdapter orderAdapter;
    private ArrayList<ChiTietDonHang> orderList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        callControls();
        initOrders();
        refreshOrders();
    }

    private void refreshOrders() {
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManagementActivity.initOrderList();
                final ProgressDialog progressDialog = new ProgressDialog(OrderActivity.this);
                progressDialog.setMessage("Đang cập nhật ... ");
                progressDialog.show();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initOrders();
                        progressDialog.dismiss();
                    }
                },2000);

            }
        });
    }

    private void initOrders() {
        if(ManagementActivity.orderList == null)
            ManagementActivity.orderList = new ArrayList<>();
        if(ManagementActivity.orderList.size() > 0 ){
            orderList = removeDuplicates(ManagementActivity.orderList);
            orderAdapter = new OrderAdapter(orderList,this);
            listView.setAdapter(orderAdapter);
            orderAdapter.notifyDataSetChanged();
        }
    }

    static ArrayList<ChiTietDonHang> removeDuplicates(ArrayList<ChiTietDonHang> list) {
        // khởi tạo kết quả
        ArrayList<String> checkResult = new ArrayList<>();
        ArrayList<ChiTietDonHang> result = new ArrayList<>();
        // khợi tạo set đựng mã sách để kiểm tra
        HashSet<String> set = new HashSet<>();

        //loại bỏ các mã sách trùng nhau
        for (int i=0 ; i<list.size();i++){
            if(!set.contains(list.get(i).getDH_Ma())){
                checkResult.add(list.get(i).getDH_Ma());
                result.add(list.get(i));
                set.add(list.get(i).getDH_Ma());
            }
        }
        return result;
    }

    private void callControls() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.listView_CusOrder);
        imgRefresh = findViewById(R.id.imageView_Refresh);
        handler = new Handler();
    }
}
