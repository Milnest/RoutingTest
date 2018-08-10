package com.milnest.testapp.customview

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import com.milnest.testapp.App
import com.milnest.testapp.R

class ContactPhotoPlaceholder(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    val atrArray: TypedArray = context!!.theme.obtainStyledAttributes(attrs, R.styleable.ContactPhotoPlaceholder, 0, 0)
    var textColor = atrArray.getColor(R.styleable.ContactPhotoPlaceholder_cph_text_color, resources.getColor(R.color.colorBlack))
    var textSize = atrArray.getDimensionPixelSize(R.styleable.ContactPhotoPlaceholder_cph_text_size, 20)
    var text = atrArray.getString(R.styleable.ContactPhotoPlaceholder_cph_text)
    var radius = atrArray.getDimension(R.styleable.ContactPhotoPlaceholder_cph_radius, 0f)
    var color = atrArray.getColor(R.styleable.ContactPhotoPlaceholder_cph_background_color, resources.getColor(R.color.colorBlueGray_500))
    var myHeight = height
    var myWidth = width
    var centerX = width / 2
    var centerY = height / 2
    var size: Int = myHeight
    val textBounds = Rect()
    val textBoundsFirst = Rect()
    val textBoundsSecond = Rect()
    val paint = Paint()
    val textPaint: Paint
        get() {
            val textPaint = Paint()
            textPaint.isAntiAlias = true
            textPaint.color = textColor
            textPaint.textSize = textSize.toFloat()/*35.0f*/
            textPaint.strokeWidth = 2.0f
            textPaint.style = Paint.Style.STROKE
            return textPaint
        }
    val textPaintSecond: Paint
        get() {
            val textPaint = Paint()
            textPaint.isAntiAlias = true
            textPaint.color = App.context.resources.getColor(R.color.red_a400)
            textPaint.textSize = textSize.toFloat()/*35.0f*/
            textPaint.strokeWidth = 2.0f
            textPaint.style = Paint.Style.FILL_AND_STROKE
            return textPaint
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        myHeight = View.MeasureSpec.getSize(heightMeasureSpec)
        myWidth = View.MeasureSpec.getSize(widthMeasureSpec)
        centerX = myWidth / 2
        centerY = myHeight / 2
        if (myWidth < myHeight) {
            size = myWidth
            if (radius > myWidth) radius = myWidth.toFloat()
        } else {
            size = myHeight
            if (radius > myHeight) radius = myHeight.toFloat()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas ?: return
        paint.style = Paint.Style.FILL_AND_STROKE
        paint.color = color
        canvas.drawCircle(centerX.toFloat(), centerY.toFloat(), radius, paint)
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        textPaint.getTextBounds(text.take(1), 0, text.take(1).length, textBoundsFirst)
        if (textBounds.height() <= size && textBounds.width() <= size) {
            val x_interval = centerX.toFloat() - textPaint.measureText(text) / 2
            canvas.drawText(text.take(1), x_interval, centerY.toFloat() + textBounds.height() / 2, textPaint)
            if (text.length > 1) {
                textPaintSecond.getTextBounds(text.get(1).toString(), 0, text.get(1).toString().length, textBoundsSecond)
                canvas.drawText(text.get(1).toString(), x_interval + textPaint.measureText(text.take(1)), centerY.toFloat() + textBounds.height() / 2, textPaintSecond)
            }
        } else
            textPaint.textSize = 10f
    }
}