package com.minibugdev.drawablebadge.demo

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.minibugdev.drawablebadge.DrawableBadge
import kotlinx.android.synthetic.main.activity_demo.*

class DemoActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_demo)
		registerEvents()
	}

	private fun registerEvents() {
		buttonTopLeft.setOnClickListener { drawBadge(1, Gravity.TOP or Gravity.START) }
		buttonTopRight.setOnClickListener { drawBadge(50, Gravity.TOP or Gravity.END) }
		buttonBottomLeft.setOnClickListener { drawBadge(99, Gravity.BOTTOM or Gravity.START) }
		buttonBottomRight.setOnClickListener { drawBadge(100, Gravity.BOTTOM or Gravity.END) }
		buttonCenterHorizontal.setOnClickListener { drawBadge(100, Gravity.CENTER_HORIZONTAL or Gravity.TOP) }
		buttonCenterVertical.setOnClickListener { drawBadge(100, Gravity.CENTER_VERTICAL or Gravity.START) }
		buttonReset.setOnClickListener { drawBadge(0, Gravity.TOP or Gravity.END) }
	}

	private fun drawBadge(number: Int, gravity: Int) {
		val drawableResId = when (radioGroup.checkedRadioButtonId) {
			R.id.radioButtonSelectorDrawable -> R.drawable.selector_badge
			R.id.radioButtonVectorDrawable   -> R.drawable.ic_notifications
			else                             -> R.drawable.ic_launcher
		}

		DrawableBadge.Builder(applicationContext)
			.drawableResId(drawableResId)
			.badgeColor(R.color.badgeColor)
			.badgeSize(R.dimen.badge_size)
			.badgeGravity(gravity)
			.textColor(R.color.textColor)
			.showBorder(true)
			.badgeBorderColor(R.color.borderColor)
			.badgeBorderSize(R.dimen.badge_border_size)
			.maximumCounter(99)
			.badgeMargin(10f)
			.showCounter(true)
			.build()
			.get(number)
			.let { drawable -> imageViewBadge.setImageDrawable(drawable) }
	}
}
