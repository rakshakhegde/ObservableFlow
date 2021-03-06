package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import android.databinding.ObservableInt
import org.junit.Test
import kotlin.test.assertEquals

class BindVarargTest {

	/**
	 * @see OnPropertyChangedVarargTest.verify_onPropertyChanged_callback_is_called_at_right_times
	 */
	@Test
	fun verify_bind_callback_is_called_at_right_times() {
		val src1 = ObservableField("Hello")
		val src2 = ObservableInt(8)
		val src3 = ObservableField("Hello")
		val dst = ObservableField<String>()

		bind(src1, src2, src3) {
			dst.set(src1.get() + src2.get() + src3.get())
		}

		assertEquals(expected = "Hello8Hello", actual = dst.get())

		src1.set("World")
		assertEquals(expected = "World8Hello", actual = dst.get())

		src2.set(16)
		assertEquals(expected = "World16Hello", actual = dst.get())

		src3.set("Cats")
		assertEquals(expected = "World16Cats", actual = dst.get())
	}
}
