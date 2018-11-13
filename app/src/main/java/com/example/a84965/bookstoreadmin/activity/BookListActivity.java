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
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.adapter.BookListAdapter;
import com.example.a84965.bookstoreadmin.model.LoaiSach;
import com.example.a84965.bookstoreadmin.model.NhaXuatBan;
import com.example.a84965.bookstoreadmin.model.Sach;
import com.example.a84965.bookstoreadmin.model.TacGia;
import com.example.a84965.bookstoreadmin.model.TacGiaChiTiet;
import com.example.a84965.bookstoreadmin.model.TatCaSach;
import com.example.a84965.bookstoreadmin.model.TheLoai;
import com.example.a84965.bookstoreadmin.ultil.GetChildFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {
    Handler handler = new Handler();
    Button btnThem;
    private static DatabaseReference mDatabase;
    ArrayList<Sach> listBook;
    Toolbar toolbar;
    BookListAdapter bookListAdapter;
    ListView listView;
    int i = 0;
    public static NhaXuatBan[] listNhaXB ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        callControls();
        initToolBar();
        initBookList();
        initListNXB();
        themSach();
    }

    private void initListNXB() {
        listNhaXB = new NhaXuatBan[ManageBookActivity.nxbCount];
        mDatabase.child("NhaXuatBan").addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NhaXuatBan nxb = dataSnapshot.getValue(NhaXuatBan.class);
                listNhaXB[i] = new NhaXuatBan();
                listNhaXB[i].setNXB_Ma(nxb.getNXB_Ma());
                listNhaXB[i].setNXB_Ten(nxb.getNXB_Ten());
                i++;
                super.onChildAdded(dataSnapshot, s);
            }
        });
    }


    private void themSach() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookListActivity.this, AddBookActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initBookList() {
        listBook = new ArrayList<>();
        bookListAdapter = new BookListAdapter(this, listBook);
        listView.setAdapter(bookListAdapter);
        mDatabase.child("Sach").addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final Sach sach = dataSnapshot.getValue(Sach.class);
                listBook.add(sach);
                bookListAdapter.notifyDataSetChanged();
                super.onChildAdded(dataSnapshot, s);
            }
        });
    }

    private void initToolBar() {
        toolbar = findViewById(R.id.toolBar_BookList);
        toolbar.setNavigationIcon(R.drawable.ic_menu_back);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Danh sách sản phẩm");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void callControls() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = findViewById(R.id.listView_BookList);
        btnThem = findViewById(R.id.btnThemSach);
    }
}
