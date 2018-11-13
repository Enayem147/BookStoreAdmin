package com.example.a84965.bookstoreadmin.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.activity.OrderDetailActivity;
import com.example.a84965.bookstoreadmin.model.ChiTietDonHang;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderAdapter extends BaseAdapter {
    ArrayList<ChiTietDonHang> list;
    Activity context;

    public OrderAdapter(ArrayList<ChiTietDonHang> list, Activity context) {
        this.list = list;
        this.context = context;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final TextView txtMaDH,txtKH,txtThoiGian,txtTrangThai;
        ImageView imgEdit,imgDetail;
        LayoutInflater inflater  = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_view_order,null);
        txtMaDH = convertView.findViewById(R.id.txtCusOrder_DH);
        txtKH = convertView.findViewById(R.id.txtCusOrder_KH);
        txtThoiGian = convertView.findViewById(R.id.txtCusOrder_NgayMua);
        txtTrangThai = convertView.findViewById(R.id.txtCusOrder_TrangThai);
        imgEdit = convertView.findViewById(R.id.imgEdit);
        imgDetail = convertView.findViewById(R.id.imgOrderDetail);

        final  ChiTietDonHang chiTietDonHang = (ChiTietDonHang) getItem(position);
        txtMaDH.setText(chiTietDonHang.getDH_Ma());
        txtKH.setText(chiTietDonHang.getKH_SDT());
        txtThoiGian.setText(chiTietDonHang.getDH_NgayDat());
        switch (chiTietDonHang.getDH_TrangThai()){
            case 1:
                txtTrangThai.setText("Chưa giao");
                txtTrangThai.setTextColor(Color.rgb(169,169,169));
                break;
            case 2:
                txtTrangThai.setText("Đang giao");
                txtTrangThai.setTextColor(Color.rgb(0,0,255));
                break;
            case 3:
                txtTrangThai.setText("Đã giao");
                txtTrangThai.setTextColor(Color.rgb(0,255,0));
                break;
            case 4:
                txtTrangThai.setText("Tạm hoãn");
                txtTrangThai.setTextColor(Color.rgb(255,0,0));
                break;
        }

        // Edit trạng thái
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_order_status);

                final RadioButton rdDaGiao,rdDangGiao,rdTamHoan;
                Button btnXacNhan;

                rdDaGiao = dialog.findViewById(R.id.rdDaGiao);
                rdDangGiao = dialog.findViewById(R.id.rdDangGiao);
                rdTamHoan = dialog.findViewById(R.id.rdTamHoan);
                btnXacNhan = dialog.findViewById(R.id.btnDialog_XN);

                switch (chiTietDonHang.getDH_TrangThai()){
                    case 2:
                        rdDangGiao.setChecked(true);
                        break;
                    case 3:
                        rdDaGiao.setChecked(true);
                        break;
                    case 4:
                        rdTamHoan.setChecked(true);
                        break;
                }

                btnXacNhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int status = 0;
                        if(rdDangGiao.isChecked()){
                            status = 2;
                        }
                        if(rdDaGiao.isChecked()){
                            status = 3;
                        }
                        if(rdTamHoan.isChecked()){
                            status = 4;
                        }

                        // update trang thái đơn hàng lên csdl
                        DatabaseReference updateTrangThai = FirebaseDatabase.getInstance().getReference("DonHang").child(chiTietDonHang.getKH_SDT());
                        final int finalStatus = status;
                        updateTrangThai.orderByChild("dh_Ma").equalTo(chiTietDonHang.getDH_Ma()).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                                        snapshot.getRef().child("dh_TrangThai").setValue(finalStatus);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(context, "Có lỗi xảy ra !!!", Toast.LENGTH_SHORT).show();
                            }
                        });

                        //
                        chiTietDonHang.setDH_TrangThai(status);
                        notifyDataSetChanged();
                        dialog.dismiss();

                    }
                });
                dialog.show();
            }
        });

        imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OrderDetailActivity.class);
                intent.putExtra("SDT",txtKH.getText().toString());
                intent.putExtra("MaDH",txtMaDH.getText().toString());
                context.startActivity(intent);
            }
        });

        return convertView;
    }


}
