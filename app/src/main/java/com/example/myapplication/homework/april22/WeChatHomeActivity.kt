package com.example.myapplication.homework.april22

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R
import kotlinx.android.synthetic.main.wechat_home.*


class WeChatHomeActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.wechat_home)

        wechat.setOnClickListener {
            var weChatFragment = WeChatFragment()
            val fm: FragmentTransaction = supportFragmentManager.beginTransaction()
            fm.replace(R.id.id_container, weChatFragment)
            fm.commit()

        }
        personal.setOnClickListener {
            var personalFragment = PersonalFragment()
            val fm: FragmentTransaction = supportFragmentManager.beginTransaction()
            fm.replace(R.id.id_container, personalFragment)
            fm.commit()
        }
    }

}