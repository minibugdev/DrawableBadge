package com.minibugdev.drawablebadge

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import androidx.test.filters.LargeTest
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

@LargeTest
class DrawableBadgeBuilderTest {

	private lateinit var drawableBuilder: DrawableBadge.Builder
	private val mockContext: Context = mock()
	private val mockResource: Resources = mock()

	@Before
	fun setup() {
		whenever(mockContext.resources).thenReturn(mockResource)
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
		whenever(mockResource.getColor(R.color.default_badge_color)).thenReturn(EXPECTED_DEFAULT_BADGE_COLOR_INT)
		whenever(mockResource.getColor(R.color.default_badge_border_color)).thenReturn(EXPECTED_DEFAULT_BADGE_BORDER_COLOR_INT)
		whenever(mockResource.getColor(R.color.default_badge_text_color)).thenReturn(EXPECTED_DEFAULT_BADGE_TEXT_COLOR_INT)
		whenever(mockResource.getDimensionPixelOffset(R.dimen.default_badge_size)).thenReturn(EXPECTED_DEFAULT_BADGE_SIZE.toInt())
		whenever(mockResource.getDimensionPixelOffset(R.dimen.default_badge_border_size)).thenReturn(EXPECTED_DEFAULT_BADGE_BORDER_SIZE.toInt())

		val actual = drawableBuilder
			.bitmap(Bitmap.createBitmap(128, 128, Bitmap.Config.ALPHA_8))
			.build()

		assertEquals(EXPECTED_DEFAULT_BADGE_SIZE, actual.badgeSize)
		assertEquals(EXPECTED_DEFAULT_BADGE_BORDER_SIZE, actual.badgeBorderSize)
		assertEquals(EXPECTED_DEFAULT_BADGE_COLOR_INT, actual.badgeColor)
		assertEquals(EXPECTED_DEFAULT_BADGE_BORDER_COLOR_INT, actual.badgeBorderColor)
		assertEquals(EXPECTED_DEFAULT_BADGE_TEXT_COLOR_INT, actual.textColor)
		assertEquals(BadgePosition.TOP_RIGHT, actual.badgePosition)
		assertEquals(true, actual.isShowBorder)
		assertEquals(99, actual.maximumCounter)
	}

	@Test
	fun testCustomConfigShouldUsingCustomConfig() {
		whenever(mockResource.getColor(FAKE_BADGE_COLOR_RES_ID)).thenReturn(EXPECTED_CONFIG_BADGE_COLOR_INT)
		whenever(mockResource.getColor(FAKE_BADGE_BORDER_COLOR_RES_ID)).thenReturn(EXPECTED_CONFIG_BADGE_BORDER_COLOR_INT)
		whenever(mockResource.getColor(FAKE_TEXT_COLOR_RES_ID)).thenReturn(EXPECTED_CONFIG_BADGE_TEXT_COLOR_INT)
		whenever(mockResource.getDimensionPixelOffset(FAKE_BADGE_SIZE_DIMENSION_ID)).thenReturn(EXPECTED_CONFIG_BADGE_SIZE.toInt())
		whenever(mockResource.getDimensionPixelOffset(FAKE_BADGE_BORDER_SIZE_DIMENSION_ID)).thenReturn(EXPECTED_CONFIG_BADGE_BORDER_SIZE.toInt())

		val actual = drawableBuilder
			.bitmap(Bitmap.createBitmap(128, 128, Bitmap.Config.ALPHA_8))
			.badgeSize(FAKE_BADGE_SIZE_DIMENSION_ID)
			.badgeBorderSize(FAKE_BADGE_BORDER_SIZE_DIMENSION_ID)
			.badgeColor(FAKE_BADGE_COLOR_RES_ID)
			.badgeBorderColor(FAKE_BADGE_BORDER_COLOR_RES_ID)
			.textColor(FAKE_TEXT_COLOR_RES_ID)
			.badgePosition(BadgePosition.BOTTOM_LEFT)
			.showBorder(false)
			.maximumCounter(50)
			.build()

		assertEquals(EXPECTED_CONFIG_BADGE_SIZE, actual.badgeSize)
		assertEquals(EXPECTED_CONFIG_BADGE_BORDER_SIZE, actual.badgeBorderSize)
		assertEquals(EXPECTED_CONFIG_BADGE_COLOR_INT, actual.badgeColor)
		assertEquals(EXPECTED_CONFIG_BADGE_BORDER_COLOR_INT, actual.badgeBorderColor)
		assertEquals(EXPECTED_CONFIG_BADGE_TEXT_COLOR_INT, actual.textColor)
		assertEquals(BadgePosition.BOTTOM_LEFT, actual.badgePosition)
		assertEquals(false, actual.isShowBorder)
		assertEquals(50, actual.maximumCounter)
	}

	companion object {
		private const val EXPECTED_DEFAULT_BADGE_SIZE = 100f
		private const val EXPECTED_DEFAULT_BADGE_BORDER_SIZE = 200f
		private const val EXPECTED_DEFAULT_BADGE_COLOR_INT = 1
		private const val EXPECTED_DEFAULT_BADGE_BORDER_COLOR_INT = 2
		private const val EXPECTED_DEFAULT_BADGE_TEXT_COLOR_INT = 3

		private const val EXPECTED_CONFIG_BADGE_SIZE = 300f
		private const val EXPECTED_CONFIG_BADGE_BORDER_SIZE = 400f
		private const val EXPECTED_CONFIG_BADGE_COLOR_INT = 4
		private const val EXPECTED_CONFIG_BADGE_BORDER_COLOR_INT = 5
		private const val EXPECTED_CONFIG_BADGE_TEXT_COLOR_INT = 6

		private const val FAKE_BADGE_SIZE_DIMENSION_ID = 100
		private const val FAKE_BADGE_BORDER_SIZE_DIMENSION_ID = 101
		private const val FAKE_BADGE_COLOR_RES_ID = 102
		private const val FAKE_BADGE_BORDER_COLOR_RES_ID = 103
		private const val FAKE_TEXT_COLOR_RES_ID = 104
	}
}