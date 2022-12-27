package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Ingredient;


import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter  extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {


    public interface ItemClickListener {
        void toDetail(Ingredient ingredient);

    }


    private Context context;
    private List<Ingredient> datas = new ArrayList<>();
    private IngredientAdapter.ItemClickListener itemClickListener;
    public IngredientAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_ingredient_layout, parent,false);
        IngredientAdapter.ViewHolder viewHolder = new IngredientAdapter.ViewHolder(view);
        return viewHolder;
    }
    public void setItemClickListener(IngredientAdapter.ItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = datas.get(position);

        holder.nameTextView.setText(ingredient.getName());

        holder.rootView.setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.toDetail(ingredient);
            }
        });



    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
    public  void setData(List datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView nameTextView;
        TextView delteTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            nameTextView = itemView.findViewById(R.id.tv_name);
            delteTextView = itemView.findViewById(R.id.tv_delete);
        }
    }
}
