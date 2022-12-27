package com.example.myapplication.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.ComplainBean;
import com.example.myapplication.beans.MealBean;
import com.example.myapplication.beans.OrderBean;

import java.util.ArrayList;
import java.util.List;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.ViewHolder> {


    public interface ItemClickListener {
        void deleteComplaint(ComplainBean complainBean);
    }


    private Context context;
    private List<ComplainBean> datas = new ArrayList<>();
    private ItemClickListener itemClickListener;
    public ComplainAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ComplainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_complain_layout, parent,false);
        ComplainAdapter.ViewHolder viewHolder = new ComplainAdapter.ViewHolder(view);
        return viewHolder;
    }
    public void setItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ComplainAdapter.ViewHolder holder, int position) {
        ComplainBean complainBean = datas.get(position);
        holder.fromTextView.setText(complainBean.getFromName());
        holder.toTextView.setText(complainBean.getToName());


        holder.btnDeleteBtn.setOnClickListener(view -> {
            if (itemClickListener != null){
                itemClickListener.deleteComplaint(complainBean);
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
        TextView fromTextView;
        TextView toTextView;
        View btnDeleteBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            fromTextView = itemView.findViewById(R.id.tv_from);
            toTextView = itemView.findViewById(R.id.tv_to);
            btnDeleteBtn = itemView.findViewById(R.id.btn_delete);
        }
    }
}
