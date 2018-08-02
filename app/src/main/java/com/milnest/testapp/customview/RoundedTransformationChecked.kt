package com.milnest.testapp.customview

import android.graphics.*
import com.milnest.testapp.App
import com.milnest.testapp.R


class RoundedTransformationChecked// radius is corner radii in dp
// margin is the board in dp
(private val radius: Int, private val margin: Int  // dp
) : com.squareup.picasso.Transformation {

    override fun transform(source: Bitmap): Bitmap {
        val paint = Paint()
        paint.setAntiAlias(true)
        paint.setShader(BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (source.width - margin).toFloat(), (source.height - margin).toFloat()), radius.toFloat(), radius.toFloat(), paint)
        val textPaint = Paint()
        val size: Int
        if (source.width > source.height)
            size = source.height
        else
            size = source.width
        textPaint.textSize = size.toFloat()
        textPaint.color = App.context.resources.getColor(R.color.lum_red)
        val textBounds = Rect()
        textPaint.getTextBounds("V", 0, "V".length, textBounds)
        canvas.drawText("V", source.width/2 - textPaint.measureText("V")/2, source.height/2.toFloat() + textBounds.height()/2, textPaint)
        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key(): String {
        return "rounded_checked"
    }
}