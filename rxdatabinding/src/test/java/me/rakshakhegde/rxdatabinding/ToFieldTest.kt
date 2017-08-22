package me.rakshakhegde.rxdatabinding

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
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

		val onPropertyChangedCallback: android.databinding.Observable.OnPropertyChangedCallback = mock()
		dest.addOnPropertyChangedCallback(onPropertyChangedCallback)

		emitter!!.onNext("First")

		verify(onPropertyChangedCallback).onPropertyChanged(dest, 0)
		assertEquals(expected = "First", actual = dest.get())

		emitter!!.onNext("Second")
		verify(onPropertyChangedCallback, times(2)).onPropertyChanged(dest, 0)
		assertEquals(expected = "Second", actual = dest.get())

		dest.removeOnPropertyChangedCallback(onPropertyChangedCallback)

		emitter!!.onNext("Third")
		verifyNoMoreInteractions(onPropertyChangedCallback)
		assertEquals(expected = "Second", actual = dest.get())
	}

	@Test
	fun verify_toField_sends_events_to_multiple_subscribers() {

		var emitter: ObservableEmitter<String>? = null

		val obsField = Observable.create<String> { emitter = it }
				.toField()

		val callback1: android.databinding.Observable.OnPropertyChangedCallback = mock()
		obsField.addOnPropertyChangedCallback(callback1)

		val callback2: android.databinding.Observable.OnPropertyChangedCallback = mock()
		obsField.addOnPropertyChangedCallback(callback2)

		emitter!!.onNext("First")

		verify(callback1).onPropertyChanged(obsField, 0)
		verify(callback2).onPropertyChanged(obsField, 0)
		assertEquals(expected = "First", actual = obsField.get())

		obsField.removeOnPropertyChangedCallback(callback1)

		emitter!!.onNext("Second")
		verifyNoMoreInteractions(callback1)
		verify(callback2, times(2)).onPropertyChanged(obsField, 0)
		assertEquals(expected = "Second", actual = obsField.get())

		obsField.removeOnPropertyChangedCallback(callback2)

		emitter!!.onNext("Third")
		verifyNoMoreInteractions(callback2)
		assertEquals(expected = "Second", actual = obsField.get())

	}
}
