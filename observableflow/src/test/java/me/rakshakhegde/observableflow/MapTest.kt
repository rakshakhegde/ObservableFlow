package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import org.junit.Test
import org.mockito.Mockito.*
import kotlin.test.assertEquals

/**
 * Created by rakshakhegde on 25/05/17.
 */
class MapTest {

	@Test
	fun check_if_map_calls_initially_only_once() {
		val src = ObservableField("Hello")
		val dst = spy(ObservableField<String>())

		src.map(dst) { it }

		verify(dst).set(anyString())
	}

	@Test
	fun assert_that_correct_mapping_is_done() {
		val src = ObservableField("Hello")

		val dst = src.map { it.reversed() }

		assertEquals(expected = "olleH", actual = dst.get())
	}
}
