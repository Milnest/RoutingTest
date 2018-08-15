package com.milnest.testapp.customview

import android.graphics.*
import com.milnest.testapp.App
import com.milnest.testapp.R

class ContactPlaceholderBackground(private val margin: Int, val color: Int) :
        com.squareup.picasso.Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val size: Int = if (source.width > source.height)
            source.height
        else
            source.width
        val radius = size/2.toFloat()
        val paint = Paint()
        paint.setAntiAlias(true)
        //paint.setShader(BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        paint.color = color//App.context.resources.getColor(R.color.red_a400)


        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        canvas.drawRect(RectF(margin.toFloat(), margin.toFloat(), (source.width - margin).toFloat(), (source.height - margin).toFloat()),  paint)
        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key(): String {
        return "photo"
    }
}