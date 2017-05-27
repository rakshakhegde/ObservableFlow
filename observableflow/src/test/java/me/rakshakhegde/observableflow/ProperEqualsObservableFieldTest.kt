package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by rakshakhegde on 25/05/17.
 */
class ProperEqualsObservableFieldTest {

	@Test
	fun called_twice_for_different_strings() {
		val src = spy(ObservableField<String>())

		src.set("Yolo")
		verify(src).notifyChange()

		src.set("Yolol")
		verify(src, times(2)).notifyChange()
	}

	@Test
	fun called_once_for_same_strings() {
		val src = spy(ObservableField<String>())

		src.set("Yolo")
		verify(src).notifyChange()

		src.set("Yolo")
		verify(src).notifyChange()
	}
}
