package com.milnest.testapp.tasklist.data.web

interface Translator {
    fun translate(transDirection: String, input: String?): String
}