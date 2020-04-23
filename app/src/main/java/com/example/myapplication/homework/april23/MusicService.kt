package com.example.myapplication.homework.april23

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.R

class MusicService : Service(), MediaPlayer.OnPreparedListener {

    private val binder = MusicBinder()
    private var player = MediaPlayer()
    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.lcf)
        player.apply {
            setOnPreparedListener(this@MusicService)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
        fun getData(): String = "service 被绑定好了！"
    }

    override fun onPrepared(mp: MediaPlayer?) {
    }

    fun onClick(mainActivity: Button) {
        when (mainActivity.id) {
            R.id.playBtn -> {
                if (player.isPlaying) return
                player.start()
                Toast.makeText(this, "start", Toast.LENGTH_SHORT).show()
            }
            R.id.pauseBtn -> {
                if (player.isPlaying) player.pause()
                Toast.makeText(this, "pause", Toast.LENGTH_SHORT).show()
            }
            R.id.resetBtn -> {
                if (player.isPlaying) player.pause()
                player.seekTo(0)
                Toast.makeText(this, "reset", Toast.LENGTH_SHORT).show()
            }
            R.id.closeBtn -> {
                if (player.isPlaying) player.stop()
                Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show()
            }
        }
    }
}