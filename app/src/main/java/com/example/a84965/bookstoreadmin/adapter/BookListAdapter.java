package com.example.a84965.bookstoreadmin.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.model.LoaiSach;
import com.example.a84965.bookstoreadmin.model.NhaXuatBan;
import com.example.a84965.bookstoreadmin.model.Sach;
import com.example.a84965.bookstoreadmin.model.TacGia;
import com.example.a84965.bookstoreadmin.model.TacGiaChiTiet;
import com.example.a84965.bookstoreadmin.model.TheLoai;
import com.example.a84965.bookstoreadmin.ultil.GetChildFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class BookListAdapter extends BaseAdapter {
    Activity context;
    ArrayList<Sach> list;
    String nhaXB = "";
    private DatabaseReference mDatabase;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    public BookListAdapter(Activity context, ArrayList<Sach> list) {
        this.context = context;
        this.list = list;
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        convertView = inflater.inflate(R.layout.list_view_book_list, null);
        ImageView imgBookList = convertView.findViewById(R.id.imgBookList);
        TextView txtTenSach = convertView.findViewById(R.id.txtBookList_TenSach);
        final TextView txtTG = convertView.findViewById(R.id.txtBookList_TacGia);
        final TextView txtNhaXB = convertView.findViewById(R.id.txtBookList_NhaXB);
        final TextView txtTL = convertView.findViewById(R.id.txtBookList_TheLoai);
        TextView txtNamXB = convertView.findViewById(R.id.txtBookList_NamXB);
        TextView txtSoTrang = convertView.findViewById(R.id.txtBookList_SoTrang);
        TextView txtGia = convertView.findViewById(R.id.txtBookList_Gia);

        ImageButton btnEdit = convertView.findViewById(R.id.btnBookList_Edit);
        ImageButton btnDelete = convertView.findViewById(R.id.btnBookList_Delete);

        final Sach sach = (Sach) getItem(position);
        txtTenSach.setText(sach.getSach_Ten());
        txtNamXB.setText(sach.getSach_NamXB()+"");
        txtSoTrang.setText(sach.getSach_SoTrang()+"");
        txtGia.setText(decimalFormat.format(sach.getSach_DonGia()) + " đ");

        //Lấy tên nhà xuất bản
        mDatabase.child("NhaXuatBan").addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                NhaXuatBan nxb = dataSnapshot.getValue(NhaXuatBan.class);
                if (nxb.getNXB_Ma().equals(sach.getNXB_Ma())) {
                    txtNhaXB.setText(nxb.getNXB_Ten());
                    nhaXB = nxb.getNXB_Ten();
                }
                super.onChildAdded(dataSnapshot, s);
            }
        });

        //Lấy tên tác giả
        final ArrayList<String> listTG = new ArrayList<>();
        mDatabase.child("TacGiaChiTiet").addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final TacGiaChiTiet tacGiaChiTiet = dataSnapshot.getValue(TacGiaChiTiet.class);
                if (tacGiaChiTiet.getSach_Ma().equals(sach.getSach_Ma())) {
                    mDatabase.child("TacGia").addChildEventListener(new GetChildFirebase() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            TacGia tacGia = dataSnapshot.getValue(TacGia.class);
                            if (tacGia.getTG_Ma().equals(tacGiaChiTiet.getTG_Ma())) {
                                // lấy danh sách đồng tác giả
                                listTG.add(tacGia.getTG_Ten());
                                String strTG = "";
                                strTG = listTG.get(0);
                                for (int i = 1; i < listTG.size(); i++) {
                                    strTG += " , " + listTG.get(i);
                                }
                                txtTG.setText(strTG);
                            }
                            super.onChildAdded(dataSnapshot, s);
                        }
                    });
                }
                super.onChildAdded(dataSnapshot, s);
            }
        });

        //lấy danh sách các thể loại
        final ArrayList<String> listTL = new ArrayList<>();
        mDatabase.child("LoaiSach").addChildEventListener(new GetChildFirebase() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                final LoaiSach loaiSach = dataSnapshot.getValue(LoaiSach.class);
                if (loaiSach.getSach_Ma().equals(sach.getSach_Ma())) {
                    mDatabase.child("TheLoai").addChildEventListener(new GetChildFirebase() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            TheLoai theLoai = dataSnapshot.getValue(TheLoai.class);
                            if (theLoai.getTL_Ma() == loaiSach.getTL_Ma()) {
                                listTL.add(theLoai.getTL_Ten());
                                String strTL = "";
                                strTL = listTL.get(0);
                                for (int i = 1; i < listTL.size(); i++) {
                                    strTL += " , " + listTL.get(i);
                                }
                                txtTL.setText(strTL);
                            }
                            super.onChildAdded(dataSnapshot, s);
                        }
                    });
                }
                super.onChildAdded(dataSnapshot, s);
            }
        });



        //set hình
        Picasso.get()
                .load(sach.getSach_HinhAnh())
                .into(imgBookList);

        return convertView;
    }
}
