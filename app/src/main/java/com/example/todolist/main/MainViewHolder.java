package com.example.todolist.main;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;
import com.example.todolist.room.Todoitem;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainViewHolder extends RecyclerView.ViewHolder {
    private TextView todo_tv_title;
    private CheckBox todo_cb;
    private TextView todo_tv_due;
    private  TextView todo_tv_left;


    public MainViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        todo_tv_title = itemView.findViewById(R.id.todo_title);
        todo_cb = itemView.findViewById(R.id.todo_cb);
        todo_tv_due = itemView.findViewById(R.id.todo_due_date);
        todo_tv_left = itemView.findViewById(R.id.todo_left);
    }

    public void onBind(Todoitem item) throws ParseException {
        todo_tv_title.setText(item.getTitle());
        todo_cb.setChecked(item.isChecked());
        todo_tv_due.setText(item.getDue());

        if(item.isChecked()){
            SpannableString contentSp = new SpannableString(item.getTitle());
            contentSp.setSpan(new StrikethroughSpan(), 0,item.getTitle().length(),0);
            todo_tv_title.setText(contentSp);
        }

        //left 계산
        Calendar cal = Calendar.getInstance();
        int mYear = cal.get(Calendar.YEAR);
        int mMonth = cal.get(Calendar.MONTH) + 1;
        int mDay = cal.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        Date dDate = simpleDateFormat.parse(item.getDue());
        Date today = simpleDateFormat.parse(mYear+"/"+mMonth+"/"+mDay);
        Long left = -(dDate.getTime() - today.getTime())/(24*60*60*1000);

        if(left.intValue() < 0) {
            todo_tv_left.setText("D-" + left);
        }else if(left.intValue()==0){
            todo_tv_left.setText("D-Day");
            todo_tv_left.setTextColor(Color.RED);
        }else{
            todo_tv_left.setText("D+"+left);
            todo_tv_left.setTextColor(Color.RED);
        }
    }

}
