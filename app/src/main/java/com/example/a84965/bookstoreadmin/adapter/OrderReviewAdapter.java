package com.example.a84965.bookstoreadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.model.DonHang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class OrderReviewAdapter extends BaseAdapter {

    ArrayList<DonHang> list;
    Context context;
    DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

    public OrderReviewAdapter(ArrayList<DonHang> list, Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView txtTenSach,txtDonGia,txtSoLuong;
        ImageView imgHinhAnh;
        LayoutInflater inflater  = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.list_view_order_reviews,null);
        txtTenSach = convertView.findViewById(R.id.txtInvoice_TenSach);
        txtSoLuong = convertView.findViewById(R.id.txtInvoice_SoLuong);
        imgHinhAnh = convertView.findViewById(R.id.imgInvoice_HinhSach);
        // Khởi tạo từng sản phẩm cho listview Giỏ hàng review ( trang invoice )
        final DonHang donHang = (DonHang)getItem(position);
        txtSoLuong.setText(donHang.getSach_SL()+"");
        txtTenSach.setText(donHang.getSach_Ten());
        Picasso.get()
                .load(donHang.getSach_HinhAnh())
                .into(imgHinhAnh);
        return convertView;
    }
}
