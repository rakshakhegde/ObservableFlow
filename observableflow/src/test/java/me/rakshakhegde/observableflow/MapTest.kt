package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
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

	@Test
	fun verify_if_map_operator_removes_callback_from_the_source_observable() {
		val srcObservable = spy(ObservableField(1))
		val dstObservable = srcObservable.map { }

		verify(srcObservable, times(0)).addOnPropertyChangedCallback(any())

		val callback = dstObservable.onPropertyChanged { }

		verify(srcObservable).addOnPropertyChangedCallback(any())

		dstObservable.removeOnPropertyChangedCallback(callback)

		verify(srcObservable).removeOnPropertyChangedCallback(any())
	}
}
