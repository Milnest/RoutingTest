package com.milnest.testapp.customview

import android.graphics.*

class ContactPhotoBackgroundTransformation :
        com.squareup.picasso.Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val paint = Paint()
        paint.setAntiAlias(true)
        paint.setShader(BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP))
        //paint.maskFilter = BlurMaskFilter(400f, BlurMaskFilter.Blur.OUTER)
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        canvas.drawRect(RectF(0.toFloat(), 0.toFloat(), (source.width).toFloat(), (source.height).toFloat()), paint)

        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key(): String {
        return "photo_back"
    }
}