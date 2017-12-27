package com.gangtok.nielit.skdirectory.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.model.Employee;

import java.util.List;

/**
 * Created by NgocTri on 11/7/2015.
 */
public class ListEmployeeAdapter extends BaseAdapter {
    private Context mContext;
    private List<Employee> mEmployeeList;

    public ListEmployeeAdapter(Context mContext, List<Employee> mEmployeeList) {
        this.mContext = mContext;
        this.mEmployeeList = mEmployeeList;
    }

    @Override
    public int getCount() {
        return mEmployeeList.size();
    }

    @Override
    public Object getItem(int position) {
        return mEmployeeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mEmployeeList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.employee_listview, null);
        TextView tvName = (TextView)v.findViewById(R.id.tv_employee_name);
        TextView tvDesignation=(TextView)v.findViewById(R.id.tv_designation);
        tvName.setText(mEmployeeList.get(position).getEmpName());
        tvDesignation.setText(mEmployeeList.get(position).getDesignation_Name());
        return v;
    }
}
