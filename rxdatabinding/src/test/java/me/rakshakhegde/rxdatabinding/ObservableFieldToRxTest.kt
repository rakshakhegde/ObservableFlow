package me.rakshakhegde.rxdatabinding

import android.databinding.ObservableField
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.Test
import org.mockito.Mockito.*

/**
 * Created by rakshakhegde on 31/05/17.
 */

class ObservableFieldToRxTest {

	@Test
	fun checkIfRxEmitsEvent() {
		val nameObservable = ObservableField("Jaka")
		val consumer: Consumer<String> = mock()

		rx(nameObservable).subscribe(consumer)

		verify(consumer).accept(anyString())

		nameObservable.set("Mayfield")

		verify(consumer, times(2)).accept(anyString())
	}

	@Test
	fun checkIfRxEmitsEventOnSwitchMap() {
		val nameObservable = ObservableField("Jaka")
		val consumer: Consumer<Int> = mock()

		rx(nameObservable)
				.switchMapSingle { Single.just(-1) }
				.subscribe(consumer)

		verify(consumer).accept(-1)

		nameObservable.set("Mayfield")

		verify(consumer, times(2)).accept(-1)
	}
}
