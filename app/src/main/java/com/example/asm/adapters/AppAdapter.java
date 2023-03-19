package com.example.asm.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.asm.R;
import com.example.asm.models.View_mot_o;

public class AppAdapter extends BaseAdapter {

    String []ten = {"Courses","Map","News","Social"};
    int []hinh = {R.drawable.courses, R.drawable.map, R.drawable.news, R.drawable.social};

    Context context;

    public AppAdapter (Context context){
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
        convertView = layoutInflater.inflate(R.layout.item_app,null);

        mot_o.textView = convertView.findViewById(R.id.tenIcon);
        mot_o.imageView = convertView.findViewById(R.id.icon);

        mot_o.imageView.setImageResource(hinh[position]);
        mot_o.textView.setText(ten[position]);
        return convertView;
    }
}
