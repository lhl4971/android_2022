package com.example.myapplication

import android.Manifest
import android.content.Context
import com.example.myapplication.ToastUtil.toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.beans.MealBean
import com.example.myapplication.beans.UserBean
import com.example.myapplication.beans.OrderBean
import android.widget.TextView
import android.widget.EditText
import android.location.LocationManager
import android.location.Criteria
import com.example.myapplication.sqlite.ComplainDataManager
import com.example.myapplication.beans.Ingredient
import android.os.Bundle
import com.example.myapplication.R
import android.content.SharedPreferences
import com.example.myapplication.sqlite.UserDataManager
import com.example.myapplication.sqlite.MealDataManager
import android.text.TextUtils
import com.example.myapplication.ToastUtil
import com.example.myapplication.beans.ComplainBean
import com.example.myapplication.ClientComplainActivity
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.widget.Toast
import android.location.LocationListener
import android.content.Intent
import android.location.Location
import android.os.Handler
import android.provider.Settings
import android.util.Log
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

class ClientComplainActivity : AppCompatActivity() {
    lateinit var mealBean: MealBean
    lateinit var userInfo: UserBean
    lateinit var orderBean: OrderBean
    lateinit var orderId: TextView
    lateinit var cookName: TextView
    lateinit var ed_grade: EditText
    lateinit var ed_complain: EditText
    lateinit var btn_complain: View
    lateinit var context: Context
    private lateinit var tv_location: TextView
    private var mLocation = ""
    private lateinit var mLocationMgr // 声明一个定位管理器对象
            : LocationManager
    private val mCriteria = Criteria() // 声明一个定位准则对象
    private val mHandler = Handler() // 声明一个处理器
    private var isLocationEnable = false // 定位服务是否可用
    private lateinit var longitude: TextView
    private lateinit var latitude: TextView
    lateinit var complainDataManager: ComplainDataManager
    private val mealId = 0
    var ingredients: List<Ingredient> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client_complain)
        val sharedPreferences = getSharedPreferences("user", 0)
        val account = sharedPreferences.getString("account", "")
        userInfo = UserDataManager.getInstance(this).getUserInfoByAccount(account)
        orderBean = intent.getSerializableExtra("order") as OrderBean
        val order_Id = orderBean!!.id
        val mId = orderBean!!.getmId()
        val cId = orderBean!!.getcId()
        val mealBean = MealDataManager.getInstance(this).getMealInfosById(mId)
        context = this
        complainDataManager = ComplainDataManager.getInstance(context)
        orderId = findViewById(R.id.orderId)
        cookName = findViewById(R.id.cookName)
        ed_grade = findViewById(R.id.ed_grade)
        ed_complain = findViewById(R.id.ed_complain)
        btn_complain = findViewById(R.id.btn_complain)
        longitude = findViewById(R.id.ed_longitude)
        latitude = findViewById(R.id.ed_latitude)
        orderId.setText(order_Id.toString())
        cookName.setText(mealBean.cookName)
        mHandler.removeCallbacks(mRefresh) // 移除定位刷新任务
        initLocation()
        mHandler.postDelayed(mRefresh, 100)
        btn_complain.setOnClickListener(View.OnClickListener {
            val grade = ed_grade.getText().toString()
            val complain = ed_complain.getText().toString()
            if (TextUtils.isEmpty(grade)) {
                toast(context, "grade is empty!")
                return@OnClickListener
            }
            if (TextUtils.isEmpty(complain)) {
                toast(context, "complain is empty!")
                return@OnClickListener
            }
            val complainBean = ComplainBean()
            complainBean.fromId = userInfo.getId()
            complainBean.toId = mealBean.getuId()
            complainBean.fromName = userInfo.getFirstName() + userInfo.getLastName()
            complainBean.toName = mealBean.name
            complainBean.setoId(order_Id)
            complainBean.status = 0
            complainBean.grade = grade.toInt()
            complainBean.comment = complain
            complainDataManager.insertComplainBean(complainBean)
            toast(context, "add complain success!")
            finish()
        })
    }

    private fun initLocation() {
        // 从系统服务中获取定位管理器
        mLocationMgr = context!!.getSystemService(LOCATION_SERVICE) as LocationManager
        Log.d(TAG, mLocationMgr.toString())
        // 设置定位精确度。Criteria.ACCURACY_COARSE表示粗略，Criteria.ACCURACY_FIN表示精细
        //mCriteria.setAccuracy(Criteria.ACCURACY_FINE);
        // 设置是否需要海拔信息
        //mCriteria.setAltitudeRequired(true);
        // 设置是否需要方位信息
        //Criteria.setBearingRequired(true);
        // 设置是否允许运营商收费
        //mCriteria.setCostAllowed(true);
        // 设置对电源的需求
        //mCriteria.setPowerRequirement(Criteria.POWER_LOW);
        // 获取定位管理器的最佳定位提供者
        var bestProvider = mLocationMgr!!.getBestProvider(mCriteria, true)
        bestProvider = LocationManager.NETWORK_PROVIDER
        Log.d(TAG, bestProvider)
        if (mLocationMgr!!.isProviderEnabled(bestProvider)) { // 定位提供者当前可用
            //tv_location.setText("正在获取" + bestProvider + "定位对象");
            mLocation = String.format("定位类型=%s", bestProvider)
            Log.d(TAG, "定位类型=%s$bestProvider")
            beginLocation(bestProvider)
            isLocationEnable = true
        } else { // 定位提供者暂不可用
            //tv_location.setText("\n" + bestProvider + "定位不可用");
            isLocationEnable = false
        }
    }

    // 开始定位
    private fun beginLocation(method: String) {
        // 检查当前设备是否已经开启了定位功能
        if (ActivityCompat.checkSelfPermission(context!!, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(context, "请授予定位权限并开启定位功能", Toast.LENGTH_SHORT).show()
            return
        }
        // 设置定位管理器的位置变更监听器
        //mLocationMgr.requestLocationUpdates(method, 300, 0, mLocationListener);
        // 获取最后一次成功定位的位置信息
        val location = mLocationMgr!!.getLastKnownLocation(method)
        setLocationText(location)
    }

    // 设置定位结果文本
    private fun setLocationText(location: Location?) {
        if (location != null) {
            val desc = String.format(
                """%s
定位对象信息如下： 
	其中时间：%s
	其中经度：%f，纬度：%f
	其中高度：%d米，精度：%d米""",
                mLocation, nowDateTimeFormat,
                location.longitude, location.latitude,
                Math.round(location.altitude), Math.round(location.accuracy)
            )
            latitude!!.text = " " + location.latitude
            longitude!!.text = " " + location.longitude
            Log.d(TAG, desc)
            //tv_location.setText(desc);
        } else {
            Log.d(TAG, "暂未获取到定位对象")
            //tv_location.setText(mLocation + "\n暂未获取到定位对象");
        }
    }

    // 定义一个位置变更监听器
    private val mLocationListener: LocationListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            setLocationText(location)
        }

        override fun onProviderDisabled(arg0: String) {}
        override fun onProviderEnabled(arg0: String) {}
        override fun onStatusChanged(arg0: String, arg1: Int, arg2: Bundle) {}
    }

    // 定义一个刷新任务，若无法定位则每隔一秒就尝试定位
    private val mRefresh: Runnable = object : Runnable {
        override fun run() {
            if (!isLocationEnable) {
                initLocation()
                mHandler.postDelayed(this, 1000)
            }
        }
    }

    companion object {
        private const val TAG = "LocationActivity"

        // 获取定位功能的开关状态
        fun getGpsStatus(ctx: Context): Boolean {
            // 从系统服务中获取定位管理器
            val lm = ctx.getSystemService(LOCATION_SERVICE) as LocationManager
            return lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        }

        // 检查定位功能是否打开，若未打开则跳到系统的定位功能设置页面
        fun checkGpsIsOpen(ctx: Context, hint: String?) {
            if (!getGpsStatus(ctx)) {
                Toast.makeText(ctx, hint, Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                ctx.startActivity(intent)
            }
        }

        val nowDateTimeFormat: String
            get() {
                val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                return sdf.format(Date())
            }
    }
}