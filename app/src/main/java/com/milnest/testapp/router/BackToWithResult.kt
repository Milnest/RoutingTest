package com.milnest.testapp.router

import android.os.Bundle
import ru.terrakok.cicerone.commands.Command

internal class BackToWithResult(val screenKey: String, val result: Bundle) : Command