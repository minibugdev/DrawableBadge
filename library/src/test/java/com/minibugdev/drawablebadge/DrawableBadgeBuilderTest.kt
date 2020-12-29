package com.minibugdev.drawablebadge

import android.content.Context
import android.graphics.Bitmap
import android.view.Gravity
import androidx.core.content.ContextCompat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@RunWith(RobolectricTestRunner::class)
class DrawableBadgeBuilderTest {

	private lateinit var drawableBuilder: DrawableBadge.Builder
	private lateinit var context: Context

	@Before
	fun setup() {
		context = RuntimeEnvironment.application
		drawableBuilder = DrawableBadge.Builder(context)
	}

	@Test
	fun testNotPassBitmapOrDrawableShouldThrowIllegalArgumentException() {
		assertFailsWith<IllegalArgumentException> {
			drawableBuilder.build()
		}
	}

	@Test
	fun testConfigOnlyBitmapShouldUsingDefaultConfig() {
		val actual = drawableBuilder
			.bitmap(Bitmap.createBitmap(128, 128, Bitmap.Config.ALPHA_8))
			.build()

		val resources = context.resources
		val expectedBadgeSize = resources.getDimensionPixelOffset(R.dimen.default_badge_size)
		val expectedBorderSize = resources.getDimensionPixelOffset(R.dimen.default_badge_border_size)
		val expectedBadgeColor = ContextCompat.getColor(context, R.color.default_badge_color)
		val expectedBorderColor = ContextCompat.getColor(context, R.color.default_badge_border_color)
		val expectedTextColor = ContextCompat.getColor(context, R.color.default_badge_text_color)

		assertEquals(expectedBadgeSize.toFloat(), actual.badgeSize)
		assertEquals(expectedBorderSize.toFloat(), actual.badgeBorderSize)
		assertEquals(expectedBadgeColor, actual.badgeColor)
		assertEquals(expectedBorderColor, actual.badgeBorderColor)
		assertEquals(expectedTextColor, actual.textColor)
		assertEquals(0f, actual.badgeMargin)
		assertEquals(Gravity.TOP or Gravity.END, actual.badgeGravity)
		assertEquals(true, actual.isShowBorder)
		assertEquals(99, actual.maximumCounter)
	}

	@Test
	fun testCustomConfigShouldUsingCustomConfig() {
		val actual = drawableBuilder
			.bitmap(Bitmap.createBitmap(128, 128, Bitmap.Config.ALPHA_8))
			.badgeSize(10f)
			.badgeBorderSize(20f)
			.badgeMargin(30f)
			.badgeColor(android.R.color.holo_red_dark)
			.badgeBorderColor(android.R.color.holo_red_light)
			.textColor(android.R.color.black)
			.badgeGravity(Gravity.CENTER_VERTICAL or Gravity.END)
			.showBorder(false)
			.maximumCounter(50)
			.showCounter(false)
			.build()

		val expectedBadgeColor = ContextCompat.getColor(context, android.R.color.holo_red_dark)
		val expectedBorderColor = ContextCompat.getColor(context, android.R.color.holo_red_light)
		val expectedTextColor = ContextCompat.getColor(context, android.R.color.black)

		assertEquals(10f, actual.badgeSize)
		assertEquals(20f, actual.badgeBorderSize)
		assertEquals(30f, actual.badgeMargin)
		assertEquals(expectedBadgeColor, actual.badgeColor)
		assertEquals(expectedBorderColor, actual.badgeBorderColor)
		assertEquals(expectedTextColor, actual.textColor)
		assertEquals(Gravity.CENTER_VERTICAL or Gravity.END, actual.badgeGravity)
		assertEquals(false, actual.isShowBorder)
		assertEquals(50, actual.maximumCounter)
		assertEquals(false, actual.isShowCounter)
	}
}