package com.milnest.testapp.customview

import android.graphics.*
import com.milnest.testapp.App
import com.milnest.testapp.R

class ContactPlaceholderTransformation(private val margin: Int, private var id: Long, private var text: String, private var color: Int) :
        com.squareup.picasso.Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val size: Int = if (source.width > source.height)
            source.height
        else
            source.width
        val radius = size/2.toFloat()
        val paint = Paint()
        paint.isAntiAlias = true
        //paint.shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        paint.color = App.context.resources.getColor(R.color.red_a400)

        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)

        val canvas = Canvas(output)

        canvas.drawCircle(margin.toFloat() + source.width/2, margin.toFloat() + source.height/2, radius, paint)
        //canvas.drawRoundRect(RectF(margin.toFloat(), margin.toFloat(), (source.width - margin).toFloat(), (source.height - margin).toFloat()), radius, radius, paint)
        val textPaint = Paint()
        val textBounds = Rect()
        textPaint.textSize = radius/*size/2.toFloat()*/
        textPaint.color = color
        textPaint.getTextBounds("а", 0, /*text*/"Д".length, textBounds)

//        canvas.drawRect(source.width/2 - textPaint.measureText(text)/2, source.height/2.toFloat() + textBounds.height()/2, Paint().apply { color = Color.GRAY })

        canvas.drawText(text, source.width/2 - textPaint.measureText(text)/2, source.height/2.toFloat() + textBounds.height()/2, textPaint)



        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key(): String {
        val orientationKey = App.context.resources.configuration.orientation
        val classKey = this.toString()
        return "placeholder_trans $classKey"
    }
}