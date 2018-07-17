package com.milnest.testapp.router

import ru.terrakok.cicerone.commands.SystemMessage

internal class CustomSystemMessage(message: String, val type: Int) : SystemMessage(message)