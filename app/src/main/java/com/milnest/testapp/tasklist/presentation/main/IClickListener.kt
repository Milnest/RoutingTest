package com.milnest.testapp.tasklist.presentation.main

interface IClickListener {
    fun onItemClick(position: Int)
    fun onItemLongClick(position: Int) : Boolean
}