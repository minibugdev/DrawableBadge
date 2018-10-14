package com.minibugdev.drawablebadge.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.minibugdev.drawablebadge.BadgePosition
import com.minibugdev.drawablebadge.DrawableBadge
import com.minibugdev.drawablebadge.demo.R.id.*
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_demo)
		registerEvents()
	}

	private fun registerEvents() {
		buttonTopLeft.setOnClickListener { drawBadge(1, BadgePosition.TOP_LEFT) }
		buttonTopRight.setOnClickListener { drawBadge(50, BadgePosition.TOP_RIGHT) }
		buttonBottomLeft.setOnClickListener { drawBadge(99, BadgePosition.BOTTOM_LEFT) }
		buttonBottomRight.setOnClickListener { drawBadge(100, BadgePosition.BOTTOM_RIGHT) }
		buttonReset.setOnClickListener { drawBadge(0, BadgePosition.TOP_LEFT) }
	}

	private fun drawBadge(number: Int, position: BadgePosition) {
		val drawableResId = when (radioGroup.checkedRadioButtonId) {
			R.id.radioButtonSelectorDrawable -> R.drawable.selector_badge
			R.id.radioButtonVectorDrawable   -> R.drawable.ic_notifications
			else                             -> R.drawable.ic_launcher
		}

		DrawableBadge.Builder(applicationContext)
			.drawableResId(drawableResId)
			.badgeColor(R.color.badgeColor)
			.badgeSize(R.dimen.badge_size)
			.badgePosition(position)
			.textColor(R.color.textColor)
			.showBorder(true)
			.badgeBorderColor(R.color.borderColor)
			.badgeBorderSize(R.dimen.badge_border_size)
			.maximumCounter(99)
			.build()
			.get(number)
			.let { drawable -> imageViewBadge.setImageDrawable(drawable) }
	}
}
