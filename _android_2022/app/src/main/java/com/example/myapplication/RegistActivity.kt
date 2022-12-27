package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.RadioGroup
import android.os.Bundle
import com.example.myapplication.R
import android.text.TextUtils
import android.view.View
import com.example.myapplication.ToastUtil
import com.example.myapplication.beans.UserBean
import com.example.myapplication.sqlite.UserDataManager

class RegistActivity : AppCompatActivity(), View.OnClickListener {
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var firstNameEditText: EditText? = null
    private var lastNameEditText: EditText? = null
    private var chequeEditText: EditText? = null
    private var addressEditText: EditText? = null
    private var descriptionEditText: EditText? = null
    private var roleRadioGroup: RadioGroup? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regist)
        initView()
    }

    private fun initView() {
        emailEditText = findViewById(R.id.ed_email)
        passwordEditText = findViewById(R.id.ed_password)
        firstNameEditText = findViewById(R.id.ed_first_name)
        lastNameEditText = findViewById(R.id.ed_last_name)
        chequeEditText = findViewById(R.id.ed_cheque)
        addressEditText = findViewById(R.id.ed_address)
        descriptionEditText = findViewById(R.id.ed_description)
        roleRadioGroup = findViewById(R.id.radioGroup_role)
        findViewById<View>(R.id.btn_login).setOnClickListener(this)
        findViewById<View>(R.id.btn_regist).setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_login -> finish()
            R.id.btn_regist -> regist()
            else -> {}
        }
    }

    private fun regist() {
        val email = emailEditText!!.text.toString()
        val password = passwordEditText!!.text.toString()
        val firstName = firstNameEditText!!.text.toString()
        val lastName = lastNameEditText!!.text.toString()
        val cheque = chequeEditText!!.text.toString()
        val address = addressEditText!!.text.toString()
        val desc = descriptionEditText!!.text.toString()
        var role = 0
        role = if (roleRadioGroup!!.checkedRadioButtonId == R.id.radioButton_client) {
            1
        } else {
            2
        }
        val checkDatas = arrayOf(email, password, firstName, lastName, cheque, address, desc)
        val msgs = arrayOf(
            "email",
            "password",
            "firstName",
            "lastName",
            "cheque",
            "address",
            "description"
        )
        var msg = ""
        for (i in checkDatas.indices) {
            if (TextUtils.isEmpty(checkDatas[i])) {
                msg = msgs[i] + " is empty!"
                break
            }
        }
        if (msg.length != 0) {
            ToastUtil.toast(this, msg)
            return
        }
        val userInfo = UserDataManager.getInstance(this).getUserInfoByAccount(email)
        if (userInfo != null) {
            ToastUtil.toast(this, "The user already exists")
            return
        }
        val userBean =
            UserBean(firstName, lastName, address, cheque, email, password, desc, role, 1)
        UserDataManager.getInstance(this).insertUser(userBean)
        ToastUtil.toast(this, "regist success!")
        emailEditText!!.postDelayed({ runOnUiThread { finish() } }, 1000)
    }
}