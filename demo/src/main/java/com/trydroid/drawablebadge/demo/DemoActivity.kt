package com.trydroid.drawablebadge.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.trydroid.drawablebadge.BadgePosition
import com.trydroid.drawablebadge.DrawableBadge
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_demo)
		registerEvents()
	}

	private fun registerEvents() {
		buttonTopLeft.setOnClickListener { drawBadge(99, BadgePosition.TOP_LEFT) }
		buttonTopRight.setOnClickListener { drawBadge(99, BadgePosition.TOP_RIGHT) }
		buttonBottomLeft.setOnClickListener { drawBadge(99, BadgePosition.BOTTOM_LEFT) }
		buttonBottomRight.setOnClickListener { drawBadge(99, BadgePosition.BOTTOM_RIGHT) }
		buttonReset.setOnClickListener { drawBadge(0, BadgePosition.TOP_LEFT) }
	}

	private fun drawBadge(number: Int, position: BadgePosition) {
		DrawableBadge.Builder(applicationContext)
			.drawableResId(R.mipmap.ic_launcher_round)
			.badgeColor(R.color.badgeColor)
			.badgeSize(R.dimen.badge_size)
			.badgePosition(position)
			.textColor(R.color.textColor)
			.build()
			.get(number)
			.let { drawable -> imageViewBadge.setImageDrawable(drawable) }
	}
}
