package com.milnest.testapp.others.utils

import android.util.Log
import java.util.regex.Pattern

object RegexPicker {
    //[A-Za-zА-Яа-я]
    val patternInitials = Pattern.compile("((\\w+)\\s(\\w+))")

    fun getInitials(nameString: String): String{
        val matcherInitials = patternInitials.matcher(nameString)
        if (matcherInitials.find()){
            //val gr1 = matcherInitials.group(1)
            val gr2 = matcherInitials.group(2)
            val gr3 = matcherInitials.group(3)
            return gr2.take(1) + gr3.take(1)
        }
        else
            return nameString.take(1)
    }
}