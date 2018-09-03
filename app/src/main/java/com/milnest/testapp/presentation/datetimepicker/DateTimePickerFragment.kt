package com.milnest.testapp.presentation.datetimepicker

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.milnest.testapp.R
import com.milnest.testapp.others.utils.setUpBar
import com.milnest.testapp.router.BaseFragment
import com.milnest.testapp.router.FragType
import kotlinx.android.synthetic.main.fragment_date_time_picker.*
import java.util.*


class DateTimePickerFragment : BaseFragment(), DateTimePickerView {

    @InjectPresenter
    lateinit var presenter: DateTimePickerPresenter

    override fun showDateDialog(dateAndTime: Calendar) {
        DatePickerDialog(context, presenter.dateSetListener,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show()
    }

    override fun showTimeDialog(dateAndTime: Calendar) {
        TimePickerDialog(context, presenter.timeSetListener,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show()
    }

    override fun showIinitialDateTime(dateAndTime: Calendar) {
        current_datetime.text = DateUtils.formatDateTime(context,
                dateAndTime.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
                        or DateUtils.FORMAT_SHOW_TIME)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_date_time_picker, container, false)
    }

    override fun onStart() {
        super.onStart()
        bindViews()
        presenter.setInitialDateTime()
    }

    private fun bindViews() {
        set_date_time_button.setOnClickListener(presenter.dateClickListener)
    }

    override fun getFragType(): FragType {
        return FragType.DATE_TIME_PICKER
    }

    override fun setUpActionBar() {
        setUpBar(activity, getString(R.string.date_time_picker_title), true)
    }
}
