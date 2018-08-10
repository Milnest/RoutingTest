package com.milnest.testapp.others.utils

import com.milnest.testapp.App
import com.milnest.testapp.R
import java.util.*

object ColorPick{
    private val DEEP_PURPLE = App.context.resources.getColor(R.color.deep_purple_500)
    private val CYAN = App.context.resources.getColor(R.color.cyan_a200)
    private val RED = App.context.resources.getColor(R.color.red_a400)
    private val BLUE = App.context.resources.getColor(R.color.blue_500)
    private val GREEN = App.context.resources.getColor(R.color.green_a400)
    private val LIGHT_GREEN = App.context.resources.getColor(R.color.light_green_a400)
    private val BROWN = App.context.resources.getColor(R.color.brown_700)
    private val PURPLE = App.context.resources.getColor(R.color.purple_a200)
    val colorArrayList = arrayListOf(DEEP_PURPLE, CYAN, RED, BLUE, GREEN, LIGHT_GREEN, BROWN, PURPLE)
}

fun pickLiteralColors(): Pair<Int, Int>{
    val colorNumber = (Random().nextInt(ColorPick.colorArrayList.size))
    val firstColor = ColorPick.colorArrayList[colorNumber]
    val tempList = ColorPick.colorArrayList
    tempList.remove(firstColor)
    val secondColor = tempList[Random().nextInt(tempList.size)]
    return Pair(firstColor, secondColor)
}