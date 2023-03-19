package com.example.asm.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.asm.R;
import com.example.asm.models.View_mot_o;

public class CoursesAdapter extends BaseAdapter {

    String []ten = {"Khóa học","Đã đăng ký"};
    int []hinh = {R.drawable.ic_khoa_hoc, R.drawable.ic_khoa_hoc_da_dang_ki};

    Context context;

    public CoursesAdapter (Context context){
        this.context = context;
    }

    @Override
    public int getCount() {
        return hinh.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View_mot_o mot_o = new View_mot_o();
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        convertView = layoutInflater.inflate(R.layout.item_list_courses,null);

        mot_o.textView = convertView.findViewById(R.id.tvNameCourse_List);
        mot_o.imageView = convertView.findViewById(R.id.iconListCourse);

        mot_o.imageView.setImageResource(hinh[position]);
        mot_o.textView.setText(ten[position]);
        return convertView;
    }
}
