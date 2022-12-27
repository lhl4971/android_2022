package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.myapplication.adapter.IngredientAdapter;
import com.example.myapplication.beans.Ingredient;
import com.example.myapplication.beans.MealBean;
import com.example.myapplication.beans.UserBean;
import com.example.myapplication.sqlite.IngredientDataManager;
import com.example.myapplication.sqlite.MealDataManager;
import com.example.myapplication.sqlite.UserDataManager;

import java.util.ArrayList;
import java.util.List;

public class MealActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private EditText nameEditText;
    private EditText priceEditText;
    private EditText descriptionEditText;
    private RecyclerView ingredientView;
    private Spinner typeSpinner;
    private Spinner cuisineTypeSpinner;
    private int type = 0;
    private  int cuisineType = 0;
    IngredientAdapter ingredientAdapter;
    UserBean userInfo;
    MealBean mealBean;
    List<Ingredient> ingredients = new ArrayList<>();
    boolean isAdd = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);
        SharedPreferences sharedPreferences = getSharedPreferences("user",0);
        String account = sharedPreferences.getString("account","");
        userInfo = UserDataManager.getInstance(this).getUserInfoByAccount(account);

        mealBean = (MealBean) getIntent().getSerializableExtra("meal");
        if (mealBean == null){
          long id = MealDataManager.getInstance(this).insertDraftMeal();
          mealBean = new MealBean();
          mealBean.setId((int)id);
          mealBean.setuId(userInfo.getId());
          mealBean.setStatus(1);
          mealBean.setCookName(userInfo.getFirstName()+userInfo.getLastName());
          isAdd = true;
        }


        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ingredients = IngredientDataManager.getInstance(this).getIngredientByMealId(mealBean.getId());
        ingredientAdapter.setData(ingredients);
    }
    private void initView() {
        nameEditText = findViewById(R.id.ed_name);
        priceEditText = findViewById(R.id.ed_price);
        descriptionEditText =  findViewById(R.id.ed_description);
        typeSpinner = findViewById(R.id.spin_type);
        cuisineTypeSpinner = findViewById(R.id.spin_cuisine_type);
        ingredientView = findViewById(R.id.tv_ingred);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ingredientView.setLayoutManager(layoutManager);
        ingredientAdapter = new IngredientAdapter(this);
        ingredientView.setAdapter(ingredientAdapter);
        ingredientAdapter.setItemClickListener(new IngredientAdapter.ItemClickListener() {
            @Override
            public void toDetail(Ingredient ingredient) {
              Intent intent = new Intent(MealActivity.this,IngredientActivity.class);
              intent.putExtra("mealId",mealBean.getId()+"");
              intent.putExtra("Id",ingredient.getId()+"");
              startActivity(intent);
            }
        });


        typeSpinner.setOnItemSelectedListener(this);
        cuisineTypeSpinner.setOnItemSelectedListener(this);
        findViewById(R.id.btn_add_meal).setOnClickListener(this);
        findViewById(R.id.btn_add_ingredient).setOnClickListener(this);
        if (isAdd == false){
            nameEditText.setText(mealBean.getName());
            priceEditText.setText(mealBean.getPrice());
            typeSpinner.setSelection(mealBean.getType());
            cuisineTypeSpinner.setSelection(mealBean.getCuisineType());
            descriptionEditText.setText(mealBean.getDescription());

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_meal:
                addMeal();
                break;
            case R.id.btn_add_ingredient:
                addIngredient();
                break;
            default:
                break;
        }

    }
    private void  addIngredient(){
        Intent intent = new Intent(this,IngredientActivity.class);
        intent.putExtra("mealId",mealBean.getId()+"");
        startActivity(intent);
    }
    private void addMeal(){
        String name = nameEditText.getText().toString();
        String price = priceEditText.getText().toString();
        String description = descriptionEditText.getText().toString();
        if (TextUtils.isEmpty(name)){
            ToastUtil.toast(this,"name is empty!");
            return;
        }
        if (TextUtils.isEmpty(price)){
            ToastUtil.toast(this,"price is empty!");
            return;
        }
        if (TextUtils.isEmpty(description)){
            ToastUtil.toast(this,"description is empty!");
            return;
        }

        mealBean.setName(name);
        mealBean.setPrice(price);
        mealBean.setType(type);
        mealBean.setCuisineType(cuisineType);
        mealBean.setDescription(description);

        MealDataManager.getInstance(this).updateMeal(mealBean);
        ToastUtil.toast(this,"add meal success!");


        nameEditText.postDelayed(() -> runOnUiThread(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }),500);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch (adapterView.getId()){
            case R.id.spin_type:
                type = i ;
                break;
            case R.id.spin_cuisine_type:
                cuisineType = i ;
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}