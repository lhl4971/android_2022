package com.example.myapplication.admin.fragments;

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


import com.example.myapplication.R;
import com.example.myapplication.admin.adapter.UserAdapter;
import com.example.myapplication.beans.OrderBean;
import com.example.myapplication.beans.UserBean;
import com.example.myapplication.sqlite.UserDataManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserFragment extends Fragment {


    private Context context;
    private UserAdapter userAdapter;
    private RecyclerView recyclerView;
    UserBean userInfo;
    private AlertDialog alert = null;
    public UserFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
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
        userAdapter = new UserAdapter(context);
        recyclerView.setAdapter(userAdapter);
        userAdapter.setItemClickListener(new UserAdapter.ItemClickListener() {
            @Override
            public void toggleAllow(UserBean userBean) {
              UserDataManager.getInstance(context).updateCookerStatus(userBean);
            }
        });
    }

    private void initData() {
       List<UserBean> orderBeans = UserDataManager.getInstance(context).getAllUser(2);
       userAdapter.setData(orderBeans);
    }
    private void cancelOrder(OrderBean orderBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        alert = builder.setTitle("tip")
                .setMessage("are you confirm cancel?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                })
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
//                        orderBean.setStatus("0");
//                        orderAdapter.notifyDataSetChanged();
//                        DBDao.getInstance(context).updateOrder(orderBean);
                    }
                }).create();
        alert.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

}