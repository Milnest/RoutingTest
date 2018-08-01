package com.milnest.testapp.entities

class InfoItem(var content: String, var itemType: Int){
    companion object {
        const val INFO_TYPE_TEXT = 0
        const val INFO_TYPE_PHOTO = 1
        const val INFO_TYPE_PHOTO_TEXT = 2
    }
}