package com.milnest.testapp.entities

data class Info(var type: Int, var content: String) {
    companion object {
        const val TYPE_CONTACT_PHOTO = 0
        const val TYPE_CONTACT_PHOTO_HOLDER = 1
        const val TYPE_CONTACT_EMAIL = 2
        const val TYPE_CONTACT_PHONE = 3
        const val TYPE_CONTACT_NAME = 4
        const val TYPE_EVENT_TITLE = 5
        const val TYPE_EVENT_CONTENT = 6
        const val TYPE_JUST_TEXT = 7
    }
}