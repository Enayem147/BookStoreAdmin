package com.example.a84965.bookstoreadmin.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Toast;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.model.ChiTietDonHang;
import com.example.a84965.bookstoreadmin.model.DonHang;
import com.example.a84965.bookstoreadmin.ultil.GetChildFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManagementActivity extends AppCompatActivity {

    GridLayout gridLayout;
    private static DatabaseReference mDatabase;
    static public ArrayList<ChiTietDonHang> orderList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management);
        callControls();
        initOrderList();
        setEventGridLayout(gridLayout);
    }

    public static void initOrderList() {
        orderList = new ArrayList<>();
        mDatabase.child("DonHang").addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final String sdt = dataSnapshot.getKey();
                mDatabase.child("DonHang").child(sdt).addChildEventListener(new GetChildFirebase() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        DonHang donHang = dataSnapshot.getValue(DonHang.class);
                        orderList.add(new ChiTietDonHang(donHang.getDH_Ma(),donHang.getDH_NgayDat(),donHang.getDH_TrangThai(),sdt));
                        super.onChildAdded(dataSnapshot, s);
                    }
                });
                super.onChildAdded(dataSnapshot, s);
            }
        });
    }

    private void callControls() {
        gridLayout = findViewById(R.id.rl_management);
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void setEventGridLayout(GridLayout eventGridLayout) {
        for(int i =0 ; i<eventGridLayout.getChildCount();i++){
            CardView cardView = (CardView) eventGridLayout.getChildAt(i);
            final int finalI = i;
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (finalI){
                        case 0 :
                            Intent intentOrder = new Intent(ManagementActivity.this,OrderActivity.class);
                            startActivity(intentOrder);
                            break;
                        case 1 :
                            Toast.makeText(ManagementActivity.this, "Sách", Toast.LENGTH_SHORT).show();
                            break;
                        case 2 :
                            Toast.makeText(ManagementActivity.this, "Kho", Toast.LENGTH_SHORT).show();
                            break;
                        case 3 :
                            Toast.makeText(ManagementActivity.this, "Quảng cáo", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }
}
