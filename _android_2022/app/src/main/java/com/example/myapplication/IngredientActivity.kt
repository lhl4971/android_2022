package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.example.myapplication.beans.Ingredient
import android.os.Bundle
import com.example.myapplication.R
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.sqlite.IngredientDataManager
import com.example.myapplication.ToastUtil

class IngredientActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var nameEditText: EditText
    private var mealId = 0
    private var isEdit = false
    private lateinit var ingredient: Ingredient
    private lateinit var alert: AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient)
        val mId = intent.getStringExtra("mealId")
        mealId = mId!!.toInt()
        val Id = intent.getStringExtra("Id")
        if (!TextUtils.isEmpty(Id)) {
            isEdit = true
            ingredient = IngredientDataManager.getInstance(this).getIngredientById(Id!!.toInt())
        } else {
            ingredient = Ingredient()
            ingredient!!.setmId(mealId)
        }
        initView()
    }

    private fun initView() {
        nameEditText = findViewById(R.id.ed_name)
        findViewById<View>(R.id.btn_delete_ingredient).setOnClickListener(this)
        findViewById<View>(R.id.btn_save_ingredient).setOnClickListener(this)
        if (isEdit) {
            nameEditText.setText(ingredient!!.name)
        } else {
            findViewById<View>(R.id.btn_delete_ingredient).visibility = View.GONE
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_delete_ingredient -> deleteIngredient()
            R.id.btn_save_ingredient -> saveIngredient()
            else -> {}
        }
    }

    private fun deleteIngredient() {
        val builder = AlertDialog.Builder(this)
        alert = builder.setTitle("tip")
            .setMessage("are you confirm delete?")
            .setNegativeButton("cancel") { dialog, which -> alert!!.dismiss() }
            .setPositiveButton("confirm") { dialog, which ->
                IngredientDataManager.getInstance(this@IngredientActivity).deleteIngredientById(
                    ingredient!!.id
                )
                ToastUtil.toast(this@IngredientActivity, "delete success!")
                finish()
            }.create()
        alert!!.show()
    }

    private fun saveIngredient() {
        val name = nameEditText!!.text.toString()
        if (TextUtils.isEmpty(name)) {
            ToastUtil.toast(this, "name is empty!")
            return
        }
        ingredient!!.name = name
        if (isEdit) {
            IngredientDataManager.getInstance(this).saveIngredient(ingredient)
        } else {
            IngredientDataManager.getInstance(this).insertIngredient(ingredient)
        }
        var msg = "add success!"
        if (isEdit) {
            msg = "edit success!"
        }
        ToastUtil.toast(this, msg)
        nameEditText!!.postDelayed({ finish() }, 500)
    }
}