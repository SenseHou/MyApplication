package com.example.myapplication

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.net.Uri
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.homework.april21.front.ListActivity
import com.example.myapplication.homework.april22.DataTransmissionActivity
import com.example.myapplication.homework.april22.WeChatHomeActivity
import com.example.myapplication.homework.april23.MusicService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mService: MusicService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        goListButton.setOnClickListener {
            startActivity(Intent(this, ListActivity::class.java))
        }

        callButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + 18503077991))
//            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + 18503077991))
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        msgButton.setOnClickListener {
            val intent2 = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:" + 18503077991));
//            val intent2 =  Intent(Intent.ACTION_VIEW,Uri.parse("smsto:"+ 18503077991));
            intent2.putExtra("sms_body", "哈哈哈");
            startActivity(intent2)
        }

        dataTranButton.setOnClickListener {
            startActivity(Intent(this, DataTransmissionActivity::class.java))
        }

        goWeChatHomeButton.setOnClickListener {
            startActivity(Intent(this, WeChatHomeActivity::class.java))
        }
        //音乐播放器
        initMusicService()
        playBtn.setOnClickListener {
            mService.onClick(playBtn)
        }
        pauseBtn.setOnClickListener {
            mService.onClick(pauseBtn)
        }
        resetBtn.setOnClickListener {
            mService.onClick(resetBtn)
        }
        closeBtn.setOnClickListener {
            mService.onClick(closeBtn)
        }
        //读取短信息
        readMsgBtn.setOnClickListener {
            readMsgText.text = getPhioneMsg()
        }

    }

    @SuppressLint("Recycle")
    private fun getPhioneMsg(): String {
        val SMS_URI_ALL = "content://sms/"; // 所有短信
        val SMS_URI_INBOX = "content://sms/inbox"; // 收件箱
        val SMS_URI_SEND = "content://sms/sent"; // 已发送
        val SMS_URI_DRAFT = "content://sms/draft"; // 草稿
        val SMS_URI_OUTBOX = "content://sms/outbox"; // 发件箱
        val SMS_URI_FAILED = "content://sms/failed"; // 发送失败
        val SMS_URI_QUEUED = "content://sms/queued"; // 待发送列表
        val uri = Uri.parse(SMS_URI_ALL);
        val projection = arrayOf(
            "_id", "address", "person",
            "body", "date", "type"
        )
        //未读短信
        var cur =
//            contentResolver.query(uri, projection, "read = ?", arrayOf("0"), "date desc")//未读
            contentResolver.query(uri, projection, null, null, "date desc")//所有
        if (cur != null && cur.moveToFirst()) {
            val address: String = cur.getString(cur.getColumnIndex("address"))
            val person: Int = cur.getInt(cur.getColumnIndex("person"))
            val body: String = cur.getString(cur.getColumnIndex("body"))
            val date: Long = cur.getLong(cur.getColumnIndex("date"))
            val type: Int = cur.getInt(cur.getColumnIndex("type"))
            return body
        }

        return "没找到！"
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
        Log.i("回写", "刚进来")
        if (resultCode == 2 && requestCode == R.layout.data_transmission) {
            Log.i("回写", "R.layout.data_transmission")
            val datas = data?.extras
            val name = datas?.get("name").toString()
            val age = datas?.get("age")
            val sex = datas?.get("sex").toString()
            dataTranButton.text = ("${name}-${age}-${sex}")
        }
        Toast.makeText(this, "接收成功", Toast.LENGTH_SHORT).show()
    }

    private fun initMusicService() {
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, ServiceConn, Context.BIND_AUTO_CREATE)
    }

    private val ServiceConn = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            if (service is MusicService.MusicBinder) {
                mService = service.getService()
                Toast.makeText(this@MainActivity, service.getData(), Toast.LENGTH_SHORT).show()
            }
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
        }
    }

}


