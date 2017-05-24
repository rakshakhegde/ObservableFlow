package me.rakshakhegde.observableflow

import android.databinding.ObservableField
import android.databinding.ObservableInt
import org.junit.Test
import kotlin.test.assertEquals

class MultiBindTest {

	@Test
	fun checkIfSingleObservableIntBinds() {
		val src = ObservableInt(1)
		val dst = ObservableInt(0)
		bind(src) {
			dst.set(11)
		}
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
		val dst = ObservableField<String>()
		bind(src1, src2) {
			dst.set(src2.get() + src1.get())
		}
		assertEquals("Yolo1", dst.get())

		src1.set(5)
		assertEquals("Yolo5", dst.get())

		src2.set("Hola")
		assertEquals("Hola5", dst.get())
	}
}
