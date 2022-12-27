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
import com.example.myapplication.beans.OrderBean;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {


    public interface ItemClickListener {
        void approval(OrderBean orderBean);
        void reject(OrderBean orderBean);
        void toDetail(OrderBean orderBean);


    }


    private Context context;
    private List<OrderBean> datas = new ArrayList<>();
    private ItemClickListener itemClickListener;
    public OrderAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_order_layout, parent,false);
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
        if (orderBean.getStatus() == 0){
            holder.approvalBtn.setVisibility(View.VISIBLE);
            holder.rejectBtn.setVisibility(View.VISIBLE);
            holder.statusView.setVisibility(View.GONE);

        }else{
            holder.approvalBtn.setVisibility(View.GONE);
            holder.rejectBtn.setVisibility(View.GONE);
            holder.statusView.setVisibility(View.VISIBLE);
            holder.statusTextView.setText(orderBean.getStatus() == 1?"approval":"reject");

        }

        holder.rootView.setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.toDetail(orderBean);
            }
        });

        holder.approvalBtn.setOnClickListener(view -> {
           if (itemClickListener != null){
               itemClickListener.approval(orderBean);
           }
        });
        holder.rejectBtn.setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.reject(orderBean);
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
        TextView clientNameTextView;
        TextView cookNameTextView;
        TextView priceTextView;
        View approvalBtn;
        View rejectBtn;
        View statusView;
        TextView statusTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            nameTextView = itemView.findViewById(R.id.tv_name);
            typeTextView = itemView.findViewById(R.id.tv_type);
            clientNameTextView = itemView.findViewById(R.id.tv_client_name);
            cookNameTextView = itemView.findViewById(R.id.tv_cook_name);
            priceTextView = itemView.findViewById(R.id.tv_price);
            approvalBtn = itemView.findViewById(R.id.btn_approval);
            rejectBtn = itemView.findViewById(R.id.btn_reject);
            statusView = itemView.findViewById(R.id.view_status);
            statusTextView = itemView.findViewById(R.id.view_status_text);
        }
    }
}
