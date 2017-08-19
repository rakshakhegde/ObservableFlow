package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by rakshakhegde on 25/05/17.
 */
class MapTest {

	@Test
	fun assert_that_correct_mapping_is_done() {
		val src = ObservableField("Hello")

		val dst = src.map { it.reversed() }

		assertEquals(expected = "olleH", actual = dst.get())

		src.set("World")
		assertEquals(expected = "dlroW", actual = dst.get())
	}
}
