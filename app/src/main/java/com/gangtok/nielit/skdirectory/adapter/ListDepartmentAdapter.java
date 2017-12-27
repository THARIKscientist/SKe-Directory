package com.gangtok.nielit.skdirectory.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gangtok.nielit.skdirectory.R;
import com.gangtok.nielit.skdirectory.model.Department;

import java.util.List;

/**
 * Created by NgocTri on 11/7/2015.
 */
public class ListDepartmentAdapter extends BaseAdapter {
    private Context mContext;
    private List<Department> mDepartmentList;

    public ListDepartmentAdapter(Context mContext, List<Department> mDepartmentList) {
        this.mContext = mContext;
        this.mDepartmentList = mDepartmentList;
    }

    @Override
    public int getCount() {
        return mDepartmentList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDepartmentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mDepartmentList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.department_listview, null);
        TextView tvName = (TextView)v.findViewById(R.id.textview_department_name);

        tvName.setText(mDepartmentList.get(position).getName());

        return v;
    }
}
