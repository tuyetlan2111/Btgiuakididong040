package com.example.lan.moneyloverapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Lan on 4/6/2018.
 */

public class Adapter extends BaseAdapter {
    Context context;
    int layout;
    List<InforMoney> list;

    public Adapter(Context context, int layout, List<InforMoney> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }



    private class ViewHolder{
        TextView tvnoidung, tvloai,tvsotien,tvngay;
}

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            viewHolder = new ViewHolder();
            viewHolder.tvnoidung = view.findViewById(R.id.tvnoidung);
//            viewHolder.tvloai = view.findViewById(R.id.tvloai);
            viewHolder.tvsotien = view.findViewById(R.id.tvtien);
            viewHolder.tvngay = view.findViewById(R.id.tvngay);
            view.setTag(viewHolder);
        }

        else {
            viewHolder = (ViewHolder) view.getTag();
        }

        InforMoney inforMoney = (InforMoney) getItem(i);
        viewHolder.tvnoidung.setText(inforMoney.getNoidung());
        viewHolder.tvsotien.setText(inforMoney.getTien()+"");
        if(!inforMoney.isThu()){
            viewHolder.tvsotien.setTextColor(view.getResources().getColor(R.color.colorAccent));
        }
        viewHolder.tvngay.setText(inforMoney.getNgay());
        return view;
    }
}
