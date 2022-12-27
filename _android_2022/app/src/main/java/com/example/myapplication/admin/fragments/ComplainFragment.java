package com.example.myapplication.admin.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.myapplication.R;
import com.example.myapplication.admin.adapter.ComplainAdapter;
import com.example.myapplication.beans.ComplainBean;
import com.example.myapplication.sqlite.ComplainDataManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComplainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComplainFragment extends Fragment {


    private Context context;
    private ComplainAdapter complainAdapter;
    private RecyclerView recyclerView;
    private AlertDialog alert = null;
    List<ComplainBean> complainBeans = new ArrayList<>();
    public ComplainFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complain, container, false);
        initView(view);





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
        complainAdapter = new ComplainAdapter(context);
        recyclerView.setAdapter(complainAdapter);
        complainAdapter.setItemClickListener(orderBean -> cancelOrder(orderBean));

    }

    private void initData() {
         complainBeans = ComplainDataManager.getInstance(context).getAllComplain();
        complainAdapter.setData(complainBeans);
    }
    private void cancelOrder(ComplainBean complainBean) {
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
                      ComplainDataManager.getInstance(context).deleteComplain(complainBean);
                      complainBeans.remove(complainBean);
                      complainAdapter.notifyDataSetChanged();
                    }
                }).create();
        alert.show();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }



}