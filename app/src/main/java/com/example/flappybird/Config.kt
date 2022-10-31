package com.example.flappybird

class Config {
    companion object {
        const val UPDATE_GAME_MILLIS: Long = 40
        const val GAP: Int = 40
        const val ZOOMED_FLAPPY: Int = 50 // (0..100)%. 0 - не уменьшая. 60 - уменьшить на 60%.
    }
}