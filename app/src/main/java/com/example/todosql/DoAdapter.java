package com.example.todosql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class DoAdapter extends RecyclerView.Adapter<DoAdapter.ViewHolder> {

    //Список с информацией о делах
    List<String> doList;

    //Инфлейтер
    LayoutInflater inflater;

    //Динамическое добавление нового действия
    public void addDo(String text){
        doList.add(text);
        notifyItemInserted(doList.size()-1);
    }

    @NonNull
    @Override
    public DoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = inflater.inflate(R.layout.do_adapter_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DoAdapter.ViewHolder holder, int position) {
        String text = doList.get(position);
        holder.tv.setText("• "+text);
    }

    @Override
    public int getItemCount() {
        return doList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv;

        public ViewHolder(View v){
            super(v);
            tv = v.findViewById(R.id.do_text);
        }
    }

    public DoAdapter(List<String> doList, Context context){
        this.doList = doList;
        this.inflater = LayoutInflater.from(context);
    }
}
