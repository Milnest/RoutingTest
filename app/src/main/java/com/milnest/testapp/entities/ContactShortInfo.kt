package com.milnest.testapp.entities

data class ContactShortInfo(var id: Long, var name: String, var phone: String, var photoUriString: String, var type: Int){
    companion object {
        const val SHORT_INFO_PHOTO = 0
        const val SHORT_INFO_PHOTO_PLACEHOLDER = 1
    }
}