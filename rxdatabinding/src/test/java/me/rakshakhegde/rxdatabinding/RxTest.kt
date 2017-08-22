package me.rakshakhegde.rxdatabinding

import android.databinding.ObservableField
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.functions.Consumer
import org.junit.Test
import org.mockito.Mockito.verify

/**
 * Created by rakshakhegde on 31/05/17.
 */

class RxTest {

	@Test
	fun checkIfRxEmitsEvent() {
		val nameObservable = ObservableField("First")
		val onNext: Consumer<String> = mock()

		nameObservable.rx().subscribe(onNext)
		verify(onNext).accept("First")

		nameObservable.set("Second")
		verify(onNext).accept("Second")
	}
}
