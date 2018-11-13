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
import com.example.a84965.bookstoreadmin.model.TacGiaCheckList;

import java.util.ArrayList;

public class ChooseAuthorAdapter extends BaseAdapter {

    Context context;
    ArrayList<TacGiaCheckList> list;


    public ChooseAuthorAdapter(Context context, ArrayList<TacGiaCheckList> list) {
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
        TextView txtTG;
        CheckBox checkBoxTG;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_view_multi_select, null);
            viewHolder = new ViewHolder();
            viewHolder.txtTG = convertView.findViewById(R.id.txtMultiSelect);
            viewHolder.checkBoxTG = convertView.findViewById(R.id.cbMultiSelect);
            convertView.setTag(viewHolder);
        }else{
            viewHolder =(ViewHolder)convertView.getTag();
        }

        final TacGiaCheckList tacGiaCheckList = (TacGiaCheckList) getItem(position);
        viewHolder.txtTG.setText(tacGiaCheckList.getTG_Ten());

        if(tacGiaCheckList.isChecked()){
            viewHolder.checkBoxTG.setChecked(true);
        }else{
            viewHolder.checkBoxTG.setChecked(false);
        }



        final ViewHolder finalViewHolder = viewHolder;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AddBookActivity.listKetQuaTG.contains(tacGiaCheckList)){
                    AddBookActivity.listKetQuaTG.remove(tacGiaCheckList);
                }else{
                    AddBookActivity.listKetQuaTG.add(tacGiaCheckList);
                }

                if(tacGiaCheckList.isChecked()){
                    finalViewHolder.checkBoxTG.setChecked(false);
                    tacGiaCheckList.setChecked(false);
                }else{
                    finalViewHolder.checkBoxTG.setChecked(true);
                    tacGiaCheckList.setChecked(true);
                }


            }
        });

        return convertView;
    }
}
