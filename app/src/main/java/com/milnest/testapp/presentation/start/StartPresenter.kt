package com.milnest.testapp.presentation.start

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.res.Configuration
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.View
import android.view.WindowManager
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.milnest.testapp.App
import com.milnest.testapp.R
import com.milnest.testapp.router.FragType
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import kotlin.math.absoluteValue
import android.view.Surface


@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {

    private var sensorManager: SensorManager? = null
    private var sensorAccel: Sensor? = null
    private var sensorMagnet: Sensor? = null
    private var timer: Timer? = null
    private var rotation: Int = 0

    fun setUpSensorManager() {
        sensorManager = App.context.getSystemService(SENSOR_SERVICE) as SensorManager
        sensorAccel = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorMagnet = sensorManager?.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
    }

    fun startSensorManager() {
        sensorManager?.registerListener(sensorListener, sensorAccel, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager?.registerListener(sensorListener, sensorMagnet, SensorManager.SENSOR_DELAY_NORMAL)

        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                Observable.just(getActualDeviceOrientation()/*getDeviceOrientation()*/)
                        .map { getColorByCoord(valuesResult) }
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .map { color ->
                            run {
                                viewState.showInfo(color)
                                viewState.showAngles("///y: " + valuesResult[0]
                                        + "\n///x: " + valuesResult[1]
                                        + "\n///z: " + valuesResult[2]
                                        + "\n///x+y: " + vector(valuesResult[1], valuesResult[0])
                                        + "\n///x+z: " + vector(valuesResult[1], valuesResult[2])
                                        + "\n///y+z: " + vector(valuesResult[0], valuesResult[2])
                                        + "\n///tg: " + Math.tan( vector(valuesResult[1], valuesResult[2])/2 - 3.14 / 2))
                            }
                        }
                        .subscribe()
            }
        }
        timer?.schedule(task, 0, 5)
        val windowManager = App.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        rotation = display.rotation
    }

    fun vector(a: Float, b: Float): Float {
        return Math.sqrt((a * a + b * b).toDouble()).toFloat()
    }

    private fun getColorByCoord(valuesResult: FloatArray): Int {
        if (App.context.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val c = (valuesResult[1].absoluteValue) / 90 * 255
            return Color.rgb(c.toInt(), c.toInt(), c.toInt())
        } else {
            val c = (valuesResult[0].absoluteValue) / 180 * 255
            return Color.rgb(c.toInt(), c.toInt(), c.toInt())
        }
        /*val c = valuesResult[2].absoluteValue / 180 * 255
        return Color.rgb(c.toInt(), c.toInt(), c.toInt())*/
    }

    fun stopSensorManager() {
        sensorManager?.unregisterListener(sensorListener)
        timer?.cancel()
    }

    var r = FloatArray(9)
    var valuesAccel = FloatArray(3)
    var valuesMagnet = FloatArray(3)
    var valuesResult = FloatArray(3)

    fun getDeviceOrientation() {
        SensorManager.getRotationMatrix(r, null, valuesAccel, valuesMagnet)
        SensorManager.getOrientation(r, valuesResult)
//        valuesResult[0] = Math.toDegrees(valuesResult[0].toDouble()).toFloat()
//        valuesResult[1] = Math.toDegrees(valuesResult[1].toDouble()).toFloat()
//        valuesResult[2] = Math.toDegrees(valuesResult[2].toDouble()).toFloat()
        return
    }

    var inR = FloatArray(9)
    var outR = FloatArray(9)

    fun getActualDeviceOrientation() {
        SensorManager.getRotationMatrix(inR, null, valuesAccel, valuesMagnet)
        var x_axis = SensorManager.AXIS_X
        var y_axis = SensorManager.AXIS_Y
        when (rotation) {
            Surface.ROTATION_0 -> {
            }
            Surface.ROTATION_90 -> {
                x_axis = SensorManager.AXIS_Y
                y_axis = SensorManager.AXIS_MINUS_X
            }
            Surface.ROTATION_180 -> y_axis = SensorManager.AXIS_MINUS_Y
            Surface.ROTATION_270 -> {
                x_axis = SensorManager.AXIS_MINUS_Y
                y_axis = SensorManager.AXIS_X
            }
            else -> {
            }
        }
        SensorManager.remapCoordinateSystem(inR, x_axis, y_axis, outR)
        SensorManager.getOrientation(outR, valuesResult)
        valuesResult[0] = Math.toDegrees(valuesResult[0].toDouble()).toFloat()
        valuesResult[1] = Math.toDegrees(valuesResult[1].toDouble()).toFloat()
        valuesResult[2] = Math.toDegrees(valuesResult[2].toDouble()).toFloat()
        return
    }

    val sensorListener: SensorEventListener
        get() = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            }

            override fun onSensorChanged(event: SensorEvent) {
                when (event.sensor.getType()) {
                    Sensor.TYPE_ACCELEROMETER -> for (i in 0..2) {
                        valuesAccel[i] = event.values[i]
                    }
                    Sensor.TYPE_MAGNETIC_FIELD -> for (i in 0..2) {
                        valuesMagnet[i] = event.values[i]
                    }
                }
            }
        }

    val onClickListener: View.OnClickListener
        get() = object : View.OnClickListener {
            override fun onClick(p0: View) { // - ?
                when (p0.id) {
                    R.id.button_to_diag -> {
                        App.getRouter().navigateTo(FragType.DIAGRAM.name)
                    }
                    R.id.button_to_view_pager -> {
                        App.getRouter().navigateTo(FragType.VIEW_PAGER.name)
                    }
                    R.id.button_to_task_list -> {
                        App.getRouter().navigateTo(FragType.TASK_LIST_MAIN.name)
                    }
                    R.id.button_to_demo -> {
                        if (App.isDemoRepository()) {
                            App.sharPref.edit().putBoolean(App.APP_PREFERENCES_IS_DEMO, false).apply()
                        } else {
                            App.sharPref.edit().putBoolean(App.APP_PREFERENCES_IS_DEMO, true).apply()
                        }
                        App.appComponent.dbRep().setIData(App.isDemoRepository())
                    }
                    R.id.button_to_content_provider -> {
                        viewState.startContentProviderActivity()
                    }
                    R.id.button_start_contacts_list -> {
                        viewState.startContactActivity()
                    }
                    R.id.button_to_anim -> {
                        App.getRouter().navigateToWithAnimation((FragType.ANIMATION.name), null,
                                { addSharedElement(p0, p0.transitionName) })
                    }
                    R.id.button_to_webview -> {
                        App.getRouter().navigateTo(FragType.WEB_VIEW.name)
                    }
                    R.id.button_to_picker ->{
                        App.getRouter().navigateTo(FragType.DATE_TIME_PICKER.name)
                    }
                    R.id.button_to_map ->{
                        App.getRouter().navigateTo(FragType.MAP.name)
                    }
                }
            }
        }

}
