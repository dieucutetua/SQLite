package com.example.sqllite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ComputerAdapter extends BaseAdapter {
    private MainActivity context;
    private int layout;
    private List<Computer> computerList;

    public ComputerAdapter(MainActivity context, int layout, List<Computer> computerList) {
        this.context = context;
        this.layout = layout;
        this.computerList = computerList;
    }

    @Override
    public int getCount() {
        return computerList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView tvTen;
        ImageView imgSua, imgXoa;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null ){
            viewHolder  = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater)  context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view  = layoutInflater.inflate(layout,null);

            viewHolder.tvTen = (TextView) view.findViewById(R.id.tvTen);
            viewHolder.imgSua = (ImageView) view.findViewById(R.id.btnSua);
            viewHolder.imgXoa = (ImageView) view.findViewById(R.id.btnXoa);
            view.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) view.getTag();



        }
        final Computer computer = computerList.get(i);
        viewHolder.tvTen.setText(computer.getTenMay());

        viewHolder.imgSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DiaglogUpdate(computer.getTenMay(),computer.getId());

            }
        });
        viewHolder.imgXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context,"Xóa",Toast.LENGTH_SHORT).show();
                context.DiaglogDelete(computer.getTenMay(), computer.getId());

            }
        });

        return view;
    }
}
