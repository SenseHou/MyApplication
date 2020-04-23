package com.example.myapplication.homework.april21.front

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_list.*
import org.json.JSONArray
import org.json.JSONObject


class ListActivity : Activity() {

    val pic = intArrayOf(R.mipmap.jlzj, R.mipmap.lmj)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        var data = "  [\n" +
                " {\"name\":\"叫了只鸡·韩式炸鸡（曲江金汇店）\",\"star\":4.4,\"mSales\":1024,\"time\":\"34\",\"distance\":\"333m\",\"defaultSend\":20,\"sendType\":\"免配送费\",\"coust\":0,\"average\":31,\"tag\":[\"特别好吃\",\"点评高分店\"],\"specialFlag\":true,\"discount\":[\"30减3\",\"3.8折\"]}\n" +
                ",{\"name\":\"老米家泡沫烧烤（金辉世界城店）\",\"star\":4.7,\"mSales\":123,\"time\":\"46\",\"distance\":\"1.2km\",\"defaultSend\":0,\"sendType\":\"夜间配送\",\"coust\":6,\"average\":32,\"tag\":[\"泡沫量足\",\"素拼好吃\"],\"specialFlag\":false,\"discount\":[\"50减8\",\"优惠券5元\"]}\n" +
                "]"
        var toMutableList = JSONArray(data).toMutableList()
        listView.layoutManager = LinearLayoutManager(this)
        val myAdapter = MyAdapter(toMutableList)
        listView.adapter = myAdapter
    }


    inner class MyAdapter(var data: MutableList<Any>) : RecyclerView.Adapter<MyViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

            return MyViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_layout, null)
            )
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.bind(data[position], position)
        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Any, index: Int) {

            itemView.findViewById<ImageView>(R.id.shop_icon).setImageDrawable(
                ContextCompat.getDrawable(applicationContext, pic[index])
            )
            itemView.findViewById<ImageView>(R.id.star_img).setImageDrawable(
                ContextCompat.getDrawable(applicationContext, R.mipmap.star)
            )
            itemView.findViewById<TextView>(R.id.title).text =
                (data as JSONObject).get("name").toString()
            itemView.findViewById<TextView>(R.id.star).text = data.get("star").toString()
            itemView.findViewById<TextView>(R.id.time).text = data.get("time").toString() + "分钟"
            itemView.findViewById<TextView>(R.id.mSales).text =
                "月售" + data.get("mSales").toString()
            itemView.findViewById<TextView>(R.id.distance).text =
                data.get("distance").toString()
            itemView.findViewById<TextView>(R.id.defaultSend).text = "起送￥" +
                    data.get("defaultSend").toString()
            itemView.findViewById<TextView>(R.id.sendType).text =
                data.get("sendType").toString()
            itemView.findViewById<TextView>(R.id.coust).text = "￥" +
                    data.get("coust").toString()
            itemView.findViewById<TextView>(R.id.average).text = "人均￥" +
                    data.get("average").toString()
            itemView.findViewById<TextView>(R.id.tag).text =
                (data.get("tag") as JSONArray).join("  ")
            itemView.findViewById<TextView>(R.id.discount).text =
                (data.get("discount") as JSONArray).join("  ")
            itemView.findViewById<TextView>(R.id.specialFlag).isVisible =
                data.get("specialFlag") as Boolean

        }
    }

    private fun JSONArray.toMutableList(): MutableList<Any> = MutableList(length(), this::get)

}