package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.MealBean;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter  extends RecyclerView.Adapter<MealAdapter.ViewHolder> {


    public interface ItemClickListener {
        void delete(MealBean mealBean);
        void toDetail(MealBean mealBean);

    }


    private Context context;
    private List<MealBean> datas = new ArrayList<>();
    private ItemClickListener itemClickListener;
    public MealAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_meal_layout, parent,false);
        MealAdapter.ViewHolder viewHolder = new MealAdapter.ViewHolder(view);
        return viewHolder;
    }
    public void setItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull MealAdapter.ViewHolder holder, int position) {
        MealBean mealBean = datas.get(position);

        String[] types =  context.getResources().getStringArray(R.array.type);
        String[] typeCuisines =  context.getResources().getStringArray(R.array.type_cuisine);

        holder.nameTextView.setText(mealBean.getName()+"");
        holder.typeTextView.setText(types[mealBean.getType()]);
        holder.cuisineTypeTextView.setText(typeCuisines[mealBean.getCuisineType()]);
        holder.priceTextView.setText(mealBean.getPrice());

        holder.rootView.setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.toDetail(mealBean);
            }
        });

        holder.btnDeleteBtn.setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.delete(mealBean);
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
        TextView typeTextView;
        TextView cuisineTypeTextView;
        TextView priceTextView;
        View btnDeleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            nameTextView = itemView.findViewById(R.id.tv_name);
            typeTextView = itemView.findViewById(R.id.tv_type);
            cuisineTypeTextView = itemView.findViewById(R.id.tv_cuisine_type);
            priceTextView = itemView.findViewById(R.id.tv_price);
            btnDeleteBtn = itemView.findViewById(R.id.btn_delete);
        }
    }
}
