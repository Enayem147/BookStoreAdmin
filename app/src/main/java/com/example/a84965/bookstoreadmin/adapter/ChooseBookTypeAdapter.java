package com.example.a84965.bookstoreadmin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.a84965.bookstoreadmin.R;
import com.example.a84965.bookstoreadmin.activity.AddBookActivity;
import com.example.a84965.bookstoreadmin.model.TheLoaiCheckList;

import java.util.ArrayList;

public class ChooseBookTypeAdapter extends BaseAdapter{

    Context context;
    ArrayList<TheLoaiCheckList> list;




    public ChooseBookTypeAdapter(Context context, ArrayList<TheLoaiCheckList> list) {
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

    class ViewHolder{
        TextView txtTL;
        CheckBox checkBoxTL;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_view_multi_select, null);
            viewHolder = new ViewHolder();
            viewHolder.txtTL = convertView.findViewById(R.id.txtMultiSelect);
            viewHolder.checkBoxTL = convertView.findViewById(R.id.cbMultiSelect);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        final TheLoaiCheckList theLoaiCheckList = (TheLoaiCheckList) getItem(position);
        viewHolder.txtTL.setText(theLoaiCheckList.getTL_Ten());

        if(theLoaiCheckList.isChecked()){
            viewHolder.checkBoxTL.setChecked(true);
        }else{
            viewHolder.checkBoxTL.setChecked(false);
        }



        final ViewHolder finalViewHolder = viewHolder;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddBookActivity.listKetQuaTL.contains(theLoaiCheckList)){
                    AddBookActivity.listKetQuaTL.remove(theLoaiCheckList);
                }else{
                    AddBookActivity.listKetQuaTL.add(theLoaiCheckList);
                }

                if(theLoaiCheckList.isChecked()){
                    finalViewHolder.checkBoxTL.setChecked(false);
                    theLoaiCheckList.setChecked(false);
                }else{
                    finalViewHolder.checkBoxTL.setChecked(true);
                    theLoaiCheckList.setChecked(true);
                }


            }
        });

        return convertView;
    }
}
