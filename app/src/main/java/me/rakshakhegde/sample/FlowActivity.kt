package me.rakshakhegde.sample

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.rakshakhegde.sample.databinding.ActivityFlowBinding

class FlowActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		DataBindingUtil.setContentView<ActivityFlowBinding>(this, R.layout.activity_flow).apply {
			presenter = FlowPresenter()
		}
	}
}
