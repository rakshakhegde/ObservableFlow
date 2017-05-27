package me.rakshakhegde.observableflow

import android.databinding.ObservableArrayList
import android.databinding.ObservableList
import android.databinding.ObservableList.OnListChangedCallback
import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.mockito.Mockito.times
import org.mockito.Mockito.verify


/**
 * Created by rakshakhegde on 27/05/17.
 */
class ObservableArrayListTest {

	@Test
	fun checkIfListNotifiesOnSetAtAParticularIndex() {
		val list = observableListOf("Rakshak", "", "Hegde")
		val listener: OnListChangedCallback<ObservableList<String>> = mock()
		list.addOnListChangedCallback(listener)

		list[1] = "R"

		verify(listener).onItemRangeChanged(list, 1, 1)
	}

	@Test
	fun listListenerGetsCalledWhenSetAtAParticularIndex() {
		val list = observableListOf("Rakshak", "", "Hegde")
		val listener: ObservableArrayList<String>.() -> Unit = mock()
		list.bindToList(listener)

		verify(listener).invoke(list)

		list[1] = "R"

		verify(listener, times(2)).invoke(list)
	}
}
