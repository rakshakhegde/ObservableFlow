package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import android.databinding.ObservableInt
import org.junit.Test
import org.mockito.Mockito.*
import kotlin.test.assertEquals

class MultiBindTest {

	@Test
	fun checkIfSingleObservableIntBinds() {
		val src = ObservableInt(1)
		val dst = spy(ObservableInt(0))
		bind(src) {
			dst.set(11)
		}
		verify(dst).set(anyInt())
		assertEquals(11, dst.get())
	}

	@Test
	fun checkIfSingleObservableIntUpdates() {
		val src = ObservableInt(1)
		val dst = ObservableInt(0)
		bind(src) {
			dst.set(src.get() + 1)
		}
		assertEquals(2, dst.get())

		src.set(5)
		assertEquals(6, dst.get())
	}

	@Test
	fun check_If_Multiple_Types_Of_Observables_Bind() {
		val src1 = ObservableInt(1)
		val src2 = ObservableField<String>("Yolo")
		val dst = ObservableField<String>()
		bind(src1, src2) {
			dst.set(src2.get() + src1.get())
		}
		assertEquals("Yolo1", dst.get())
	}

	@Test
	fun check_If_Multiple_Types_Of_Observables_Updates() {
		val src1 = ObservableInt(1)
		val src2 = ObservableField<String>("Yolo")

		val dst = spy(ObservableField<String>())

		bind(src1, src2) {
			dst.set(src2.get() + src1.get())
		}

		verify(dst).set(anyString())
		assertEquals("Yolo1", dst.get())

		src1.set(5)
		verify(dst, times(2)).set(anyString())
		assertEquals("Yolo5", dst.get())

		src2.set("Hola")
		verify(dst, times(3)).set(anyString())
		assertEquals("Hola5", dst.get())
	}

	@Test(expected = IllegalArgumentException::class)
	fun throws_for_non_observable_type() {
		val integer = 0

		bind(integer) {}
	}
}
