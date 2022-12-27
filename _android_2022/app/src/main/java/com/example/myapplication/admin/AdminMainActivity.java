package com.example.myapplication.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Chronometer;
import android.widget.RelativeLayout;

import com.example.myapplication.R;
import com.example.myapplication.admin.fragments.ComplainFragment;
import com.example.myapplication.admin.fragments.UserFragment;
import com.example.myapplication.beans.ComplainBean;
import com.example.myapplication.beans.UserBean;
import com.example.myapplication.sqlite.ComplainDataManager;
import com.example.myapplication.sqlite.UserDataManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AdminMainActivity extends AppCompatActivity implements Chronometer.OnChronometerTickListener, View.OnClickListener {


    RelativeLayout layout;
    private List<Fragment> fragments;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    showFragment(0);
                    return true;
                case R.id.navigation_dashboard:
                    showFragment(1);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        layout = findViewById(R.id.layout);
        testData();
        addFragments();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void testData(){
        List<UserBean> cooks = UserDataManager.getInstance(this).getAllUser(2);
        List<UserBean> clients = UserDataManager.getInstance(this).getAllUser(1);
        for (int i = 0; i < cooks.size(); i++) {
            for (int j = 0; j < clients.size(); j++) {
                UserBean fromUser = clients.get(j);
                UserBean toUser = cooks.get(i);
                ComplainBean complainBean = new ComplainBean();
                complainBean.setFromId(fromUser.getId());
                complainBean.setToId(toUser.getId());
                complainBean.setFromName(fromUser.getFirstName()+fromUser.getLastName());
                complainBean.setToName(toUser.getFirstName()+toUser.getLastName());
                complainBean.setStatus(0);
                ComplainDataManager.getInstance(this).insertComplainBean(complainBean);
            }
        }

    }

    //add Fragment
    private void addFragments() {
        fragments = new ArrayList<Fragment>();
        ComplainFragment complainFragment = new ComplainFragment(this);
        UserFragment userFragment = new UserFragment(this);

        fragments.add(complainFragment);
        fragments.add(userFragment);


        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(R.id.layout,complainFragment);
        ft.add(R.id.layout,userFragment);
        ft.commit();
        showFragment(0);

    }

    private void showFragment(int index) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {
            ft.hide(fragments.get(i));
        }
        ft.show(fragments.get(index));
        ft.commit();
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onChronometerTick(Chronometer chronometer) {

    }
}