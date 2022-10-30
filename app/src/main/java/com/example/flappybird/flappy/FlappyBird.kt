package com.example.flappybird.flappy

import android.graphics.Bitmap

class FlappyBird(
    var imageState1: Bitmap,
    var imageState2: Bitmap,
    var state: FlappyState = FlappyState.STATE_1,
)