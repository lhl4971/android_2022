package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.os.Bundle
import com.example.myapplication.R
import com.example.myapplication.beans.UserBean
import com.example.myapplication.sqlite.UserDataManager
import android.content.Intent
import com.example.myapplication.RegistActivity
import android.text.TextUtils
import com.example.myapplication.ToastUtil
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.view.View
import com.example.myapplication.MainActivity
import com.example.myapplication.client.ClientMainActivity
import com.example.myapplication.admin.AdminMainActivity

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private var accountEditText: EditText? = null
    private var passwordEditText: EditText? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var admin = UserDataManager.getInstance(this).getUserInfoByAccount("admin")
        if (admin == null) {
            admin = UserBean("", "", "", "", "admin", "admin", "", 0, 1)
            UserDataManager.getInstance(this).insertUser(admin)
        }
        initView()
    }

    private fun initView() {
        accountEditText = findViewById(R.id.ed_account)
        passwordEditText = findViewById(R.id.ed_password)
        findViewById<View>(R.id.btn_login).setOnClickListener(this)
        findViewById<View>(R.id.btn_regist).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_login -> login()
            R.id.btn_regist -> {
                val intent = Intent(this, RegistActivity::class.java)
                startActivity(intent)
            }
            else -> {}
        }
    }

    private fun login() {
        val account = accountEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        if (TextUtils.isEmpty(account)) {
            ToastUtil.toast(this, "account is empty!")
            return
        }
        if (TextUtils.isEmpty(password)) {
            ToastUtil.toast(this, "password is empty!")
            return
        }
        val userInfo = UserDataManager.getInstance(this).getUserInfoByAccount(account)
        if (userInfo == null) {
            ToastUtil.toast(this, "account is not exists,please regist!")
            return
        }
        if (userInfo.password != password) {
            ToastUtil.toast(this, "password is not right!")
            return
        }
        if (userInfo.status == 0) {
            ToastUtil.toast(this, "Your account has been disabled by the Administrator!")
            return
        }
        ToastUtil.toast(this, "login success!")
        val sharedPreferences = getSharedPreferences("user", 0)
        val editor = sharedPreferences.edit()
        editor.putString("account", account)
        editor.commit()
        accountEditText!!.postDelayed({
            runOnUiThread {
                accountEditText!!.setText("")
                passwordEditText!!.setText("")
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                val clientIntent = Intent(this@LoginActivity, ClientMainActivity::class.java)
                val adminIntent = Intent(this@LoginActivity, AdminMainActivity::class.java)
                if (0 == userInfo.role) {
                    startActivity(adminIntent)
                } else if (1 == userInfo.role) {
                    startActivity(clientIntent)
                } else {
                    startActivity(intent)
                }
            }
        }, 500)
    }
}