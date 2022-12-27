package com.example.myapplication.client

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ClientMealActivity
import com.example.myapplication.ClientOrderActivity
import com.example.myapplication.R
import com.example.myapplication.ToastUtil.toast
import com.example.myapplication.adapter.ClientMealAdapter
import com.example.myapplication.beans.MealBean
import com.example.myapplication.beans.UserBean
import com.example.myapplication.sqlite.UserDataManager
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class ClientMainActivity : AppCompatActivity(), View.OnClickListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var nameEditText: EditText
    private lateinit var typeSpinner: Spinner
    private lateinit var cuisineTypeSpinner: Spinner
    private lateinit var search: View
    private lateinit var orderList: View
    private lateinit var mealAdapter: ClientMealAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var context: Context
    private lateinit var client: OkHttpClient
    private lateinit var request: Request
    lateinit var  userInfo: UserBean
    var mealBeanList: MutableList<MealBean?> = ArrayList()
    private var type = 0
    private var cuisineType = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_main)
        val sharedPreferences = getSharedPreferences("user", 0)
        val account = sharedPreferences.getString("account", "")
        if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
        userInfo = UserDataManager.getInstance(this).getUserInfoByAccount(account)
        context = this
        nameEditText = findViewById(R.id.ed_name)
        typeSpinner = findViewById(R.id.spin_type)
        cuisineTypeSpinner = findViewById(R.id.spin_cuisine_type)
        search = findViewById(R.id.search)
        recyclerView = findViewById(R.id.recyclerView)
        orderList = findViewById(R.id.orderList)
        typeSpinner.setOnItemSelectedListener(this)
        cuisineTypeSpinner.setOnItemSelectedListener(this)
        search.setOnClickListener(this)
        orderList.setOnClickListener(this)
        client = OkHttpClient()
        val layoutManager = LinearLayoutManager(context)
        recyclerView.setLayoutManager(layoutManager)
        mealAdapter = ClientMealAdapter(context)
        recyclerView.setAdapter(mealAdapter)
        mealAdapter.setItemClickListener {mealBean ->
            /*val intent  = Intent(context, ClientMealActivity::class.java);
                intent.putExtra("mealId",mealBean.getId()+"");
                startActivity(intent);*/
            Log.i("222222", "点击了111111")

            //fun toDetail(mealBean: MealBean) {
                Log.i("222222", "点击了222")
                val intent = Intent(context, ClientMealActivity::class.java)
                intent.putExtra("mealId", mealBean.id.toString() + "")
                intent.putExtra("cookName", mealBean.cookName.toString() + "")
                intent.putExtra("type", mealBean.type.toString() + "")
                intent.putExtra("des", mealBean.description.toString() + "")
                intent.putExtra("price", mealBean.price.toString() + "")
                intent.putExtra("name", mealBean.name.toString() + "")
                intent.putExtra("CuisineType", mealBean.cuisineType.toString() + "")
                startActivity(intent)
            //}
        }
    }

    private fun getOrderList() {
        val intent = Intent(context, ClientOrderActivity::class.java)
        startActivity(intent)
    }

    private fun search() {
        val name = nameEditText!!.text.toString()
        if (TextUtils.isEmpty(name)) {
            toast(this, "name is empty!")
            return
        }
        request = Request.Builder()
            .url("https://www.themealdb.com/api/json/v1/1/filter.php?i=$name")
            .build()
        try {
            val response = client!!.newCall(request).execute()
            val responseData = response.body().string()
            Log.i("222222", responseData)
            val jsonObject = JSONObject(responseData)
            Log.i("222222", jsonObject.getString("meals"))
            val jsonArray = JSONArray(jsonObject.getString("meals"))
            Log.i("222222", jsonArray.length().toString() + "")
            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                val tempmealBean = MealBean()
                tempmealBean.id = (obj["idMeal"] as String).toInt()
                tempmealBean.name = obj["strMeal"] as String
                tempmealBean.description = obj["strMealThumb"] as String
                tempmealBean.type = 0
                tempmealBean.price = "10"
                tempmealBean.cuisineType = 0
                mealBeanList.add(tempmealBean)
            }
            mealAdapter.setData(mealBeanList)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        /* mealBeanList = MealDataManager.getInstance(this).getMealList(name,type,cuisineType);
        Log.i("11111111111",mealBeanList.size()+"");
        mealAdapter.setData(mealBeanList);*/
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.search -> search()
            R.id.orderList -> getOrderList()
            else -> {}
        }
    }

    override fun onItemSelected(adapterView: AdapterView<*>, view: View, i: Int, l: Long) {
        when (adapterView.id) {
            R.id.spin_type -> type = i
            R.id.spin_cuisine_type -> cuisineType = i
            else -> {}
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}