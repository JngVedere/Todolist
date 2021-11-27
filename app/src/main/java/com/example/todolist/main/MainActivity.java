package com.example.todolist.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.todolist.R;
import com.example.todolist.addEdit.AddEditActivity;
import com.example.todolist.room.MyDatabase;
import com.example.todolist.room.Todoitem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    /*
    1.adapter 가져오기
    2. action Bar -> 과제
    MainAdapter에서 만들었던 함수 사용해서 코드 짜보기
    */

    private RecyclerView main_rcv;
    private FloatingActionButton main_fab;
    private MainAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Action Bar 만들기
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setTitle("TODO APP");
        }

        main_rcv = findViewById(R.id.main_rcv);
        main_fab = findViewById(R.id.main_fab);
        adapter = new MainAdapter();

        main_rcv.setAdapter(adapter);
        main_rcv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

        final MyDatabase myDatabase = MyDatabase.getInstance(this);
        List<Todoitem> list_item = myDatabase.todoDao().getAllTodo();
        adapter.submitAll(list_item);

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //과제
                //힌트 : INTENT 사용하기
                //3주차 과제
                //힌트 : mode 넘기기
                Intent intent = new Intent(getApplicationContext(), AddEditActivity.class);
                intent.putExtra("mode",0); //null == 0인 add mode를 intent로 전달
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_delete_all:
                //Todo 전체삭제
                adapter.removeAll();
                MyDatabase myDatabase = MyDatabase.getInstance(this);
                myDatabase.todoDao().deleteAllTodo();
                break;

            case R.id.menu_delete_selected:
                //Todo 선택삭제
                MyDatabase myDatabase1 = MyDatabase.getInstance(this);
                List<Todoitem> itemList = myDatabase1.todoDao().getAllTodo();
                List<Todoitem> newList = new ArrayList<>();

                for(int i = 0; i<itemList.size();i++){
                    if(adapter.checkItem(itemList.get(i))){
                        myDatabase1 = MyDatabase.getInstance(this);
                        myDatabase1.todoDao().deleteTodo(itemList.get(i));
                    }else{
                        newList.add(itemList.get(i));
                    }
                }

                adapter.removeAllItem(newList);
                break;
        }
        adapter.notifyDataSetChanged();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        MyDatabase myDatabase = MyDatabase.getInstance(this);
        adapter.submitAll(myDatabase.todoDao().getAllTodo());
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyDatabase myDatabase = MyDatabase.getInstance(this);
        adapter.submitAll(myDatabase.todoDao().getAllTodo());
    }
}