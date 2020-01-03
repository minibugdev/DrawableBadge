package com.minibugdev.drawablebadge

import android.content.Context
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat

class DrawableBadge private constructor(val context: Context,
                                        @ColorInt val textColor: Int,
                                        @ColorInt val badgeColor: Int,
                                        @ColorInt val badgeBorderColor: Int,
                                        val badgeBorderSize: Float,
                                        val badgeSize: Float,
                                        val badgePosition: BadgePosition?,
                                        val badgeGravity: Int,
                                        val badgeMargin: Float,
                                        val bitmap: Bitmap,
                                        val isShowBorder: Boolean,
                                        val maximumCounter: Int) {

	class Builder(private val context: Context) {

		@ColorInt private var textColor: Int? = null
		@ColorInt private var badgeColor: Int? = null
		@ColorInt private var badgeBorderColor: Int? = null
		private var badgeBorderSize: Float? = null
		private var badgeSize: Float? = null
		private var badgeGravity: Int? = null
		private var badgeMargin: Float? = null
		private var bitmap: Bitmap? = null
		private var isShowBorder: Boolean? = null
		private var maximumCounter: Int? = null

		@Deprecated("Move to Gravity")
		private var badgePosition: BadgePosition? = null

		private fun createBitmapFromDrawable(drawable: Drawable): Bitmap {
			val bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
			val canvas = Canvas(bitmap)
			drawable.setBounds(0, 0, canvas.width, canvas.height)
			drawable.draw(canvas)
			return bitmap
		}

		fun drawableResId(@DrawableRes drawableRes: Int) = apply {
			val res = context.resources
			bitmap = BitmapFactory.decodeResource(res, drawableRes)

			if (bitmap == null) {
				ResourcesCompat.getDrawable(res, drawableRes, null)
					?.current
					?.let {
						drawable(it)
					}
			}
		}

		fun drawable(drawable: Drawable): Builder = apply {
			val drawableCompat = DrawableCompat.wrap(drawable)
			bitmap = when (drawableCompat) {
				is BitmapDrawable -> drawableCompat.bitmap
				else              -> createBitmapFromDrawable(drawableCompat)
			}
		}

		fun bitmap(bitmap: Bitmap) = apply { this.bitmap = bitmap }

		fun textColor(@ColorRes textColorRes: Int) = apply { this.textColor = ContextCompat.getColor(context, textColorRes) }

		fun badgeColor(@ColorRes badgeColorRes: Int) = apply { this.badgeColor = ContextCompat.getColor(context, badgeColorRes) }

		fun badgeBorderColor(@ColorRes badgeBorderColorRes: Int) = apply { this.badgeBorderColor = ContextCompat.getColor(context, badgeBorderColorRes) }

		fun badgeBorderSize(@DimenRes badgeBorderSize: Int) = apply {
			this.badgeBorderSize = context.resources.getDimensionPixelOffset(badgeBorderSize)
				.toFloat()
		}

		fun badgeSize(@DimenRes badgeSize: Int) = apply {
			this.badgeSize = context.resources.getDimensionPixelOffset(badgeSize)
				.toFloat()
		}

		@Deprecated("Move to Gravity")
		fun badgePosition(badgePosition: BadgePosition) = apply { this.badgePosition = badgePosition }

		fun badgeGravity(badgeGravity: Int) = apply { this.badgeGravity = badgeGravity }

		fun badgeMargin(@DimenRes badgeMargin: Int) = apply {
			this.badgeMargin = context.resources.getDimensionPixelOffset(badgeMargin)
				.toFloat()
		}

		fun showBorder(isShowBorder: Boolean) = apply { this.isShowBorder = isShowBorder }

		fun maximumCounter(maximumCounter: Int) = apply { this.maximumCounter = maximumCounter }

		fun build(): DrawableBadge {
			if (bitmap == null) throw IllegalArgumentException("Badge drawable/bitmap can not be null.")
			if (badgeSize == null) badgeSize(R.dimen.default_badge_size)
			if (textColor == null) textColor(R.color.default_badge_text_color)
			if (badgeColor == null) badgeColor(R.color.default_badge_color)
			if (badgeBorderColor == null) badgeBorderColor(R.color.default_badge_border_color)
			if (badgeBorderSize == null) badgeBorderSize(R.dimen.default_badge_border_size)
			if (badgeGravity == null) badgeGravity(Gravity.TOP or Gravity.END)
			if (isShowBorder == null) showBorder(true)
			if (maximumCounter == null) maximumCounter(MAXIMUM_COUNT)

			return DrawableBadge(
					context = context,
					bitmap = bitmap!!,
					textColor = textColor!!,
					badgeColor = badgeColor!!,
					badgeBorderColor = badgeBorderColor!!,
					badgeBorderSize = badgeBorderSize!!,
					badgeSize = badgeSize!!,
					badgePosition = badgePosition,
					badgeGravity = badgeGravity!!,
					badgeMargin = badgeMargin ?: 0.0f,
					isShowBorder = isShowBorder!!,
					maximumCounter = maximumCounter!!)
		}
	}

	fun get(counter: Int): Drawable {
		val resources = context.resources
		if (counter == 0) return BitmapDrawable(resources, bitmap)

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

		val widthFloat = width.toFloat()
		val heightFloat = height.toFloat()
		val badgeRect = getBadgeRect(rect, widthFloat, heightFloat)
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

		val textSize: Float
		val text: String
		val max = if (maximumCounter > MAXIMUM_COUNT) MAXIMUM_COUNT else maximumCounter
		if (counter > max) {
			textSize = badgeRect.height() * 0.45f
			text = "$max+"
		}
		else {
			textSize = badgeRect.height() * 0.55f
			text = counter.toString()
		}

		val textPaint = TextPaint().apply {
			this.isAntiAlias = true
			this.color = textColor
			this.textSize = textSize
		}

		val x = badgeRect.centerX() - (textPaint.measureText(text) / 2f)
		val y = badgeRect.centerY() - (textPaint.ascent() + textPaint.descent()) * 0.5f
		canvas.drawText(text, x, y, textPaint)

		return BitmapDrawable(resources, output)
	}

	private fun getBadgeRect(bound: Rect, width: Float, height: Float): RectF {
		val borderSize = if (isShowBorder) badgeBorderSize else 0f
		val adjustSpace = borderSize + badgeMargin

		return if (badgePosition != null) {
			when (badgePosition) {
				BadgePosition.TOP_LEFT     -> RectF(adjustSpace, adjustSpace, badgeSize, badgeSize)
				BadgePosition.TOP_RIGHT    -> RectF(width - badgeSize, adjustSpace, width - adjustSpace, badgeSize)
				BadgePosition.BOTTOM_LEFT  -> RectF(adjustSpace, height - badgeSize, badgeSize, height - adjustSpace)
				BadgePosition.BOTTOM_RIGHT -> RectF(width - badgeSize, height - badgeSize, width - adjustSpace, height - adjustSpace)
			}
		}
		else {
			val dest = Rect()
			val size = badgeSize.toInt()
			Gravity.apply(badgeGravity, size, size, bound, adjustSpace.toInt(), adjustSpace.toInt(), dest)
			RectF(dest)
		}
	}

	companion object {
		private const val MAXIMUM_COUNT = 99
	}
}
