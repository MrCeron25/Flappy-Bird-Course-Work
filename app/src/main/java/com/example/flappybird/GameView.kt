package com.example.flappybird

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.view.*
import androidx.annotation.RequiresApi
import com.example.flappybird.flappy.FlappyBird
import com.example.flappybird.flappy.FlappyState
import com.example.flappybird.percent.Percent.Companion.minusPercentNumber


@RequiresApi(Build.VERSION_CODES.R)
class GameView(context: Context) : View(context) {

    private val runnable: Runnable = Runnable {
        invalidate()
    }

    private lateinit var background: Bitmap
    private val heightPixels: Int
    private val widthPixels: Int
    private val base: Bitmap
    private val gameOver: Bitmap
    private val gameOverRectangle: Rect
    private val backgroundRectangle: Rect
    private val baseRectangle: Rect
    private val flappyBird: FlappyBird
    private var birdX: Float
    private var startBirdY: Float
    private var maxBirdY: Float
    private var minBirdY: Float
    private var birdY: Float
    private var velocity: Int = 0 // скорость
    private var gravity: Int = 5 // гравитация
    private var gameState: GameState = GameState.GAME

    init {
        // get display pixels
        val displayMetrics = context.resources.displayMetrics
        heightPixels = getFullHeight(displayMetrics.heightPixels)
        widthPixels = displayMetrics.widthPixels
        // set game over
        val gameOverImage = BitmapFactory.decodeResource(resources, R.drawable.gameover)
        gameOver = Bitmap.createScaledBitmap(
            gameOverImage, widthPixels.minusPercentNumber(30), gameOverImage.height, false
        )
        val leftGameOver = widthPixels / 2 - gameOver.width / 2
        val topGameOver = heightPixels / 2 - gameOver.height / 2
        val rightGameOver = leftGameOver + gameOver.width
        val bottomGameOver = topGameOver + gameOver.height
        gameOverRectangle = Rect(leftGameOver, topGameOver, rightGameOver, bottomGameOver)
        // set random background
        setRandomBackground()
        backgroundRectangle = Rect(0, 0, widthPixels, heightPixels)
        // set and resize base
        val baseImage = BitmapFactory.decodeResource(resources, R.drawable.base)
        base = Bitmap.createScaledBitmap(baseImage, widthPixels, baseImage.height, false)
        val leftBase = 0
        val topBase = heightPixels - base.height
        val rightBase = leftBase + base.width
        val bottomBase = topBase + base.height
        baseRectangle = Rect(leftBase, topBase, rightBase, bottomBase)
        // getting flappy images
        val flappyImageState1 = BitmapFactory.decodeResource(resources, R.drawable.flappy_state_1)
        val flappyImageState2 = BitmapFactory.decodeResource(resources, R.drawable.flappy_state_2)
        // set zoomed flappy
        flappyBird = FlappyBird(
            Bitmap.createScaledBitmap(
                flappyImageState1,
                flappyImageState1.width.minusPercentNumber(Config.ZOOMED_FLAPPY),
                flappyImageState1.height.minusPercentNumber(Config.ZOOMED_FLAPPY),
                false
            ),
            Bitmap.createScaledBitmap(
                flappyImageState2,
                flappyImageState2.width.minusPercentNumber(Config.ZOOMED_FLAPPY),
                flappyImageState2.height.minusPercentNumber(Config.ZOOMED_FLAPPY),
                false
            ),
        )
        // flappy position
        startBirdY = heightPixels / 2 - flappyBird.imageState1.height / 2.toFloat()
        birdX = widthPixels / 2 - flappyBird.imageState1.width / 2.toFloat()
        birdY = startBirdY
        maxBirdY = heightPixels - base.height - flappyBird.imageState1.height.toFloat()
        minBirdY = 0F
    }

    @SuppressLint("InternalInsetResource")
    private fun getFullHeight(heightPixels: Int): Int {
        // status bar height
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }

        // navigation bar height
        var navigationBarHeight = 0
        val resourceId2 = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId2 > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId2)
        }
        return heightPixels + navigationBarHeight + statusBarHeight
    }

    private fun setRandomBackground() {
        val backgroundRandom = (0..1).shuffled().first()
        background = if (backgroundRandom == 0) {
            BitmapFactory.decodeResource(resources, R.drawable.background_day)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.background_night)
        }
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawBitmap(background, null, backgroundRectangle, null)
    }

    private fun drawBase(canvas: Canvas) {
        canvas.drawBitmap(base, null, baseRectangle, null)
    }

    private fun drawGameOver(canvas: Canvas) {
        canvas.drawBitmap(gameOver, null, gameOverRectangle, null)
    }

    private fun drawFlappy(canvas: Canvas) {
        when (flappyBird.state) {
            FlappyState.STATE_1 -> {
                canvas.drawBitmap(flappyBird.imageState1, birdX, birdY, null)
                flappyBird.state = FlappyState.STATE_2
            }
            FlappyState.STATE_2 -> {
                canvas.drawBitmap(flappyBird.imageState2, birdX, birdY, null)
                flappyBird.state = FlappyState.STATE_1
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // рисование элементов
        drawBackground(canvas)
        drawBase(canvas)
        drawFlappy(canvas)

        if (gameState == GameState.GAME) {
            velocity += gravity // увеличиваю скорость падения
            if (birdY + velocity >= maxBirdY) { // упал
                birdY = maxBirdY // не дает упасть ниже base
                gameState = GameState.GAME_OVER
            } else if (birdY < minBirdY) {
                birdY = minBirdY // не дает взлететь выше экрана
                velocity = 0 // чтобы небыло тряски
            } else {  // в воздухе
                birdY += velocity
            }
//            println("$velocity ($birdX, $birdY)")
        } else {
            drawGameOver(canvas)
        }

        handler.postDelayed(runnable, Config.UPDATE_GAME_MILLIS)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            if (gameState == GameState.GAME) {
                velocity = -50
            } else {
                setRandomBackground()
                birdY = startBirdY
                velocity = 0
                gameState = GameState.GAME
            }
        }
        return true
    }
}