package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by Rakshak.R.Hegde on 19-Aug-17.
 */
class FilterTest {

	@Test
	fun verify_filters_immediately_if_predicate_true() {
		val src = ObservableField("even")

		val dst = src.filter { it.length % 2 == 0 }

		assertEquals(expected = "even", actual = dst.get())

		src.set("odd")
		assertEquals(expected = "even", actual = dst.get())
	}

	@Test
	fun verify_doesnt_filter_immediately_if_predicate_false() {
		val src = ObservableField("odd")

		val dst = src.filter { it.length % 2 == 0 }

		assertEquals(expected = null, actual = dst.get())

		src.set("even")
		assertEquals(expected = "even", actual = dst.get())
	}

	@Test
	fun verify_filter_default_val_assigned_if_predicate_false() {
		val src = ObservableField("odd")

		val dst = src.filter(defaultVal = "default") { it.length % 2 == 0 }

		assertEquals(expected = "default", actual = dst.get())

		src.set("even")
		assertEquals(expected = "even", actual = dst.get())
	}

	@Test
	fun verify_filter_default_val_changed_if_predicate_true() {
		val src = ObservableField("even")

		val dst = src.filter(defaultVal = "default") { it.length % 2 == 0 }

		assertEquals(expected = "even", actual = dst.get())
	}

	@Test
	fun example() {
		val source = ObservableField("source")
		val dest = source.filter { it.startsWith('s') }
				.map { it.capitalize() }

		assertEquals(expected = "Source", actual = dest.get())
	}
}