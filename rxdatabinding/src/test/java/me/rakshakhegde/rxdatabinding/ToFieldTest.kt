package me.rakshakhegde.rxdatabinding

import android.databinding.ObservableField
import io.reactivex.Observable
import me.rakshakhegde.observableflow.onPropertyChanged
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by rakshakhegde on 24/05/17.
 */
class ToFieldTest {

	@Test
	fun check_if_toField_takes_the_value() {
		val result = Observable.just(123)
				.toField(-1)

		assertEquals(-1, result.get())

		// simulate xml binding
		result.onPropertyChanged { }

		assertEquals(123, result.get())
	}

	@Test
	fun check_if_toField_set_does_not_work() {
		val result: ObservableField<Int> = Observable.just(123)
				.toField(-1)

		// simulate xml binding
		result.onPropertyChanged { }

		result.set(45)
		assertEquals(123, result.get())
	}
}
