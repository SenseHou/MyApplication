package com.example.myapplication.homework.april22

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapplication.R

class WeChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater
        , container: ViewGroup?
        , savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tab01, container, false);
    }


}