package com.example.a84965.bookstoreadmin.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayout;
import android.view.View;
import android.widget.Toast;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.model.ChiTietDonHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageBookActivity extends AppCompatActivity {
    GridLayout gridLayout;
    private static DatabaseReference mDatabase;
    static public ArrayList<ChiTietDonHang> orderList;
    public static int nxbCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_book);
        callControls();
        setEventGridLayout(gridLayout);
        getCountNXB();
    }

    private void getCountNXB() {
        mDatabase.child("NhaXuatBan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    nxbCount = (int)dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void callControls() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        gridLayout = findViewById(R.id.rl_manageBook);
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
                            Intent intentOrder = new Intent(ManageBookActivity.this,BookListActivity.class);
                            startActivity(intentOrder);
                            break;
                        case 1 :
                            Intent intentBook = new Intent(ManageBookActivity.this,OrderActivity.class);
                            startActivity(intentBook);
                            break;
                        case 2 :
                            Toast.makeText(ManageBookActivity.this, "Tác giả", Toast.LENGTH_SHORT).show();
                            break;
                        case 3 :
                            Toast.makeText(ManageBookActivity.this, "Giảm giá", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });
        }
    }
}
