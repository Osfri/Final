package com.example.android.lunch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.R

class FoodActivity : AppCompatActivity() {

    // 데이터 확인용 변수로 지워도 됩니다
    var userList = arrayListOf<FoodDto>(
        FoodDto("1", "2022-06-08", "자장면,짬뽕"),
        FoodDto("2", "2022-05-08", "자장면"),
        FoodDto("3", "2022-01-08", "밥")


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_food)

        var foodRecyclerView = findViewById<RecyclerView>(R.id.foodRecyclerView)

        val mAdapter = CustomAdapterFood(this, userList)
        foodRecyclerView.adapter = mAdapter

        var layout = LinearLayoutManager(this)
        foodRecyclerView.layoutManager = layout

        foodRecyclerView.setHasFixedSize(true)

    }
}