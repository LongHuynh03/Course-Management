package com.example.asm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.asm.R;
import com.example.asm.models.Courses;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter {
    private ArrayList<Courses> list;
    private int po;

    public CourseAdapter(ArrayList<Courses> list) {
        this.list = list;
    }

    public CourseAdapter(ArrayList<Courses> list, int po){
        this.list = list;
        this.po = po;
    }

    public Courses getCourseUpdate(){
        Courses courses = list.get(po);
        return courses;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
//            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);

            view = View.inflate(parent.getContext(), R.layout.item_course, null);
            TextView tvCourseName = view.findViewById(R.id.tvName);
            TextView tvCourseCode = view.findViewById(R.id.tvCode);
            TextView tvCourseRoom = view.findViewById(R.id.tvRoom);
            TextView tvCourseTime = view.findViewById(R.id.tvTime);
            TextView tvCourseDay = view.findViewById(R.id.tvDay);
            TextView tvCourseLecturer = view.findViewById(R.id.tvLecturer);
            TextView tvCourseDangKi = view.findViewById(R.id.tvDangKyCourse);

            ViewHolder holder = new ViewHolder(tvCourseName, tvCourseCode, tvCourseRoom, tvCourseTime, tvCourseDay, tvCourseLecturer);
            view.setTag(holder);
        }
        Courses course = (Courses) getItem(position);
        ViewHolder holder1 = (ViewHolder) view.getTag();

        if (course.getName() != null){
            holder1.tvCourseName.setText(course.getName());
            holder1.tvCourseCode.setText(course.getCode());
            holder1.tvCourseTime.setText(course.getTime());
            holder1.tvCourseDay.setText(course.getDay());
            holder1.tvCourseLecturer.setText(course.getLecturer());
            holder1.tvCourseRoom.setText(course.getRoom());
        }
        return view;
    }

    public static class ViewHolder {
        TextView tvCourseName, tvCourseCode, tvCourseRoom, tvCourseTime, tvCourseDay, tvCourseLecturer;

        public ViewHolder() {
        }

        public ViewHolder(TextView tv1, TextView tv2, TextView tv3, TextView tv4, TextView tv5, TextView tv6) {
            tvCourseName = tv1;
            tvCourseCode = tv2;
            tvCourseRoom = tv3;
            tvCourseTime = tv4;
            tvCourseDay = tv5;
            tvCourseLecturer = tv6;
        }
    }
}
