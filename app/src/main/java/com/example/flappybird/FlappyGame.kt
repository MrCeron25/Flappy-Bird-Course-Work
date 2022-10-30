//package com.example.flappybird
//
//import android.widget.RelativeLayout
//import androidx.core.view.setMargins
//
//class FlappyGame(
//    val flappyBird: FlappyBird,
//    mainLayout: RelativeLayout,
//    var stateGame: GameState = GameState.GAME
//) : Thread() {
//
//    init {
//        val layoutParams = flappyBird.imageView.layoutParams as RelativeLayout.LayoutParams
//        println(mainLayout.width / 2)
//        println(mainLayout.height / 2)
//        layoutParams.setMargins(
//            mainLayout.width / 2,
//            0,
//            mainLayout.width / 2,
//            0
//        )
//        flappyBird.imageView.layoutParams = layoutParams
//    }
//    /*
//           android:layout_centerHorizontal="true"
//        android:layout_centerVertical="true"
//     */
//
//    private fun privateStartGame() {
////        val layoutParams = flappyBird.imageView.layoutParams as RelativeLayout.LayoutParams
////        layoutParams.setMargins()
////        println(flappyBird.position)
////        flappyBird.posY += 20
////        println(flappyBird.position)
////        flappyBird.posY = 300
////        layoutParams.rightMargin
////            layoutParams.setMargins(100, 100, 100, 100)
//        while (true) {
////            println(flappyBird.margins)
////            println(flappyBird.margins)
//            if (stateGame == GameState.GAME) {
////                flappyBird.posY += 20
//
//            } else if (stateGame == GameState.MENU) {
//
//            }
//            sleep(Config.GAME_SPEED)
//        }
//    }
//
//    fun startGame() {
//        start()
//    }
//
//    override fun run() {
//        privateStartGame()
//    }
//
//    private fun gameOver() {
//
//    }
//
//}