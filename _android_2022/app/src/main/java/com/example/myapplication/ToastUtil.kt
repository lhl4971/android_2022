package com.example.myapplication

import android.content.Context
import android.widget.Toast

object ToastUtil {
    @JvmStatic
    fun toast(context: Context?, msg: String?) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}