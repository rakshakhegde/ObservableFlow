package me.rakshakhegde.sample

import io.github.rakshakhegde.sugarprefs.obsrvprefs.ObsrvStringNotNullPref
import me.rakshakhegde.observableflow.filter
import me.rakshakhegde.observableflow.map
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by rakshakhegde on 30/03/17.
 */
class FlowPresenter {

	val text = ObsrvStringNotNullPref(key = "MY_TEXT", defaultVal = "HELLO WORLD")

	val length = text.map { it.length }

	val evenLengthText = text.filter { it.length % 2 == 0 }

	val dateFmt = SimpleDateFormat("hh:mm:ss a")

	val changedAt = text.map { dateFmt.format(Date()) }

}
