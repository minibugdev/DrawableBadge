package com.minibugdev.drawablebadge

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.test.filters.LargeTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@LargeTest
class DrawableBadgeBuilderTest {

	private lateinit var drawableBuilder: DrawableBadge.Builder
	private val mockContext: Context = mockk()
	private val mockResource: Resources = mockk()

	@Before
	fun setup() {
		every { mockContext.resources } returns mockResource
		drawableBuilder = DrawableBadge.Builder(mockContext)
	}

	@Test
	fun testNotPassBitmapOrDrawableShouldThrowIllegalArgumentException() {
		assertFailsWith<IllegalArgumentException> {
			drawableBuilder.build()
		}
	}

	@Test
	fun testConfigOnlyBitmapShouldUsingDefaultConfig() {
		every { mockContext.getColor(R.color.default_badge_color) } returns EXPECTED_DEFAULT_BADGE_COLOR_INT
		every { mockContext.getColor(R.color.default_badge_border_color) } returns EXPECTED_DEFAULT_BADGE_BORDER_COLOR_INT
		every { mockContext.getColor(R.color.default_badge_text_color) } returns EXPECTED_DEFAULT_BADGE_TEXT_COLOR_INT
		every { mockResource.getDimensionPixelOffset(R.dimen.default_badge_size) } returns EXPECTED_DEFAULT_BADGE_SIZE.toInt()
		every { mockResource.getDimensionPixelOffset(R.dimen.default_badge_border_size) } returns EXPECTED_DEFAULT_BADGE_BORDER_SIZE.toInt()

		val actual = drawableBuilder
			.bitmap(Bitmap.createBitmap(128, 128, Bitmap.Config.ALPHA_8))
			.build()

		assertEquals(EXPECTED_DEFAULT_BADGE_SIZE, actual.badgeSize)
		assertEquals(EXPECTED_DEFAULT_BADGE_BORDER_SIZE, actual.badgeBorderSize)
		assertEquals(EXPECTED_DEFAULT_BADGE_COLOR_INT, actual.badgeColor)
		assertEquals(EXPECTED_DEFAULT_BADGE_BORDER_COLOR_INT, actual.badgeBorderColor)
		assertEquals(EXPECTED_DEFAULT_BADGE_TEXT_COLOR_INT, actual.textColor)
		assertEquals(EXPECTED_DEFAULT_BADGE_MARGIN, actual.badgeMargin)
		assertEquals(null, actual.badgePosition)
		assertEquals(true, actual.isShowBorder)
		assertEquals(99, actual.maximumCounter)
	}

	@Test
	fun testCustomConfigShouldUsingCustomConfig() {
		every { mockContext.getColor(FAKE_BADGE_COLOR_RES_ID) } returns EXPECTED_CONFIG_BADGE_COLOR_INT
		every { mockContext.getColor(FAKE_BADGE_BORDER_COLOR_RES_ID) } returns EXPECTED_CONFIG_BADGE_BORDER_COLOR_INT
		every { mockContext.getColor(FAKE_TEXT_COLOR_RES_ID) } returns EXPECTED_CONFIG_BADGE_TEXT_COLOR_INT
		every { mockResource.getDimensionPixelOffset(FAKE_BADGE_SIZE_DIMENSION_ID) } returns EXPECTED_CONFIG_BADGE_SIZE.toInt()
		every { mockResource.getDimensionPixelOffset(FAKE_BADGE_BORDER_SIZE_DIMENSION_ID) } returns EXPECTED_CONFIG_BADGE_BORDER_SIZE.toInt()
		every { mockResource.getDimensionPixelOffset(FAKE_BADGE_MARGIN_RES_ID) } returns EXPECTED_CONFIG_BADGE_MARGIN.toInt()

		val actual = drawableBuilder
			.bitmap(Bitmap.createBitmap(128, 128, Bitmap.Config.ALPHA_8))
			.badgeSize(FAKE_BADGE_SIZE_DIMENSION_ID)
			.badgeBorderSize(FAKE_BADGE_BORDER_SIZE_DIMENSION_ID)
			.badgeColor(FAKE_BADGE_COLOR_RES_ID)
			.badgeBorderColor(FAKE_BADGE_BORDER_COLOR_RES_ID)
			.textColor(FAKE_TEXT_COLOR_RES_ID)
			.badgePosition(BadgePosition.BOTTOM_LEFT)
			.badgeMargin(FAKE_BADGE_MARGIN_RES_ID)
			.showBorder(false)
			.maximumCounter(50)
			.build()

		assertEquals(EXPECTED_CONFIG_BADGE_SIZE, actual.badgeSize)
		assertEquals(EXPECTED_CONFIG_BADGE_BORDER_SIZE, actual.badgeBorderSize)
		assertEquals(EXPECTED_CONFIG_BADGE_COLOR_INT, actual.badgeColor)
		assertEquals(EXPECTED_CONFIG_BADGE_BORDER_COLOR_INT, actual.badgeBorderColor)
		assertEquals(EXPECTED_CONFIG_BADGE_TEXT_COLOR_INT, actual.textColor)
		assertEquals(EXPECTED_CONFIG_BADGE_MARGIN, actual.badgeMargin)
		assertEquals(BadgePosition.BOTTOM_LEFT, actual.badgePosition)
		assertEquals(false, actual.isShowBorder)
		assertEquals(50, actual.maximumCounter)
	}

	@Test
	fun testCustomConfigSizeInPx() {
		every { mockContext.getColor(R.color.default_badge_color) } returns EXPECTED_DEFAULT_BADGE_COLOR_INT
		every { mockContext.getColor(R.color.default_badge_border_color) } returns EXPECTED_DEFAULT_BADGE_BORDER_COLOR_INT
		every { mockContext.getColor(R.color.default_badge_text_color) } returns EXPECTED_DEFAULT_BADGE_TEXT_COLOR_INT

		val actual = drawableBuilder
			.bitmap(Bitmap.createBitmap(128, 128, Bitmap.Config.ALPHA_8))
			.badgeSize(10f)
			.badgeBorderSize(20f)
			.badgeMargin(30f)
			.build()

		assertEquals(10f, actual.badgeSize)
		assertEquals(20f, actual.badgeBorderSize)
		assertEquals(30f, actual.badgeMargin)
	}

	companion object {
		private const val EXPECTED_DEFAULT_BADGE_SIZE = 100f
		private const val EXPECTED_DEFAULT_BADGE_BORDER_SIZE = 200f
		private const val EXPECTED_DEFAULT_BADGE_COLOR_INT = 1
		private const val EXPECTED_DEFAULT_BADGE_BORDER_COLOR_INT = 2
		private const val EXPECTED_DEFAULT_BADGE_TEXT_COLOR_INT = 3
		private const val EXPECTED_DEFAULT_BADGE_MARGIN = 0.0f

		private const val EXPECTED_CONFIG_BADGE_SIZE = 300f
		private const val EXPECTED_CONFIG_BADGE_BORDER_SIZE = 400f
		private const val EXPECTED_CONFIG_BADGE_COLOR_INT = 4
		private const val EXPECTED_CONFIG_BADGE_BORDER_COLOR_INT = 5
		private const val EXPECTED_CONFIG_BADGE_TEXT_COLOR_INT = 6
		private const val EXPECTED_CONFIG_BADGE_MARGIN = 16.0f

		private const val FAKE_BADGE_SIZE_DIMENSION_ID = 100
		private const val FAKE_BADGE_BORDER_SIZE_DIMENSION_ID = 101
		private const val FAKE_BADGE_COLOR_RES_ID = 102
		private const val FAKE_BADGE_BORDER_COLOR_RES_ID = 103
		private const val FAKE_TEXT_COLOR_RES_ID = 104
		private const val FAKE_BADGE_MARGIN_RES_ID = 105
	}
}