package com.example.a84965.bookstoreadmin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.example.a84965.bookstoreadmin.model.NhaXuatBan;

public class SpinnerNXBAdapter extends ArrayAdapter<NhaXuatBan> {
    private Context context;
    private NhaXuatBan[] values;

    public SpinnerNXBAdapter(@NonNull Context context, int resource, @NonNull NhaXuatBan[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

    @Override
    public int getCount(){
        return values.length;
    }

    @Override
    public NhaXuatBan getItem(int position){
        return values[position];
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // I created a dynamic TextView here, but you can reference your own  custom layout for each spinner item
        TextView label = new TextView(context);
        label.setTextColor(Color.BLACK);
        // Then you can get the current item using the values array (Users array) and the current position
        // You can NOW reference each method you has created in your bean object (User class)
        label.setText(values[position].getNXB_Ten());
        label.setTextSize(15);
        // And finally return your dynamic (or custom) view for each spinner item
        return label;
    }

    // And here is when the "chooser" is popped up
    // Normally is the same view, but you can customize it if you want
    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        TextView label = new TextView(context);
        label.setTextSize(15);
        label.setTextColor(Color.BLACK);
        label.setText(values[position].getNXB_Ten());

        return label;
    }
}
