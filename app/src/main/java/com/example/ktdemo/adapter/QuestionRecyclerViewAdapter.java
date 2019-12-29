package com.example.ktdemo.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ktdemo.R;
import com.example.ktdemo.model.Question;

import java.util.List;

public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder> {
    private List<Question> questions;
    private Context context;

    public QuestionRecyclerViewAdapter(List<Question> questions, Context context) {
        this.questions = questions;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.question_recyclerview_item,
                parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        Question q = questions.get(position);
        holder.tvQuestion.setText(q.getQuest());
        holder.tvWeight.setText(q.getWeight()+"");
        if(q.isActive()) {
            holder.tvPercent.setText(percentCalc(q.getWeight()) + "%");
        }else{
            holder.tvPercent.setText("Inactive");
        }
        holder.switchActive.setChecked(q.isActive());


    }

    private double percentCalc(int weight){

        int weightSum=0;
        double percent=0;
        if(questions!=null){
            for(Question q : questions){
                if(q.isActive()) {
                    weightSum += q.getWeight();
                }
            }
            percent=100.0/weightSum*weight;

        }
        return percent;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView tvQuestion;
        public TextView tvWeight;
        public TextView tvPercent;
        public Switch switchActive;
        public LinearLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion=itemView.findViewById(R.id.tvQuestion);
            tvWeight=itemView.findViewById(R.id.tvWeight);
            tvPercent=itemView.findViewById(R.id.tvPercent);
            switchActive=itemView.findViewById(R.id.switchActive);
            itemLayout=itemView.findViewById(R.id.itemLayout);
            itemLayout.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            menu.add(this.getAdapterPosition(),119,0,"Show the answers" );
            menu.add(this.getAdapterPosition(),120,0,"Edit" );
            menu.add(this.getAdapterPosition(),121,0,"Delete" );


        }
    }
}
