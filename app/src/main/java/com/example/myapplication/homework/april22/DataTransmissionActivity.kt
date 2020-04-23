package com.example.myapplication.homework.april22

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.myapplication.MainActivity
import com.example.myapplication.R

class DataTransmissionActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.data_transmission)
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val bundle = Bundle()
        bundle.putString("name", "张三")
        bundle.putInt("age", 22)
        bundle.putString("sex", "女")
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(2, Intent(this, MainActivity::class.java).putExtras(intent))
        Log.i("onBackPressed","信息绑定了")
        finish()
//        finishActivity(R.layout.data_transmission)
//        finishAfterTransition()
    }

}