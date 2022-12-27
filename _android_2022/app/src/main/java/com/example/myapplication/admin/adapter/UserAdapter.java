package com.example.myapplication.admin.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.OrderBean;
import com.example.myapplication.beans.UserBean;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {


    public interface ItemClickListener {
        void toggleAllow(UserBean userBean);

    }


    private Context context;
    private List<UserBean> datas = new ArrayList<>();
    private ItemClickListener itemClickListener;
    public UserAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(context).inflate(R.layout.item_user_layout, parent,false);
       ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    public void setItemClickListener(ItemClickListener listener){
        itemClickListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserBean userBean = datas.get(position);
        holder.nameTextView.setText(userBean.getFirstName() + userBean.getLastName());
        holder.emailTextView.setText(userBean.getEmail());
        holder.addressTextView.setText(userBean.getAddress());
        holder.addressTextView.setText(userBean.getAddress());
        holder.btnDisableTextView.setText(userBean.getStatus() == 0?"allow":"disallow");
        holder.btnDisableBtn.setOnClickListener(view -> {
           if (itemClickListener != null){
               userBean.setStatus(userBean.getStatus() == 0?1:0);
               holder.btnDisableTextView.setText(userBean.getStatus() == 0?"allow":"disallow");
               itemClickListener.toggleAllow(userBean);
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
        TextView emailTextView;
        TextView addressTextView;
        View btnDisableBtn;
        TextView btnDisableTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            nameTextView = itemView.findViewById(R.id.tv_name);
            emailTextView = itemView.findViewById(R.id.tv_email);
            addressTextView = itemView.findViewById(R.id.tv_address);
            btnDisableBtn = itemView.findViewById(R.id.btn_disable);
            btnDisableTextView  = itemView.findViewById(R.id.btn_disable_text);
        }
    }
}
