package com.example.asm.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.asm.R;
import com.example.asm.adapters.CourseAdapter;
import com.example.asm.models.Courses;
import com.example.asm.services.CourseService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;

public class DanhSachKhoaHocActivity extends AppCompatActivity {
    GridView gridView;
    FloatingActionButton floatingActionButton;
    String time, date;
    AlertDialog dialog;
    ArrayList<Courses> listUpdate;
    Courses coursesUpdate;

    private CourseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_khoa_hoc);

        gridView = findViewById(R.id.gvThongTin);
        floatingActionButton = findViewById(R.id.ftAddCourse);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddCourse();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DanhSachKhoaHocActivity.this, position+"", Toast.LENGTH_SHORT).show();
                CourseAdapter courseAdapter = new CourseAdapter(listUpdate,position);
                coursesUpdate = courseAdapter.getCourseUpdate();
                System.out.println("ID: " +coursesUpdate.getId());
                onLongClick();
                return true;
            }
        });

    }

    public void onLongClick(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Thao tác")
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // START THE GAME!
                        Toast.makeText(DanhSachKhoaHocActivity.this, "Update", Toast.LENGTH_SHORT).show();
                        onUpdateCourse();
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        Toast.makeText(DanhSachKhoaHocActivity.this, "Delete", Toast.LENGTH_SHORT).show();
                        onDeleteCOurse();
                    }
                }).create().show();
    }

    public void onDeleteCOurse(){
        Intent intent = new Intent(DanhSachKhoaHocActivity.this,CourseService.class);
        intent.setAction(CourseService.COURSE_SERVICE_ACTION_DELETE);
        intent.putExtra("id",coursesUpdate.getId());
        startService(intent);
        onGetCourse();
    }

    public void onUpdateCourse(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_addcourse,null);
        EditText edtNameCourse = dialogLayout.findViewById(R.id.edtNameCourse);
        EditText edtCodeCourse = dialogLayout.findViewById(R.id.edtCodeCourse);
        EditText edtRoomCourse = dialogLayout.findViewById(R.id.edtRoomCourse);
        EditText edtLecturerCourse = dialogLayout.findViewById(R.id.edtLecturerCourse);
        TextView tvGetTimeCourse = dialogLayout.findViewById(R.id.tvGetTimeCourse);
        TextView tvGetCalenderCourse = dialogLayout.findViewById(R.id.tvGetCalenderCourse);
        Button btnAddCourse = dialogLayout.findViewById(R.id.btnSaveCourse);
        Button btnCancelCourse = dialogLayout.findViewById(R.id.btnCacelCourse);


        edtNameCourse.setText(coursesUpdate.getName());
        edtCodeCourse.setText(coursesUpdate.getCode());
        edtRoomCourse.setText(coursesUpdate.getRoom());
        edtLecturerCourse.setText(coursesUpdate.getLecturer());
        tvGetCalenderCourse.setText(coursesUpdate.getDay());
        tvGetTimeCourse.setText(coursesUpdate.getTime());

        time = coursesUpdate.getTime();
        date = coursesUpdate.getDay();

        Calendar lich = Calendar.getInstance();

        //Lấy time
        TimePickerDialog tpdiablog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time = hourOfDay + " : " + minute;
                        tvGetTimeCourse.setText(time);
                    }
                },lich.get(Calendar.HOUR),
                lich.get(Calendar.MINUTE),
                true);

        //Lấy date
        DatePickerDialog dpdiablog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        tvGetCalenderCourse.setText(date);
                    }
                },
                lich.get(Calendar.YEAR),
                lich.get(Calendar.MONTH),
                lich.get(Calendar.DAY_OF_MONTH));

        tvGetTimeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpdiablog.show();
            }
        });

        tvGetCalenderCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpdiablog.show();
            }
        });

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = edtNameCourse.getText().toString();
                    String code = edtCodeCourse.getText().toString();
                    String room = edtRoomCourse.getText().toString();
                    String lecturer = edtLecturerCourse.getText().toString();

                    if (name.isEmpty() || code.isEmpty() || room.isEmpty() || lecturer.isEmpty() || time.isEmpty() || date.isEmpty()){
                        Toast.makeText(DanhSachKhoaHocActivity.this, "Thiếu thông tin", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(DanhSachKhoaHocActivity.this,CourseService.class);
                        intent.setAction(CourseService.COURSE_SERVICE_ACTION_UPDATE);
                        intent.putExtra("id",coursesUpdate.getId());
                        intent.putExtra("name",name);
                        intent.putExtra("code",code);
                        intent.putExtra("room",room);
                        intent.putExtra("lecturer",lecturer);
                        intent.putExtra("time",time);
                        intent.putExtra("day",date);
                        startService(intent);

                        onGetCourse();
                        dialog.dismiss();
                    }
                }
                catch (Exception e){
                    System.out.println("Lỗi UpdateCourse: " + e.getMessage());
                }

            }
        });

        btnCancelCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                edtNameCourse.setText("");
//                edtCodeCourse.setText("");
//                edtRoomCourse.setText("");
//                edtLecturerCourse.setText("");
//                tvGetCalenderCourse.setText("");
//                tvGetTimeCourse.setText("");
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachKhoaHocActivity.this);
        builder.setView(dialogLayout);
        dialog = builder.create();
        dialog.show();
    }

    public void onAddCourse(){
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.dialog_addcourse,null);
        EditText edtNameCourse = dialogLayout.findViewById(R.id.edtNameCourse);
        EditText edtCodeCourse = dialogLayout.findViewById(R.id.edtCodeCourse);
        EditText edtRoomCourse = dialogLayout.findViewById(R.id.edtRoomCourse);
        EditText edtLecturerCourse = dialogLayout.findViewById(R.id.edtLecturerCourse);
        TextView tvGetTimeCourse = dialogLayout.findViewById(R.id.tvGetTimeCourse);
        TextView tvGetCalenderCourse = dialogLayout.findViewById(R.id.tvGetCalenderCourse);
        Button btnAddCourse = dialogLayout.findViewById(R.id.btnSaveCourse);
        Button btnCancelCourse = dialogLayout.findViewById(R.id.btnCacelCourse);

        Calendar lich = Calendar.getInstance();

        //Lấy time
        TimePickerDialog tpdiablog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        time = hourOfDay + " : " + minute;
                        tvGetTimeCourse.setText(time);
                    }
                },lich.get(Calendar.HOUR),
                lich.get(Calendar.MINUTE),
                true);

        //Lấy date
        DatePickerDialog dpdiablog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "-" + (month + 1) + "-" + year;
                        tvGetCalenderCourse.setText(date);
                    }
                },
                lich.get(Calendar.YEAR),
                lich.get(Calendar.MONTH),
                lich.get(Calendar.DAY_OF_MONTH));

        tvGetTimeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tpdiablog.show();
            }
        });

        tvGetCalenderCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpdiablog.show();
            }
        });

        btnAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String name = edtNameCourse.getText().toString();
                    String code = edtCodeCourse.getText().toString();
                    String room = edtRoomCourse.getText().toString();
                    String lecturer = edtLecturerCourse.getText().toString();

                    if (name.isEmpty() || code.isEmpty() || room.isEmpty() || lecturer.isEmpty() || time.isEmpty() || date.isEmpty()){
                        Toast.makeText(DanhSachKhoaHocActivity.this, "Thiếu thông tin", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent intent = new Intent(DanhSachKhoaHocActivity.this,CourseService.class);
                        intent.setAction(CourseService.COURSE_SERVICE_ACTION_INSERT);
                        intent.putExtra("name",name);
                        intent.putExtra("code",code);
                        intent.putExtra("room",room);
                        intent.putExtra("lecturer",lecturer);
                        intent.putExtra("time",time);
                        intent.putExtra("day",date);
                        startService(intent);

                        onGetCourse();
                        dialog.dismiss();
                    }
                }
                catch (Exception e){
                    System.out.println("Lỗi AddCourse: " + e);
                }

            }
        });

        btnCancelCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                edtNameCourse.setText("");
//                edtCodeCourse.setText("");
//                edtRoomCourse.setText("");
//                edtLecturerCourse.setText("");
//                tvGetCalenderCourse.setText("");
//                tvGetTimeCourse.setText("");
                dialog.dismiss();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(DanhSachKhoaHocActivity.this);
        builder.setView(dialogLayout);
        dialog = builder.create();
        dialog.show();
    }

    private  void onGetCourse(){
        Intent intent = new Intent(DanhSachKhoaHocActivity.this,CourseService.class);
        intent.setAction(CourseService.COURSE_SERVICE_ACTION_GET);
        startService(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        onGetCourse();
        IntentFilter intentFilter = new IntentFilter(CourseService.COURSE_SERVICE_EVENT);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ArrayList<Courses> list = (ArrayList<Courses>) intent.getSerializableExtra("result");
            adapter = new CourseAdapter(list);
            listUpdate = list;
            for (int i = 0; i<list.size(); i++){
                System.out.println(i + ": " + list.get(i).getId() + "\n" + list.get(i).getName());
            }
            gridView.setAdapter(adapter);
        }
    };
}