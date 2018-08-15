package com.milnest.testapp.entities

import com.milnest.testapp.App
import com.milnest.testapp.R

data class ContactShortInfo(var id: Long, var name: String, var phone: String, var photoUriString: String, var type: Int){
    var color = App.context.resources.getColor(R.color.colorGray_900)
    constructor(id: Long, name: String, phone: String, photoUriString: String, type: Int, color: Int) : this(id, name, phone, photoUriString, type){
        this.color = color
    }
    companion object {
        const val SHORT_INFO_PHOTO = 0
        const val SHORT_INFO_PHOTO_PLACEHOLDER = 1
        const val SHORT_INFO_GROUP = 2
    }
}