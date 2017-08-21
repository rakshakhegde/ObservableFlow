package me.rakshakhegde.rxdatabinding

import android.databinding.ObservableField
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import me.rakshakhegde.observableflow.onPropertyChanged
import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by rakshakhegde on 24/05/17.
 */
class ToFieldTest {

	@Test
	fun verify_toField_takes_the_value() {

		var emitter: ObservableEmitter<String>? = null

		val dest = Observable.create<String> { emitter = it }
				.toField()

		assertEquals(expected = null, actual = dest.get()) // not yet subscribed

		val listener: ObservableField<String>.(propertyId: Int) -> Unit = mock()
		val onPropertyChangedCallback = dest.onPropertyChanged(listener)

		emitter!!.onNext("First")

		verify(listener).invoke(dest, 0)
		assertEquals(expected = "First", actual = dest.get())

		emitter!!.onNext("Second")
		verify(listener, times(2)).invoke(dest, 0)
		assertEquals(expected = "Second", actual = dest.get())

		dest.removeOnPropertyChangedCallback(onPropertyChangedCallback)

		emitter!!.onNext("Third")
		verifyNoMoreInteractions(listener)
		assertEquals(expected = "Second", actual = dest.get())
	}

	@Test
	fun verify_toField_sends_events_to_multiple_subscribers() {

		var emitter: ObservableEmitter<String>? = null

		val obsField = Observable.create<String> { emitter = it }
				.toField()

		val listener1: ObservableField<String>.(propertyId: Int) -> Unit = mock()
		val callback1 = obsField.onPropertyChanged(listener1)

		val listener2: ObservableField<String>.(propertyId: Int) -> Unit = mock()
		val callback2 = obsField.onPropertyChanged(listener2)

		emitter!!.onNext("First")

		verify(listener1).invoke(obsField, 0)
		verify(listener2).invoke(obsField, 0)
		assertEquals(expected = "First", actual = obsField.get())

		obsField.removeOnPropertyChangedCallback(callback1)

		emitter!!.onNext("Second")
		verifyNoMoreInteractions(listener1)
		verify(listener2, times(2)).invoke(obsField, 0)
		assertEquals(expected = "Second", actual = obsField.get())

		obsField.removeOnPropertyChangedCallback(callback2)

		emitter!!.onNext("Third")
		verifyNoMoreInteractions(listener2)
		assertEquals(expected = "Second", actual = obsField.get())

	}
}
