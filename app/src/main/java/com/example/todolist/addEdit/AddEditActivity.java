package com.example.todolist.addEdit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;

import com.example.todolist.R;
import com.example.todolist.room.Todoitem;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;

public class AddEditActivity extends AppCompatActivity {
    private TextInputLayout til_title, til_sDate,til_dDate, til_memo;
    private ImageButton ib_sDate, ib_dDate;
    private final int START_DATE=0;
    private final int DUE_DATE=1;
    private int mode;
    private int id;
    private Todoitem selectItem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        mode = getIntent().getExtras().getInt("mode"); //과제

        ActionBar actionBar = getSupportActionBar(); //액션바 이름 바꾸기
        if (actionBar != null) {

            actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 띄우기
            if (mode == 0) {
                actionBar.setTitle("Add Todo");
            } else {
                actionBar.setTitle("Edit Todo");
            }
        }

        til_title = findViewById(R.id.addEdit_til_title);
        til_sDate = findViewById(R.id.addEdit_til_start);
        til_dDate = findViewById(R.id.addEdit_til_due);
        til_memo = findViewById(R.id.addEdit_til_memo);

        ib_sDate = findViewById(R.id.addEdit_ibtn_start);
        ib_dDate = findViewById(R.id.addEdit_ibtn_due);

        if(mode == 1){ //edit mode
            til_title.getEditText().setText(selectItem.getTitle());
            til_sDate.getEditText().setText(selectItem.getStart());
            til_dDate.getEditText().setText(selectItem.getDue());
            til_memo.getEditText().setText(selectItem.getMemo());
            //Todo:edit
        }
        ib_sDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(START_DATE);
            }
        });

        ib_dDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCalendar(DUE_DATE);
            }
        });

    }

    public void showCalendar(final int mode){
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH);
        int mDate = cal.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(AddEditActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String s_month = new Integer(month).toString();
                String s_date = new Integer(dayOfMonth).toString();

                if(month+1<10){
                    s_month = "0" + new Integer(month+1).toString();
                }
                if(dayOfMonth<10){
                    s_date = "0" + new Integer(dayOfMonth).toString();
                }

                String date = year+"/"+s_month+"/"+s_date;
                if(mode==START_DATE){
                    til_sDate.getEditText().setText(date);
                }else if(mode==DUE_DATE){
                    til_dDate.getEditText().setText(date);
                }

            }
        }, mYear, mMonth,mDate).show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.home: //Todo : menu_save 화면에 home이 없음
                finish();
                break;
            case R.id.save_todo:
                //세이브
        }
        return super.onOptionsItemSelected(item);
    }
}