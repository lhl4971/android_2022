package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.beans.OrderBean;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ClientOrderAdapter extends RecyclerView.Adapter<ClientOrderAdapter.ViewHolder> {


    public interface ItemClickListener {
        void toDetail(OrderBean orderBean);


    }


    private Context context;
    private List<OrderBean> datas = new ArrayList<>();
    private ItemClickListener itemClickListener;
    public ClientOrderAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_client_order_layout, parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void setItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderBean orderBean = datas.get(position);
        holder.nameTextView.setText(orderBean.getName());
        holder.clientNameTextView.setText(orderBean.getClientName());
        holder.cookNameTextView.setText(orderBean.getCookName());
        holder.priceTextView.setText(orderBean.getPrice());
        int status = orderBean.getStatus();
        if(status==0){
            holder.order_status.setText("order");
        }else if(status==1){
            holder.order_status.setText("approvel");
        }else{
            holder.order_status.setText("reject");
        }

        holder.rootView.setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.toDetail(orderBean);
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
        TextView order_status;
        TextView typeTextView;
        TextView clientNameTextView;
        TextView cookNameTextView;
        TextView priceTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            nameTextView = itemView.findViewById(R.id.tv_name);
            typeTextView = itemView.findViewById(R.id.tv_type);
            clientNameTextView = itemView.findViewById(R.id.tv_client_name);
            cookNameTextView = itemView.findViewById(R.id.tv_cook_name);
            priceTextView = itemView.findViewById(R.id.tv_price);
            order_status = itemView.findViewById(R.id.order_status);
        }
    }
}
