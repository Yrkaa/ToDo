package com.example.todosql;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Список с делами для адаптера под список
    ArrayList<String> adapterList = new ArrayList<>();

    //Адаптер для списка дел
    DoAdapter adapter;

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

        //Создание таблицы для дел, если она не существует
        db.execSQL("CREATE TABLE IF NOT EXISTS Do (_id INTEGER PRIMARY KEY AUTOINCREMENT, txt TEXT NOT NULL)");

        //Инициализация переменных для элементов разметки
        doList = findViewById(R.id.do_list_rv);
        addDo = findViewById(R.id.add_do_btn);

        //Получение существующих дел из бд
        Cursor cursor = db.rawQuery("SELECT * FROM Do", null);
        while (cursor.moveToNext()){
            adapterList.add(cursor.getString(1));
        }

        //Инициализация адаптера
        adapter = new DoAdapter(adapterList, this, db);

        //Заполнение списка
        doList.setAdapter(adapter);

        //Вызов окна для создания нового действия
        addDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewDoDialog();
            }
        });
    }

    private void createNewDoDialog() {
        //Создание и показ диалогового окна
        BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.create_new_do_layout);
        dialog.show();

        //Инициализация элементов разметки
        EditText text = dialog.findViewById(R.id.new_do_text_et);
        Button button = dialog.findViewById(R.id.create_new_do_btn);

        //Создание нового действия
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(text.getText().toString().length() > 0){
                    adapter.addDo(text.getText().toString());
                    db.execSQL("INSERT INTO Do(txt) VALUES ("+"'"+text.getText().toString()+"'"+")");
                    dialog.dismiss();

                }
                else{
                    Toast.makeText(MainActivity.this, "Напишите что-нибудь!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}