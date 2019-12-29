package com.example.ktdemo.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktdemo.R;
import com.example.ktdemo.model.Answer;

import java.util.List;

public class AnswerRecyclerViewAdapter extends RecyclerView.Adapter<AnswerRecyclerViewAdapter.ViewHolder> {
   private List<Answer> items;
   private Context context;

    public AnswerRecyclerViewAdapter(List<Answer> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_recyclerview_item,
                parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Answer a = items.get(position);
        holder.tvRate.setText(a.getRate()+"");
        holder.tvDate.setText(a.getDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView tvRate;
        public TextView tvDate;
        public LinearLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRate=itemView.findViewById(R.id.tvRateRcItem);
            tvDate=itemView.findViewById(R.id.tvDateRcItem);
            itemLayout=itemView.findViewById(R.id.answerRvItemLayout);
            itemLayout.setOnCreateContextMenuListener(this);


        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(this.getAdapterPosition(),150,0,"Edit" );
        }
    }
}
