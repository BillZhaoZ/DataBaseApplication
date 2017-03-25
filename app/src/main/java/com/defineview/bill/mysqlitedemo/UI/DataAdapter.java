package com.defineview.bill.mysqlitedemo.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.defineview.bill.mysqlitedemo.R;
import com.defineview.bill.mysqlitedemo.greendao.entity.UserTwo;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询数据适配器
 */
public class DataAdapter extends BaseAdapter {

    private List<UserTwo> userList = new ArrayList<UserTwo>();
    private LayoutInflater layoutInflater;
    ListView mylistView;

    private Context context;
    private ViewHolder viewHolder;

    public DataAdapter(List userList, Context context) {
        this.userList = userList;
        this.context = context;
        layoutInflater = LayoutInflater.from(this.context);
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public UserTwo getItem(int position) {
        return userList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.user_list_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.tv_id = (TextView) convertView.findViewById(R.id.tv_user_id);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            viewHolder.tv_sex = (TextView) convertView.findViewById(R.id.tv_user_addest);

            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();

        UserTwo user = userList.get(position);
        viewHolder.tv_id.setText(String.valueOf(user.getId()));
        viewHolder.tv_name.setText(user.getName());
        viewHolder.tv_sex.setText(user.getAddTest());

        return convertView;
    }

    class ViewHolder {
        TextView tv_id;
        TextView tv_name;
        TextView tv_sex;
    }
}
