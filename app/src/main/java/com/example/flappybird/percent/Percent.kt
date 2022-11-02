package com.example.flappybird.percent

class Percent {
    companion object {
        private const val MAX_PERCENT = 100
        private const val MIN_PERCENT = 0

        private fun isPercent(percent: Int): Boolean = percent in (MIN_PERCENT..MAX_PERCENT)
        private fun isNotPercent(percent: Int): Boolean = !isPercent(percent)

        private fun Int.getPercentageNumber(percent: Int): Int {
            if (isNotPercent(percent)) return MIN_PERCENT
            return (this * percent) / MAX_PERCENT
        }

        fun Int.minusPercentNumber(percent: Int): Int {
            if (isNotPercent(percent)) return this
            return this - getPercentageNumber(percent)
        }

        fun Int.plusPercentNumber(percent: Int): Int {
            if (isNotPercent(percent)) return this
            return this + getPercentageNumber(percent)
        }
    }
}