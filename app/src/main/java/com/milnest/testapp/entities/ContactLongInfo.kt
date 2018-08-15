package com.milnest.testapp.entities

import com.milnest.testapp.App
import com.milnest.testapp.R

data class ContactLongInfo(var id: Long, var info: MutableList<Info>, var photo: Info, var color: Int = App.context.resources.getColor(R.color.colorGray_900))