package com.milnest.testapp.presentation.datetimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.text.format.DateUtils
import android.view.View
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import java.util.*

@InjectViewState
class DateTimePickerPresenter : MvpPresenter<DateTimePickerView>() {
    var dateAndTime = Calendar.getInstance()

    fun setInitialDateTime() {
        viewState.showIinitialDateTime(dateAndTime)
    }

    val dateClickListener: View.OnClickListener
    get() = View.OnClickListener { _ ->
        viewState.showDateDialog(dateAndTime)
    }

    val timeClickListener: View.OnClickListener
        get() = View.OnClickListener { _ ->
            viewState.showTimeDialog(dateAndTime)
        }

    // установка обработчика выбора времени
    val timeSetListener: TimePickerDialog.OnTimeSetListener
        get() = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTime.set(Calendar.MINUTE, minute)
            setInitialDateTime()
        }

    // установка обработчика выбора даты
    val dateSetListener: DatePickerDialog.OnDateSetListener
        get() = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateAndTime.set(Calendar.YEAR, year)
            dateAndTime.set(Calendar.MONTH, monthOfYear)
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            /*setInitialDateTime()*/
            viewState.showTimeDialog(dateAndTime)
        }
}
