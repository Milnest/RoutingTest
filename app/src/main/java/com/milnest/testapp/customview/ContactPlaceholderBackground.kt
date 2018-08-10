package com.milnest.testapp.customview

import android.graphics.*

class ContactPlaceholderBackground(private val margin: Int) :
        com.squareup.picasso.Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val size: Int = if (source.width > source.height)
            source.height
        else
            source.width
        val radius = size/2.toFloat()
        val paint = Paint()
        paint.setAntiAlias(true)
        paint.setShader(BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))

        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (source.width - margin).toFloat(), (source.height - margin).toFloat()), radius, radius, paint)
        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key(): String {
        return "photo"
    }
}