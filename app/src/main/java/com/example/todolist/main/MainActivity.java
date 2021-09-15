package com.example.todolist.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.todolist.R;
import com.example.todolist.addEdit.AddEditActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private RecyclerView main_rcv;
    private FloatingActionButton main_fab;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_rcv = findViewById(R.id.main_rcv);
        main_fab = findViewById(R.id.main_fab);

        main_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //과제
                //힌트 : INTENT 사용하기
                Intent intent = new Intent(getApplicationContext(), AddEditActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu_delete_all:
                //Todo 전체삭제
            case R.id.menu_delete_selected:
                //Todo 선택삭제
        }

        return super.onOptionsItemSelected(item);
    }
}