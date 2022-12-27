package com.example.myapplication.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.myapplication.adapter.OrderAdapter;

import com.example.myapplication.R;
import com.example.myapplication.beans.OrderBean;
import com.example.myapplication.beans.UserBean;
import com.example.myapplication.sqlite.OrderDataManager;
import com.example.myapplication.sqlite.UserDataManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderFragment extends Fragment {


    private Context context;
    private OrderAdapter orderAdapter;
    private RecyclerView recyclerView;
    UserBean userInfo;
    private AlertDialog alert = null;
    public OrderFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        initView(view);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("user",0);
        String account = sharedPreferences.getString("account","");
        userInfo = UserDataManager.getInstance(context).getUserInfoByAccount(account);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }
    private void initView(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        orderAdapter = new OrderAdapter(context);
        recyclerView.setAdapter(orderAdapter);


        orderAdapter.setItemClickListener(new OrderAdapter.ItemClickListener() {
            @Override
            public void approval(OrderBean orderBean) {
                operationOrder(orderBean,true);
            }

            @Override
            public void reject(OrderBean orderBean) {
                operationOrder(orderBean,false);
            }

            @Override
            public void toDetail(OrderBean orderBean) {
//                Intent intent = new Intent(context,OrderDetailActivity.class);
//                intent.putExtra("order",orderBean);
//                startActivity(intent);
            }
        });
    }

    private void initData() {
       List<OrderBean> orderBeans = OrderDataManager.getInstance(context).getCookOrder(userInfo.getId());
       orderAdapter.setData(orderBeans);
    }
    private void operationOrder(OrderBean orderBean,Boolean isApproval) {
        String msg = "reject";
        if (isApproval){
            msg = "approval";
        }
        msg = "are you confirm " + msg  + "?";
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        alert = builder.setTitle("tip")
                .setMessage(msg)
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                })
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderBean.setStatus(isApproval?1:2);
                        OrderDataManager.getInstance(context).updateOrder(orderBean);
                        orderAdapter.notifyDataSetChanged();
                    }
                }).create();
        alert.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}