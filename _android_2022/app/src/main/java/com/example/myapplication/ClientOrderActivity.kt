package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.beans.UserBean
import com.example.myapplication.adapter.ClientOrderAdapter
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import com.example.myapplication.R
import android.content.SharedPreferences
import com.example.myapplication.sqlite.UserDataManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.beans.OrderBean
import com.example.myapplication.sqlite.OrderDataManager
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.ClientComplainActivity

class ClientOrderActivity : AppCompatActivity() {
    lateinit var userInfo: UserBean
    private lateinit var context: Context
    private lateinit var orderAdapter: ClientOrderAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var alert: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_order)
        val sharedPreferences = getSharedPreferences("user", 0)
        val account = sharedPreferences.getString("account", "")
        userInfo = UserDataManager.getInstance(this).getUserInfoByAccount(account)
        context = this
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(context)
        recyclerView.setLayoutManager(layoutManager)
        orderAdapter = ClientOrderAdapter(context)
        recyclerView.setAdapter(orderAdapter)
        val orderBeans = OrderDataManager.getInstance(context).getCookOrder(userInfo.getId())
        orderAdapter!!.setData(orderBeans)
        orderAdapter!!.setItemClickListener { orderBean ->
            val intent = Intent(context, ClientComplainActivity::class.java)
            intent.putExtra("order", orderBean)
            startActivity(intent)
        }
    }
}