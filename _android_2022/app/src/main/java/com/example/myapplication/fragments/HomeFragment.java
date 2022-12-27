package com.example.myapplication.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.myapplication.MealActivity;
import com.example.myapplication.R;
import com.example.myapplication.ToastUtil;
import com.example.myapplication.adapter.MealAdapter;
import com.example.myapplication.beans.MealBean;
import com.example.myapplication.beans.UserBean;
import com.example.myapplication.sqlite.MealDataManager;
import com.example.myapplication.sqlite.UserDataManager;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {


    private Context context;
    private MealAdapter mealAdapter;
    private RecyclerView recyclerView;
    UserBean userInfo;
    List<MealBean> mealBeans;
    private AlertDialog alert = null;
    public HomeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
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
        mealAdapter = new MealAdapter(context);
        recyclerView.setAdapter(mealAdapter);
        mealAdapter.setItemClickListener(new MealAdapter.ItemClickListener() {
            @Override
            public void delete(MealBean mealBean) {
                cancelOrder(mealBean);
            }

            @Override
            public void toDetail(MealBean mealBean) {
                Intent intent = new Intent(context,MealActivity.class);
                intent.putExtra("meal",mealBean);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.btn_meal).setOnClickListener(view1 -> {
            Intent intent = new Intent(context, MealActivity.class);
            startActivity(intent);
        });
    }

    private void initData() {
        mealBeans = MealDataManager.getInstance(context).getMealInfos(userInfo.getId());
        mealAdapter.setData(mealBeans);
    }
    private void cancelOrder(MealBean mealBean) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        alert = builder.setTitle("tip")
                .setMessage("are you confirm delete?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alert.dismiss();
                    }
                })
                .setPositiveButton("confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mealBean.setStatus(0);
                        MealDataManager.getInstance(context).updateMeal(mealBean);
                        mealBeans.remove(mealBean);
                        mealAdapter.notifyDataSetChanged();
                        ToastUtil.toast(context,"delete success!");
                    }
                }).create();
        alert.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



}