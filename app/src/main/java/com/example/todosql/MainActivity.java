package com.example.todosql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Список с делами для адаптера под список
    ArrayList<String> adapterList = new ArrayList<>();

    //База данных
    SQLiteDatabase db;

    //Создание переменных для элементов разметки
    RecyclerView doList;
    Button addDo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Инициализация базы данных
        db = getBaseContext().openOrCreateDatabase("doData.db", MODE_PRIVATE, null);

        //Инициализация переменных для элементов разметки
        doList = findViewById(R.id.do_list_rv);
        addDo = findViewById(R.id.add_do_btn);

        //Заполнение списка
        doList.setAdapter(new DoAdapter(adapterList, this));
    }
}