package com.example.flappybird

import android.annotation.SuppressLint
import android.app.Activity
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

    private val heightPixels: Int
    private val widthPixels: Int
    private val background: Bitmap
    private val base: Bitmap
    private val backgroundRectangle: Rect
    private val baseRectangle: Rect
    private val flappyBird: FlappyBird
    private var birdX: Float
    private var birdY: Float
    private var velocity: Int = 0 // скорость
    private var gravity: Int = 5 // гравитация
    private var gameState: GameState = GameState.GAME

    init {
        // get display pixels
        val displayMetrics = context.resources.displayMetrics
        heightPixels = getFullHeight(displayMetrics.heightPixels)
        widthPixels = displayMetrics.widthPixels
        // set random background
        val backgroundRandom = (0..1).shuffled().first()
        background = if (backgroundRandom == 0) {
            BitmapFactory.decodeResource(resources, R.drawable.background_day)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.background_night)
        }
        backgroundRectangle = Rect(0, 0, widthPixels, heightPixels)
        // set and resize base
        val baseImage = BitmapFactory.decodeResource(resources, R.drawable.base)
        base = Bitmap.createScaledBitmap(baseImage, widthPixels, baseImage.height, false)
        baseRectangle = Rect(0, heightPixels - base.height, base.width, heightPixels)
        // getting images
        val flappyImageState1 = BitmapFactory.decodeResource(resources, R.drawable.flappy_state_1)
        val flappyImageState2 = BitmapFactory.decodeResource(resources, R.drawable.flappy_state_2)
        // zoomed flappy
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
        birdX = (widthPixels / 2 - flappyBird.imageState1.width / 2).toFloat()
        birdY = (heightPixels / 2 - flappyBird.imageState1.height / 2).toFloat()
    }

    @SuppressLint("InternalInsetResource")
    private fun getFullHeight(heightPixels: Int): Int {
        // status bar height
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
//        // action bar height
//        var actionBarHeight = 0
//        val styledAttributes: TypedArray =
//            (context as Activity).theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
//        actionBarHeight = styledAttributes.getDimension(0, 0f).toInt()
//        styledAttributes.recycle()

        // navigation bar height
        var navigationBarHeight = 0
        val resourceId2 = resources.getIdentifier("navigation_bar_height", "dimen", "android")
        if (resourceId2 > 0) {
            navigationBarHeight = resources.getDimensionPixelSize(resourceId2)
        }
        return heightPixels + navigationBarHeight + statusBarHeight
    }

    private fun drawBackground(canvas: Canvas) {
        canvas.drawBitmap(background, null, backgroundRectangle, null)
    }

    private fun drawBase(canvas: Canvas) {
        canvas.drawBitmap(base, null, baseRectangle, null)
    }

    private fun drawGameOver(canvas: Canvas) {
        canvas.drawBitmap(base, null, baseRectangle, null)
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

        drawBackground(canvas)
        drawBase(canvas)
        drawFlappy(canvas)

//        if (birdY < heightPixels - flappyBird.imageState1.height || velocity < 0) {
//        if (birdY < heightPixels - flappyBird.imageState1.height
//        ) {
//        if (birdY > heightPixels - flappyBird.imageState1.height) {
//            birdY = (heightPixels - flappyBird.imageState1.height).toFloat()
//
        if (birdY > heightPixels - base.height - flappyBird.imageState1.height) {
            birdY = (heightPixels - base.height - flappyBird.imageState1.height).toFloat()
//            gameState = GameState.GAME_OVER
        }
        if (gameState == GameState.GAME) {
            velocity += gravity
            birdY += velocity
            println("$velocity $birdY")
        } else if (gameState == GameState.GAME_OVER) {

        }

        handler.postDelayed(runnable, Config.UPDATE_GAME_MILLIS)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            velocity = -50
        }
        return true
    }
}

//        val metrics: WindowMetrics = (context as Activity).windowManager.getCurrentWindowMetrics()
//        // Gets all excluding insets
//        // Gets all excluding insets
//        val windowInsets = metrics.windowInsets
//        val insets: Insets = windowInsets.getInsetsIgnoringVisibility(
//            WindowInsets.Type.navigationBars()
//                    or WindowInsets.Type.displayCutout()
//        )
//
//        val insetsWidth: Int = insets.right + insets.left
//        val insetsHeight: Int = insets.top + insets.bottom
//
//        // Legacy size that Display#getSize reports
//
//        // Legacy size that Display#getSize reports
//        val bounds = metrics.bounds
//        val legacySize = Size(
//            bounds.width() - insetsWidth,
//            bounds.height() - insetsHeight
//        )