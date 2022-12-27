package com.example.myapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.beans.MealBean
import com.example.myapplication.beans.UserBean
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.sqlite.OrderDataManager
import com.example.myapplication.adapter.IngredientAdapter
import com.example.myapplication.beans.Ingredient
import android.os.Bundle
import com.example.myapplication.R
import android.content.SharedPreferences
import android.util.Log
import android.view.View
import com.example.myapplication.sqlite.UserDataManager
import com.example.myapplication.sqlite.MealDataManager
import com.example.myapplication.beans.OrderBean
import com.example.myapplication.ToastUtil
import java.util.ArrayList

class ClientMealActivity : AppCompatActivity() {
    lateinit var mealBean: MealBean
    lateinit var userInfo: UserBean
    private var mealId = 0
    private lateinit var cookName: TextView
    private lateinit var name1: TextView
    private lateinit var type: TextView
    private lateinit var cuisineType: TextView
    private lateinit var price: TextView
    private lateinit var description: TextView
    private lateinit var add_order: View
    private lateinit var ingredientView: RecyclerView
    private lateinit var context: Context
    lateinit var orderDataManager: OrderDataManager
    lateinit var ingredientAdapter: IngredientAdapter
    var ingredients: List<Ingredient> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_meal)
        val sharedPreferences = getSharedPreferences("user", 0)
        val account = sharedPreferences.getString("account", "")
        userInfo = UserDataManager.getInstance(this).getUserInfoByAccount(account)
        val temp_mId = intent.getStringExtra("mealId")
        val temp_cookName = intent.getStringExtra("cookName")
        val temp_type = intent.getStringExtra("type")
        val temp_des = intent.getStringExtra("des")
        val temp_price = intent.getStringExtra("price")
        val temp_name = intent.getStringExtra("name")
        val temp_CuisineType = intent.getStringExtra("CuisineType")
        if (temp_mId != null) {
            mealId = temp_mId.toInt()
        }
//        mealBean = MealDataManager.getInstance(this).getMealInfosById(mealId)
        mealBean = MealBean()
        mealBean.name = temp_name;
        mealBean.description = temp_des
        mealBean.price = temp_price
        mealBean.cookName = temp_cookName
        mealBean.cuisineType = (temp_CuisineType as String).toInt()
        mealBean.type = (temp_type as String).toInt()
        orderDataManager = OrderDataManager.getInstance(this)
        cookName = findViewById(R.id.cookName)
        name1 = findViewById(R.id.name1)
        type = findViewById(R.id.client_meal_type)
        cuisineType = findViewById(R.id.client_meal_cuisineType)
        price = findViewById(R.id.client_meal_price)
        description = findViewById(R.id.description)
        add_order = findViewById(R.id.add_order)
        context = this
        //ingredientView = findViewById(R.id.recyclerView);
        var typeShow = ""
        typeShow = if (mealBean.getType() == 0) {
            "main dish"
        } else if (mealBean.getType() == 1) {
            "soup"
        } else {
            "desert"
        }
        var CuisineShow = ""
        CuisineShow = if (mealBean.getType() == 0) {
            "Italian"
        } else if (mealBean.getType() == 1) {
            "Chinese"
        } else {
            "Greek"
        }
Log.i(
            "1111111111",
            mealBean.getCookName() + " " + mealBean.getName() + " " + mealBean.getType() + " " + mealBean.getCuisineType() + " " + mealBean.getDescription()
        )
        cookName.setText(mealBean.getCookName() + "")
        name1.setText(mealBean.getName() + "")
        type.setText(typeShow)
        cuisineType.setText(CuisineShow)
        price.setText(mealBean.getPrice() + "")
        description.setText(mealBean.getDescription() + "")
        add_order.setOnClickListener(View.OnClickListener {
            val orderBean = OrderBean()
            orderBean.clientName = userInfo.getFirstName() + userInfo.getLastName()
            orderBean.setuId(userInfo.getId())
            orderBean.setcId(mealId)
            orderBean.cookName = mealBean.getCookName()
            orderBean.setmId(mealBean.getId())
            orderBean.status = 0
            orderBean.cuisineType = mealBean.getCuisineType()
            orderBean.price = mealBean.getPrice()
            orderDataManager.insertOrder(orderBean)
            ToastUtil.toast(context, "add order success!")
            finish()
        })
        //ingredients = IngredientDataManager.getInstance(this).getIngredientByMealId(mealId);
        //ingredientAdapter.setData(ingredients);
    }
}