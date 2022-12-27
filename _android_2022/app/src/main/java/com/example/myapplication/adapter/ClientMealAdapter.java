package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.beans.MealBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClientMealAdapter extends RecyclerView.Adapter<ClientMealAdapter.ViewHolder> {


    public interface ItemClickListener {
        void toDetail(MealBean mealBean);

    }


    private Context context;
    private List<MealBean> datas = new ArrayList<>();
    private ClientMealAdapter.ItemClickListener itemClickListener;
    public ClientMealAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ClientMealAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_client_meal_layout, parent,false);
        ClientMealAdapter.ViewHolder viewHolder = new ClientMealAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClientMealAdapter.ViewHolder holder, int position) {
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
    }

    public void setItemClickListener(ClientMealAdapter.ItemClickListener listener){
        itemClickListener = listener;
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
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            nameTextView = itemView.findViewById(R.id.tv_name);
            typeTextView = itemView.findViewById(R.id.tv_type);
            cuisineTypeTextView = itemView.findViewById(R.id.tv_cuisine_type);
            priceTextView = itemView.findViewById(R.id.tv_price);
        }
    }
}

