package com.example.todosql;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DoAdapter extends RecyclerView.Adapter<DoAdapter.ViewHolder> {

    //Список с информацией о делах
    List<String> doList;

    //Инфлейтер
    LayoutInflater inflater;

    //База данных
    SQLiteDatabase db;

    //Динамическое добавление нового действия
    public void addDo(String text){
        doList.add(text);
        notifyItemInserted(doList.size()-1);
    }

    //Динамическое удаление действия
    public void removeDo(int pos){
        doList.remove(pos);
        notifyItemRemoved(pos);
    }

    @NonNull
    @Override
    public DoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.do_adapter_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoAdapter.ViewHolder holder, int position) {
        //Получение дела
        String text = doList.get(position);

        //Заполнение текста
        holder.tv.setText("• "+text);

        //Удаление сообщения
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeDo(position);
                Cursor c = db.rawQuery("SELECT * FROM Do", null);
                int count = 0;
                while(c.moveToNext()){
                    if(count == position){
                        int id = c.getInt(0);
                        db.execSQL("DELETE FROM Do WHERE _id="+id);
                        db.execSQL("UPDATE Do SET _id = _id - 1 WHERE _id >"+id);
                    }
                    count++;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return doList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //Эл. разметки
        TextView tv;
        Button del;

        public ViewHolder(View v){
            super(v);
            tv = v.findViewById(R.id.do_text);
            del = v.findViewById(R.id.del_do_btn);
        }
    }

    public DoAdapter(List<String> doList, Context context, SQLiteDatabase db){
        this.doList = doList;
        this.inflater = LayoutInflater.from(context);
        this.db = db;
    }
}
