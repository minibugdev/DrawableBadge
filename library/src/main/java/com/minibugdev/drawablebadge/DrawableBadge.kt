package com.minibugdev.drawablebadge

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.text.TextPaint

class DrawableBadge private constructor(private val context: Context,
                                        @ColorInt private val textColor: Int,
                                        @ColorInt private val badgeColor: Int,
                                        @ColorInt private val badgeBorderColor: Int,
                                        private val badgeBorderSize: Float,
                                        private val badgeSize: Float,
                                        private val badgePosition: BadgePosition,
                                        private val bitmap: Bitmap,
                                        private val isShowBorder: Boolean) {

	class Builder(private val context: Context) {

		@ColorInt private var textColor: Int? = null
		@ColorInt private var badgeColor: Int? = null
		@ColorInt private var badgeBorderColor: Int? = null
		private var badgeBorderSize: Float? = null
		private var badgeSize: Float? = null
		private var badgePosition: BadgePosition? = null
		private var bitmap: Bitmap? = null
		private var isShowBorder: Boolean? = null

		fun drawableResId(@DrawableRes drawableRes: Int) = apply { this.bitmap = BitmapFactory.decodeResource(context.resources, drawableRes) }

		fun drawable(drawable: Drawable) = apply {
			if (drawable is BitmapDrawable) {
				this.bitmap = drawable.bitmap
			}
		}

		fun bitmap(bitmap: Bitmap) = apply { this.bitmap = bitmap }

		fun textColor(@ColorRes textColorRes: Int) = apply { this.textColor = ContextCompat.getColor(context, textColorRes) }

		fun badgeColor(@ColorRes badgeColorRes: Int) = apply { this.badgeColor = ContextCompat.getColor(context, badgeColorRes) }

		fun badgeBorderColor(@ColorRes badgeBorderColorRes: Int) = apply { this.badgeBorderColor = ContextCompat.getColor(context, badgeBorderColorRes) }

		fun badgeBorderSize(@DimenRes badgeBorderSize: Int) = apply { this.badgeBorderSize = context.resources.getDimensionPixelOffset(badgeBorderSize).toFloat() }

		fun badgeSize(@DimenRes badgeSize: Int) = apply { this.badgeSize = context.resources.getDimensionPixelOffset(badgeSize).toFloat() }

		fun badgePosition(badgePosition: BadgePosition) = apply { this.badgePosition = badgePosition }

		fun showBorder(isShowBorder: Boolean) = apply { this.isShowBorder = isShowBorder }

		fun build(): DrawableBadge {
			if (bitmap == null) throw IllegalArgumentException("Badge drawable/bitmap can not be null.")
			if (badgeSize == null) badgeSize(R.dimen.default_badge_size)
			if (textColor == null) textColor(R.color.default_badge_text_color)
			if (badgeColor == null) badgeColor(R.color.default_badge_color)
			if (badgeBorderColor == null) badgeBorderColor(R.color.default_badge_border_color)
			if (badgeBorderSize == null) badgeBorderSize(R.dimen.default_badge_border_size)
			if (badgePosition == null) badgePosition(BadgePosition.TOP_RIGHT)
			if (isShowBorder == null) showBorder(true)

			return DrawableBadge(
				context = context,
				bitmap = bitmap!!,
				textColor = textColor!!,
				badgeColor = badgeColor!!,
				badgeBorderColor = badgeBorderColor!!,
				badgeBorderSize = badgeBorderSize!!,
				badgeSize = badgeSize!!,
				badgePosition = badgePosition!!,
				isShowBorder = isShowBorder!!)
		}
	}

	fun get(number: Int): Drawable {
		val resources = context.resources
		if (number == 0) return BitmapDrawable(resources, bitmap)

		val sourceBitmap = bitmap
		val width = sourceBitmap.width
		val height = sourceBitmap.height
		val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

		val canvas = Canvas(output)
		val rect = Rect(0, 0, width, height)
		val paint = Paint().apply {
			isAntiAlias = true
			isFilterBitmap = true
			isDither = true
			textAlign = Paint.Align.CENTER
			color = badgeColor
		}
		canvas.drawBitmap(sourceBitmap, rect, rect, paint)

		val additionalBorderSpace = if (isShowBorder) badgeBorderSize else 0f
		val widthFloat = width.toFloat()
		val heightFloat = height.toFloat()
		val badgeRect = when (badgePosition) {
			BadgePosition.TOP_LEFT     -> RectF(additionalBorderSpace, additionalBorderSpace, badgeSize, badgeSize)
			BadgePosition.TOP_RIGHT    -> RectF(widthFloat - badgeSize, additionalBorderSpace, widthFloat - additionalBorderSpace, badgeSize)
			BadgePosition.BOTTOM_LEFT  -> RectF(additionalBorderSpace, heightFloat - badgeSize, badgeSize, heightFloat - additionalBorderSpace)
			BadgePosition.BOTTOM_RIGHT -> RectF(widthFloat - badgeSize, heightFloat - badgeSize, widthFloat - additionalBorderSpace, heightFloat - additionalBorderSpace)
		}
		canvas.drawOval(badgeRect, paint)

		if (isShowBorder) {
			val paintBorder = Paint().apply {
				isAntiAlias = true
				isFilterBitmap = true
				isDither = true
				textAlign = Paint.Align.CENTER
				style = Paint.Style.STROKE
				color = badgeBorderColor
				strokeWidth = badgeBorderSize
			}
			canvas.drawOval(badgeRect, paintBorder)
		}

		val textSize = badgeRect.height() * 0.55f
		val textPaint = TextPaint().apply {
			this.isAntiAlias = true
			this.color = textColor
			this.textSize = textSize
		}

		val text = number.toString()
		val x = badgeRect.centerX() - (textPaint.measureText(text) / 2f)
		val y = badgeRect.centerY() - (textPaint.ascent() + textPaint.descent()) * 0.5f
		canvas.drawText(text, x, y, textPaint)

		return BitmapDrawable(resources, output)
	}
}
