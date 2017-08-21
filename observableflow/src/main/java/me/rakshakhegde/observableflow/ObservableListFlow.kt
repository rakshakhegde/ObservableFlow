package me.rakshakhegde.observableflow

import android.databinding.ObservableArrayList
import android.databinding.ObservableList

/**
 * Created by rakshakhegde on 27/05/17.
 */
fun <T> observableListOf(vararg items: T): ObservableArrayList<T> =
		ObservableArrayList<T>().apply { addAll(items) }

inline fun <T, S : ObservableList<T>> S.bindToList(crossinline listener: S.() -> Unit): ObservableList.OnListChangedCallback<S> {
	// Call the callback as soon as listener is hooked up
	listener()
	return onListChanged(listener)
}

inline fun <T, S : ObservableList<T>> S.onListChanged(crossinline listener: S.() -> Unit): ObservableList.OnListChangedCallback<S> {
	val onListChangedCallback = object : ObservableList.OnListChangedCallback<S>() {

		override fun onChanged(sender: S) {
			listener()
		}

		override fun onItemRangeInserted(sender: S, positionStart: Int, itemCount: Int) {
			listener()
		}

		override fun onItemRangeRemoved(sender: S, positionStart: Int, itemCount: Int) {
			listener()
		}

		override fun onItemRangeMoved(sender: S, fromPosition: Int, toPosition: Int, itemCount: Int) {
			listener()
		}

		override fun onItemRangeChanged(sender: S, positionStart: Int, itemCount: Int) {
			listener()
		}
	}
	addOnListChangedCallback(onListChangedCallback)
	return onListChangedCallback
}
