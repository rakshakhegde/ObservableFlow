package me.rakshakhegde.rxdatabinding

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import me.rakshakhegde.observableflow.onPropertyChanged
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by rakshakhegde on 24/05/17.
 */
class MultiRxBindTest {

	@Test
	fun bind_only_1_observable() {
		val src = ObservableField<String>("Rust")

		val result: ObservableField<String> = rxbind(src)
				.map { "Fe" + src.get() }
				.toField("Iron")

		assertEquals(expected = "Iron", actual = result.get())

		// simulate binding in xml because ReadOnlyField will only subscribe in addOnPropertyChangedCallback
		result.onPropertyChanged { }

		assertEquals(expected = "FeRust", actual = result.get())
	}

	@Test
	fun check_if_updates_for_1_observable() {
		val src = ObservableField<String>("Rust")

		val result: ObservableField<String> = rxbind(src)
				.map { "Fe" + src.get() }
				.toField("Iron")

		// simulate binding in xml because ReadOnlyField will only subscribe in addOnPropertyChangedCallback
		result.onPropertyChanged { }

		assertEquals(expected = "FeRust", actual = result.get())

		src.set("Water")
		assertEquals(expected = "FeWater", actual = result.get())
	}

	@Test
	fun ensure_removes_callback_for_1_observable() {
		val src = ObservableField<String>("Rust")

		val result: ObservableField<String> = rxbind(src)
				.map { "Fe" + src.get() }
				.toField("Iron")

		// simulate binding in xml because ReadOnlyField will only subscribe in addOnPropertyChangedCallback
		val callback = result.onPropertyChanged { }
		result.removeOnPropertyChangedCallback(callback)

		src.set("Water")
		// expect old result even after updating
		assertEquals(expected = "FeRust", actual = result.get())
	}

	@Test
	fun bind_multi_observables() {
		val src1 = ObservableField<String>("Rust")
		val src2 = ObservableBoolean()
		val src3 = ObservableInt()

		val result: ObservableField<String> = rxbind(src1, src2, src3)
				.map { src1.get() + src2.get() + src3.get() }
				.toField("Iron")

		// simulate binding in xml because ReadOnlyField will only subscribe in addOnPropertyChangedCallback
		result.onPropertyChanged { }

		assertEquals(expected = "Rustfalse0", actual = result.get())

		src1.set("Haem")
		assertEquals(expected = "Haemfalse0", actual = result.get())

		src2.set(true)
		assertEquals(expected = "Haemtrue0", actual = result.get())

		src3.set(-1)
		assertEquals(expected = "Haemtrue-1", actual = result.get())

	}

}
