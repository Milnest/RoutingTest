package com.milnest.testapp.tasklist.entities

import android.content.Intent

data class ResultOfActivity(val requestCode: Int, val resultCode: Int, val data: Intent?) {
}