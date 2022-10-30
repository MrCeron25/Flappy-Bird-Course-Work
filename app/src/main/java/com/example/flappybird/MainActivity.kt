package com.example.flappybird

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Size
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowMetrics
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.view.marginBottom

class MainActivity : AppCompatActivity() {

//    lateinit var mainLayout: RelativeLayout
//    lateinit var flappyImageView: ImageView
//    lateinit var flappy: FlappyBird
//    lateinit var game: FlappyGame

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startGame()

//        mainLayout = findViewById(R.id.mainRelativeLayout)
//        flappyImageView = findViewById(R.id.flappyBird)
//        flappy = FlappyBird(flappyImageView)
//        game = FlappyGame(flappy, mainLayout)
//        game.startGame()
    }

    fun startGame() {
        val intent = Intent(this, StartGame::class.java)
        startActivity(intent)
        finish()
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//
//    }
//
//    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
//        super.onRestoreInstanceState(savedInstanceState)
//
//    }

}