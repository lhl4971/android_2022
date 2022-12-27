package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.RelativeLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.myapplication.R
import android.os.Bundle
import android.view.View
import com.example.myapplication.beans.MealBean
import com.example.myapplication.sqlite.MealDataManager
import com.example.myapplication.beans.UserBean
import com.example.myapplication.sqlite.UserDataManager
import com.example.myapplication.beans.OrderBean
import com.example.myapplication.sqlite.OrderDataManager
import com.example.myapplication.fragments.HomeFragment
import com.example.myapplication.fragments.OrderFragment
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import java.util.ArrayList

class MainActivity : AppCompatActivity(), OnChronometerTickListener, View.OnClickListener {
    var layout: RelativeLayout? = null
    private lateinit var fragments: MutableList<Fragment>
    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showFragment(0)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    showFragment(1)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layout = findViewById(R.id.layout)
        testData()
        addFragments()
        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun testData() {
        val mealBeans = MealDataManager.getInstance(this).getMealInfos(0)
        val clients = UserDataManager.getInstance(this).getAllUser(1)
        for (i in mealBeans.indices) {
            for (j in clients.indices) {
                val userBean = clients[j]
                val mealBean = mealBeans[i]
                val orderBean = OrderBean()
                orderBean.setuId(userBean.id)
                orderBean.setcId(mealBean.getuId())
                orderBean.name = mealBean.name
                orderBean.price = mealBean.price
                orderBean.clientName = userBean.firstName + userBean.lastName
                orderBean.cookName = mealBean.cookName
                orderBean.setmId(mealBean.id)
                OrderDataManager.getInstance(this).insertOrder(orderBean)
            }
        }
    }

    //add Fragment
    private fun addFragments() {
        fragments = ArrayList()
        val homeFragment = HomeFragment(this@MainActivity)
        val orderFragment = OrderFragment(this@MainActivity)
        fragments.add(homeFragment)
        fragments.add(orderFragment)
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        ft.add(R.id.layout, homeFragment)
        ft.add(R.id.layout, orderFragment)
        ft.commit()
        showFragment(0)
    }

    private fun showFragment(index: Int) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()
        for (i in fragments!!.indices) {
            ft.hide(fragments!![i])
        }
        ft.show(fragments!![index])
        ft.commit()
    }

    override fun onClick(view: View) {}
    override fun onChronometerTick(chronometer: Chronometer) {}
}